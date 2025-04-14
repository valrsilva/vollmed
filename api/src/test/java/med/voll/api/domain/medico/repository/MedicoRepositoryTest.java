package med.voll.api.domain.medico.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import med.voll.api.domain.consulta.model.Consulta;
import med.voll.api.domain.endereco.model.Endereco;
import med.voll.api.domain.medico.enums.Especialidade;
import med.voll.api.domain.medico.model.Medico;
import med.voll.api.domain.paciente.Paciente;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

	@Autowired
	MedicoRepository medicoRepository;
	
	@Autowired
	TestEntityManager em;
	
	@Test
	@DisplayName("Deveria retornar NULL quando unico medico cadastrado não está disponível na data")
	void testEscolherMedicoAleatorioLivreNaData() {
		
		LocalDateTime proximaSegundaAsDez = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
		
		Endereco endereco = new Endereco("RUA XPTO", "BAIRRO XPTO", "02927130", "SAO PAULO", "SP", "100", "AP 34");
		Medico medico = new Medico(1L, "JOSÉ", "jose@email.com", "994067920", "123456", Especialidade.CARDIOLOGIA, endereco, true);
		Paciente paciente = new Paciente(1L, "ARNALDO", "arnaldo@email.com", "911112222", "11111111111", endereco, true);
		Consulta consulta = new Consulta(1L, medico, paciente, proximaSegundaAsDez);
		
		cadastrarMedico(medico);
		cadastrarPaciente(paciente);
		cadastrarConsulta(consulta);		
		
		Medico escolherMedicoAleatorioLivreNaData = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAsDez);
		
		assertThat(escolherMedicoAleatorioLivreNaData).isNull();
		
	}
	
	@Test
	@DisplayName("Deveria retornar médico quando ele estiver disponível na data")
	void testDeveriaRetornarMedicoAleatorioLivreNaData() {
		
		LocalDateTime proximaSegundaAsDez = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
		
		Endereco endereco = new Endereco("RUA XPTO", "BAIRRO XPTO", "02927130", "SAO PAULO", "SP", "100", "AP 34");
		Medico medico = new Medico(1L, "JOSÉ", "jose@email.com", "994067920", "123456", Especialidade.CARDIOLOGIA, endereco, true);
		Paciente paciente = new Paciente(1L, "ARNALDO", "arnaldo@email.com", "911112222", "11111111111", endereco, true);
		Consulta consulta = new Consulta(1L, medico, paciente, proximaSegundaAsDez);
		
		cadastrarMedico(medico);
		cadastrarPaciente(paciente);
		cadastrarConsulta(consulta);		
		
		Medico escolherMedicoAleatorioLivreNaData = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAsDez);
		
		assertThat(escolherMedicoAleatorioLivreNaData).isEqualTo(medico);
		
	}
	
	private Medico cadastrarMedico(Medico medico) {
		em.persist(medico);
		return medico;
	}
	
	private Paciente cadastrarPaciente(Paciente paciente) {
		em.persist(paciente);
		return paciente;
	}
	
	private Consulta cadastrarConsulta(Consulta consulta) {
		em.persist(consulta);
		return consulta;
	}

}
