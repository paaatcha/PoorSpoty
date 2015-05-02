package br.PoorSpoty.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public abstract class BaseJPADAO<T extends Serializable> implements BaseDAO<T> {

	protected abstract EntityManager getEntityManager();

	protected abstract Class<T> getDomainClass();
	
	@Override
	public T salvar(T obj) {
		EntityManager em = getEntityManager();
		obj = em.merge(obj);
		return obj;
	}

	@Override
	public T obter(Long id) {
		EntityManager em = getEntityManager();
		return (T) em.find(getDomainClass(), id);
	}

	@Override
	public List<T> listar() {
		EntityManager em = getEntityManager();
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(getDomainClass()));
		return em.createQuery(cq).getResultList();
	}

	@Override
	public void excluir(Long id) {
		T obj = this.obter(id);
		getEntityManager().remove(obj);		
	}

	@Override
	public long retornaTotal() {
		EntityManager em = getEntityManager();
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		Root<T> rt = cq.from(getDomainClass());
		cq.select(em.getCriteriaBuilder().count(rt));
		Query q = em.createQuery(cq);
		return ((Long) q.getSingleResult()).longValue();
	}

}
