package br.com.csl.lynx.generic;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.api.DataFilter;
import br.com.csl.lynx.api.RipFilter;
import br.com.csl.lynx.model.Movimentacao;
import br.com.csl.lynx.model.Rip;

public class AbstractRipFilter extends AbstractRipInfo implements RipFilter {

	private static final long serialVersionUID = 1L;
	
	protected DataFilter filter;
	
	private String protocolo;
	
	private Integer ripCount;
	private Integer filteredRipCount;  
	
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
			ripDataModel.setRestraints(filter.getFilters());
		}
		else if (filter.getFilterClass().equals(Movimentacao.class))
			movimentacaoDataModel.setRestraints(filter.getFilters());
	}

	@Override
	public void updateCount() {
		if (filter.getFilterClass().equals(Rip.class))
			ripCount = ripService.count(filter.getPrimaryFilter());
		else if (filter.getFilterClass().equals(Movimentacao.class))
			ripCount = movimentacaoService.count(filter.getPrimaryFilter());
		
		if (!filter.getFilterStatus())
			return;
		
		Conjunction conjunction = new Conjunction();
		for (Criterion aux : filter.getFilters()) {
			conjunction.add(aux);
		}
		
		if (filter.getFilterClass().equals(Rip.class))
			filteredRipCount = ripService.count(conjunction);
		else if (filter.getFilterClass().equals(Movimentacao.class))
			filteredRipCount = movimentacaoService.count(conjunction);
	}
	
	public void find() {
		protocolo = protocolo.replace("/", "").replace("-", "").replace("_", "");
		if (protocolo.isEmpty()) {
			addFacesErrorMessage("Número de protocolo inválido.");
			return;
		}
		Rip result = null;
		
		if (filter.getFilterClass().equals(Rip.class))
			result = ripService.find(Restrictions.and(Restrictions.eq("id", Long.parseLong(protocolo)), filter.getPrimaryFilter()));
		else if (filter.getFilterClass().equals(Movimentacao.class))
			result = ripService.find(Long.parseLong(protocolo));
		
		if (result == null && ripService.count(Restrictions.eq("id", Long.parseLong(protocolo))) == 1) {
			addFacesErrorMessage("Rip não está disponível para visualização no momento.");
			protocolo = "";
			return;
		} else if (result == null) {
			addFacesErrorMessage("Rip não encontrada.");
			return;
		} else {
			rip = result;
			protocolo = "";
			load();
		}
	}
	
	@Override
	public List<Rip> getFilteredRips() {
		List<Rip> rips = new ArrayList<Rip>();
		
		if (!filter.getFilterStatus())
			return rips;
		
		Conjunction conjunction = new Conjunction();
		for (Criterion aux : filter.getFilters()) {
			conjunction.add(aux);
		}
		
		if (filter.getFilterClass().equals(Rip.class))
			rips = ripService.listAliased(conjunction, Order.desc("id"));
		else if (filter.getFilterClass().equals(Movimentacao.class))
			for (Movimentacao aux : movimentacaoService.listAliased(conjunction, Order.desc("id")))
				if (!rips.contains(aux.getRip()))
					rips.add(aux.getRip());
		
		return rips;
	}
	
	@Override
	public List<Rip> getAllRips() {
		List<Rip> rips = new ArrayList<Rip>();
		
		if (filter.getFilterClass().equals(Rip.class))
			rips = ripService.list(filter.getPrimaryFilter(), Order.desc("id"));
		else if (filter.getFilterClass().equals(Movimentacao.class))			
			for (Movimentacao aux : movimentacaoService.list(filter.getPrimaryFilter(), Order.desc("id")))
				if (!rips.contains(aux.getRip()))
					rips.add(aux.getRip());
		
		return rips;
	}

	@Override
	public Integer getRipCount() {
		return ripCount;
	}
	
	@Override
	public Integer getFilteredRipCount() {
		if (!filter.getFilterStatus())
			return 0;
		
		return filteredRipCount;
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
