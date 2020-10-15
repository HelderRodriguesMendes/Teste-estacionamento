package br.com.testePratico.model.DTO;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import jdk.jfr.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VeiculoSaida_DTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String placa;
	private String modelo;
	
	@DateTimeFormat
	private LocalDate dataSaida;
	
	@Timestamp
	private LocalTime tempo;
	private Double valor;
	
	public VeiculoSaida_DTO() {}
}
