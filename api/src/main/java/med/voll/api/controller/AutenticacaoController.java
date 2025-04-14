package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import med.voll.api.domain.usuario.model.Usuario;
import med.voll.api.domain.usuario.record.DadosAutenticacao;
import med.voll.api.infra.TokenService;
import med.voll.api.infra.record.DadosTokenJWT;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

	@Autowired
	AuthenticationManager manager;
	
	@Autowired
	TokenService tokenService;
	
	@PostMapping
	public ResponseEntity<?> efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
		var authToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
		Authentication authenticate = manager.authenticate(authToken);
		Usuario usuario = (Usuario) authenticate.getPrincipal();
		String tokenJwt = tokenService.gerarToken(usuario);
		DadosTokenJWT dadosTokenJWT = new DadosTokenJWT(tokenJwt);
		return ResponseEntity.ok().body(dadosTokenJWT);
	}

	
}
