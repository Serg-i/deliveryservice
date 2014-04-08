package com.itechart.deliveryservice.dao.impl;

import com.itechart.deliveryservice.dao.Dao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public abstract class DaoImpl<Type> implements Dao<Type> {

    private final Class<Type> entityClass;

    @PersistenceContext
    private EntityManager entityManager;

    public DaoImpl(Class<Type> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public void save(Type object) {
        getEntityManager().persist(object);
        getEntityManager().flush();
    }

    @Override
    public void merge(Type object) {
        getEntityManager().merge(object);
    }

    @Override
    public void delete(Type object) {
        getEntityManager().remove(object);
    }

    @Override
    public List<Type> getAll() {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Type> query = builder.createQuery(entityClass);
        Root<Type> root = query.from(entityClass);
        query.select(root);
        return getEntityManager().createQuery(query).getResultList();
    }

    @Override
    public Type getById(long id) {
        return getEntityManager().find(entityClass, id);
    }

    @Override
    public long getCount() {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        query.select(builder.count(query.from(entityClass)));
        return getEntityManager().createQuery(query).getSingleResult();
    }

    @Override
    public List<Type> getOffset(int from, int count) {

        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Type> query = builder.createQuery(entityClass);
        Root<Type> root = query.from(entityClass);
        query.select(root);
        return getEntityManager()
                .createQuery(query)
                .setFirstResult(from)
                .setMaxResults(count)
                .getResultList();
    }

    @Override
    public List<Type> getOrderedOffset(int from, int count, String by, boolean asc) {

        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Type> query = builder.createQuery(entityClass);
        Root<Type> root = query.from(entityClass);
        if (asc) {
            query.orderBy(builder.asc(root.get(by)));
        } else {
            query.orderBy(builder.desc(root.get(by)));
        }
        query.select(root);
        return getEntityManager()
                .createQuery(query)
                .setFirstResult(from)
                .setMaxResults(count)
                .getResultList();
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

}