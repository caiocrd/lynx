package br.com.csl.utils.data.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import br.com.csl.utils.data.DataAccess;
import br.com.csl.utils.data.DataService;
import br.com.csl.utils.entity.PersistentEntity;
import br.com.csl.utils.exception.DataAccessException;
import br.com.csl.utils.exception.ServiceException;

@Transactional(rollbackFor = ServiceException.class)
public abstract class AbstractDataService<T extends PersistentEntity> implements DataService<T> {

	private static final long serialVersionUID = 796900199238869189L;

	private DataAccess<T> DAO;
	private HashMap<String, String> aliases;
	private ProjectionList projections;
	
	@Override
	public DataAccess<T> getDAO() {
		return DAO;
	}

	public void setDAO(final DataAccess<T> DAO) {
		this.DAO = DAO;
	}
	
	private List<Order> getOrder(final Order order) {
		List<Order> orderList = null;
		if (order != null) {
			orderList = new ArrayList<Order>();
			orderList.add(order); 
		}
		return orderList;
	}

	/**
	 * Finds an entity with or without alias.
	 * @param example
	 * @return Entity
	 */
	@Override
	public T find(final T example) {
		Example ex = Example.create(example).enableLike().ignoreCase().excludeProperty("id").excludeZeroes();
		return (T) getDAO().find(ex, null, null);
	}
	
	@Override
	public T find(final Serializable id) {
		return (T) getDAO().find(id);
	}
	
	/**
	 * Finds an entity with or without alias.
	 * @param field
	 * @param value
	 * @return Entity
	 */
	@Override
	public T find(final Criterion criterion) {
		return (T) getDAO().find(criterion, null, aliases);
	}
	
	/**
	 * Finds an entity with or without alias.
	 * @param field
	 * @param value
	 * @return Entity
	 */
	@Override
	public T find(final String field, final Object value) {
		return (T) getDAO().find(Restrictions.eq(field, value), null, null);
	}
	
	/**
	 * Finds an entity with or without alias.
	 * @param field
	 * @param value
	 * @return Entity
	 */
	@Override
	public List<T> list() {
		return (List<T>) getDAO().list(null, null, null);
	}

	
	@Override
	public List<T> list(final Order order) {
		return (List<T>) getDAO().list(getOrder(order), null, null);
	}

	@Override
	public List<T> list(final Criterion criterion) {
		return (List<T>) getDAO().list(criterion, null, null, null);
	}
	
	@Override
	public List<T> list(final Criterion criterion, final Order order) {
		return (List<T>) getDAO().list(criterion, getOrder(order), null, null);
	}

	
	@Override
	public List<T> list(final String field, final Object value, final Order order) {
		return (List<T>) getDAO().list(Restrictions.eq(field, value), getOrder(order), null, null);
	}

	@Override
	public List<T> list(final Integer startingAt, final Integer maxPerPage, final Order order) {
		return getDAO().list(startingAt, maxPerPage, getOrder(order), null, null);
	}
	
	@Override
	public List<T> list(final Integer startingAt, final Integer maxPerPage, final Criterion criterion, final Order order) {
		return getDAO().list(startingAt, maxPerPage, criterion, getOrder(order), null, null);
	}
	
	@Override
	public List<T> list(final Integer startingAt, final Integer maxPerPage, final Criterion criterion, final List<Order> order) {
		return getDAO().list(startingAt, maxPerPage, criterion, order, null, null);
	}
	
	@Override
	public List<T> list(final Integer startingAt, final Integer maxPerPage, final List<Criterion> criterions, final List<Order> order) {
		return getDAO().list(startingAt, maxPerPage, criterions, order, null, null);
	}

	/**
	 * Finds a entity with or without alias.
	 * @param field
	 * @param value
	 * @return Entity
	 */
	@Override
	public T findAliased(final Criterion criterion) {
		return (T) getDAO().find(criterion, null, aliases);
	}
	
