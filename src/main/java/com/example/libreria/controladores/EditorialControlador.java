package com.example.libreria.controladores;

import com.example.libreria.excepciones.ErrorServicio;
import com.example.libreria.servicios.EditorialServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/")
public class EditorialControlador {
    
    @Autowired
    private EditorialServicios editorialServicio;
    
    @GetMapping("/editorial")
    public String editoriales(ModelMap modelo) {
        modelo.put("editoriales", editorialServicio.lista());
        return "editorial";
    }
    
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/editorial")
    public String guardarEditorial(ModelMap modelo, @RequestParam long id, @RequestParam String nombre, @RequestParam String alta) {
        if (editorialServicio.buscarPorNombreBoolean(nombre) && id == 0) {//si esta el nombre y se quiere agregar
            modelo.put("AVISO", "Ya existe el nombre de la Editorial");
        }else if (id > 0 && editorialServicio.buscarPorNombreId(nombre,id) != id) {//si es una actualizacion y existe el nombre en otro id
            modelo.put("AVISO", "Ya existe el nombre de la Editorial en otro registro");
        } else {
            try {
                if(id > 0){//actualizacion                    
                    editorialServicio.actualizar(id, nombre,Boolean.parseBoolean(alta));
                    modelo.put("EXITO", "Actualizacion Exitosa de la Editorial '"+nombre+"'");
                }else{//nuevo
                    editorialServicio.crear(nombre,Boolean.parseBoolean(alta));
                    modelo.put("EXITO", "Registro Exitoso de la Nueva Editorial");
                }                
            } catch (ErrorServicio e) {
                modelo.put("ERROR", e.getMessage());
                modelo.put("nombre", nombre);//reenvio variables a la vista
                modelo.put("alta", alta);
                modelo.put("id", id);
            }
        }
        modelo.put("editoriales", editorialServicio.lista());
        return "editorial";
    }
}
