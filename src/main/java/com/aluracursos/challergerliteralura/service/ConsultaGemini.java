package com.aluracursos.challergerliteralura.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

public class ConsultaGemini {
    final static String APY_KEY = System.getenv("API_KEY_GEMINI");

    public static String verificarBusquedaTraduccion(String libro, String autor) {
        String modelo = "gemini-2.0-flash-lite";

        if (!autor.isEmpty()){
            autor = "y este es el autor "+autor;
        }

        String promptBase = """
                -Realiza una evaluacion de este titulo del libro '%s' %s (faltas de ortografia o mala escritura) y corrigelo si esta mal escrito,
                traduselo al ingles retorna un string solo comillas del titulo del libro ya corregido y traducido sin mas sin autor ni nada
                ejemplo el titulo es ("el principitro" y corrige y se traduce retornamos en ingles ): "The Little Prince"
                -Si no hay concidencia retornamos: "null"
                -Siempre retornamos en Ingles                  
                """;
        String prompt = String.format(promptBase, libro, autor);

        Client cliente = new Client.Builder().apiKey(APY_KEY).build();

        try {
            GenerateContentResponse respuesta = (cliente).models.generateContent(
                    modelo,
                    prompt,
                    null
            );
            if (!respuesta.text().isEmpty()) {
                return respuesta.text();
            }
        } catch (Exception e) {
            System.out.println("Error al llamar a la API de Gemini para traducción: " + e.getMessage());
        }
        return null;
    }
}


//String promptBase = """
//                You are a helpful assistant who helps correct book titles for searching in an English catalog.
//
//                Given a book search input from a user, you will receive:
//
//                - The book title as entered by the user (may be in any language or contain spelling mistakes).
//                - The author name if provided (optional).
//
//                Your task is to:
//
//                1. Determine the correct and official English title of the book as it appears in the Gutenberg catalog.
//                2. If the author is provided, use it to help identify the correct title.
//                3. If you cannot confidently identify the exact title, reply with the original title as is.
//                4. Return only the corrected English title as a plain string, nothing else.
//
//                User input example:
//                Title: "%s"
//                Author: "%s"
//
//                Return:
//                Corrected English title here
//
//                """;
//
//String getPromptBase = """
//        Realiza una evaluacion de este titulo de libro (faltas de ortografia o mala escritura)  y corrigelo si esta mal escrito y traduselo al ingles
//        retorna un string solo comillas y el titulo del libro ya corregido y traducido;
//        """;
