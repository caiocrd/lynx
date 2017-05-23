package br.com.csl.utils.data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.primefaces.model.SelectableDataModel;
import org.primefaces.model.SortMeta;

import br.com.csl.utils.entity.PersistentEntity;

public interface DataModel<T extends PersistentEntity> extends SelectableDataModel<T>, Serializable {

	DataService<T> getDataService();

	void setDataService(DataService<T> dataService);

	Class<?> getIdClass();

	void setIdClass(Class<?> idClass);

	List<Criterion> getRestraints();

	void setRestraints(List<Criterion> restraints);

	void addRestraint(String field, Object value);

	void removeRestraints();

	void newRestraints(String field, Object value);

	Integer count(Criterion criterion);

	List<T> paginatedList(Integer startingAt, Integer maxPerPage, Criterion criterion, List<Order> orders);

	T find(Serializable id);
	List<T> load(int first, int pageSize, List<SortMeta> multiSortMeta,	Map<String, String> filters);

}
