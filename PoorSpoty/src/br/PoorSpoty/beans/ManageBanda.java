package br.PoorSpoty.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import br.PoorSpoty.domain.Banda;
import br.PoorSpoty.domain.EstiloMusical;
import br.PoorSpoty.persistence.BandaDAO;
import br.PoorSpoty.persistence.EstiloMusicalDAO;

@ManagedBean
@SessionScoped
public class ManageBanda implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	BandaDAO bandaDAO;
	
	@EJB
	EstiloMusicalDAO estiloMusicalDAO;
	
	private Banda banda;
	private Long estilo;
	private DataModel<Banda> bandas;
	private List<SelectItem> estilos;
	
	/* Getters and Setters*/
	public Banda getBanda() {
		return banda;
	}
	public void setBanda(Banda banda) {
		this.banda = banda;
	}
	public Long getEstilo() {
		return estilo;
	}
	public void setEstilo(Long estilo) {
		this.estilo = estilo;
	}
	
	public List<SelectItem> getEstilos(){
		this.estilos = new ArrayList<SelectItem>();
		try{
			List<EstiloMusical> estilos= this.estiloMusicalDAO.listar();
			for(EstiloMusical estilo : estilos){
				this.estilos.add(new SelectItem(estilo.getId(), estilo.getNome()));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return this.estilos;
	}
	
	public DataModel<Banda> getBandas(){
		try {
			this.bandas = new ListDataModel<Banda>(bandaDAO.listar());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.bandas;
	}
	
	public String adicionar(){
		this.banda = new Banda();
		return "/manage/manageBanda/cadastrar_banda"; 
	}
	
	public String alterar(){
		this.banda = (Banda)(this.bandas.getRowData());
		this.estilo = this.banda.getEstilo().getId();
		return "/manage/manageBanda/cadastrar_banda"; 
	}
		
	public String salvar(){
		try {
			this.banda.setEstilo(estiloMusicalDAO.obter(this.estilo));
			this.bandaDAO.salvar(this.banda);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  "/manage/manageBanda/listar_bandas"; 
	}
		
	public void excluir(){
		Long idBanda = (this.bandas.getRowData()).getId();
		try {
			this.bandaDAO.excluir(idBanda);
		} catch (Exception e) {
			e.printStackTrace();
		}		 
	}

	
}
