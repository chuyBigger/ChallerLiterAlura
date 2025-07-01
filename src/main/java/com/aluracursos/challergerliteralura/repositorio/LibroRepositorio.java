package com.aluracursos.challergerliteralura.repositorio;

import com.aluracursos.challergerliteralura.modelos.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LibroRepositorio extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTituloContainsIgnoreCase(String libroBuscar);
}
