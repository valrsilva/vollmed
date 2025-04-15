package med.voll.api.infra;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class TratadorExceptions {

	@ExceptionHandler(ValidacaoException.class)
	public ResponseEntity<?> tratarValidacaoException(ValidacaoException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> tratarErroNotFound() {
		return ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> tratarErroValidations(MethodArgumentNotValidException ex){
		List<FieldError> fieldErrors = ex.getFieldErrors();
		List<DadosErroValidacao> list = fieldErrors.stream().map(DadosErroValidacao::new).toList();
		return ResponseEntity.badRequest().body(list);
	}
}
