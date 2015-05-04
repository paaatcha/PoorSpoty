package br.PoorSpoty.beans;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import br.PoorSpoty.domain.Usuario;
import br.PoorSpoty.persistence.BandaDAO;
import br.PoorSpoty.persistence.UsuarioDAO;

@ManagedBean
@SessionScoped
public class ManageUsuario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	UsuarioDAO usuarioDAO;
	
	DataModel<Usuario> usuarios;
	
	Usuario usuario;
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public DataModel<Usuario> getUsuarios(){
		try {
			this.usuarios = new ListDataModel<Usuario>(usuarioDAO.listar());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usuarios;		
	}
	
	public String adicionar (){	
		this.usuario = new Usuario ();
		return "/manageUsuario/visualizar_usuario";
	}
	
	public String salvar (){
		try{
			this.usuarioDAO.salvar(usuario);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/manageUsuario/listar_usuarios";
	}
	
	public void excluir(){
		Long idUsuario = ((Usuario)this.usuarios.getRowData()).getId();
		try{
			this.usuarioDAO.excluir(idUsuario);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String alterar(){
		this.usuario = (Usuario) (this.usuarios.getRowData());
		return "/manageUsuario/visualizar_usuario";
	}
	
	public String retornar (){
		return "/manageUsuario/listar_usuarios";
	}	
}
