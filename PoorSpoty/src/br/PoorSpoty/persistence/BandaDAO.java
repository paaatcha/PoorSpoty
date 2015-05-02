package br.PoorSpoty.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

}
