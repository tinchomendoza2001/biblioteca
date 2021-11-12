package com.example.libreria.servicios;
import org.springframework.stereotype.Service;
/*
import com.example.libreria.entidades.Usuario;
import com.example.libreria.excepciones.ErrorServicio;
import com.example.libreria.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
*/
@Service
public class UsuarioServicios {
/* implements UserDetailsService
    @Autowired//llamadas al repositorio con sus metodos
    private UsuarioRepositorio usuarioRepositorio;
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void registrar(String nombre, String login, String password) throws ErrorServicio {
        Usuario usuario = usuarioRepositorio.buscarPorLogin(login);
        if (usuario != null) {
            throw new ErrorServicio("Ya existe cuenta de usuario");
        }
        usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setLogin(login);        
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setAlta(new Date());
        usuario.setBaja(null);

        usuarioRepositorio.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarPorLogin(login);
        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList();
            permisos.add(new SimpleGrantedAuthority("MODULO_AUTOR"));
            permisos.add(new SimpleGrantedAuthority("MODULO_EDITORIAL"));
            permisos.add(new SimpleGrantedAuthority("MODULO_LIBRO"));
            User user = new User(usuario.getLogin(), usuario.getPassword(), permisos);
            return user;
        }
        return null;
    }
*/
}
