package med.voll.api.domain.medico.record;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.endereco.record.DadosEndereco;

public record DadosAtualizacaoMedico(
		@NotNull Long id,
		String nome, 
		@Email String email, 
		String telefone,
		@Valid DadosEndereco endereco) {
}
