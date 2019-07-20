package com.groupal.universia.security;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableAutoConfiguration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
   
	@Autowired
    PasswordEncoder passwordEncoder; 

    @Autowired
    DataSource dataSource;
    
    @Autowired
    CustomAuthenticationSuccessHandler customSuccessHandler;
    
   
    @Override
	protected void configure(HttpSecurity http) throws Exception {
    	 http.csrf().disable();      
		http.authorizeRequests().antMatchers("/","/index","/css/**","/js/**","/img/**","/register","/login","/user","/guardarEstudiante","/403").permitAll()
                                .antMatchers("/welcome","/guardarProfesor","/nuevoProfesor","/guardarCarrera","/editarCarrera","/administrarCarreras","/nuevaCarrera","/administrarAulas","/administrarCarreraMaterias","/administrarInscripciones").access("hasRole('ADMIN')")
                                
                                .antMatchers("/administrarEstudiantes").access("hasRole('ESTUDIANTE') or hasRole('ADMIN')")
                                
                                .antMatchers("/administrarProfesores").access("hasRole('PROFESOR') or hasRole('ADMIN')")
                                .anyRequest().authenticated()
                                .and()
                                .formLogin().loginPage("/login").successHandler(customSuccessHandler)
                                .and()
                                .logout()
                                .permitAll();
                                http.exceptionHandling().accessDeniedPage("/403");
	}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
        
                .usersByUsernameQuery("select username,password, activo from estudiante where username=?")
                .authoritiesByUsernameQuery("select username, role from role where username=?");

        
        auth.jdbcAuthentication().dataSource(dataSource)
        
                .usersByUsernameQuery("select username,password, activo from profesor where username=?")
                .authoritiesByUsernameQuery("select username, role from role where username=?");

        
        auth.jdbcAuthentication().dataSource(dataSource)
        		
                .usersByUsernameQuery("select username,password, activo from usuario where username=?")
                .authoritiesByUsernameQuery("select username, role from role where username=?");


        auth.inMemoryAuthentication().withUser("administrador").password("12345").roles("ADMIN");
//    	 auth.inMemoryAuthentication()
//         .passwordEncoder(passwordEncoder)
//         .withUser("user").password(passwordEncoder.encode("12345")).roles("USER")
//         .and()
//         .withUser("admin").password(passwordEncoder.encode("12345")).roles("USER", "ADMIN");
                
        
                

    }
    
   


}

