package br.PoorSpoty.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.jboss.resteasy.spi.touri.MappedBy;


@Entity
public class Usuario implements Serializable{
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	private String sobrenome;
	private String email;
	private String senha;
	private char sexo;
	private String nick;
	private String descricao;
	
	@Temporal(TemporalType.DATE)
	private Date dataNasc;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioBanda")		
	private List<Banda> bandas;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")	
	private List<EstiloMusical> estilos;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")	
	private List<EstiloMusical> estilosNao;

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
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public char getSexo() {
		return sexo;
	}

	public void setSexo(char sexo) {
		this.sexo = sexo;
	}

	public Date getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}
	
	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Banda> getBandas() {
		return bandas;
	}

	public void setBandas(List<Banda> bandas) {
		this.bandas = bandas;
	}

	public List<EstiloMusical> getEstilos() {
		return estilos;
	}

	public void setEstilos(List<EstiloMusical> estilos) {
		this.estilos = estilos;
	}

	public List<EstiloMusical> getEstilosNao() {
		return estilosNao;
	}

	public void setEstilosNao(List<EstiloMusical> estilosNao) {
		this.estilosNao = estilosNao;
	}	
	
	
}
