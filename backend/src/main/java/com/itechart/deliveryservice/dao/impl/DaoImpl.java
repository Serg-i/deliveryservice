package com.itechart.deliveryservice.dao.impl;

import com.itechart.deliveryservice.dao.Dao;
import com.itechart.deliveryservice.utils.SearchParams;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.*;

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

    @Override
    public List<Type> searchAll(SearchParams params) {

        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Type> query = builder.createQuery(entityClass);
        Root<Type> root = query.from(entityClass);
        fillSearchQuery(builder, query, root, params);
        query.select(root);
        return getEntityManager()
                .createQuery(query)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    private void fillSearchQuery( CriteriaBuilder builder,
                                  CriteriaQuery<?> query,
                                  Root<Type> root,
                                  SearchParams params) {


        List<Predicate> list = new ArrayList<Predicate>();
        TreeMap<String, ArrayList<Predicate>> map = new TreeMap<String, ArrayList<Predicate>>();

        for (Iterator<SearchParams.Pair> iter
                     = params.iterator(); iter.hasNext();) {

            SearchParams.Pair p = iter.next();
            String[] s = p.getKey().split("\\.");

            if(!map.containsKey(p.getKey()))
                map.put(p.getKey(), new ArrayList<Predicate>());

            Path path = null;
            if (s.length > 1) {
                path = root.get(s[0]).get(s[1]);
            } else {
                path = root.get(s[0]);
            }
            switch(p.getValue().operator) {
                case EQUAL:
                    map.get(p.getKey()).add(builder.equal(path, p.getValue().value));
                    break;
                case GREATER:
                    map.get(p.getKey()).add(builder.greaterThanOrEqualTo(
                            (Path<Date>) path, (Date) p.getValue().value));
                    break;
                case LESS:
                    map.get(p.getKey()).add(builder.lessThanOrEqualTo(
                            (Path<Date>) path, (Date) p.getValue().value));
                    break;
            }
        }
        for(Map.Entry<String, ArrayList<Predicate>> ent : map.entrySet()) {
            Predicate p = builder.or(ent.getValue().toArray(
                    new Predicate[ent.getValue().size()]));
            list.add(p);
        }

        query.where(list.toArray(new Predicate[list.size()]));
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

}