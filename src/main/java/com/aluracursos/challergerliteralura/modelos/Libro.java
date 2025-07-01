package com.aluracursos.challergerliteralura.modelos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "Libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Autor> autor;
    @Enumerated(EnumType.STRING)
    private List<Idioma> idioma;
    private Integer totalDescargas;

    public Libro(){}


    public Libro(DatosLibro dL) {

        this.titulo = dL.titulo();

        this.autor = dL.autor()
                .stream()
                .map(datos -> {
                    Autor a =new Autor(datos);
                    a.setLibro(this);
                    return a;
                })
                .toList();

        this.idioma = dL.idioma();
        this.totalDescargas = dL.totalDescargas();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autor> getAutor() {
        return autor;
    }

    public void setAutor(List<Autor> autors) {
        this.autor = autors;
    }

    public List<Idioma> getIdioma() {
        return idioma;
    }

    public void setIdioma(List<Idioma> idioma) {
        this.idioma = idioma;
    }

    public int getTotalDescargas() {
        return totalDescargas;
    }

    public void setTotalDescargas(int totalDescargas) {
        this.totalDescargas = totalDescargas;
    }

    @Override
    public String toString() {
        return "\n------------  LIBRO  -------------" +
                "\nTitulo: " + titulo +"."+
                "\nAutor: " + autor +"."+
                "\nIdioma: " + idioma +"."+
                "\nCantidad de veces descargado: " + totalDescargas +"."+
                "\n---------------------------------";
    }
}
