package com.example.libreria.servicios;

import com.example.libreria.entidades.Rol;
import com.example.libreria.entidades.Usuario;
import com.example.libreria.excepciones.ErrorServicio;
import com.example.libreria.repositorios.UsuarioRepositorio;
import java.util.Collections;
import java.util.Date;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/*
import java.util.ArrayList;
import java.util.List;
 */

@Service
public class UsuarioServicios implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private BCryptPasswordEncoder encoder;

    private String mensaje = "No existe ningún usuario asociado con el ID %s";

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) throws ErrorServicio {
        return usuarioRepositorio.getById(id);
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorNombre(String nombre) throws ErrorServicio {
        return usuarioRepositorio.buscarPorNombre(nombre);
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorLogin(String login) throws ErrorServicio {
        return usuarioRepositorio.buscarPorLogin(login);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void crear(String nombre, String login, String password, Rol rol) throws ErrorServicio {
        Usuario usuario = usuarioRepositorio.buscarPorLogin(login);
        if (usuario != null) {
            throw new ErrorServicio("Ya existe cuenta de usuario");
        }        
        usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setLogin(login);
        usuario.setAlta(new Date());
        usuario.setEstado(Boolean.TRUE);
        usuario.setRol(rol);
        usuario.setPassword(encoder.encode(password));
        usuarioRepositorio.save(usuario);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void modificar(Long id, String nombre, String login, String password, Rol rol, Boolean alta) throws ErrorServicio {
        Usuario usuario = usuarioRepositorio.getById(id);
        usuario.setNombre(nombre);
        usuario.setLogin(login);
        usuario.setEstado(alta);
        usuario.setRol(rol);
        usuario.setPassword(encoder.encode(password));
        usuarioRepositorio.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarPorLogin(string);
        GrantedAuthority authority = new SimpleGrantedAuthority(Long.toString(usuario.getRol().getId()));

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(true);

        session.setAttribute("id", usuario.getId());
        session.setAttribute("nombre", usuario.getNombre());
        session.setAttribute("login", usuario.getLogin());
        session.setAttribute("rol", usuario.getRol().getNombre());

        return new User(usuario.getLogin(), usuario.getPassword(), Collections.singletonList(authority));
    }
    /* 
    
    implements UserDetailsService

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
