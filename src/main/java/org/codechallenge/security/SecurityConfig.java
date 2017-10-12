package org.codechallenge.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

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
		.anyRequest().authenticated()
			.antMatchers("/chartapi/chart").hasAnyRole("User", "Admin")
			.antMatchers("/chartapi/statistics").hasRole("Admin")
			.and().httpBasic()
			//.and().requestCache().requestCache(new NullRequestCache())
			.and().csrf().disable();
	}

}
