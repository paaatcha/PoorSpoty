package br.PoorSpoty.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


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
	private int tipo; /* 1 : Admin; 0 : Usuario Normal*/
	private int avatar;
	
	@Temporal(TemporalType.DATE)
	private Date dataNasc;
	
	@ManyToMany	(fetch = FetchType.EAGER)
	private List<Banda> bandas;// = new ArrayList<Banda>();

	@ManyToMany (fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_estilomusical_sim")
	private List<EstiloMusical> estilos;// = new ArrayList<EstiloMusical>();
	
	@ManyToMany (fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_estilomusical_nao")
	private List<EstiloMusical> estilosNao;// = new ArrayList<EstiloMusical>();

	public int getAvatar() {
		return avatar;
	}

	public void setAvatar(int avatar) {
		this.avatar = avatar;
	}
	
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
	
	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public void printUsuario (){
		System.out.println("Imprimindo usuaio:");
		System.out.println(this.nome);
		
		System.out.println("Estilos gosta:");
		for (int i=0; i<this.estilos.size(); i++){
			this.estilos.get(i).printEstilo();
		}
		
		System.out.println("Estilos Nao gosta:");
		for (int i=0; i<this.estilosNao.size(); i++){
			this.estilosNao.get(i).printEstilo();
		}
		
		System.out.println("Bandas:");
		for (int i=0; i<this.bandas.size(); i++){
			this.bandas.get(i).printBanda();
		}			
		
	}
	
	
	
}
