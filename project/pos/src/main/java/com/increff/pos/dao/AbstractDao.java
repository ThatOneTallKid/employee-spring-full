package com.increff.pos.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public abstract class AbstractDao {
	
	@PersistenceContext
	private EntityManager em;

	protected <T> T getSingle(TypedQuery<T> query) {
		return query.getResultList().stream().findFirst().orElse(null);
	}
	
	protected <T> TypedQuery<T> getQuery(String jpql, Class<T> clazz) {
		return em.createQuery(jpql, clazz);
	}
	
	protected EntityManager em() {
		return em;
	}

	public <T> void insert(T t)  {
		// saves object into the db
		em.persist(t);
	}


	public  <T> T selectByID(Integer id, Class<T> clazz) {
		String dynamicQuery = "select b from " + clazz.getName() + " b where id=:id";
		TypedQuery<T> query = getQuery(dynamicQuery, clazz);
		query.setParameter("id", id);
		return getSingle(query);
	}


	public <T> List<T> selectALL( Class<T> clazz) {
		String SELECT_ALL = "select b from " + clazz.getName() + " b";
		TypedQuery<T> query = getQuery(SELECT_ALL, clazz);
		return query.getResultList();
	}

}
