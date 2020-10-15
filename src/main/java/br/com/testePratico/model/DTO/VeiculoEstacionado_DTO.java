package br.com.testePratico.model.DTO;

import java.io.Serializable;
import java.time.LocalTime;

import jdk.jfr.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VeiculoEstacionado_DTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String modelo;
	private String placa;
	
	@Timestamp
	private LocalTime tempo;
	
	public VeiculoEstacionado_DTO() {}

}
