package br.PoorSpoty.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Banda implements Serializable{
	
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String nome;
	
	private int tipo; // 1 - banda; 0 - musico/artista	
	
	@ManyToOne (fetch = FetchType.EAGER)
	private EstiloMusical estilo;
	
	
	// Atributos transientes
	@Transient
	private String membros;
	@Transient
	private String descricao;
	@Transient
	private String site;
	@Transient
	private int load; // indica se já foi carregado as informações do database 1 = sim, 0 = nao
	
	
		
	public int getLoad() {
		return load;
	}

	public void setLoad(int load) {
		this.load = load;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getMembros() {
		return membros;
	}

	public void setMembros(String membros) {
		this.membros = membros;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		nome = nome.toUpperCase();
		this.nome = nome;
	}

	public EstiloMusical getEstilo() {
		return estilo;
	}

	public void setEstilo(EstiloMusical estilo) {
		this.estilo = estilo;
	}
	
	public void printBanda (){
		System.out.print(this.id+" ");
		System.out.print(this.nome+"\n");
	}
	
}
