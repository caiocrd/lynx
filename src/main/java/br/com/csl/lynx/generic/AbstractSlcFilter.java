package br.com.csl.lynx.generic;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.api.DataFilter;
import br.com.csl.lynx.api.SlcFilter;
import br.com.csl.lynx.model.Movimentacao;
import br.com.csl.lynx.model.MovimentacaoSlc;
import br.com.csl.lynx.model.Rip;
import br.com.csl.lynx.model.Slc;

public class AbstractSlcFilter extends AbstractSlcInfo implements SlcFilter {

	private static final long serialVersionUID = 1L;
	
	protected DataFilter filter;
	
	private String protocolo;
	
	private Integer slcCount;
	private Integer filteredSlcCount;  
	
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
			slcDataModel.setRestraints(filter.getFilters());
		}
		else if (filter.getFilterClass().equals(Movimentacao.class))
			movimentacaoDataModel.setRestraints(filter.getFilters());
	}

	@Override
	public void updateCount() {
		if (filter.getFilterClass().equals(Rip.class))
			slcCount = slcService.count(filter.getPrimaryFilter());
		else if (filter.getFilterClass().equals(Movimentacao.class))
			slcCount = movimentacaoService.count(filter.getPrimaryFilter());
		
		if (!filter.getFilterStatus())
			return;
		
		Conjunction conjunction = new Conjunction();
		for (Criterion aux : filter.getFilters()) {
			conjunction.add(aux);
		}
		
		if (filter.getFilterClass().equals(Rip.class))
			filteredSlcCount = slcService.count(conjunction);
		else if (filter.getFilterClass().equals(Movimentacao.class))
			filteredSlcCount = movimentacaoService.count(conjunction);
	}
	
	public void find() {
		protocolo = protocolo.replace("/", "").replace("-", "").replace("_", "");
		if (protocolo.isEmpty()) {
			addFacesErrorMessage("Número de protocolo inválido.");
			return;
		}
		Slc result = null;
		
		if (filter.getFilterClass().equals(Rip.class))
			result = slcService.find(Restrictions.and(Restrictions.eq("id", Long.parseLong(protocolo)), filter.getPrimaryFilter()));
		else if (filter.getFilterClass().equals(Movimentacao.class))
			result = slcService.find(Long.parseLong(protocolo));
		
		if (result == null && slcService.count(Restrictions.eq("id", Long.parseLong(protocolo))) == 1) {
			addFacesErrorMessage("Slc não está disponível para visualização no momento.");
			protocolo = "";
			return;
		} else if (result == null) {
			addFacesErrorMessage("Slc não encontrada.");
			return;
		} else {
			slc = result;
			protocolo = "";
			load();
		}
	}
	
	@Override
	public List<Slc> getFilteredSlcs() {
		List<Slc> slcs = new ArrayList<Slc>();
		
		if (!filter.getFilterStatus())
			return slcs;
		
		Conjunction conjunction = new Conjunction();
		for (Criterion aux : filter.getFilters()) {
			conjunction.add(aux);
		}
		
		if (filter.getFilterClass().equals(Rip.class))
			slcs = slcService.listAliased(conjunction, Order.desc("id"));
		else if (filter.getFilterClass().equals(Movimentacao.class))
			for (MovimentacaoSlc aux : movimentacaoService.listAliased(conjunction, Order.desc("id")))
				if (!slcs.contains(aux.getSlc()))
					slcs.add(aux.getSlc());
		
		return slcs;
	}
	
	@Override
	public List<Slc> getAllSlcs() {
		List<Slc> slcs = new ArrayList<Slc>();
		
		if (filter.getFilterClass().equals(Rip.class))
			slcs = slcService.list(filter.getPrimaryFilter(), Order.desc("id"));
		else if (filter.getFilterClass().equals(Movimentacao.class))			
			for (MovimentacaoSlc aux : movimentacaoService.list(filter.getPrimaryFilter(), Order.desc("id")))
				if (!slcs.contains(aux.getSlc()))
					slcs.add(aux.getSlc());
		
		return slcs;
	}

	@Override
	public Integer getSlcCount() {
		return slcCount;
	}
	
	@Override
	public Integer getFilteredSlcCount() {
		if (!filter.getFilterStatus())
			return 0;
		
		return filteredSlcCount;
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
