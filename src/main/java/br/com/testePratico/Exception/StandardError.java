package br.com.testePratico.Exception;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StandardError implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer status;
	private String msg;
	
	public StandardError() {
		
	}
	
	public StandardError(Integer status, String msg) {
		super();
		this.status = status;
		this.msg = msg;
	}

}
