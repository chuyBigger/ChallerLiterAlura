package com.aluracursos.challergerliteralura.modelos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year")Integer fechaNacimiento,
        @JsonAlias("death_year")Integer fechaDefuncion
) {
}
