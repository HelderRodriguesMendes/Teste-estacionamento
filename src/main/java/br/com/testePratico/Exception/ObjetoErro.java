package br.com.testePratico.Exception;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObjetoErro implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String error;
	private String code;
	
	public ObjetoErro() {
		
	}


}
