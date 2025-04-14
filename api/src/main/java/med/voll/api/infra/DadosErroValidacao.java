package med.voll.api.infra;

import org.springframework.validation.FieldError;

public record DadosErroValidacao(String campo, String mensagem) {

	public DadosErroValidacao(FieldError f) {
		this(f.getField(),f.getDefaultMessage()); 
	}
}
