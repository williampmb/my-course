package com.login.service.mycourse.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	private final PasswordEncoder passwordEncoder;

	@Autowired
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable() // not disable if the request could be processed by a browser. For non-browser clients, it can be disabled
			.authorizeRequests()
			.antMatchers("/","index","/css/*","/js/*").permitAll()
			.antMatchers("/api/**").hasRole(UserRole.STUDENT.name()) // ROLE BASE AUTHENTICATION
			// PreAuthorize on Student Admin Controller methods
		//	.antMatchers(HttpMethod.DELETE, "/admin/api/**").hasAnyAuthority(UserPermission.COURSE_WRITE.getPermission()) // PERMISSION BASE AUTH
		//	.antMatchers(HttpMethod.POST, "/admin/api/**").hasAuthority(UserPermission.COURSE_WRITE.getPermission()) // PERMISSION BASE AUTH
		//	.antMatchers(HttpMethod.PUT, "/admin/api/**").hasAuthority(UserPermission.COURSE_WRITE.getPermission()) // PERMISSION BASE AUTH
			.antMatchers(HttpMethod.GET, "/admin/api/**").hasAnyRole(UserRole.ADMIN.name(),UserRole.ADMINTRAINEE.name()) // PERMISSION BASE AUTH
			.anyRequest()
			.authenticated()
			.and()
			.httpBasic();
	}
	
	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		UserDetails bob = User.builder()
				.username("ana")
				.password(passwordEncoder.encode("123"))
				//.roles(UserRole.STUDENT.name())
				.authorities(UserRole.STUDENT.getGrantedAuthorities())
				.build();
		
		UserDetails max = User.builder()
				.username("linda")
				.password(passwordEncoder.encode("123"))
				//.roles(UserRole.ADMIN.name())
				.authorities(UserRole.ADMIN.getGrantedAuthorities())
				.build();
		
		UserDetails tom = User.builder()
				.username("tom")
				.password(passwordEncoder.encode("123"))
				//.roles(UserRole.ADMINTRAINEE.name())
				.authorities(UserRole.ADMINTRAINEE.getGrantedAuthorities())
				.build();
		
		return new InMemoryUserDetailsManager(bob,max,tom);
	}
	
}
