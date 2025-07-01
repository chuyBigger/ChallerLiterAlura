package com.aluracursos.challergerliteralura.service;

import java.beans.Encoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ConfigUrl {

    private String tituloIndexado;
    private String autorIndexado;
    private String url;

    private final static String API_URL_SPACE = "%20";
    private final static String API_URL_BASE = "https://gutendex.com/books/?search=";

    public String urlTitulo(String titulo) {
        tituloIndexado = URLEncoder.encode(titulo, StandardCharsets.UTF_8);
        url = API_URL_BASE + tituloIndexado;
        return url;
    }

    public String urlTituloAutor(String titulo, String autor) {
        tituloIndexado = URLEncoder.encode(titulo, StandardCharsets.UTF_8);
        autorIndexado = URLEncoder.encode(autor, StandardCharsets.UTF_8);
        url = API_URL_BASE+autorIndexado+API_URL_SPACE+tituloIndexado;
        return url;
    }


}
