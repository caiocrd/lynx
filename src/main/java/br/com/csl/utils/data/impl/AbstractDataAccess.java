package br.com.csl.utils.data.impl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.csl.lynx.domain.DomainObject;
import br.com.csl.lynx.model.Log;
import br.com.csl.lynx.model.Usuario;
import br.com.csl.utils.data.DataAccess;
import br.com.csl.utils.entity.PersistentEntity;
import br.com.csl.utils.exception.DataAccessException;

public abstract class AbstractDataAccess<T extends PersistentEntity>  implements DataAccess<T> {

	private static final long serialVersionUID = 5094291780901817994L;

	@PersistenceContext
	private EntityManager entityManager;
	private Class<T> persistentClass;

	@Override
	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	@Override
	public Session getSession() {
		return (Session) this.entityManager.getDelegate();
	}

	public T getPersistentEntity(T entity) {
		return find(Example.create(entity), null, null);
	}

	@Override
	public Class<T> getPersistentClass() {
		return this.persistentClass;
	}

	public void setPersistentClass(Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	@Override
	public T find(final Serializable id) {
		return this.entityManager.find(this.persistentClass, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public T find(final Example example, final ProjectionList projections, final HashMap<String, String> aliases) {
		Criteria crit = getSession().createCriteria(this.persistentClass);
		crit.add(example);
		if (aliases != null && aliases.size() > 0) {
			for (Entry<String, String> entry : aliases.entrySet()){	crit.createAlias(entry.getKey(), entry.getValue(), JoinType.LEFT_OUTER_JOIN);	}
		}
		if (projections != null) { crit.setProjection(projections).setResultTransformer(new AliasToBeanResultTransformer(this.persistentClass)); }
		else { crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); }
		return (T) crit.uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public T find(final Criterion criterion, final ProjectionList projections, final HashMap<String, String> aliases) {
		Criteria crit = getSession().createCriteria(this.persistentClass);
		if (aliases != null && aliases.size() > 0) {
			for (Entry<String, String> entry : aliases.entrySet()){	crit.createAlias(entry.getKey(), entry.getValue(), JoinType.LEFT_OUTER_JOIN);	}
		}
		if (criterion != null) { crit.add(criterion);	}
		if (projections != null) { crit.setProjection(projections).setResultTransformer(new AliasToBeanResultTransformer(this.persistentClass)); }
		else { crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); }
		return (T) crit.uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> list(final List<Order> order, final ProjectionList projections, final HashMap<String, String> aliases) {
		Criteria crit = getSession().createCriteria(this.persistentClass);
		if (aliases != null && aliases.size() > 0) {
			for (Entry<String, String> entry : aliases.entrySet()){	crit.createAlias(entry.getKey(), entry.getValue(), JoinType.LEFT_OUTER_JOIN);	}
		}
		if (order != null && order.size() > 0) { for (Order aux : order) { crit.addOrder(aux); } }
		if (projections != null) { crit.setProjection(projections).setResultTransformer(new AliasToBeanResultTransformer(this.persistentClass)); }
		else { crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); }
		return crit.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> list(final Example example, final List<Order> order, final ProjectionList projections, final HashMap<String, String> aliases) {
		Criteria crit = getSession().createCriteria(this.persistentClass);
		crit.add(example);
		if (aliases != null && aliases.size() > 0) {
			for (Entry<String, String> entry : aliases.entrySet()){	crit.createAlias(entry.getKey(), entry.getValue(), JoinType.LEFT_OUTER_JOIN);	}
		}
		if (order != null && order.size() > 0) { for (Order aux : order) { crit.addOrder(aux); } }
		if (projections != null) { crit.setProjection(projections).setResultTransformer(new AliasToBeanResultTransformer(this.persistentClass)); }
		else { crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); }
		return crit.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> list(final Criterion criterion, final List<Order> order, final ProjectionList projections, final HashMap<String, String> aliases) {
		Criteria crit = getSession().createCriteria(this.persistentClass);
		if (aliases != null && aliases.size() > 0) {
			for (Entry<String, String> entry : aliases.entrySet()){	crit.createAlias(entry.getKey(), entry.getValue(), JoinType.LEFT_OUTER_JOIN);	}
		}
		if (criterion != null) { crit.add(criterion); }
		if (order != null && order.size() > 0) { for (Order aux : order) { crit.addOrder(aux); } }
		if (projections != null) { crit.setProjection(projections).setResultTransformer(new AliasToBeanResultTransformer(this.persistentClass)); }
		else { crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); }
		return crit.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> list(final Integer startingAt, final Integer maxPerPage, final List<Order> order, final ProjectionList projections, final HashMap<String, String> aliases) {
		Criteria crit = getSession().createCriteria(this.persistentClass);
		if (startingAt != null) crit.setFirstResult(startingAt);
		if (maxPerPage != null) crit.setMaxResults(maxPerPage);
		if (aliases != null && aliases.size() > 0) {
			for (Entry<String, String> entry : aliases.entrySet()){	crit.createAlias(entry.getKey(), entry.getValue(), JoinType.LEFT_OUTER_JOIN);	}
		}
		if (order != null && order.size() > 0) { for (Order aux : order) { crit.addOrder(aux); } }
		if (projections != null) { crit.setProjection(projections).setResultTransformer(new AliasToBeanResultTransformer(this.persistentClass)); }
		else { crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); }
		return crit.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> list(final Integer startingAt, final Integer maxPerPage, final Criterion criterion, List<Order> order, final ProjectionList projections, final HashMap<String, String> aliases) {
		Criteria crit = getSession().createCriteria(this.persistentClass);
		if (startingAt != null) crit.setFirstResult(startingAt);
		if (maxPerPage != null) crit.setMaxResults(maxPerPage);
		if (aliases != null && aliases.size() > 0) {
			for (Entry<String, String> entry : aliases.entrySet()){	crit.createAlias(entry.getKey(), entry.getValue(), JoinType.LEFT_OUTER_JOIN);	}
		}
		if (criterion != null) { crit.add(criterion); }
		if (order != null && order.size() > 0) { for (Order aux : order) { crit.addOrder(aux); } }
		if (projections != null) { crit.setProjection(projections).setResultTransformer(new AliasToBeanResultTransformer(this.persistentClass)); }
		else { crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); }
		return crit.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> list(final Integer startingAt, final Integer maxPerPage, final List<Criterion> criterions, List<Order> order, final ProjectionList projections, final HashMap<String, String> aliases) {
		Criteria crit = getSession().createCriteria(this.persistentClass);
		if (startingAt != null) crit.setFirstResult(startingAt);
		if (maxPerPage != null) crit.setMaxResults(maxPerPage);
		if (aliases != null && aliases.size() > 0) {
			for (Entry<String, String> entry : aliases.entrySet()){	crit.createAlias(entry.getKey(), entry.getValue(), JoinType.LEFT_OUTER_JOIN);	}
		}
		if (criterions != null && criterions.size() > 0) for (Criterion aux : criterions) { crit.add(aux); }
		if (order != null && order.size() > 0) for (Order aux : order) { crit.addOrder(aux); }
		if (projections != null) crit.setProjection(projections).setResultTransformer(new AliasToBeanResultTransformer(this.persistentClass));
		else crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return crit.list();
	}

	@Override
	public Integer count(final Criterion criterion, final HashMap<String, String> aliases) {
		Criteria crit = getSession().createCriteria(this.persistentClass);
		if (aliases != null && aliases.size() > 0) {
			for (Entry<String, String> entry : aliases.entrySet()){	crit.createAlias(entry.getKey(), entry.getValue(), JoinType.LEFT_OUTER_JOIN);	}
		}
		if (criterion != null) { crit.add(criterion); }
		crit.setProjection(Projections.rowCount());
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return Integer.valueOf(crit.uniqueResult().toString());
	}

	@Override
	public synchronized T save(final T entity) throws DataAccessException {
		T persistedEntity = null;
		try {
			persistedEntity = this.entityManager.merge(entity);
			return persistedEntity;
		} catch (Exception e) {
			throw new DataAccessException(e, "Erro ao atualizar entidade.");
		} finally{
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof Usuario) {
				Usuario user = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (user!=null && entity!=null){
					String metodo = "Edição";
					if (entity.getId() == null){
						metodo = "Inserção";
					}
					DomainObject domain = (DomainObject) persistedEntity;
					String idString = String.valueOf(domain.getId());
					Long idLong = Long.valueOf(idString);
					Log log = new Log(entity.getClass().getSimpleName(), idLong, user.getId(), user.getNome(), Calendar.getInstance(), metodo);
					this.entityManager.merge(log);
				}
			}
		}
	}

	@Override
	public synchronized void persist(final T entity) throws DataAccessException {
		try {
			this.entityManager.persist(entity);
			this.entityManager.detach(entity);
		} catch (Exception e) {
			throw new DataAccessException(e, "Erro ao persistir entidade.");
		}
	}

	@Override
	public void remove(final T entity) throws DataAccessException {
		try {
			T persistedEntity = getPersistentEntity(entity);
			this.entityManager.remove(persistedEntity);
		} catch (Exception e) {
			throw new DataAccessException(e, "Erro ao remover entidade.");
		} finally{
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof Usuario) {
				Usuario user = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (user!=null && entity!=null){
					String idString = String.valueOf(entity.getId());
					Long idLong = Long.valueOf(idString);
					Log log = new Log(entity.getClass().getSimpleName(), idLong, user.getId(), user.getNome(), Calendar.getInstance(), "Remoção");
					this.entityManager.merge(log);
				}
			}
		}
	}

	@Override
	public void remove(final Serializable id) throws DataAccessException {
		try {
			this.entityManager.remove(find(id));
		} catch (Exception e) {
			throw new DataAccessException(e, "Erro ao remover entidade.");
		}
	}

}
