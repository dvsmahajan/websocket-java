package com.dvsapp.dvsfinalwebsocketapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.dvsapp.dvsfinalwebsocketapp.config.jwt.JwtAuthenticationEntryPoint;
import com.dvsapp.dvsfinalwebsocketapp.config.jwt.JwtSecurityConfigurer;
import com.dvsapp.dvsfinalwebsocketapp.config.jwt.JwtTokenProvider;
import com.dvsapp.dvsfinalwebsocketapp.repository.IUserRepository;

@Configuration
@EnableWebSecurity
@EnableJpaRepositories(basePackageClasses = IUserRepository.class)
public class UserApp extends WebSecurityConfigurerAdapter  {


	
	@Autowired
    private JwtTokenProvider jwtTokenProvider;

	@Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;
	
 
	
	@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http
            .httpBasic().disable().csrf().disable().cors().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests().antMatchers("/user/signin","/user/save","/ws/**").permitAll().anyRequest().authenticated()
             .and()
            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
            .apply(new JwtSecurityConfigurer(jwtTokenProvider));
        http.addFilterBefore(new CorsFilter(),UsernamePasswordAuthenticationFilter.class);
        //@formatter:on
    }
    
    
    @Bean
    public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

    
//    @Bean
//	public CorsConfigurationSource corsConfigurationSource() {
//		CorsConfiguration configuration = new CorsConfiguration();
//		configuration.setAllowedOrigins(Arrays.asList("*"));
//		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
//		configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
//		configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", configuration);
//		return source;
//	}
}