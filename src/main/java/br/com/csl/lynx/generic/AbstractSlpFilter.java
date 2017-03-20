package br.com.csl.lynx.generic;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.api.DataFilter;
import br.com.csl.lynx.api.SlpFilter;
import br.com.csl.lynx.model.Movimentacao;
import br.com.csl.lynx.model.MovimentacaoSlp;
import br.com.csl.lynx.model.Rip;
import br.com.csl.lynx.model.Slp;

public class AbstractSlpFilter extends AbstractSlpInfo implements SlpFilter {

	private static final long serialVersionUID = 1L;
	
	protected DataFilter filter;
	
	private String protocolo;
	
	private Integer slpCount;
	private Integer filteredSlpCount;  
	
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
			slpDataModel.setRestraints(filter.getFilters());
		}
		else if (filter.getFilterClass().equals(Movimentacao.class))
			movimentacaoDataModel.setRestraints(filter.getFilters());
	}

	@Override
	public void updateCount() {
		if (filter.getFilterClass().equals(Rip.class))
			slpCount = slpService.count(filter.getPrimaryFilter());
		else if (filter.getFilterClass().equals(Movimentacao.class))
			slpCount = movimentacaoService.count(filter.getPrimaryFilter());
		
		if (!filter.getFilterStatus())
			return;
		
		Conjunction conjunction = new Conjunction();
		for (Criterion aux : filter.getFilters()) {
			conjunction.add(aux);
		}
		
		if (filter.getFilterClass().equals(Rip.class))
			filteredSlpCount = slpService.count(conjunction);
		else if (filter.getFilterClass().equals(Movimentacao.class))
			filteredSlpCount = movimentacaoService.count(conjunction);
	}
	
	public void find() {
		protocolo = protocolo.replace("/", "").replace("-", "").replace("_", "");
		if (protocolo.isEmpty()) {
			addFacesErrorMessage("Número de protocolo inválido.");
			return;
		}
		Slp result = null;
		
		if (filter.getFilterClass().equals(Rip.class))
			result = slpService.find(Restrictions.and(Restrictions.eq("id", Long.parseLong(protocolo)), filter.getPrimaryFilter()));
		else if (filter.getFilterClass().equals(Movimentacao.class))
			result = slpService.find(Long.parseLong(protocolo));
		
		if (result == null && slpService.count(Restrictions.eq("id", Long.parseLong(protocolo))) == 1) {
			addFacesErrorMessage("Slp não está disponível para visualização no momento.");
			protocolo = "";
			return;
		} else if (result == null) {
			addFacesErrorMessage("Slp não encontrada.");
			return;
		} else {
			slp = result;
			protocolo = "";
			load();
		}
	}
	
	@Override
	public List<Slp> getFilteredSlps() {
		List<Slp> slps = new ArrayList<Slp>();
		
		if (!filter.getFilterStatus())
			return slps;
		
		Conjunction conjunction = new Conjunction();
		for (Criterion aux : filter.getFilters()) {
			conjunction.add(aux);
		}
		
		if (filter.getFilterClass().equals(Rip.class))
			slps = slpService.listAliased(conjunction, Order.desc("id"));
		else if (filter.getFilterClass().equals(Movimentacao.class))
			for (MovimentacaoSlp aux : movimentacaoService.listAliased(conjunction, Order.desc("id")))
				if (!slps.contains(aux.getSlp()))
					slps.add(aux.getSlp());
		
		return slps;
	}
	
	@Override
	public List<Slp> getAllSlps() {
		List<Slp> slps = new ArrayList<Slp>();
		
		if (filter.getFilterClass().equals(Rip.class))
			slps = slpService.list(filter.getPrimaryFilter(), Order.desc("id"));
		else if (filter.getFilterClass().equals(Movimentacao.class))			
			for (MovimentacaoSlp aux : movimentacaoService.list(filter.getPrimaryFilter(), Order.desc("id")))
				if (!slps.contains(aux.getSlp()))
					slps.add(aux.getSlp());
		
		return slps;
	}

	@Override
	public Integer getSlpCount() {
		return slpCount;
	}
	
	@Override
	public Integer getFilteredSlpCount() {
		if (!filter.getFilterStatus())
			return 0;
		
		return filteredSlpCount;
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
