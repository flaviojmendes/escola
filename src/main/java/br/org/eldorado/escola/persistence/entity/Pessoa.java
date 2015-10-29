package br.org.eldorado.escola.persistence.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import br.org.eldorado.fw.persistence.entity.EntitySupport;

@Entity
@Table(name = "pessoa", catalog = "escola")
public class Pessoa extends EntitySupport implements java.io.Serializable {

	private Long id;
	private String nome;
	private String sobrenome;
	private String cpf;

	public Pessoa() {
	}

	public Pessoa(String nome, String sobrenome, String cpf) {
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.cpf = cpf;
		
	}


	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_pessoa", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "nome", nullable = false, length = 300)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "sobrenome", nullable = false, length = 300)
	public String getSobrenome() {
		return this.sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	@Column(name = "cpf", nullable = false, length = 20)
	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

}
