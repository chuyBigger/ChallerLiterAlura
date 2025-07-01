package com.aluracursos.challergerliteralura.modelos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(

        @JsonAlias("title")String titulo,
        @JsonAlias("authors")List<DatosAutor> autor,
        @JsonAlias("languages")List<Idioma> idioma,
        @JsonAlias("download_count")Integer totalDescargas

) {
}
