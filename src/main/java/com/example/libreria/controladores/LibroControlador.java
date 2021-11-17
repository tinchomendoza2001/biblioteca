package com.example.libreria.controladores;

import com.example.libreria.excepciones.ErrorServicio;
import com.example.libreria.servicios.AutorServicios;
import com.example.libreria.servicios.EditorialServicios;
import com.example.libreria.servicios.LibroServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class LibroControlador {
    @Autowired
    private AutorServicios autorServicio;

    @Autowired
    private EditorialServicios editorialServicio;

    @Autowired
    private LibroServicios libroServicio;
    
    @GetMapping("/libro")
    public String libros(ModelMap modelo) {
        modelo.put("autores", autorServicio.lista());
        modelo.put("editoriales", editorialServicio.lista());
        modelo.put("libros", libroServicio.lista());
        return "libro";
    }
    
    @PostMapping("/libro")
    public String guardarLibro(ModelMap modelo, @RequestParam long id, @RequestParam String isbn, @RequestParam String titulo, @RequestParam int anio, @RequestParam String alta, @RequestParam int ejemplares, @RequestParam long autorId, @RequestParam long editorialId) {
        if (libroServicio.buscarPorTituloBoolean(titulo) && libroServicio.buscarPorIsbnBoolean(isbn) && id == 0) {//si esta el nombre y se quiere agregar
            modelo.put("AVISO", "Ya existe el Libro con el mismo Titulo e ISBN");
        } else if (id > 0 && libroServicio.buscarPorIsbnId(isbn, id) != id) {//si es una actualizacion y existe el nombre en otro id
            modelo.put("AVISO", "Ya existe el Libro con el ISBN ingresado");
        } else {
            try {
                if (id > 0) {//actualizacion
                    libroServicio.actualizar(id, isbn, titulo, anio, Boolean.parseBoolean(alta), ejemplares, autorId, editorialId);
                    modelo.put("EXITO", "Actualizacion Exitosa del Libro '" + titulo + "'");
                } else {//nuevo
                    libroServicio.crear(isbn, titulo, anio, Boolean.parseBoolean(alta), ejemplares, autorId, editorialId);
                    modelo.put("EXITO", "Registro Exitoso del Libro");
                }
            } catch (ErrorServicio e) {
                modelo.put("ERROR", e.getMessage());
                modelo.put("isbn", isbn);//reenvio variables a la vista
            }
        }
        modelo.put("autores", autorServicio.lista());
        modelo.put("editoriales", editorialServicio.lista());
        modelo.put("libros", libroServicio.lista());
        return "redirect:/libro";
    }
}
