package br.PoorSpoty.beans;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.PoorSpoty.domain.Banda;
import br.PoorSpoty.domain.EstiloMusical;
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
	
	public boolean isAdmin(){
		if( this.usuario.getTipo() == 1)
			return true;
		else
			return false;
	}	
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
			if(this.usuario.getTipo()==1)
				return "/pag_admin/admin.xhtml?faces-redirect=true";
			else
				return "/pag_usuario/inicio.xhtml?faces-redirect=true";
				
		}		
	}
	
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		this.usuario = null;
   	 	this.logado = false;
   	 	return "../index.xhtml?faces-redirect=true";
	}
	
	public int getIdadeUsuario() {
		java.util.Date birthDate = new java.util.Date(this.usuario.getDataNasc().getTime());				
		//if (birthDate == null) return 0;
		Calendar birth = Calendar.getInstance();
		birth.setTime(birthDate);
		Calendar today = Calendar.getInstance();
		today.setTime(new Date(System.currentTimeMillis()));
		int age = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
		birth.add(Calendar.YEAR, age);
		if (birth.after(today)) age--;
		return age;
	}
	
	public String getBandasUsuario() {
		List<Banda> bandasUsu = this.usuario.getBandas();
		String saida = new String();
		saida = "";
		
		for (int i = 0; i < bandasUsu.size(); i++) {  	        
			String band = new String(); 
			band = bandasUsu.get(i).getNome().toLowerCase();
			band = band.substring(0,1).toUpperCase() + band.substring(1);
			saida = saida + band;
			if (i < bandasUsu.size()-2){
				saida = saida + ", ";
			}else if(i < bandasUsu.size()-1){
				saida = saida + " e ";
			}
		}  		
		return saida;	
	}
	
	public String getEstilosUsuario() {
		List<EstiloMusical> estilosUsu = this.usuario.getEstilos();
		String saida = new String();
		saida = "";
		
		for (int i = 0; i < estilosUsu.size(); i++) {  	        
			String est = new String(); 
			est = estilosUsu.get(i).getNome().toLowerCase();
			est = est.substring(0,1).toUpperCase() + est.substring(1);
			saida = saida + est;
			if (i < estilosUsu.size()-2){
				saida = saida + ", ";
			}else if(i < estilosUsu.size()-1){
				saida = saida + " e ";
			}
		}  		
		return saida;	
	}
	
	public String getEstilosNaoUsuario() {
		List<EstiloMusical> estilosUsu = this.usuario.getEstilosNao();
		String saida = new String();
		saida = "";
		
		for (int i = 0; i < estilosUsu.size(); i++) {  	        
			String est = new String(); 
			est = estilosUsu.get(i).getNome().toLowerCase();
			est = est.substring(0,1).toUpperCase() + est.substring(1);
			saida = saida + est;
			if (i < estilosUsu.size()-2){
				saida = saida + ", ";
			}else if(i < estilosUsu.size()-1){
				saida = saida + " e ";
			}
		}  		
		return saida;	
	}	
	
	

}
