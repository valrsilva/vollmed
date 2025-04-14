package med.voll.api.domain.consulta.validacoes;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import med.voll.api.domain.consulta.record.DadosAgendamentoConsulta;
import med.voll.api.infra.ValidacaoException;

@Component
public class ValidadorHorarioAntecedencia {

	public void validar(DadosAgendamentoConsulta dados) {

		var dataConsulta = dados.data();
		var agora = LocalDateTime.now();
		var diferencaEmMinutos = Duration.between(agora, dataConsulta).toMinutes();

		if (diferencaEmMinutos < 30) {
			throw new ValidacaoException("Consulta deve ser agendada com antecedência de no mínimo 30 minutos");
		}
	}

}
