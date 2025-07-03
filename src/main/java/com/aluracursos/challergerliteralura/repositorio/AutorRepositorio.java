package com.aluracursos.challergerliteralura.repositorio;

import com.aluracursos.challergerliteralura.modelos.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AutorRepositorio extends JpaRepository<Autor, Long> {

    List<Autor> findByFechaNacimientoLessThanEqualAndFechaDefuncionGreaterThanOrFechaDefuncionIsNull(Integer anio1, Integer anio2);


}
