package br.PoorSpoty.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.PoorSpoty.domain.Banda;

@Stateless
public class BandaDAO extends BaseJPADAO<Banda> {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	protected Class<Banda> getDomainClass() {
		return Banda.class;
	}
		
	public Banda buscaPorNome (String nome){
		Query q = getEntityManager().createQuery(
					"SELECT t FROM banda t WHERE t.nome = " + nome
				);
				
		return (Banda)q.getSingleResult();
	}	

}
