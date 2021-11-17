/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.libreria.controladores;

import com.example.libreria.excepciones.ErrorServicio;
import com.example.libreria.servicios.AutorServicios;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class AutorControlador {

    @Autowired
    private AutorServicios autorServicio;

    @GetMapping("/autor")
    public String autores(ModelMap modelo) {
        modelo.put("autores", autorServicio.lista());
        return "autor";
    }
    
    @PostMapping("/autor")
    public String guardarAutor(ModelMap modelo, @RequestParam long id, @RequestParam String nombre, @RequestParam String alta, HttpServletRequest request) {
        if (autorServicio.buscarPorNombreBoolean(nombre) && id == 0) {//si esta el nombre y se quiere agregar
            //modelo.put("AVISO", "Ya existe el nombre del Autor");
            request.getSession().setAttribute("AVISO", "Ya existe el nombre del Autor");
        } else if (id > 0 && autorServicio.buscarPorNombreId(nombre, id) != id) {//si es una actualizacion y existe el nombre en otro id
            //modelo.put("AVISO", "Ya existe el nombre del Autor en otro registro");
            request.getSession().setAttribute("AVISO", "Ya existe el nombre del Autor en otro registro");
        } else {
            try {
                if (id > 0) {//actualizacion
                    autorServicio.actualizar(id, nombre, Boolean.parseBoolean(alta));
                    //modelo.put("EXITO", "Actualizacion Exitoso del Autor '" + nombre + "'");
                    request.getSession().setAttribute("EXITO", "Actualizacion Exitoso del Autor '" + nombre + "'");
                } else {//nuevo
                    autorServicio.crear(nombre, Boolean.parseBoolean(alta));
                    //modelo.put("EXITO", "Registro Exitoso del Nuevo Autor");
                    request.getSession().setAttribute("EXITO", "Registro Exitoso del Nuevo Autor");
                }
            } catch (ErrorServicio e) {
                //modelo.put("ERROR", e.getMessage());
               // modelo.put("nombre", nombre);//reenvio variables a la vista
                //modelo.put("alta", alta);
                //modelo.put("id", id);                
                request.getSession().setAttribute("ERROR", e.getMessage());
            }
        }
        modelo.put("autores", autorServicio.lista());
        System.out.println(request.getSession().toString());
        return "redirect:/autor";//para que no aparezca en la recarga de la pagina de nuevo el post
    }
    /*
    @PostMapping("/registro")
    public String guardar(ModelMap modelo, @RequestParam String nombre){
        try{
            autorServicio.crear(nombre);
            //al front       variable            valor
            modelo.put("EXITO", "Registro exitoso");
            return "autor.html"; 
        }catch(Exception e){
            //al front       variable            valor
            modelo.put("EXITO", "Falta algun dato");            
            return e.toString();
        }
    }
   
    @PostMapping("/modificar/{id}")//PATHVARIABLE
    public String modificar(ModelMap modelo, @PathVariable Long id, @RequestParam String nombre){
        try{
            autorServicio.burcarPorId(id);
            autorServicio.crear(nombre);
            //al front       variable            valor
            modelo.put("EXITO", "Registro exitoso");
            return "autor.html"; 
        }catch(Exception e){
            //al front       variable            valor
            modelo.put("EXITO", "Falta algun dato");            
            return e.toString();
        }
    }
    
        @PostMapping("/lista")//PATHVARIABLE
    public String listar(ModelMap modelo){
            modelo.addAttribute("autores",  autorServicio.lista());
            return "autor.html"; 
    }
     */
}
