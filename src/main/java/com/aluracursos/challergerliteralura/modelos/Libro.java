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
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Autor> autor;
    @Enumerated(EnumType.STRING)
    private Idioma idioma;
    private Integer totalDescargas;

    public Libro(){}


    public Libro(DatosLibro dL) {

        this.titulo = dL.titulo();

        this.autor = dL.autor()
                .stream()
                .findFirst()
                .map(datos -> {
                    Autor a =new Autor(datos);
                    a.setLibro(this);
                    return List.of(a);
                })
                .orElse(List.of());

        this.idioma = dL.idioma()
                .stream()
                .findFirst()
                .map(Idioma::fromIdioma)
                .orElse(null);

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

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public void setTotalDescargas(Integer totalDescargas) {
        this.totalDescargas = totalDescargas;
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
