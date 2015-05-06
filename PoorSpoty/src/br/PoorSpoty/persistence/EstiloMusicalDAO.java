package br.PoorSpoty.persistence;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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
		TypedQuery<EstiloMusical> q = em.createQuery(
					"SELECT e FROM EstiloMusical AS e WHERE e.nome = :nome",
				EstiloMusical.class);
		q.setParameter("nome",nome);
		List<EstiloMusical> results = q.getResultList();
		
		return results.get(0);
	}	
	
}
