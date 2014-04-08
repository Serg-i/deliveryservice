package com.itechart.deliveryservice.dao.impl;

import com.itechart.deliveryservice.dao.Dao;
import com.itechart.deliveryservice.utils.SearchParams;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
    public List<Type> getOffset(int from, int count, String by, boolean asc) {

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

    @Override
    public long searchCount(SearchParams params) {

        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Type> root = query.from(entityClass);
        fillSearchQuery(builder, query, root, params);
        query.select(builder.count(root));
        return getEntityManager().createQuery(query).getSingleResult();
    }

    @Override
    public List<Type> search(SearchParams params, int from, int count) {

        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Type> query = builder.createQuery(entityClass);
        Root<Type> root = query.from(entityClass);
        fillSearchQuery(builder, query, root, params);
        query.select(root);
        return getEntityManager()
                .createQuery(query)
                .setFirstResult(from)
                .setMaxResults(count)
                .getResultList();
    }

    @Override
    public List<Type> search(SearchParams params, int from, int count, String by, boolean asc) {

        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Type> query = builder.createQuery(entityClass);
        Root<Type> root = query.from(entityClass);
        fillSearchQuery(builder, query, root, params);
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

    private void fillSearchQuery( CriteriaBuilder builder,
                                  CriteriaQuery<?> query,
                                  Root<Type> root,
                                  SearchParams params) {

        for (Iterator<Map.Entry<String, SearchParams.OpAndValue>> iter
                     = params.iterator(); iter.hasNext();) {

            Map.Entry<String, SearchParams.OpAndValue> p = iter.next();
            switch(p.getValue().operator) {
                case EQUAL:
                    query.where(builder.equal(root.get(p.getKey()), p.getValue().value));
                    break;
                case GREATER:
                    query.where(builder.greaterThanOrEqualTo(root.<Date>get(p.getKey()), (Date)p.getValue().value));
                    break;
                case LESS:
                    query.where(builder.lessThanOrEqualTo(root.<Date>get(p.getKey()), (Date)p.getValue().value));
                    break;
            }
        }
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

}