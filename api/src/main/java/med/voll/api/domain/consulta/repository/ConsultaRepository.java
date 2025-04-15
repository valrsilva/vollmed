package med.voll.api.domain.consulta.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.consulta.model.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

	boolean existsByPacienteIdAndDataBetween(Long idPaciente, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);
	boolean existsByMedicoIdAndData(Long idMedico, LocalDateTime data);
	List<Consulta> findAllByData(LocalDateTime data);
	
}