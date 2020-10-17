package br.com.testePratico.model.DTO;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VeiculoSaida_DTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataSaida;
	
	@JsonFormat(pattern = "HH:mm")
	private LocalTime tempo;
	
	public VeiculoSaida_DTO() {}
}
