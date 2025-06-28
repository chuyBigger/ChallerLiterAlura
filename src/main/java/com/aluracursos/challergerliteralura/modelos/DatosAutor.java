package com.aluracursos.challergerliteralura.modelos;

import java.util.Date;

public record DatosAutor(
        String nombre,
        Date fechaNacimiento,
        Date fechaDefuncion,
        String libros
) {
}
