package br.PoorSpoty.beans;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.PoorSpoty.domain.Usuario;
import br.PoorSpoty.persistence.UsuarioDAO;

@ManagedBean(name="auth")
@SessionScoped
public class LoginBean {
	@EJB
	UsuarioDAO usuarioDAO;
	
	private Usuario usuario;
	
	private String nomeUsuario;
	private String senha;
	
	private boolean logado;
	private boolean deslogado;
	
	public String getNomeUsuario() {
		return nomeUsuario;
	}
	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public boolean isLogado() {
		return logado;
	}
	public void setLogado(boolean logado) {
		this.logado = logado;
	}
	public boolean isDeslogado() {
		return deslogado;
	}
	public void setDeslogado(boolean deslogado) {
		this.deslogado = deslogado;
	}
	
	public String login(){
		FacesContext context = FacesContext.getCurrentInstance();
		this.usuario = usuarioDAO.getByNameAndPass(this.nomeUsuario, this.senha);
		if (this.usuario == null) {
			FacesMessage mensagem = new FacesMessage(
					"Usuario/senha invalidos!");
					mensagem.setSeverity(FacesMessage.SEVERITY_ERROR);
					context.addMessage(null, mensagem);
			return "failure";
		} else {
			this.logado = true;
			this.deslogado = false;
			return "/pag_admin/admin.xhtml?faces-redirect=true";
		}		
	}
	
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		this.usuario = null;
   	 	this.logado = false;
   	 	return "index.html";
	}
	

}
