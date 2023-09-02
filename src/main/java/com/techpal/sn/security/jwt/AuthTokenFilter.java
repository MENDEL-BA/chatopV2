package com.techpal.sn.security.jwt;

import com.techpal.sn.security.services.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String jwt = parseJwt(request);
			if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
				String username = jwtUtils.getUserNameFromJwtToken(jwt);
				System.out.println("Nom d'utilisateur : " + request.getUserPrincipal().getName());

				// Chargez les détails de l'utilisateur depuis la source de données
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);

				if (userDetails != null) {
					// Créez l'objet d'authentification
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					// Définissez l'authentification dans le contexte de sécurité
					SecurityContextHolder.getContext().setAuthentication(authentication);
				} else {
					// Gérez le cas où l'utilisateur n'est pas trouvé
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Utilisateur non trouvé.");
					return;
				}
			}
		} catch (Exception e) {
			logger.error("Impossible de définir l'authentification de l'utilisateur : {}", e.getMessage());
		}

		filterChain.doFilter(request, response);
	}


	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}

		return null;
	}
}
