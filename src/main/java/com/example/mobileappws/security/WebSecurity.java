package com.example.mobileappws.security;

import java.util.Arrays;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.mobileappws.io.repository.UserRepository;
import com.example.mobileappws.service.UserService;

@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)//for method level security
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter{

	private final UserService userDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final UserRepository userRepository;
	
	public WebSecurity (UserService userDetailsService,BCryptPasswordEncoder bCryptPasswordEncoder,
			UserRepository userRepository)
	{
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userRepository=userRepository;
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.cors().and()
		.csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL)
				.permitAll()
				.antMatchers(HttpMethod.GET, SecurityConstants.VERIFICATION_EMAIL_URL)
				.permitAll()
				.antMatchers(HttpMethod.DELETE,"/users/**").hasRole("ACADEMICIAN") //here is for using role
														  //.hasAuthority("DELETE_AUTHORITY)
														  //hasAnyroleorauthority  for more than one role or authority
				.antMatchers(HttpMethod.POST,"/courses/**").hasRole("ACADEMICIAN") //only academician can create course
				.antMatchers(HttpMethod.PUT,"/courses/**").hasRole("ACADEMICIAN")
				.antMatchers(HttpMethod.POST,"/announcement/**").permitAll()
				.antMatchers(HttpMethod.GET,"/courses/**").permitAll()
				.anyRequest().authenticated().and().
				addFilter(getAuthenticationFilter())
				.addFilter(new AuthorizationFilter(authenticationManager(),userRepository))
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}
	
	public AuthenticationFilter getAuthenticationFilter() throws Exception
	{
		final AuthenticationFilter filter=new AuthenticationFilter(authenticationManager());
		filter.setFilterProcessesUrl("/users/login");
		return filter;
	}
	public CorsConfigurationSource corsConfigurationSource()
	{
		final CorsConfiguration configuration=new CorsConfiguration();
		
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("*"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(Arrays.asList("*"));
		
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
		
	}
	
	
	
}
