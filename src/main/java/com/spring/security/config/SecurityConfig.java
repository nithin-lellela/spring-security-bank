package com.spring.security.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.spring.security.filter.AuthoritiesLoggingAfterFilter;
import com.spring.security.filter.AuthoritiesLoggingAtFilter;
import com.spring.security.filter.CsrfCookieFilter;
import com.spring.security.filter.RequestValidationBeforeFilter;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		
		CsrfTokenRequestAttributeHandler requestAttribute = new CsrfTokenRequestAttributeHandler();
		requestAttribute.setCsrfRequestAttributeName("_csrf");
		
		http.securityContext().requireExplicitSave(false);
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));
		
		http.cors().configurationSource(new CorsConfigurationSource() {
			
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration config = new CorsConfiguration();
				config.setAllowedMethods(Collections.singletonList("*"));
				//config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
				config.setAllowedOrigins(Collections.singletonList("*"));
				config.setAllowCredentials(true);
				config.setAllowedHeaders(Collections.singletonList("*"));
				config.setMaxAge(3600L);
				return config;
			}
		});
		http.csrf((csrf) -> csrf.csrfTokenRequestHandler(requestAttribute).ignoringRequestMatchers("/contact", "/register")
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));
		http.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class);
		http.addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class);
		http.addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class);
		//http.addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class);
		http.authorizeHttpRequests((requests) -> requests
				/*.requestMatchers("/myAccount").hasAuthority("USER")
				.requestMatchers("/myBalance").hasAnyAuthority("USER", "ADMIN")
				.requestMatchers("/myLoan").hasAuthority("USER")*/
				.requestMatchers("/myAccount").hasRole("USER")
				.requestMatchers("/myBalance").hasAnyRole("USER", "ADMIN")
				.requestMatchers("/myLoan").hasRole("MANAGER")
				.requestMatchers("/user").authenticated()
				.requestMatchers("/notices","/contact", "/register").permitAll()
		);
		http.formLogin(withDefaults());
		http.httpBasic(withDefaults());
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		//return NoOpPasswordEncoder.getInstance();
	}
	
	/*@Bean
	public UserDetailsService userDetailsService(DataSource dataSource) {
		return new JdbcUserDetailsManager(dataSource);
	}*/
	
	
	/*@Bean
	public InMemoryUserDetailsManager userDetailsService() {
		APPROACH - 1
		UserDetails admin = User.withDefaultPasswordEncoder()
				.username("admin")
				.password("12345")
				.authorities("admin")
				.build();
		UserDetails user = User.withDefaultPasswordEncoder()
				.username("user")
				.password("12345")
				.authorities("read")
				.build();
		return new InMemoryUserDetailsManager(admin, user);
		
		
		UserDetails admin = User.withUsername("admin").password("12345").authorities("admin").build();
		UserDetails user = User.withUsername("user").password("12345").authorities("read").build();
		return new InMemoryUserDetailsManager(admin, user);
	}*/
	
}
