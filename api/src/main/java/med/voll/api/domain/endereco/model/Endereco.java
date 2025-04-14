package med.voll.api.domain.endereco.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import med.voll.api.domain.endereco.record.DadosEndereco;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
    private String logradouro;
    private String bairro;
    private String cep;
    private String cidade;
    private String uf;
    private String numero;
    private String complemento;
    
	public void atualizarDados(@Valid DadosEndereco endereco) {
		
		if(endereco.bairro() != null) {
			this.bairro = endereco.bairro();
		}
		if(endereco.cep() != null) {
			this.cep = endereco.cep();
		}
		if(endereco.cidade() != null) {
			this.cidade = endereco.cidade();
		}
		if(endereco.complemento() != null) {
			this.complemento = endereco.complemento();
		}
		if(endereco.logradouro() != null) {
			this.logradouro = endereco.logradouro();
		}
		if(endereco.numero() != null) {
			this.numero = endereco.numero();
		}
		if(endereco.uf() != null) {
			this.uf = endereco.uf();
		}
		
	}

	public Endereco(@NotNull @Valid DadosEndereco endereco) {
		
		this.bairro = endereco.bairro();
		this.cep = endereco.cep();
		this.cidade = endereco.cidade();
		this.complemento = endereco.complemento();
		this.logradouro = endereco.logradouro();
		this.numero = endereco.numero();
		this.uf = endereco.uf();
		
	}
}