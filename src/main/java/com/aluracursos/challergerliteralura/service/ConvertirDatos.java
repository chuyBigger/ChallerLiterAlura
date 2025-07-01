package com.aluracursos.challergerliteralura.service;

import com.aluracursos.challergerliteralura.modelos.DatosLibro;
import com.aluracursos.challergerliteralura.modelos.Libro;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    public Libro obtnerDatosLibro1(String json) {
        try {
            JsonNode root = objectMapper.readTree(json);
            JsonNode resultsNode = root.get("results");
            if (resultsNode != null && resultsNode.isArray() && resultsNode.size() > 0) {
                JsonNode primerLibroNode = resultsNode.get(0);
                DatosLibro datosLibro = objectMapper.treeToValue(primerLibroNode, DatosLibro.class);
                return new Libro(datosLibro);
            } else {
                throw new RuntimeException("No hay libros en 'Results'");
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al convertir el Json en Libro", e);
        }
    }

}
