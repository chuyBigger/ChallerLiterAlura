package com.aluracursos.challergerliteralura.modelos;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Date fechaNacimiento;
    private Date fechaDefuncion;
    @ManyToOne
    private Libro libro;

    public Autor(Long id, String nombre, Date fechaNacimiento, Date fechaDefuncion, Libro libro) {
        this.id = id;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaDefuncion = fechaDefuncion;
        this.libro = libro;
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

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Date getFechaDefuncion() {
        return fechaDefuncion;
    }

    public void setFechaDefuncion(Date fechaDefuncion) {
        this.fechaDefuncion = fechaDefuncion;
    }

    public Libro getLibros() {
        return libro;
    }

    public void setLibros(Libro libros) {
        this.libro = libros;
    }

    @Override
    public String toString() {
        return
                "Autor: " + nombre + '\'' +
                "Fecha de Nacimiento: " + fechaNacimiento +
                "Fecha de Defuncion" + fechaDefuncion +
                "Libros='" + libro ;
    }
}
