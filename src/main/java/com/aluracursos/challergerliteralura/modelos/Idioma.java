package com.aluracursos.challergerliteralura.modelos;

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

    public static Idioma fromIdioma(String nombre) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.name().equalsIgnoreCase(nombre)){
                return idioma;
            }
        }
        throw new IllegalArgumentException("Idioma no reconocido: "+ nombre);
    }


}
