package br.com.csl.lynx.generic;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.api.DataFilter;
import br.com.csl.lynx.api.SvpaFilter;
import br.com.csl.lynx.model.Movimentacao;
import br.com.csl.lynx.model.MovimentacaoSvpa;
import br.com.csl.lynx.model.Rip;
import br.com.csl.lynx.model.Svpa;

public class AbstractSvpaFilter extends AbstractSvpaInfo implements SvpaFilter {

	private static final long serialVersionUID = 1L;
	
	protected DataFilter filter;
	
	private String protocolo;
	
	private Integer svpaCount;
	private Integer filteredSvpaCount;  
	
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
		if (filter.getFilterClass().equals(Rip.class)) {
			svpaDataModel.setRestraints(filter.getFilters());
		}
		else if (filter.getFilterClass().equals(Movimentacao.class))
			movimentacaoDataModel.setRestraints(filter.getFilters());
	}

	@Override
	public void updateCount() {
		if (filter.getFilterClass().equals(Rip.class))
			svpaCount = svpaService.count(filter.getPrimaryFilter());
		else if (filter.getFilterClass().equals(Movimentacao.class))
			svpaCount = movimentacaoService.count(filter.getPrimaryFilter());
		
		if (!filter.getFilterStatus())
			return;
		
		Conjunction conjunction = new Conjunction();
		for (Criterion aux : filter.getFilters()) {
			conjunction.add(aux);
		}
		
		if (filter.getFilterClass().equals(Rip.class))
			filteredSvpaCount = svpaService.count(conjunction);
		else if (filter.getFilterClass().equals(Movimentacao.class))
			filteredSvpaCount = movimentacaoService.count(conjunction);
	}
	
	public void find() {
		protocolo = protocolo.replace("/", "").replace("-", "").replace("_", "");
		if (protocolo.isEmpty()) {
			addFacesErrorMessage("Número de protocolo inválido.");
			return;
		}
		Svpa result = null;
		
		if (filter.getFilterClass().equals(Rip.class))
			result = svpaService.find(Restrictions.and(Restrictions.eq("id", Long.parseLong(protocolo)), filter.getPrimaryFilter()));
		else if (filter.getFilterClass().equals(Movimentacao.class))
			result = svpaService.find(Long.parseLong(protocolo));
		
		if (result == null && svpaService.count(Restrictions.eq("id", Long.parseLong(protocolo))) == 1) {
			addFacesErrorMessage("Svpa não está disponível para visualização no momento.");
			protocolo = "";
			return;
		} else if (result == null) {
			addFacesErrorMessage("Svpa não encontrada.");
			return;
		} else {
			svpa = result;
			protocolo = "";
			load();
		}
	}
	
	@Override
	public List<Svpa> getFilteredSvpas() {
		List<Svpa> svpas = new ArrayList<Svpa>();
		
		if (!filter.getFilterStatus())
			return svpas;
		
		Conjunction conjunction = new Conjunction();
		for (Criterion aux : filter.getFilters()) {
			conjunction.add(aux);
		}
		
		if (filter.getFilterClass().equals(Rip.class))
			svpas = svpaService.listAliased(conjunction, Order.desc("id"));
		else if (filter.getFilterClass().equals(Movimentacao.class))
			for (MovimentacaoSvpa aux : movimentacaoService.listAliased(conjunction, Order.desc("id")))
				if (!svpas.contains(aux.getSvpa()))
					svpas.add(aux.getSvpa());
		
		return svpas;
	}
	
	@Override
	public List<Svpa> getAllSvpas() {
		List<Svpa> svpas = new ArrayList<Svpa>();
		
		if (filter.getFilterClass().equals(Rip.class))
			svpas = svpaService.list(filter.getPrimaryFilter(), Order.desc("id"));
		else if (filter.getFilterClass().equals(Movimentacao.class))			
			for (MovimentacaoSvpa aux : movimentacaoService.list(filter.getPrimaryFilter(), Order.desc("id")))
				if (!svpas.contains(aux.getSvpa()))
					svpas.add(aux.getSvpa());
		
		return svpas;
	}

	@Override
	public Integer getSvpaCount() {
		return svpaCount;
	}
	
	@Override
	public Integer getFilteredSvpaCount() {
		if (!filter.getFilterStatus())
			return 0;
		
		return filteredSvpaCount;
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
