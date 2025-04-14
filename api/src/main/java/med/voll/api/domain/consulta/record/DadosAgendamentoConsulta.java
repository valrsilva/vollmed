package med.voll.api.domain.consulta.record;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.enums.Especialidade;

public record DadosAgendamentoConsulta(
		Long idMedico, @NotNull Long idPaciente, @NotNull @Future LocalDateTime data, Especialidade especialidade) {

}
