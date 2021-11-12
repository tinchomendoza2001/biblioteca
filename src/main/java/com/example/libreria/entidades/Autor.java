package com.example.libreria.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Autor implements Serializable {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private Boolean alta;
    @Temporal(TemporalType.TIMESTAMP)
    private Date creacion;
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificacion;
    @Temporal(TemporalType.TIMESTAMP)
    private Date eliminacion;
    @OneToMany(mappedBy = "autor")
    private List<Libro> libros;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getAlta() {
        return alta;
    }

    public void setAlta(Boolean alta) {
        this.alta = alta;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    public Date getCreacion() {
        return creacion;
    }

    public void setCreacion(Date creacion) {
        this.creacion = creacion;
    }

    public Date getModificacion() {
        return modificacion;
    }

    public void setModificacion(Date modificacion) {
        this.modificacion = modificacion;
    }

    public Date getEliminacion() {
        return eliminacion;
    }

    public void setEliminacion(Date eliminacion) {
        this.eliminacion = eliminacion;
    }

}
