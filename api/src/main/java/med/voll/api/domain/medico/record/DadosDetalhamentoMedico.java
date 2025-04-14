package med.voll.api.domain.medico.record;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.record.DadosEndereco;
import med.voll.api.domain.medico.enums.Especialidade;
import med.voll.api.domain.medico.model.Medico;

public record DadosDetalhamentoMedico(@NotNull Long id, @NotBlank String nome, @NotBlank @Email String email,
		@NotBlank String telefone, @NotBlank @Pattern(regexp = "\\d{4,6}") String crm,
		@NotNull Especialidade especialidade, @NotNull @Valid DadosEndereco endereco) {

	public DadosDetalhamentoMedico(Medico medico) {
		this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getTelefone(), medico.getCrm(),
				medico.getEspecialidade(), new DadosEndereco(medico.getEndereco()));
	}
}
