package br.com.csl.lynx.generic;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.api.DataFilter;
import br.com.csl.lynx.api.SiepFilter;
import br.com.csl.lynx.model.MovimentacaoSiep;
import br.com.csl.lynx.model.Siep;

public class AbstractSiepFilter extends AbstractSiepInfo implements SiepFilter {

	private static final long serialVersionUID = 1L;
	
	protected DataFilter filter;
	
	private String protocolo;
	
	private Integer siepCount;
	private Integer filteredSiepCount;  
	
	public void clear() {
		super.clear();
		
		clearFilter();
	}
	
	@Override
	public void clearFilter() {
		if (filter != null) {
			filter.clear();
			filter();
		}
		protocolo = "";
	}
	
	@Override
	public void filter() {
		if (filter.getFilterClass().equals(Siep.class)) {
			siepDataModel.setRestraints(filter.getFilters());
		}
		else if (filter.getFilterClass().equals(MovimentacaoSiep.class))
			movimentacaoDataModel.setRestraints(filter.getFilters());
	}

	@Override
	public void updateCount() {
		if (filter.getFilterClass().equals(Siep.class))
			siepCount = siepService.count(filter.getPrimaryFilter());
		else if (filter.getFilterClass().equals(MovimentacaoSiep.class))
			siepCount = movimentacaoService.count(filter.getPrimaryFilter());
		
		if (!filter.getFilterStatus())
			return;
		
		Conjunction conjunction = new Conjunction();
		for (Criterion aux : filter.getFilters()) {
			conjunction.add(aux);
		}
		
		if (filter.getFilterClass().equals(Siep.class))
			filteredSiepCount = siepService.count(conjunction);
		else if (filter.getFilterClass().equals(MovimentacaoSiep.class))
			filteredSiepCount = movimentacaoService.count(conjunction);
	}
	
	public void find() {
		protocolo = protocolo.replace("/", "").replace("-", "").replace("_", "");
		if (protocolo.isEmpty()) {
			addFacesErrorMessage("Número de protocolo inválido.");
			return;
		}
		Siep result = null;
		
		if (filter.getFilterClass().equals(Siep.class))
			result = siepService.find(Restrictions.and(Restrictions.eq("id", Long.parseLong(protocolo)), filter.getPrimaryFilter()));
		else if (filter.getFilterClass().equals(MovimentacaoSiep.class))
			result = siepService.find(Long.parseLong(protocolo));
		
		if (result == null && siepService.count(Restrictions.eq("id", Long.parseLong(protocolo))) == 1) {
			addFacesErrorMessage("Siep não está disponível para visualização no momento.");
			protocolo = "";
			return;
		} else if (result == null) {
			addFacesErrorMessage("Siep não encontrada.");
			return;
		} else {
			siep = result;
			protocolo = "";
			load();
		}
	}
	
	@Override
	public List<Siep> getFilteredSieps() {
		List<Siep> sieps = new ArrayList<Siep>();
		
		if (!filter.getFilterStatus())
			return sieps;
		
		Conjunction conjunction = new Conjunction();
		for (Criterion aux : filter.getFilters()) {
			conjunction.add(aux);
		}
		
		if (filter.getFilterClass().equals(Siep.class))
			sieps = siepService.listAliased(conjunction, Order.desc("id"));
		else if (filter.getFilterClass().equals(MovimentacaoSiep.class))
			for (MovimentacaoSiep aux : movimentacaoService.listAliased(conjunction, Order.desc("id")))
				if (!sieps.contains(aux.getSiep()))
					sieps.add(aux.getSiep());
		
		return sieps;
	}
	
	@Override
	public List<Siep> getAllSieps() {
		List<Siep> sieps = new ArrayList<Siep>();
		
		if (filter.getFilterClass().equals(Siep.class))
			sieps = siepService.list(filter.getPrimaryFilter(), Order.desc("id"));
		else if (filter.getFilterClass().equals(MovimentacaoSiep.class))			
			for (MovimentacaoSiep aux : movimentacaoService.list(filter.getPrimaryFilter(), Order.desc("id")))
				if (!sieps.contains(aux.getSiep()))
					sieps.add(aux.getSiep());
		
		return sieps;
	}

	@Override
	public Integer getSiepCount() {
		return siepCount;
	}
	
	@Override
	public Integer getFilteredSiepCount() {
		if (!filter.getFilterStatus())
			return 0;
		
		return filteredSiepCount;
	}

	@Override
	public DataFilter getFilter() {
		return filter;
	}

	public String getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

}
