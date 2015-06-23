package br.PoorSpoty.beans;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;

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
	
	private List<Banda> bandasUsu;
	private List<EstiloMusical> estilosUsu;
	private List<EstiloMusical> estilosNaoUsu;
	
	private List<String> bandasString;
	private List<String> estilosString;
	private List<String> estilosNaoString;
	

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
			
			if(this.usuario.getTipo()==1){
				return "/pag_admin/admin.xhtml?faces-redirect=true";
			} else {
				
				// Pegando as listas de bandas e estilos de uma vez pra evitar ficar pegando toda hora
				// admin não pega pq nao precisa
				this.bandasUsu = new ArrayList<Banda>();
				this.estilosUsu = new ArrayList<EstiloMusical>();
				this.estilosNaoUsu = new ArrayList<EstiloMusical>();
				
				this.bandasString = new ArrayList<String>();
				this.estilosString = new ArrayList<String>();
				this.estilosNaoString = new ArrayList<String>();
				
				this.bandasUsu = this.usuario.getBandas();
				this.estilosUsu = this.usuario.getEstilos();
				this.estilosNaoUsu = this.usuario.getEstilosNao();				
				// fim
				return "/pag_usuario/inicio.xhtml?faces-redirect=true";
			}
				
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
		//List<Banda> bandasUsu = this.usuario.getBandas();
		String saida = new String();
		saida = "";
		
		for (int i = 0; i < bandasUsu.size(); i++) {  	        
			String band = new String(); 
			band = bandasUsu.get(i).getNome().toLowerCase();
			band = band.substring(0,1).toUpperCase() + band.substring(1);
			bandasString.add(band);
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
		//List<EstiloMusical> estilosUsu = this.usuario.getEstilos();
		String saida = new String();
		saida = "";
		
		for (int i = 0; i < estilosUsu.size(); i++) {  	        
			String est = new String(); 
			est = estilosUsu.get(i).getNome().toLowerCase();
			est = est.substring(0,1).toUpperCase() + est.substring(1);
			estilosString.add(est);
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
		//List<EstiloMusical> estilosUsu = this.usuario.getEstilosNao();
		String saida = new String();
		saida = "";
		
		for (int i = 0; i < estilosNaoUsu.size(); i++) {  	        
			String est = new String(); 
			est = estilosNaoUsu.get(i).getNome().toLowerCase();
			est = est.substring(0,1).toUpperCase() + est.substring(1);
			saida = saida + est;
			estilosNaoString.add(est);
			if (i < estilosNaoUsu.size()-2){
				saida = saida + ", ";
			}else if(i < estilosNaoUsu.size()-1){
				saida = saida + " e ";
			}
		}  		
		return saida;	
	}	
	
	public String getMembrosBanda(){
		
		String band = "Iron Maiden";
		String query = "PREFIX mo: <http://purl.org/ontology/mo/> PREFIX foaf: <http://xmlns.com/foaf/0.1/> SELECT ?name WHERE { ?member foaf:name ?name ; mo:member_of ?band . ?band foaf:name \"" + band + "\"}";
		
		
		QueryExecution queryExecution = 
				QueryExecutionFactory.sparqlService(
						"http://linkedbrainz.org/sparql", 
						query);
		ResultSet results = queryExecution.execSelect();
		
		String membros = new String ();
		membros = "";
		

		for (; results.hasNext() ; ) {
			QuerySolution solution = results.next();
			Literal literal = solution.getLiteral("name");
			membros = (membros + literal.getValue() + "\r\n");
		}
					
		return membros;	
		
			
	}
	

}
