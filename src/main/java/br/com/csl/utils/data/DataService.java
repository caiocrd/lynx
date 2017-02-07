package br.com.csl.utils.data;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

import br.com.csl.utils.entity.PersistentEntity;
import br.com.csl.utils.exception.ServiceException;

public interface DataService<T extends PersistentEntity> extends Serializable {

	DataAccess<T> getDAO();
	
	T find(final T example);
	T find(final Serializable id);
	T find(final Criterion criterion);
	T find(final String field, final Object value);
	
	List<T> list();
	List<T> list(final Order order);
	List<T> list(final Criterion criterion);
	List<T> list(final Criterion criterion, final Order order);
	List<T> list(final String field, final Object value, final Order order);
	List<T> list(final Integer startingAt, final Integer maxPerPage, final Order order);
	List<T> list(final Integer startingAt, final Integer maxPerPage, final Criterion criterion, final Order order);
	List<T> list(final Integer startingAt, final Integer maxPerPage, final Criterion criterion, final List<Order> order);
	List<T> list(final Integer startingAt, final Integer maxPerPage, final List<Criterion> criterions, final List<Order> order);
	
	T findAliased(final Criterion criterion);
	
	List<T> listAliased();
	List<T> listAliased(final Order order);
	List<T> listAliased(final Criterion criterion);
	List<T> listAliased(final Criterion criterion, final Order order);
	List<T> listAliased(final String field, final Object value, final Order order);
	List<T> listAliased(final Integer startingAt, final Integer maxPerPage, final Order order);
	List<T> listAliased(final Integer startingAt, final Integer maxPerPage, final Criterion criterion, final Order order);
	List<T> listAliased(final Integer startingAt, final Integer maxPerPage, final Criterion criterion, final List<Order> order);
	List<T> listAliased(final Integer startingAt, final Integer maxPerPage,	final List<Criterion> criterias, final List<Order> order);

	T findProjected(final Criterion criterion);
	
	List<T> listProjected();
	List<T> listProjected(final Order order);
	List<T> listProjected(final Criterion criterion);
	List<T> listProjected(final Criterion criterion, final Order order);
	List<T> listProjected(final String field, final Object value, final Order order);
	List<T> listProjected(final Integer startingAt, final Integer maxPerPage, final Order order);
	List<T> listProjected(final Integer startingAt, final Integer maxPerPage, final Criterion criterion, final Order order);
	List<T> listProjected(final Integer startingAt, final Integer maxPerPage, final Criterion criterion, final List<Order> order);
	List<T> listProjected(final Integer startingAt, final Integer maxPerPage, final List<Criterion> criterions, final List<Order> order);

	T findProjectedAndAliased(final Criterion criterion);

	List<T> listProjectedAndAliased();
	List<T> listProjectedAndAliased(final Order order);
	List<T> listProjectedAndAliased(final Criterion criterion);
	List<T> listProjectedAndAliased(final Criterion criterion, final Order order);
	List<T> listProjectedAndAliased(final String field, final Object value, final Order order);
	List<T> listProjectedAndAliased(final Integer startingAt, final Integer maxPerPage, final Order order);
	List<T> listProjectedAndAliased(final Integer startingAt, final Integer maxPerPage, final Criterion criterion, final Order order);
	List<T> listProjectedAndAliased(final Integer startingAt, final Integer maxPerPage, final Criterion criterion, final List<Order> order);
	List<T> listProjectedAndAliased(final Integer startingAt, final Integer maxPerPage, final List<Criterion> criterions, final List<Order> order);
	
	Integer count();
	Integer count(final Criterion criterion);
	Integer count(final String field, final Object value);

	T save(final T entity) throws ServiceException;
	void persist(final T entity) throws ServiceException;
	void remove(final T entity) throws ServiceException;
	void remove(final Serializable id) throws ServiceException;

}
