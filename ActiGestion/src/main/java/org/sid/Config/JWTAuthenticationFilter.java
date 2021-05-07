package org.sid.Config;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sid.entity.AppUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	/**
	 * @param authenticationManager
	 */
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		super ( );
		System.out.println ( "Constructeur : " + authenticationManager );
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		System.out.println ( "AUTHENTICATION ********************" );
		AppUser user = null;
		try {
			System.out.println ( "TRY USEEEEEEEEEEEER " );
			user = new ObjectMapper ( ).readValue ( request.getInputStream ( ) , AppUser.class );
		} catch (Exception e) {
			System.out.println ( "FILTEEEEEEEEEEEEEEEEEEEEEEEER " );
			throw new RuntimeException ( e );
		}
		System.out.println ( "TRY Authentication " );
		System.out.println ( user.getUsername ( ) );
		return authenticationManager.authenticate (
				new UsernamePasswordAuthenticationToken ( user.getUsername ( ) , user.getPassword ( ) ) );
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		System.out.println ( "succeessssssss" );
		User springUser = (User) authResult.getPrincipal ( );
		String jwt = Jwts.builder ( ).setSubject ( springUser.getUsername ( ) )
				.setExpiration ( new Date ( System.currentTimeMillis ( ) + SecurityConstants.EXPIRATION_TIME ) )
				.signWith ( SignatureAlgorithm.HS256 , SecurityConstants.SERCRET )
				.claim ( "roles" , springUser.getAuthorities ( ) ).compact ( );
		response.addHeader ( SecurityConstants.HEADER_STRING , SecurityConstants.TOKEN_PREFIX + jwt );
	}
}
