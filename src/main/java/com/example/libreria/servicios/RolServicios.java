package com.example.libreria.servicios;

import com.example.libreria.entidades.Rol;
import com.example.libreria.excepciones.ErrorServicio;
import com.example.libreria.repositorios.RolRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolServicios  {

    @Autowired
    private RolRepositorio rolRepositorio;

    public Rol buscarPorId(Long id) throws ErrorServicio {
        return rolRepositorio.getById(id);
    }

    public Rol buscarPorNombre(String nombre) throws ErrorServicio {
        return rolRepositorio.buscarPorNombre(nombre);
    }
    
}
