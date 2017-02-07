package br.com.csl.lynx.filter;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.csl.lynx.api.DataFilter;
import br.com.csl.lynx.handler.EnderecoHandler;
import br.com.csl.lynx.model.Rip;

public class AddressFilter implements DataFilter {
	
	private static final long serialVersionUID = 1L;
	
	private EnderecoHandler enderecoHandler;
	
	private Boolean filterStatus;
	
	private Class<?> filterClass;
	
	private Criterion primaryFilter;

	public AddressFilter(Criterion primaryFilter, EnderecoHandler enderecoHandler) {
		this.enderecoHandler = enderecoHandler;
		this.primaryFilter = primaryFilter;
		filterClass = Rip.class;
		clear();
	}
	
	public void clear() {		
		filterStatus = false;
	}
	
	@Override
	public List<Criterion> getFilters() {
		List<Criterion> filters = new ArrayList<>();
		if (filterClass.equals(Rip.class)) {
	
			filters.add(primaryFilter);
			
			filterStatus = false;
			
			if (enderecoHandler.getEndereco() != null)
				filters.add(Restrictions.eq("endereco", enderecoHandler.getEndereco()));
			else {
				if (enderecoHandler.getLogradouro() != null)
					filters.add(Restrictions.eq("e.fk.logradouro", enderecoHandler.getLogradouro()));

				if (enderecoHandler.getBairro() != null) 
					filters.add(Restrictions.eq("e.fk.bairro", enderecoHandler.getBairro()));
				
				if (enderecoHandler.getZonaNome() != null && enderecoHandler.getZonaNome() != null)
					filters.add(Restrictions.eq("bairro.zona", enderecoHandler.getZonaNome()));

				if (enderecoHandler.getConjunto() != null)
					filters.add(Restrictions.eq("e.conjunto",	enderecoHandler.getConjunto()));

			}
		}
		return filters;
	}

	public Boolean getFilterStatus() {
		return filterStatus;
	}

	public Class<?> getFilterClass() {
		return filterClass;
	}

	public Criterion getPrimaryFilter() {
		return primaryFilter;
	}

	public void setEnderecoHandler(EnderecoHandler enderecoHandler) {
		this.enderecoHandler = enderecoHandler;
	}

}
