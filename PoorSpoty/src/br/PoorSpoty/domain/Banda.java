package br.PoorSpoty.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Banda implements Serializable{
	
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String nome;
	
	@ManyToOne (fetch = FetchType.EAGER)
	private EstiloMusical estilo;
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
