package br.com.csl.utils.data.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import br.com.csl.utils.data.DataModel;
import br.com.csl.utils.data.DataService;
import br.com.csl.utils.entity.PersistentEntity;

public abstract class AbstractDataModel<T extends PersistentEntity> extends LazyDataModel<T> implements DataModel<T> {
	
	private static final long serialVersionUID = 1L;
	
	private DataService<T> dataService;
	private List<Order> order;
	private List<Criterion> restraints;
	private HashMap<String, Object> currentRestraints;
	private Class<?> idClass;

	public AbstractDataModel() {
		order = new ArrayList<Order>();
		currentRestraints = new HashMap<String, Object>();
		restraints = new ArrayList<Criterion>();
	}

	@Override
	public DataService<T> getDataService() {
		return dataService;
	}

	@Override
	public void setDataService(DataService<T> dataService) {
		this.dataService = dataService;
	}
	
	@Override
	public Integer count(Criterion criterion) {
		return getDataService().count(criterion);
	}
	
	@Override
	public List<T> paginatedList(Integer startingAt, Integer maxPerPage, Criterion criterion, List<Order> orders) {
		return getDataService().listAliased(startingAt, maxPerPage, criterion, orders);
	}
	
	@Override
	public T find(Serializable id) {
		return getDataService().find(id);
	}
	
	@Override
	public Class<?> getIdClass() {
		return idClass;
	}

	@Override
	public void setIdClass(Class<?> idClass) {
		this.idClass = idClass;
	}
	
	@Override
	public Object getRowKey(T entity) {
		return entity.getId();
	}
	
	@Override
	public T getRowData(String rowKey){
		if (idClass != String.class) {
			try {
				return find((Serializable) idClass.getMethod("valueOf", String.class).invoke(idClass, rowKey));
			} catch (Exception e) {
				return null;
			}
		} else {
			return find(rowKey);
		}
	}
	
	@Override
	public List<T> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String,String> filters) {
		List<T> data = new ArrayList<T>();
		Conjunction conjunction = new Conjunction();
		
		if (filters.size() > 0) {
			
			for (Entry<String, String> entry : filters.entrySet()) {
				if (entry.getKey().equalsIgnoreCase("id")) {
					if (idClass != String.class) {
						try {
							conjunction.add(Restrictions.idEq((Serializable) idClass.getMethod("valueOf", String.class).invoke(idClass, entry.getValue())));
						} catch (Exception e) {};
					} else {
						conjunction.add(Restrictions.idEq(entry.getValue()));
					}
				} else {
					conjunction.add(Restrictions.like(entry.getKey(), (String) entry.getValue(), MatchMode.ANYWHERE));
				}
			}
			
		}
		
		if (this.restraints != null && this.restraints.size() > 0) {
			for (Criterion aux : this.restraints) {
				conjunction.add( aux );
			}
		}
		
		if (multiSortMeta != null) {
			order = new ArrayList<Order>();
			for (SortMeta sortMeta : multiSortMeta) {
				switch (sortMeta.getSortOrder().toString()) {
					case "ASCENDING":
					case "UNSORTED":
						order.add(Order.asc(sortMeta.getSortField()));
					break;

					case "DESCENDING":
						order.add(Order.desc(sortMeta.getSortField()));
					break;
				}
			}
		}
		
		if (filters.size() > 0 || (this.restraints != null && this.restraints.size() > 0)) {
			this.setRowCount(count(conjunction));
			if (Integer.valueOf(first) != null && getRowCount() < first) { first = 0; } 
			data = paginatedList(first, pageSize, conjunction, order);
		} else {
			this.setRowCount(count(null));
			data = paginatedList(first, pageSize, null, order);
		}
		return data;		
	}
	
	@Override
	public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
		List<SortMeta> sortMeta = new ArrayList<SortMeta>();
		if (sortField != null && sortOrder != null) {
			sortMeta.add(new SortMeta(null, sortField, sortOrder, null));
		}
		return load(first, pageSize, sortMeta, filters);
	}

	@Override
	public List<Criterion> getRestraints() {
		return restraints;
	}

	@Override
	public void setRestraints(List<Criterion> restraints) {
		this.restraints = restraints;
	}
	
	@Override
	public void addRestraint(String field, Object value) {
		if (field != null && !field.isEmpty()) {
			if (currentRestraints.containsKey(field)) {
				if (currentRestraints.get(field) != value) {
					currentRestraints.put(field, value);
					removeRestraints();
					for (Entry<String, Object> entry : currentRestraints.entrySet()) {
						restraints.add(Restrictions.eqOrIsNull(entry.getKey(), entry.getValue()));
					}
				}
			} else {
				currentRestraints.put(field, value);
				restraints.add(Restrictions.eqOrIsNull(field, value));
			}
		}
	}
	
	@Override
	public void removeRestraints() {
		currentRestraints = new HashMap<String, Object>();
		restraints = new ArrayList<Criterion>();
	}
	
	@Override
	public void newRestraints(String field, Object value) {
		removeRestraints();
		addRestraint(field, value);
	}

}