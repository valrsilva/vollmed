package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.record.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.record.DadosDetalhamentoConsulta;
import med.voll.api.domain.consulta.service.AgendaDeConsultas;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

	@Autowired
	AgendaDeConsultas agendaDeConsultas;

	@PostMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoConsulta> agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
		
		DadosDetalhamentoConsulta agendar = agendaDeConsultas.agendar(dados);
		return ResponseEntity.ok().body(agendar);
		
	}

}
