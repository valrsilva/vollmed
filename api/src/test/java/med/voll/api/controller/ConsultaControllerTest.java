package med.voll.api.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import med.voll.api.domain.consulta.record.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.record.DadosDetalhamentoConsulta;
import med.voll.api.domain.consulta.service.AgendaDeConsultas;
import med.voll.api.domain.medico.enums.Especialidade;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJson;
	
	@Autowired
	private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJson;

	@MockitoBean
	private AgendaDeConsultas agendaDeConsultas;
	
	@Test
	@DisplayName("Deveria devolver codigo http 400 quando informacoes estao invalidas")
	@WithMockUser
	void agendarCenario1() throws Exception {

		var response = mvc.perform(MockMvcRequestBuilders.post("/consultas")).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

	}

	@Test
	@DisplayName("Deveria devolver codigo http 200 quando informacoes estao validas")
	@WithMockUser
	void agendarCenario2() throws Exception {

		var data = LocalDateTime.now().plusHours(1);
		var dadosDetalhamentoConsulta = new DadosDetalhamentoConsulta(null, 2L, 5L, data);
				
		Mockito.doReturn(dadosDetalhamentoConsulta).when(agendaDeConsultas).agendar(Mockito.any());

		var response = mvc.perform(MockMvcRequestBuilders.post("/consultas").contentType(MediaType.APPLICATION_JSON)
				.content(dadosAgendamentoConsultaJson
						.write(new DadosAgendamentoConsulta(2L, 5L, data, Especialidade.CARDIOLOGIA)).getJson()))
				.andReturn().getResponse();


		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		
		var jsonEsperado = dadosDetalhamentoConsultaJson.write(dadosDetalhamentoConsulta).getJson();
		
		assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);

	}

}
