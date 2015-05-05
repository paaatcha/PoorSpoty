package br.PoorSpoty.beans;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;


import br.PoorSpoty.domain.EstiloMusical;
import br.PoorSpoty.persistence.EstiloMusicalDAO;

@ManagedBean
@SessionScoped
public class ManageEstiloMusical implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	EstiloMusicalDAO estiloMusicalDAO;
	
	EstiloMusical estiloMusical;
	
	private DataModel<EstiloMusical> estilosMusicais;
	
	
	public EstiloMusical getEstiloMusical(){
		return this.estiloMusical;
	}
	
	public void setEstiloMusical(EstiloMusical estiloMusical){
		this.estiloMusical = estiloMusical;
	}
	
	public DataModel<EstiloMusical> getEstilosMusicais(){
		try{
			this.estilosMusicais = new ListDataModel<EstiloMusical>(estiloMusicalDAO.listar());
		}catch(Exception e){
			e.printStackTrace();
		}
		return this.estilosMusicais;
	}
	
	public String adicionar(){
		this.estiloMusical = new EstiloMusical();
		return "/manage/manageEstiloMusical/cadastrar_estilo";
	}
	
	public String alterar(){
		this.estiloMusical = (EstiloMusical) (this.estilosMusicais.getRowData());
		return "/manage/manageEstiloMusical/cadastrar_estilo";
	}
	
	public String salvar(){
		try{
			this.estiloMusicalDAO.salvar(estiloMusical);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/manage/manageEstiloMusical/listar_estilos";
	}
		
	public void excluir(){
		Long idEstilo = ((EstiloMusical)this.estilosMusicais.getRowData()).getId();
		try{
			this.estiloMusicalDAO.excluir(idEstilo);
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	
}
