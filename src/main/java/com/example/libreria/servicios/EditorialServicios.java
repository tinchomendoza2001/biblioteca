package com.example.libreria.servicios;

import com.example.libreria.entidades.Editorial;
import com.example.libreria.excepciones.ErrorServicio;
import com.example.libreria.repositorios.EditorialRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialServicios {

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void crear(String nombre, Boolean alta) throws ErrorServicio {
        validar(nombre);

        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setAlta(alta);
        if (alta) {
            editorial.setEliminacion(null);
        } else {
            editorial.setEliminacion(new Date());
        }
        editorial.setCreacion(new Date());
        editorial.setModificacion(new Date());

        editorialRepositorio.save(editorial);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void actualizar(Long id, String nombre, Boolean alta) throws ErrorServicio {
        validar(nombre);
        //validar sino existe en la base de datos
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setNombre(nombre);
            editorial.setAlta(alta);
            if (alta) {
                editorial.setEliminacion(null);
            } else {
                editorial.setEliminacion(new Date());
            }
            editorial.setModificacion(new Date());

            editorialRepositorio.save(editorial);
        } else {
            throw new ErrorServicio("No se encontro el Autor por ID");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    private void validar(String nombre) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no debe ser vacio");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void baja(Long id) throws ErrorServicio {
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setEliminacion(new Date());
            editorialRepositorio.save(editorial);
        } else {
            throw new ErrorServicio("No se encontro el Autor por ID");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void alta(Long id) throws ErrorServicio {
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setEliminacion(null);
            editorialRepositorio.save(editorial);
        } else {
            throw new ErrorServicio("No se encontro el Autor por ID");
        }
    }

    //solo de carga para lectura, no carga todo  ->    @Transactional(readOnly = true)
    @Transactional(readOnly = true)
    public Editorial burcarPorId(Long editorial_id) {
        Optional<Editorial> respuesta = editorialRepositorio.findById(editorial_id);
        if (respuesta.isPresent()) {
            return respuesta.get();
        }
        return null;
    }

    @Transactional(readOnly = true)
    public boolean buscarPorNombreBoolean(String nombre) {
        Editorial editorial = editorialRepositorio.buscarPorNombre(nombre);
        if (editorial != null) {
            return editorial.getId() > 0;
        } else {
            return false;
        }
    }

    @Transactional(readOnly = true)
    public Long buscarPorNombreId(String nombre, Long id) {
        Editorial editorial = editorialRepositorio.buscarPorNombre(nombre);
        if (editorial != null) {
            return editorial.getId();
        } else {
            return id;
        }
    }

    @Transactional(readOnly = true)
    public List<Editorial> lista() {
        return editorialRepositorio.findAll();
    }
}
