package com.aluracursos.challergerliteralura.service;

import java.beans.Encoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ConfigUrl {


    private final static String API_URL_BASE = "https://gutendex.com/books/?search=";

    public String urlTitulo(String titulo) {
        var tituloIndexado = URLEncoder.encode(titulo, StandardCharsets.UTF_8);
        System.out.println(tituloIndexado);

        String url = API_URL_BASE + tituloIndexado;
        return url;
    }

}
