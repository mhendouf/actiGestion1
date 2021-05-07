package org.sid;

import org.sid.repository.UserRepository;
import org.sid.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class ActiGestionApplication implements CommandLineRunner {
	@Autowired
	private AccountService accountService;
	@Autowired
	private UserRepository ur;

	@Bean
	public BCryptPasswordEncoder getBCryptPasswordEncoder() {

		return new BCryptPasswordEncoder ( );
	}

	@Bean
	public ObjectMapper getObjectMapper() {

		return new ObjectMapper ( );
	}

	public static void main(String[] args) {
		SpringApplication.run ( ActiGestionApplication.class , args );
	}

	@Override
	public void run(String... args0) throws Exception {

		/*
		 * System.out.println ( "RUN OK *******************" );
		 * 
		 * AppUser admin = new AppUser ( null , "admin" , "1234" , null ); AppUser user
		 * = new AppUser ( null , "user" , "1234" , null ); accountService.saveUser (
		 * admin ); accountService.saveUser ( user ); AppRoles adminRole = new AppRoles
		 * ( null , "ADMIN" ); AppRoles userRole = new AppRoles ( null , "USER" );
		 * accountService.saveRole ( adminRole ); accountService.saveRole ( userRole );
		 * accountService.addRolesToUser ( "admin" , "USER" );
		 * accountService.addRolesToUser ( "admin" , "ADMIN" );
		 * accountService.addRolesToUser ( "user" , "USER" );
		 */

	}

}
