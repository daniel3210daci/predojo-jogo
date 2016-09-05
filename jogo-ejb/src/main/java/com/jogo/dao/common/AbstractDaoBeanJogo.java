package com.jogo.dao.common;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.jogo.common.AbstractEntityJogo;

public abstract class AbstractDaoBeanJogo<E extends AbstractEntityJogo> implements DaoJogo<E> {

	protected final Class<E> entityClass;
	
	ValidatorFactory factory;
	protected Validator validator;

	@PersistenceContext(unitName="PostgreSqlDSMaximus")
	@PersistenceUnit(unitName="PostgreSqlDSMaximus")
	public EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	protected AbstractDaoBeanJogo() {
		super();
		entityClass = (Class<E>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	protected E getSingleResult(final CriteriaQuery<E> query) {
		return this.<E> getTypedSingleResult(query);
	}

	protected <T> T getTypedSingleResult(final CriteriaQuery<T> query) {
		try {
			return getEntityManager().createQuery(query).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	protected List<E> getResultList(final CriteriaQuery<E> query) {
		return getEntityManager().createQuery(query).getResultList();
	}

	protected List<E> getResultList(final CriteriaQuery<E> query,
			int maxresults, int firstresult) {
		return getEntityManager().createQuery(query).setMaxResults(maxresults)
				.setFirstResult(firstresult).getResultList();
	}

	protected CriteriaBuilder getCriteriaBuilder() {
		return getEntityManager().getCriteriaBuilder();
	}

	public void persist(final E instance) {
		getEntityManager().persist(instance);
	}

	public E find(final long id) {
		return getEntityManager().find(entityClass, id);
	}

	public void remove(final E instance) {
		boolean contains = getEntityManager().contains(instance);
		E remove = instance;
		if (!contains) {
			//remove = find(instance.getId());
		}
		getEntityManager().remove(remove);
	}

	public E merge(final E instance) {
		E merge = getEntityManager().merge(instance);
		getEntityManager().flush();
		return merge;
	}

	public List<E> findAll() {
		CriteriaBuilder builder = getCriteriaBuilder();
		CriteriaQuery<E> query = builder.createQuery(entityClass);
		query.from(entityClass);

		return getResultList(query);
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
