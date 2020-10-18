package br.com.testePratico.Exception;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@ControllerAdvice
public class ControllerExcecoes extends ResponseEntityExceptionHandler{
	
	
	@ExceptionHandler(RegistroNaoEncontradoException.class)
	public ResponseEntity<StandardError> objectNotFound(RegistroNaoEncontradoException e, HttpServletRequest request){
		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	
	//TRATAMENTO DA MAIORIA DOS ERROS A NIVEL DE BANCO DE DADOS
	@ExceptionHandler({DataIntegrityViolationException.class, ConstraintViolationException.class, PSQLException.class, SQLException.class})
	protected ResponseEntity<Object> handleExcpetionDataIntegry(Exception ex){
		
		String msg = "";
		
		if(ex instanceof DataIntegrityViolationException) {
			msg = ((DataIntegrityViolationException) ex).getCause().getCause().getMessage();
			
		}else if(ex instanceof ConstraintViolationException) {
			msg = ((ConstraintViolationException) ex).getCause().getCause().getMessage();
			
		}else if(ex instanceof PSQLException) {
			msg = ((PSQLException) ex).getCause().getCause().getMessage();
			
		}else if(ex instanceof SQLException) {
			msg = ((SQLException) ex).getCause().getCause().getMessage();
		}
		
		else {
			msg = ex.getMessage();
		}
		
		ObjetoErro objetoErro = new ObjetoErro();
		objetoErro.setError(msg);
		objetoErro.setCode(HttpStatus.INTERNAL_SERVER_ERROR + " ==> " + HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		
		return new ResponseEntity<>(objetoErro, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
