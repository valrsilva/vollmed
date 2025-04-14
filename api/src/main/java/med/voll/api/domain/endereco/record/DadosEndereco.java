package med.voll.api.domain.endereco.record;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import med.voll.api.domain.endereco.model.Endereco;

public record DadosEndereco(
		@NotBlank @Length(max = 255) String logradouro, 
		@NotBlank @Length(max = 255) String bairro, 
		@NotBlank @Length(max = 20) String cep, 
		@NotBlank @Length(max = 255) String cidade, 
		@NotBlank @Length(min = 2, max = 2) String uf, 
		@NotNull @Positive String numero,
		@Length(max = 255) String complemento) {

	public DadosEndereco(Endereco endereco) {
		
		this(endereco.getLogradouro(), endereco.getBairro(), endereco.getCep(), endereco.getCidade(), endereco.getUf(), endereco.getNumero(), endereco.getComplemento());
			
	}

}
