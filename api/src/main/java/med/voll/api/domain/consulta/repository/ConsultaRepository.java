package med.voll.api.domain.consulta.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import med.voll.api.domain.consulta.model.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

	boolean existsByPacienteIdAndDataBetween(Long idPaciente, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);
	boolean existsByMedicoIdAndData(Long idMedico, LocalDateTime data);
	
}