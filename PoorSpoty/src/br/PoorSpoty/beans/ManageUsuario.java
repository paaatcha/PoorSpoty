package br.PoorSpoty.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.primefaces.event.SelectEvent;

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
	DataModel<Usuario> usuarios;
	Usuario usuario;
	
	@EJB
	EstiloMusicalDAO estiloMusicalDAO;
	private List<EstiloMusical> estilos;

	
	@EJB
	BandaDAO bandaDAO;
	private List<Banda> bandasAll;

	
	private String estiloCurtido = new String();	
	private List<String> estilosCurtidos = new ArrayList<String>();	
	
	private String estiloNaoCurtido = new String();	
	private List<String> estilosNaoCurtidos = new ArrayList<String>();	
	
	private String banda = new String();	
	private List<String> bandas = new ArrayList<String>();
	
	// ###################  INICIO GETTERS E SETTERS ###################
	
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

	public List<String> getEstilosCurtidos() {
		return estilosCurtidos;
	}
	public void setEstilosCurtidos(List<String> estilosCurtidos) {
		this.estilosCurtidos = estilosCurtidos;
	}
	
	public String getEstiloNaoCurtido() {
		return estiloNaoCurtido;
	}
	public void setEstiloNaoCurtido(String estiloNaoCurtido) {
		this.estiloNaoCurtido = estiloNaoCurtido;
	}
	public List<String> getEstilosNaoCurtidos() {
		return estilosNaoCurtidos;
	}
	public void setEstilosNaoCurtidos(List<String> estilosNaoCurtidos) {
		this.estilosNaoCurtidos = estilosNaoCurtidos;
	}
		
	public List<EstiloMusical> getEstilos() {
		return estilos;
	}
	public void setEstilos(List<EstiloMusical> estilos) {
		this.estilos = estilos;
	}
		
	public String getBanda() {
		return banda;
	}
	public void setBanda(String banda) {
		this.banda = banda;
	}
	
	public List<String> getBandas() {
		return bandas;
	}
	public void setBandas(List<String> bandas) {
		this.bandas = bandas;
	}
	// ################### FIM GETTERS E SETTERS ###################
	
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
		return "/manage/manageUsuario/cadastrar_usuario";
	}
	
	public String salvar (){
		try{
			this.usuario.setBandas(listStringToListBanda(this.bandas));			
			this.usuario.setEstilos(listStringToListEstilo(this.estilosCurtidos));
			this.usuario.setEstilosNao(listStringToListEstilo(this.estilosNaoCurtidos));	
			
			//this.usuario.printUsuario();
			
			this.usuarioDAO.salvar(usuario);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/manage/manageUsuario/listar_usuarios";
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
		return "/manage/manageUsuario/cadastrar_usuario";
	}
	
	public List<String> completaNome (String query){
		query = query.toUpperCase();
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
	
	public List<String> completaNomeBanda (String query){
		query = query.toUpperCase();
		this.bandasAll = new ArrayList<Banda>();
		List<String> sugestao = new ArrayList<String>();
		try{
			this.bandasAll = this.bandaDAO.listar();			
		}catch (Exception e){
			e.printStackTrace();
		}
		
		for (Banda b : this.bandasAll){
			 if (b.getNome().startsWith(query)){
				 sugestao.add(b.getNome());
			 }
		}		
		return sugestao;
	}	
	
	public void inserirBandas (){
		this.bandas.add(this.banda);		
		this.banda = new String();		
	}
	
	public void excluirBandas (){
		this.bandas.remove(this.banda);
	}
	
	public void inserirEstiloCurtido (){
		this.estilosCurtidos.add(this.estiloCurtido);		
		this.estiloCurtido = new String();				
	}
	
	public void excluirEstiloCurtido (){
		this.estilosCurtidos.remove(this.estiloCurtido);
	}
	
	public void inserirEstiloNaoCurtido (){
		this.estilosNaoCurtidos.add(this.estiloNaoCurtido);		
		this.estiloNaoCurtido = new String();
				
	}
	
	public void excluirEstiloNaoCurtido (){
		this.estilosNaoCurtidos.remove(this.estiloNaoCurtido);
	}	
	
	public void onItemSelect(SelectEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Item selecionado", event.getObject().toString()));
    }	
	
	public Banda stringToBanda (String string){
		Banda bandaAux = new Banda ();
		bandaAux = bandaDAO.buscaPorNome(string);
		return bandaAux;
	}
	
	public List<Banda> listStringToListBanda (List<String> stringsL){
		List<Banda> bandasL = new ArrayList<Banda>();
		for (int i = 0; i < stringsL.size(); i++) {  			
			bandasL.add(stringToBanda(stringsL.get(i)));	        
	    } 
		
		return bandasL;		
	}
	
	public void printList (List<String> lista){
		System.out.println("Iniciando:");
		for (int i = 0; i < lista.size(); i++) {  	        
	        System.out.println(lista.get(i));
	    }  
	}	

	public EstiloMusical stringToEstilo (String string){
		EstiloMusical estiloAux = new EstiloMusical ();
		estiloAux = estiloMusicalDAO.buscaPorNome(string);
		return estiloAux;
	}
	
	public List<EstiloMusical> listStringToListEstilo (List<String> stringsL){
		List<EstiloMusical> estilosL = new ArrayList<EstiloMusical>();
		for (int i = 0; i < stringsL.size(); i++) {  			
	        estilosL.add(stringToEstilo(stringsL.get(i)));
	    }  
		return estilosL;		
	}	
}
