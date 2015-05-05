package br.PoorSpoty.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

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
	@SuppressWarnings("unchecked")
	public List<T> listar(){
		Query q = getEntityManager()
					.createQuery(
							"SELECT t FROM " + getDomainClass().getSimpleName()
									+ " t ");
			return q.getResultList();		
	}	
	
	@Override
	public void excluir(Long id) {
		T obj = this.obter(id);
		getEntityManager().remove(obj);		
	}
	
}
