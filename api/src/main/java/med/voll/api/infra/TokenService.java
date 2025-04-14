package med.voll.api.infra;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import med.voll.api.domain.usuario.model.Usuario;

@Service
public class TokenService {

	@Value("${api.security.token.secret}")
	private String secret;

	private final String ISSUER = "API Voll.Med";

	public String gerarToken(Usuario usuario) {

		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.create().withIssuer(ISSUER).withSubject(usuario.getLogin()).withExpiresAt(dataExpiracao())
					.sign(algorithm);
		} catch (JWTCreationException ex) {
			throw new RuntimeException("Erro ao Gerar Token JWT", ex);
		}
	}

	private Instant dataExpiracao() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}

	public String validarJwt(String token) {

		DecodedJWT decodedJWT;
		String subject = null;

		try {
			
			Algorithm algorithm = Algorithm.HMAC256(secret);
			JWTVerifier verifier = JWT.require(algorithm)
					.withIssuer(ISSUER)
					.build();

			decodedJWT = verifier.verify(token);
			subject = decodedJWT.getSubject();

		} catch (JWTVerificationException exception) {
			exception.printStackTrace();
		}
		
		return subject;
		

	}
}
