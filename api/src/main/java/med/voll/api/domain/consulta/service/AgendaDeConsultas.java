package med.voll.api.domain.consulta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import med.voll.api.domain.consulta.model.Consulta;
import med.voll.api.domain.consulta.record.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.repository.ConsultaRepository;
import med.voll.api.domain.medico.model.Medico;
import med.voll.api.domain.medico.repository.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.ValidacaoException;

@Service
public class AgendaDeConsultas {

	@Autowired
	ConsultaRepository consultaRepository;
	
	@Autowired
	MedicoRepository medicoRepository;
	
	@Autowired
	PacienteRepository pacienteRepository;
	
	public void agendar(@Valid DadosAgendamentoConsulta dados) {
		
		if(!pacienteRepository.existsById(dados.idPaciente())) {
			throw new ValidacaoException("Paciente não existe");
		}
		
		Paciente paciente = pacienteRepository.getReferenceById(dados.idPaciente());
		
		if(dados.idMedico() != null && medicoRepository.existsById(dados.idMedico())) {
			throw new ValidacaoException("Médico não existe");
		}
		
		Medico medico = escolherMedico(dados);
		
		Consulta consulta = new Consulta(null, medico, paciente, dados.data());
		consultaRepository.save(consulta); 
		
	}
	
	private Medico escolherMedico(DadosAgendamentoConsulta dados) {
		
		if(dados.idMedico() != null) {
			return medicoRepository.getReferenceById(dados.idMedico());
		}
		if(dados.especialidade() == null) {
			throw new ValidacaoException("Especialidade não informada");
		}
		
		return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
		
	}
	
}
