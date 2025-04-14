package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.medico.model.Medico;
import med.voll.api.domain.medico.record.DadosAtualizacaoMedico;
import med.voll.api.domain.medico.record.DadosDetalhamentoMedico;
import med.voll.api.domain.medico.record.DadosListagemMedico;
import med.voll.api.domain.medico.record.DadosMedico;
import med.voll.api.domain.medico.repository.MedicoRepository;

@RestController
@RequestMapping("/medico")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {

	@Autowired
	MedicoRepository medicoRepository;

	@PostMapping
	@Transactional // faz com que qualquer alteracao na entidade seja refletido no banco
	public ResponseEntity<Medico> incluirMedico(@RequestBody @Valid DadosMedico dados,
			UriComponentsBuilder uriBuilder) {
		Medico save = medicoRepository.save(new Medico(dados));
		return ResponseEntity.created(uriBuilder.path("/medico/{id}").buildAndExpand(save.getId()).toUri()).body(save);
	}

	@PutMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoMedico> atualizarMedico(@RequestBody @Valid DadosAtualizacaoMedico dados) {
		Medico medico = medicoRepository.getReferenceById(dados.id());
		medico.atualizarDados(dados);
		return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> excluirMedico(@PathVariable Long id) {
		Medico medico = medicoRepository.getReferenceById(id);
		medico.excluir();
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<DadosDetalhamentoMedico> detalharMedico(@PathVariable Long id) {
		Medico medico = medicoRepository.getReferenceById(id);
		return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
	}

	/*
	 * @GetMapping public List<DadosListagemMedico> listarMedicos(Pageable page) {
	 * return
	 * medicoRepository.findAll().stream().map(DadosListagemMedico::new).toList(); }
	 */

	@GetMapping
	public Page<DadosListagemMedico> listarMedicos(@PageableDefault(size = 10, sort = { "nome" }) Pageable page) {
		return medicoRepository.findAllByAtivoTrue(page).map(DadosListagemMedico::new);
	}
}
