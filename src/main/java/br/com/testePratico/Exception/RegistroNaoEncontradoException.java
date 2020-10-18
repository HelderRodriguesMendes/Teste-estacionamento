package br.com.testePratico.Exception;

public class RegistroNaoEncontradoException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public RegistroNaoEncontradoException(final String message) {
        this(message, null);
    }

	 public RegistroNaoEncontradoException(final String message, final Throwable cause) {
	        super(message, cause);
	    } 

}