	/**
	 * Finds a entity with or without alias.
	 * @param field
	 * @param value
	 * @return Entity
	 */
	@Override
	public List<T> listAliased() {
		return (List<T>) getDAO().list(null, null, aliases);
	}

	@Override
	public List<T> listAliased(final Order order) {
		return (List<T>) getDAO().list(getOrder(order), null, aliases);
	}
	
	@Override
	public List<T> listAliased(final Criterion criterion) {
		return (List<T>) getDAO().list(criterion, null, null, aliases);
	}
	
	@Override
	public List<T> listAliased(final Criterion criterion, final Order order) {
		return (List<T>) getDAO().list(criterion, getOrder(order), null, aliases);
	}
	
	@Override
	public List<T> listAliased(final String field, final Object value, final Order order) {
		return (List<T>) getDAO().list(Restrictions.eq(field, value), getOrder(order), null, aliases);
	}
	
	@Override
	public List<T> listAliased(final Integer startingAt, final Integer maxPerPage, final Order order) {
		return getDAO().list(startingAt, maxPerPage, getOrder(order), null, aliases);
	}
	
	@Override
	public List<T> listAliased(final Integer startingAt, final Integer maxPerPage, final Criterion criterion, final Order order) {
		return getDAO().list(startingAt, maxPerPage, criterion, getOrder(order), null, aliases);
	}
	
	@Override
	public List<T> listAliased(final Integer startingAt, final Integer maxPerPage, final Criterion criterion, final List<Order> order) {
		return getDAO().list(startingAt, maxPerPage, criterion, order, null, aliases);
	}

	@Override
	public List<T> listAliased(final Integer startingAt, final Integer maxPerPage, final List<Criterion> criterions, final List<Order> order) {
		return getDAO().list(startingAt, maxPerPage, criterions, order, null, aliases);
	}


	/**
	 * Finds a entity with or without alias.
	 * @param field
	 * @param value
	 * @return Entity
	 */
	@Override
	public T findProjected(final Criterion criterion) {
		return (T) getDAO().find(criterion, projections, null);
	}
	
	/**
	 * Finds a entity with or without alias.
	 * @param field
	 * @param value
	 * @return Entity
	 */
	@Override
	public List<T> listProjected() {
		return (List<T>) getDAO().list(null, projections, null);
	}

	@Override
	public List<T> listProjected(final Order order) {
		return (List<T>) getDAO().list(getOrder(order), projections, null);
	}
	
	@Override
	public List<T> listProjected(final Criterion criterion) {
		return (List<T>) getDAO().list(criterion, null, projections, null);
	}
	
	@Override
	public List<T> listProjected(final Criterion criterion, final Order order) {
		return (List<T>) getDAO().list(criterion, getOrder(order), projections, null);
	}
	
	@Override
	public List<T> listProjected(final String field, final Object value, final Order order) {
		return (List<T>) getDAO().list(Restrictions.eq(field, value), getOrder(order), projections, null);
	}

	@Override
	public List<T> listProjected(final Integer startingAt, final Integer maxPerPage, final Order order) {
		return getDAO().list(startingAt, maxPerPage, getOrder(order), projections, null);
	}
	
	@Override
	public List<T> listProjected(final Integer startingAt, final Integer maxPerPage, final Criterion criterion, final Order order) {
		return getDAO().list(startingAt, maxPerPage, criterion, getOrder(order), projections, null);
	}
	
	@Override
	public List<T> listProjected(final Integer startingAt, final Integer maxPerPage, final Criterion criterion, final List<Order> order) {
		return getDAO().list(startingAt, maxPerPage, criterion, order, projections, null);
	}
	
	@Override
	public List<T> listProjected(final Integer startingAt, final Integer maxPerPage, final List<Criterion> criterions, final List<Order> order) {
		return getDAO().list(startingAt, maxPerPage, criterions, order, projections, null);
	}

	
	/**
	 * Finds a entity with or without alias.
	 * @param field
	 * @param value
	 * @return Entity
	 */
	@Override
	public T findProjectedAndAliased(final Criterion criterion) {
		return (T) getDAO().find(criterion, projections, aliases);
	}

