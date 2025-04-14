package med.voll.api.domain.consulta.record;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(
		Long id, Long idMedico, Long idPaciente, LocalDateTime data) {

}
