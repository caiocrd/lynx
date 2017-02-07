package br.com.csl.utils.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;

import br.com.csl.utils.entity.PersistentEntity;
import br.com.csl.utils.exception.DataAccessException;

public interface DataAccess<T extends PersistentEntity> extends Serializable {
	
	Class<T> getPersistentClass();
	
	EntityManager getEntityManager();
	Session getSession();
	
	T find(final Serializable id);
	T find(final Example example, final ProjectionList projections, final HashMap<String, String> aliases);
	T find(final Criterion criterion, final ProjectionList projections, final HashMap<String, String> aliases);
	
	List<T> list(final List<Order> order, final ProjectionList projections, final HashMap<String, String> aliases);
	List<T> list(final Example example, final List<Order> order, final ProjectionList projections, final HashMap<String, String> aliases);
	List<T> list(final Criterion criterion, final List<Order> order, final ProjectionList projections, final HashMap<String, String> aliases);
	List<T> list(final Integer startingAt, final Integer maxPerPage, final List<Order> order, final ProjectionList projections, final HashMap<String, String> aliases);
	List<T> list(final Integer startingAt, final Integer maxPerPage, final Criterion criterion, final List<Order> order, final ProjectionList projections, final HashMap<String, String> aliases);
	List<T> list(final Integer startingAt, final Integer maxPerPage, final List<Criterion> criterions, final List<Order> order,	final ProjectionList projections, final HashMap<String, String> aliases);
	
	Integer count(final Criterion criterion, final HashMap<String, String> aliases);
	
	T save(final T entity) throws DataAccessException;
	void persist(final T entity) throws DataAccessException;
	void remove(final T entity) throws DataAccessException;
	void remove(final Serializable id) throws DataAccessException;


	
}