	/**
	 * Finds a entity with or without alias.
	 * @param field
	 * @param value
	 * @return Entity
	 */
	@Override
	public List<T> listProjectedAndAliased() {
		return (List<T>) getDAO().list(null, projections, aliases);
	}
	
	@Override
	public List<T> listProjectedAndAliased(final Order order) {
		return (List<T>) getDAO().list(getOrder(order), projections, aliases);
	}
	
	@Override
	public List<T> listProjectedAndAliased(final Criterion criterion) {
		return (List<T>) getDAO().list(criterion, null, projections, aliases);
	}
	
	@Override
	public List<T> listProjectedAndAliased(final Criterion criterion, final Order order) {
		return (List<T>) getDAO().list(criterion, getOrder(order), projections, aliases);
	}
	
	@Override
	public List<T> listProjectedAndAliased(final String field, final Object value, final Order order) {
		return (List<T>) getDAO().list(Restrictions.eq(field, value), getOrder(order), projections, aliases);
	}
	
	@Override
	public List<T> listProjectedAndAliased(final Integer startingAt, final Integer maxPerPage, final Order order) {
		return getDAO().list(startingAt, maxPerPage, getOrder(order), projections, aliases);
	}
	
	@Override
	public List<T> listProjectedAndAliased(final Integer startingAt, final Integer maxPerPage, final Criterion criterion, final Order order) {
		return getDAO().list(startingAt, maxPerPage, criterion, getOrder(order), projections, aliases);
	}
	
	@Override
	public List<T> listProjectedAndAliased(final Integer startingAt, final Integer maxPerPage, final Criterion criterion, final List<Order> order) {
		return getDAO().list(startingAt, maxPerPage, criterion, order, projections, aliases);
	}
	
	@Override
	public List<T> listProjectedAndAliased(final Integer startingAt, final Integer maxPerPage, final List<Criterion> criterions, final List<Order> order) {
		return getDAO().list(startingAt, maxPerPage, criterions, order, projections, aliases);
	}

	@Override
	public Integer count() {
		return getDAO().count(null, null);
	}
	
	@Override
	public Integer count(final String field, final Object value) {
		return getDAO().count(Restrictions.eq(field, value), aliases);
	}
	
	@Override
	public Integer count(final Criterion criterion) {
		return getDAO().count(criterion, aliases);
	}
	
	@Override
	public T save(final T entity) throws ServiceException {
		try {
			return getDAO().save(entity);
		} catch (DataAccessException e) {
			throw new ServiceException(e, "Erro ao salvar " + getDAO().getPersistentClass().getSimpleName().toLowerCase() + ".");
		}
	}

	@Override
	public void persist(final T entity) throws ServiceException {
		try {
			getDAO().persist(entity);
		} catch (DataAccessException e) {
			throw new ServiceException(e, "Erro ao persistir " + getDAO().getPersistentClass().getSimpleName().toLowerCase() + ".");
		}
	}

	@Override
	public void remove(final T entity) throws ServiceException {
		try {
			getDAO().remove(entity);
		} catch (DataAccessException e) {
			throw new ServiceException(e, "Erro ao remover " + getDAO().getPersistentClass().getSimpleName().toLowerCase() + ".");
		}
	}

	@Override
	public void remove(final Serializable id) throws ServiceException {
		try {
			getDAO().remove(id);
		} catch (DataAccessException e) {
			throw new ServiceException(e, "Erro ao excluir " + getDAO().getPersistentClass().getSimpleName().toLowerCase() + ".");
		}
	}

	public HashMap<String, String> getAliases() {
		return aliases;
	}

	public void setAliases(HashMap<String, String> aliases) {
		this.aliases = aliases;
	}

	public ProjectionList getProjections() {
		return projections;
	}

	public void setProjections(ProjectionList projections) {
		this.projections = projections;
	}
	
}
