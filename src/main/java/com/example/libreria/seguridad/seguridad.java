package com.example.libreria.seguridad;

import com.example.libreria.servicios.UsuarioServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class seguridad extends WebSecurityConfigurerAdapter {
   
    @Autowired
    private UsuarioServicios usuarioService;
    
    @Autowired
    private BCryptPasswordEncoder encoder;    
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioService).passwordEncoder(encoder);
    }    
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/admin/*").hasRole("1")
                    .antMatchers("/font/*","/css/*", "/js/*", "/img/*").permitAll()
                    //.antMatchers("/**").authenticated()
                    .and().
                formLogin()
                    .loginPage("/index")
                    .loginProcessingUrl("/loginIn")
                    .usernameParameter("usuario")
                    .passwordParameter("contrasenia")
                    .defaultSuccessUrl("/libro")
                    .permitAll()
                    .and().logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")             
                    .permitAll().
                and().csrf().disable();
    }
}
