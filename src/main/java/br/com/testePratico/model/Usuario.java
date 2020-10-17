package br.com.testePratico.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "usuario")
@EqualsAndHashCode(of = { "id" })
public class Usuario  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, unique = true)
	private Long id;
	
	@Column(name = "nome", nullable = false, columnDefinition = "varchar(10)")
	private String nome;
	
	@Column(name = "usuario", nullable = false, columnDefinition = "varchar(10)")
	private String usuario ;
	
	@Column(name = "senha", nullable = false, columnDefinition = "varchar(10)")
	private String senha;
	
	public Usuario() {}

	public Usuario(String nome, String usuario, String senha) {
		super();
		this.nome = nome;
		this.usuario = usuario;
		this.senha = senha;
	}
}
