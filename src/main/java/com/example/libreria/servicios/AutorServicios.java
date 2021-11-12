package com.example.libreria.servicios;

import com.example.libreria.entidades.Autor;
import com.example.libreria.excepciones.ErrorServicio;
import com.example.libreria.repositorios.AutorRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorServicios {

    @Autowired//llamadas al repositorio con sus metodos
    private AutorRepositorio autorRepositorio;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void crear(String nombre, Boolean alta) throws ErrorServicio {

        validar(nombre); //validar sino existe en la base de datos
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setAlta(alta);
        if (alta) {
            autor.setEliminacion(null);
        } else {
            autor.setEliminacion(new Date());
        }
        autor.setCreacion(new Date());
        autor.setModificacion(new Date());

        autorRepositorio.save(autor);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void actualizar(Long id, String nombre, Boolean alta) throws ErrorServicio {
        validar(nombre);
        //validar sino existe en la base de datos
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setNombre(nombre);
            autor.setAlta(alta);
            if (alta) {
                autor.setEliminacion(null);
            } else {
                autor.setEliminacion(new Date());
            }
            autor.setModificacion(new Date());

            autorRepositorio.save(autor);
        } else {
            throw new ErrorServicio("No se encontro el Autor por ID");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void baja(Long id) throws ErrorServicio {
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setEliminacion(new Date());
            autorRepositorio.save(autor);
        } else {
            throw new ErrorServicio("No se encontro el Autor por ID");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void alta(Long id) throws ErrorServicio {
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setEliminacion(null);
            autorRepositorio.save(autor);
        } else {
            throw new ErrorServicio("No se encontro el Autor por ID");
        }
    }

    private void validar(String nombre) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no debe ser vacio");
        }
    }

    @Transactional(readOnly = true)
    public Autor burcarPorId(Long autor_id) {
        return autorRepositorio.getById(autor_id);
    }

    @Transactional(readOnly = true)
    public List<Autor> lista() {
        return autorRepositorio.findAll();
    }

    @Transactional(readOnly = true)
    public boolean buscarPorNombreBoolean(String nombre) {
        Autor autor = autorRepositorio.buscarPorNombre(nombre);
        if (autor != null) {
            return autor.getId() > 0;
        } else {
            return false;
        }
    }

    @Transactional(readOnly = true)
    public Long buscarPorNombreId(String nombre, long id) {
        Autor autor = autorRepositorio.buscarPorNombre(nombre);
        if (autor != null) {
            return autor.getId();
        } else {
            return id;
        }
    }

}
