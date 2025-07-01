package com.aluracursos.challergerliteralura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoApi {

    public String obtenerDatos(String url) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        String json = response.body();
        if (json.isEmpty() || json.isBlank()){
            System.out.println("No se encontraron resultados de esta busqueda!!");
        }
            return json;
    }

}
