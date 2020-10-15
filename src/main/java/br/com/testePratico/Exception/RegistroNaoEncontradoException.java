package br.com.testePratico.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RegistroNaoEncontradoException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public RegistroNaoEncontradoException(final String message) {
        this(message, null);
    }

	 public RegistroNaoEncontradoException(final String message, final Throwable cause) {
	        super(message, cause);
	    } 

}
