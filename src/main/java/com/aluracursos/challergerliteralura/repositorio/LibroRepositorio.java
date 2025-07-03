package com.aluracursos.challergerliteralura.repositorio;

import com.aluracursos.challergerliteralura.modelos.Autor;
import com.aluracursos.challergerliteralura.modelos.Idioma;
import com.aluracursos.challergerliteralura.modelos.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LibroRepositorio extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTituloContainsIgnoreCase(String libroBuscar);

    @Query("SELECT DISTINCT a FROM Autor a")
    List<Autor> findAllAutores();

    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento <= :anio AND (a.fechaDefuncion IS NULL OR a.fechaDefuncion >= :anio)")
    List<Autor> autoresVivosEn(@Param("anio") Integer anio);

    Optional<Autor> findByTitulo(String nombre);

    @Query("SELECT l FROM Libro l JOIN FETCH l.autor WHERE l.idioma = :idioma")
    List<Libro> findByIdioma(@Param("idioma") Idioma idioma);

    @Query("SELECT l.idioma, COUNT(l) FROM Libro l GROUP BY l.idioma")
    List<Object[]> contarLibrosIdioma();


}
