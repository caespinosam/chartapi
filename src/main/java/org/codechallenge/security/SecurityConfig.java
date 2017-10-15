package org.codechallenge.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Secures the application with a Role Base authentication.
 * @author caespinosam
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder amb) throws Exception {
		amb.inMemoryAuthentication()
			.withUser("user").password("user").roles("User")
			.and().withUser("admin").password("admin").roles("Admin");
	}
	
	@Override
	protected void configure(HttpSecurity hs) throws Exception {
		hs.authorizeRequests()
			.antMatchers("/chart").hasAnyRole("User", "Admin")
			.antMatchers("/statistics").hasRole("Admin")
			.anyRequest().authenticated()
			.and().httpBasic()
			.and().csrf().disable();
	}

}
