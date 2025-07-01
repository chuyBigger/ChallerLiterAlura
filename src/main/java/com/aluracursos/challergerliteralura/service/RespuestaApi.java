package com.aluracursos.challergerliteralura.service;

import com.aluracursos.challergerliteralura.modelos.DatosLibro;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

// esta es por que el json tiene mas objetos y listas
@JsonIgnoreProperties(ignoreUnknown = true)
public class RespuestaApi {

    private List<DatosLibro> results;

    public List<DatosLibro> getResults(){
        return results;
    }

    public void setResults(List<DatosLibro> results){
        this.results = results;
    }

}
