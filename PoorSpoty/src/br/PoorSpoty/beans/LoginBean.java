package br.PoorSpoty.beans;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.PoorSpoty.domain.Banda;
import br.PoorSpoty.domain.EstiloMusical;
import br.PoorSpoty.domain.Usuario;
import br.PoorSpoty.persistence.UsuarioDAO;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

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
	private List<Banda> bandasSugestao = new ArrayList<Banda>();
	private List<EstiloMusical> estilosUsu;
	private List<EstiloMusical> estilosNaoUsu;
	
	private List<String> bandasString;
	private List<String> estilosString;
	private List<String> estilosNaoString;
	

	
	
	public List<Banda> getBandasSugestao() {
		return bandasSugestao;
	}
	public void setBandasSugestao(List<Banda> bandasSugestao) {
		this.bandasSugestao = bandasSugestao;
	}
	public List<Banda> getBandasUsu() {
		return bandasUsu;
	}
	public void setBandasUsu(List<Banda> bandasUsu) {
		this.bandasUsu = bandasUsu;
	}
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
	
	public String formatMembros (String membros){
		// retirando o primeiro ;
		int tam = membros.length();
		membros = membros.substring(1,tam);
		//System.out.println (membros);
			
		// Quebrando a string em todos os membros
		String vecMembros[] = membros.split(";");
		int nMembros = vecMembros.length;
			
		// Padrão da do caminho de inicio
		String padrao = "http://dbpedia.org/resource/";
		int tamPadrao = padrao.length();
		
		// Novo vetor de string com membros pré-tratados
		String preMembros[] = new String[tamPadrao];
		
		// String final resultante
		String membrosFinal = "";
		
		for (int i = 0; i < nMembros; i++){
			if (vecMembros[i].startsWith(padrao)){
				preMembros[i] = vecMembros[i].substring(tamPadrao, vecMembros[i].length()).replace("_", " ");
				if (i>=1){
					membrosFinal = membrosFinal + " - " + preMembros[i];
				} else {
					membrosFinal = membrosFinal + preMembros[i];
				}
				//System.out.println (preMembros[i]);
			} else {
				preMembros[i] = vecMembros[i].substring(0,vecMembros[i].length()-3);
				if (i>=1){
					membrosFinal = membrosFinal + " - " + preMembros[i];
				} else {
					membrosFinal = membrosFinal + preMembros[i];
				}
				//System.out.println (preMembros[i]);
			}			
					
		}
		
		return membrosFinal;
	}
	
	public void getInfoBandas (String banda, int indice){
		String prefixos = "PREFIX foaf: <http://xmlns.com/foaf/0.1/> "+
				"PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> "+
					"PREFIX dbpprop: <http://dbpedia.org/property/> ";

		String queryBanda = prefixos +
		"SELECT ?desc ?membersLink ?website " +
		"WHERE { " +
		 "?x a dbpedia-owl:Band . " +
		 "?x dbpprop:name ?name . " +			 
		 "?x dbpedia-owl:abstract ?desc . " +
		 "?x foaf:homepage ?website . " +
		 "?x dbpprop:currentMembers ?membersLink . " +
		 
		 "FILTER (lcase(str(?name)) = \"" + banda.toLowerCase() + "\") " +
		 "FILTER (langMatches(lang(?desc), \"PT\")) " + 
		"}";
			
		QueryExecution queryExecution = 
				QueryExecutionFactory.sparqlService(
						"http://dbpedia.org/sparql", 
						queryBanda);
		ResultSet results = queryExecution.execSelect();
		
		String descricao = "";
		String site = "";
		String membros = "";		
		
		while (results.hasNext()){
			QuerySolution linha = (QuerySolution) results.nextSolution();
			
			// Pegando o site da banda				
			Resource siteRes = linha.getResource("website");
			site = siteRes.getURI();
							
			// Pegando a descrição da banda
			Literal descLiteral = linha.getLiteral("desc");
			descricao = ("" + descLiteral.getValue());
			
			// Pegando os membros da banda
			RDFNode membersNode = linha.get("membersLink");				
			membros = (membros + ";"  + membersNode.toString());
			
		}		
		
		this.bandasUsu.get(indice).setDescricao(descricao);
		this.bandasUsu.get(indice).setSite (site);
		this.bandasUsu.get(indice).setMembros(formatMembros(membros));	
						
		queryExecution.close();				
					
	}
	
	public void getInfoArtistas (String artista, int indice){
		String prefixos = "PREFIX foaf: <http://xmlns.com/foaf/0.1/> "+
				"PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> "+
					"PREFIX dbpprop: <http://dbpedia.org/property/> ";

		String queryBanda = prefixos +
		"SELECT ?desc ?website " +
		"WHERE { " +
		 "?x a dbpedia-owl:MusicalArtist . " +
		 "?x dbpprop:name ?name . " +			 
		 "?x dbpedia-owl:abstract ?desc . " +
		 "?x foaf:homepage ?website . " +
			 
		 "FILTER (lcase(str(?name)) = \"" + artista.toLowerCase() + "\") " +
		 "FILTER (langMatches(lang(?desc), \"PT\")) " + 
		"}";
			
		QueryExecution queryExecution = 
				QueryExecutionFactory.sparqlService(
						"http://dbpedia.org/sparql", 
						queryBanda);
		ResultSet results = queryExecution.execSelect();
		
		//ResultSetFormatter.out(System.out, results);
		
		String descricao = "";
		String site = "";		
		
		if (results.hasNext()){
			QuerySolution linha = (QuerySolution) results.nextSolution();
			
			// Pegando o site do artista			
			Resource siteRes = linha.getResource("website");
			site = siteRes.getURI();
							
			// Pegando a descrição do artista
			Literal descLiteral = linha.getLiteral("desc");
			descricao = ("" + descLiteral.getValue());			
		}		
		
		this.bandasUsu.get(indice).setDescricao(descricao);
		this.bandasUsu.get(indice).setSite (site);				 
		this.bandasUsu.get(indice).setMembros(" - ");	
						
		queryExecution.close();			
	}
	
	public int getSugestao (String estilo){
						
		String prefixos = "PREFIX foaf: <http://xmlns.com/foaf/0.1/> "+
				"PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> "+
					"PREFIX dbpprop: <http://dbpedia.org/property/> ";
		
		String query = prefixos + "SELECT ?nomeBanda ?website ?desc "+
				"WHERE { " +
			  "{ "+ 
			  "?x a dbpedia-owl:Band . " +
			  "?x dbpprop:genre ?name . " +
			  "?x dbpprop:name ?nomeBanda . " +
			  "?x dbpedia-owl:activeYearsStartYear ?dur . " +
			  "?x dbpedia-owl:abstract ?desc . " + 
			  "?x foaf:homepage ?website . " + 
			  "FILTER regex(?name,\""+ estilo + "\",\"i\") "+
			  "FILTER (langMatches(lang(?desc), \"PT\")) " +
			  "} " +
			"UNION " +
			  "{ " +
			  "?x a dbpedia-owl:MusicArtist . " +
			  "?x dbpprop:genre ?name . " +
			  "?x dbpprop:name ?nomeBanda . " +
			  "?x dbpedia-owl:activeYearsStartYear ?dur . " +
			  "?x dbpedia-owl:abstract ?desc . " + 
			  "?x foaf:homepage ?website . " + 
			  "FILTER regex(?name,\""+ estilo + "\",\"i\") " +
			  "FILTER (langMatches(lang(?desc), \"PT\")) " +
			  "} " +
			"} ORDER BY ?dur " + 
			"LIMIT 100 ";
		
				
		QueryExecution queryExecution = 
				QueryExecutionFactory.sparqlService(
						"http://dbpedia.org/sparql", 
						query);
		ResultSet results = queryExecution.execSelect();
		//ResultSetFormatter.out(System.out, results);
			
				
		String nome = "";
		String site = "";
		String descricao = "";		
		int flag = 0;
		
		while (results.hasNext()){
			QuerySolution linha = (QuerySolution) results.nextSolution();
						
			if (linha.get("nomeBanda").isLiteral()) {				
				// Pegando o site do artista			
				Resource siteRes = linha.getResource("website");
				site = siteRes.getURI();
											
				Literal nomeLiteral = linha.getLiteral("nomeBanda");
				nome = ("" + nomeLiteral.getValue());				
								
				// Pegando a descrição do artista
				Literal descLiteral = linha.getLiteral("desc");
				descricao = ("" + descLiteral.getValue());		
				
				//System.out.println(nome);
				
				Banda band = new Banda ();			
				band.setNome(nome);
				band.setDescricao(descricao);
				band.setSite(site);
				
				this.bandasSugestao.add(band);	
				flag = 1;
			}
		}	
		
		return flag;
			
	}
	
	
	public String goSugestao (){
		
		int numEstilos = this.estilosString.size();				
		Random gerador = new Random();  
		
		// Escolhendo um estilo ao acaso
		int numRand = gerador.nextInt(numEstilos);
		String estiloAlvo = estilosString.get(numRand);
		
		int flag = getSugestao(estiloAlvo);
		
		if (flag==0){
			return "/pag_usuario/semSugestao.faces";			
		} else {
		
			int tamSug = this.bandasSugestao.size();
			numRand = gerador.nextInt(tamSug);
							
			while (tamSug > 5){
				if (numRand <= this.bandasSugestao.size()){
					this.bandasSugestao.remove(numRand);	
				}
				tamSug = this.bandasSugestao.size();
				numRand = gerador.nextInt(tamSug);
			}
			
			return "/pag_usuario/sugestao.faces";
			
		}
		
		
	}
		
	public String getMinhasBandas(){
						
		for (int i = 0; i < bandasUsu.size(); i++){
		
			// se não for carregado a informação, carrega. Caso contrario, pula fora
			if (bandasUsu.get(i).getLoad() == 0){
				
				if (bandasUsu.get(i).getTipo() == 1){
					getInfoBandas(bandasUsu.get(i).getNome(), i);
				} else{
					getInfoArtistas(bandasUsu.get(i).getNome(), i);
				}
				
				bandasUsu.get(i).setLoad(1);
			}	
		}
		
		return "/pag_usuario/minhasBandas.faces";			
	}
	
	
	

}

	