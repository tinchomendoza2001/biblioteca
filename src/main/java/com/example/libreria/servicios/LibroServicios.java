package com.example.libreria.servicios;

import com.example.libreria.entidades.Autor;
import com.example.libreria.entidades.Editorial;
import com.example.libreria.entidades.Libro;
import com.example.libreria.excepciones.ErrorServicio;
import com.example.libreria.repositorios.LibroRepositorio;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroServicios {

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Autowired
    private AutorServicios as;
    @Autowired
    private EditorialServicios es;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void crear(String isbn, String titulo, Integer anio, Boolean alta, Integer ejemplares, Long autor_id, Long editorial_id) throws ErrorServicio {
        Autor autor = as.burcarPorId(autor_id);
        Editorial editorial = es.burcarPorId(editorial_id);
        validar(isbn, titulo, anio, ejemplares, autor, editorial);
        Libro libro = new Libro();
        libro.setAlta(alta);
        libro.setAnio(anio);
        libro.setAutor(autor);
        libro.setCreacion(new Date());
        libro.setEditorial(editorial);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(0);
        libro.setEjemplaresRestantes(0);
        libro.setEliminacion(null);
        libro.setIsbn(isbn);
        libro.setModificacion(new Date());
        libro.setTitulo(titulo);
        libroRepositorio.save(libro);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void actualizar(Long id, String isbn, String titulo, Integer anio, Boolean alta, Integer ejemplares, Long autor_id, Long editorial_id) throws ErrorServicio {
        Autor autor = as.burcarPorId(autor_id);
        Editorial editorial = es.burcarPorId(editorial_id);
        validar(isbn, titulo, anio, ejemplares, autor, editorial);
        Libro libro = libroRepositorio.getById(id);
        if (libro == null) {
            throw new ErrorServicio("No se encontró el libro a actualizar");
        }
        libro.setAnio(anio);
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        libro.setEjemplares(ejemplares);
        libro.setIsbn(isbn);
        libro.setModificacion(new Date());
        libro.setTitulo(titulo);
        libro.setAlta(alta);
        libroRepositorio.save(libro);
    }

    private void validar(String isbn, String nombre, Integer anio, Integer ejemplares, Autor autor, Editorial editorial) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no debe ser vacio");
        }
        if (isbn == null || isbn.isEmpty()) {
            throw new ErrorServicio("El isbn no debe ser vacio");
        }
        if (anio == null) {
            throw new ErrorServicio("El año no debe ser vacio");
        }
        if (ejemplares == null) {
            throw new ErrorServicio("Los ejemplares no debe ser vacio");
        }        
        if (autor == null) {
            throw new ErrorServicio("El Autor no existe");
        }
        if (editorial == null) {
            throw new ErrorServicio("La Editorial no existe");
        }
    }
    
    public List<Libro> lista(){
        return libroRepositorio.findAll();
    }

    public long buscarPorIsbnId(String isbn, long id) {
        Libro libro = libroRepositorio.buscarPorIsbn(isbn);
        if(libro != null){
            return libro.getId();
        }else{
            return id;
        }   
    }

    public boolean buscarPorTituloBoolean(String titulo) {
         Libro libro = libroRepositorio.buscarPorTitulo(titulo);
        if(libro != null){
            return libro.getId() > 0;
        }else{
            return false;
        }
    }

    public boolean buscarPorIsbnBoolean(String isbn) {
         Libro libro = libroRepositorio.buscarPorIsbn(isbn);
        if(libro != null){
            return libro.getId() > 0;
        }else{
            return false;
        }
    }
}
