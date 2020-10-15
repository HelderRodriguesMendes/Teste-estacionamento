package br.com.testePratico.model;

import java.io.Serializable;
import java.time.LocalDate;

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
@Table(name = "valor")
@EqualsAndHashCode(of = { "id" })
public class Valor implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, unique = true)
	private Long id;
	
	@Column(name = "valor_primeira_hora", nullable = false)
	private Double valor_primeira_hora;
	
	@Column(name = "valor_demais_horas", nullable = false)
	private Double valor_demais_horas;
	
	@Column(name = "data_fim")
	private LocalDate data_fim;

	public Valor() {}

	public Valor(Double valor_primeira_hora, Double valor_demais_horas) {
		super();
		this.valor_primeira_hora = valor_primeira_hora;
		this.valor_demais_horas = valor_demais_horas;
	}
	
}
