package br.PoorSpoty.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;


import br.PoorSpoty.domain.Banda;
import br.PoorSpoty.domain.EstiloMusical;
import br.PoorSpoty.domain.Usuario;
import br.PoorSpoty.persistence.BandaDAO;
import br.PoorSpoty.persistence.EstiloMusicalDAO;
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
	
	@EJB
	EstiloMusicalDAO estiloMusicalDAO;	
	private List<EstiloMusical> estilos;	
	
	
	DataModel<Usuario> usuarios;
	String estiloCurtido;	
	private List<String> estilosCurtidos = new ArrayList<String>();
	
	Usuario usuario;
	
	
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public String getEstiloCurtido() {
		return estiloCurtido;
	}
	public void setEstiloCurtido(String estiloCurtido) {
		this.estiloCurtido = estiloCurtido;
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
	
	public List<String> completaNome (String query){
		this.estilos = new ArrayList<EstiloMusical>();
		List<String> sugestao = new ArrayList<String>();
		try{
			this.estilos = this.estiloMusicalDAO.listar();			
		}catch (Exception e){
			e.printStackTrace();
		}
		
		for (EstiloMusical m : this.estilos){
			 if (m.getNome().startsWith(query)){
				 sugestao.add(m.getNome());
			 }
		}		
		return sugestao;
	}	
	
	public void inserirEstiloCurtido (AjaxBehaviorEvent event){
		estilosCurtidos.add(estiloCurtido);
	}

}
