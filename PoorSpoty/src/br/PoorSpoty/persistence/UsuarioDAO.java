package br.PoorSpoty.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
	
	public Usuario getByNameAndPass(String login, String senha){
		Usuario user = new Usuario();
		Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.nome = :login AND u.senha = :senha");
		q.setParameter("login", login);
		q.setParameter("senha", senha);
		try {
			user = (Usuario)q.getSingleResult();
			if(! login.equalsIgnoreCase(user.getNome()) && senha.equalsIgnoreCase(user.getSenha())){
				user = null;
			}
		} catch (Exception e) {
			return null;
		}
		return user;
	}

}
