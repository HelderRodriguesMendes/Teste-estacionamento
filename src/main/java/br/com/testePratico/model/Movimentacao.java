package br.com.testePratico.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "movimentacao")
@EqualsAndHashCode(of = { "id" })
public class Movimentacao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, unique = true)
	private Long id;
	
	@Column(name = "placa", nullable = false, columnDefinition = "varchar(10)")
	private String placa;
	
	@Column(name = "modelo", nullable = false, columnDefinition = "varchar(30)")
	private String modelo;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "dataEntrada", nullable = false)
	private LocalDate dataEntrada;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "dataSaida")
	private LocalDate dataSaida;
	
	@JsonFormat(pattern = "HH:mm")
	@Column(name = "tempo")
	private LocalTime tempo;
	
	@Column(name = "valor")
	private Double valor;
	
	public Movimentacao() {}
}
