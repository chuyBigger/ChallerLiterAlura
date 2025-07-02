package com.aluracursos.challergerliteralura.modelos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ServiceLoader;

public enum Idioma {

    ESPAÑOL("es"),
    INGLES("en"),
    FRANCES("fr"),
    ALEMAN("de"),
    ITALIANO("it"),
    PORTUGUES("pt"),
    JAPONES("ja"),
    CHINO("zh"),
    COREANO("ko"),
    RUSO("ru");

    private final String abreviatura;

    Idioma(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    @JsonCreator
    public static Idioma fromIdioma(String abreviatura) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.abreviatura.equalsIgnoreCase(abreviatura)){
                return idioma;
            }
        }
        throw new IllegalArgumentException("Idioma no reconocido: "+ abreviatura);
    }


    @JsonValue
    public String getAbreviatura(){
        return abreviatura;
    }


}

