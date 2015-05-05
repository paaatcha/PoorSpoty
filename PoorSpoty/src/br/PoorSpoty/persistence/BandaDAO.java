package br.PoorSpoty.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.PoorSpoty.domain.Banda;
import br.PoorSpoty.domain.EstiloMusical;

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
		TypedQuery<Banda> q = em.createQuery(
					"SELECT b FROM Banda AS b WHERE b.nome = '" + nome +"'", 
					Banda.class);
		
		return q.getSingleResult();
	}	

}
