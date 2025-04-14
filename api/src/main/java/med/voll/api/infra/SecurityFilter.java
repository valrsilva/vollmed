package med.voll.api.infra;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.repository.UsuarioRepository;

@Component
public class SecurityFilter extends OncePerRequestFilter{

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = recuperarToken(request);
		String subjectJwt = tokenService.validarJwt(token);
		
		if(subjectJwt != null) {
			
			UserDetails usuarioByLogin = usuarioRepository.findUsuarioByLogin(subjectJwt);
			
			if(usuarioByLogin != null) {
				
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(usuarioByLogin, null, usuarioByLogin.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authToken);
				
				filterChain.doFilter(request, response);
				
			}else {
				throw new RuntimeException("Token Inválido");
			}
			
		}
		
		filterChain.doFilter(request, response);
		
	}

	private String recuperarToken(HttpServletRequest request) {
		String authorizationHeader = request.getHeader("Authorization");
		if(authorizationHeader != null) {
			return authorizationHeader.replace("Bearer ", "");
		}else {
			return "";
		}
	}

	
}
