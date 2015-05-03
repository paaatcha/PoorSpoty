package br.PoorSpoty.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.PoorSpoty.domain.Usuario;

@Stateless
public class UsuarioDAO extends BaseJPADAO<Usuario> {


	@PersistenceContext
	private EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	protected Class<Usuario> getDomainClass() {
		return Usuario.class;
	}

}
