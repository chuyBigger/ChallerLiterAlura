package com.aluracursos.challergerliteralura.modelos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)

@Entity
@Table(name = "Autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer fechaNacimiento;
    private Integer fechaDefuncion;
    @ManyToOne
    private Libro libro;

    public Autor(){}

    public Autor(DatosAutor dA) {
        this.nombre = dA.nombre();
        this.fechaNacimiento = dA.fechaNacimiento();
        this.fechaDefuncion = dA.fechaDefuncion();
    }

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

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Integer fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getFechaDefuncion() {
        return fechaDefuncion;
    }

    public void setFechaDefuncion(Integer fechaDefuncion) {
        this.fechaDefuncion = fechaDefuncion;
    }

    public Libro getLibros() {
        return libro;
    }

    public void setLibros(Libro libros) {
        this.libro = libros;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    @Override
    public String toString() {
        return
                "Autor: " + nombre +
                " /Fecha de Nacimiento: " + fechaNacimiento +
                " /Fecha de Defuncion: " + fechaDefuncion ;
    }
}
