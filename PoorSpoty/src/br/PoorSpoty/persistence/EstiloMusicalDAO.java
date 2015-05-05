package br.PoorSpoty.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.PoorSpoty.domain.Banda;
import br.PoorSpoty.domain.EstiloMusical;

@Stateless
public class EstiloMusicalDAO extends BaseJPADAO<EstiloMusical> {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	protected Class<EstiloMusical> getDomainClass() {
		return EstiloMusical.class;
	}

	public EstiloMusical buscaPorNome (String nome){
		Query q = getEntityManager().createQuery(
					"SELECT t FROM estilomusical t WHERE t.nome = " + nome
				);
				
		return (EstiloMusical)q.getSingleResult();
	}	
	
}
