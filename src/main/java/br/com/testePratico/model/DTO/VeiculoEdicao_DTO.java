package br.com.testePratico.model.DTO;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VeiculoEdicao_DTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String placa;
	private String modelo;
	
	public VeiculoEdicao_DTO(){}
}
