package com.login.service.mycourse.security;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.login.service.mycourse.service.AppUserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	private final PasswordEncoder passwordEncoder;
	private final AppUserService appUserService;
	
	@Autowired
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, AppUserService appUserService) {
		this.passwordEncoder = passwordEncoder;
		this.appUserService = appUserService;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable() // not disable if the request could be processed by a browser. For non-browser clients, it can be disabled
			.authorizeRequests()
			.antMatchers("/","index","/css/*","/js/*").permitAll()
			.antMatchers("/api/**").hasRole(UserRole.STUDENT.name()) // ROLE BASE AUTHENTICATION
			.antMatchers(HttpMethod.GET, "/admin/api/**").hasAnyRole(UserRole.ADMIN.name(),UserRole.ADMINTRAINEE.name()) // PERMISSION BASE AUTH
			.anyRequest()
			.authenticated()
			.and()
			.formLogin()
				.loginPage("/login").permitAll()
				.defaultSuccessUrl("/courses",true)
				.passwordParameter("password") // To customize the name from the input. Default: password
				.usernameParameter("username") // To customize the name from the input. Default: username
			.and()
			.rememberMe()//default 2weeks
				.tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
				.key("nfiuefnqidnsadowqnhvosmxcio289s2138u12hndisandiwqei1nklnasjk312diuhsaudyg2187e8217y2183182usbjbxzcz")
				.rememberMeParameter("remember-me") // To customize the name from the input. Default: remember-me
			.and()
			.logout()
				.logoutUrl("/logout")
				.clearAuthentication(true)
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID","remember-me")
				.logoutSuccessUrl("/login"); 
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(appUserService);
		return provider;
	}
	
}
