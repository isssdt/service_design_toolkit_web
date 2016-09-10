/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.ejb.eao;

import common.dto.QueryParamValue;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author longnguyen
 * @param <T>
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public T create(T entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
        return entity;
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
        getEntityManager().flush();
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
        getEntityManager().flush();
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public T findSingleByQueryName(String queryName, QueryParamValue[] queryParamValues) {
        TypedQuery<T> typedQuery = getEntityManager().createNamedQuery(queryName, entityClass);
        for (QueryParamValue queryParamValue : queryParamValues) {
            typedQuery.setParameter(queryParamValue.getParam(), queryParamValue.getValue());
        }
        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<T> findListByQueryName(String queryName, Map<String, Object> queryParamValues) {
        TypedQuery<T> typedQuery = getEntityManager().createNamedQuery(queryName, entityClass);
        for (Map.Entry<String, Object> param : queryParamValues.entrySet()) {
            if (null != param.getValue() && !"class".equals(param.getKey())) {
                typedQuery.setParameter(param.getKey(), param.getValue());
            }
        }
        try {
            return typedQuery.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<T> findListByNativeQuery(String query, List<Object> params) {
        Query nativeQuery = getEntityManager().createNativeQuery(query, entityClass);
        if (null != params) {
            for (int i = 0; i < params.size(); i++) {
                nativeQuery.setParameter(i + 1, params.get(i));
            }
        }
        try {
            return nativeQuery.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public T findSingleByNativeQuery(String query, List<Object> params) {
        Query nativeQuery = getEntityManager().createNativeQuery(query, entityClass);
        if (null != params) {
            for (int i = 0; i < params.size(); i++) {
                nativeQuery.setParameter(i + 1, params.get(i));
            }
        }
        try {
            return (T) nativeQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
}
