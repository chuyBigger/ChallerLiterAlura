package com.aluracursos.challergerliteralura.service;

import com.aluracursos.challergerliteralura.modelos.DatosLibro;
import com.aluracursos.challergerliteralura.modelos.Libro;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.stream.StreamSupport;

public class ConvertirDatos implements IConvierteDatos {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json, clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    // busca coincidence exacta dentro del array "results"
    public Libro obtenerLibroArray(String json, String tituloVerificado) {
        try {
            JsonNode root = objectMapper.readTree(json);
            JsonNode resultsNode = root.get("results");

            if (resultsNode != null && resultsNode.isArray()) {
                return StreamSupport.stream(resultsNode.spliterator(), false)
                        .filter(node -> node.has("title") &&
                                tituloVerificado.equalsIgnoreCase(node.get("title").asText()))
                        .findFirst()
                        .map(this::convertirNodeALibro)
                        .orElseThrow(() -> new RuntimeException("No se encontró coincidencia exacta con el título: " + tituloVerificado));
            } else {
                throw new RuntimeException("""
                        "results" esta vacio o no es un array valido
                        """);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al convertir el Json en Libro", e);

        }

    }

    private Libro convertirNodeALibro(JsonNode node) {
        try {
            DatosLibro datosLibro = objectMapper.treeToValue(node, DatosLibro.class);
            return new Libro(datosLibro);
        } catch (Exception e) {
            throw new RuntimeException("Error al convertir json a libro", e);
        }
    }


}
