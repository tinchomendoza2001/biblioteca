package com.example.libreria.controladores;

import com.example.libreria.entidades.Usuario;
import com.example.libreria.excepciones.ErrorServicio;
import com.example.libreria.servicios.UsuarioServicios;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsuarioControlador {

    @Autowired
    private UsuarioServicios usuarioServicios;
    
    @PostMapping("/actualizar-usuario")
    public String registrar(ModelMap modelo, HttpSession session, @RequestParam long id, @RequestParam String nombre, @RequestParam String login, @RequestParam String clave) throws ErrorServicio{
        Usuario usuario = null;
        try{
            usuario = usuarioServicios.buscarPorId(id);
            usuarioServicios.modificar(id,nombre,login,clave);
            session.setAttribute("usuariosession",usuario);
        }catch(ErrorServicio e){
            modelo.put("error",e.getMessage());
            modelo.put("usuario",usuario);
        }
        return "usuario";
    }
}
