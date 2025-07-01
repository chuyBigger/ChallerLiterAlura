package com.aluracursos.challergerliteralura.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

public class ConsultaGemini {
    final static String APY_KEY = System.getenv("API_KEY_GEMINI");

    public static String verificarBusquedaTraduccion(String libro) {
        String modelo = "gemini-2.0-flash-lite";


        String promptBase = """
                
                You are a helpful assistant that helps normalize book titles for searching in the Gutenberg catalog.
                
                You will receive:
                - A book title entered by a user (which may be in another language or have spelling mistakes)
                - Optionally, an author name
                
                Your task is to:
                1. Determine the correct and official English book title from the Gutenberg catalog.
                2. Use the author if provided to improve accuracy.
                3. Return only the correct English title — no extra characters, no comments, and no formatting.
                4. Do NOT include quotation marks, punctuation, line breaks, or any additional explanation.
                5. Your response must be in English only. Do NOT translate to any other language.
                6. If you cannot confidently find the correct title, just return the input title exactly as received.
                
                ⚠️ Important:
                - Output MUST be the exact English title
                - No quotation marks ("), no newline characters (\\n), and no explanations
                - Response must be only plain text, one line, English only
                
                Example Input:
                Title: "el principito"
                Author: ""
                
                Expected Output:
                The Little Prince
                
                
                -Realiza una evaluacion de este titulo del libro '%s' (faltas de ortografia o mala escritura) y corrigelo si esta mal escrito,
                traduselo al ingles retorna un string solo comillas del titulo del libro ya corregido y traducido sin mas sin autor ni nada
                ejemplo el titulo es ("el principitro" y corrige y se traduce retornamos en ingles ): "The Little Prince"
                -Si no hay concidencia retornamos: "null"
                Muy importante 
                -Siempre retornamos en Ingles
                -Sin comillas ni saltos de linea ningun simbolo (", '\\n'  nada )                  
                """;
        String prompt = String.format(promptBase, libro);


        Client cliente = new Client.Builder().apiKey(APY_KEY).build();

        try {
            GenerateContentResponse respuesta = (cliente).models.generateContent(
                    modelo,
                    prompt,
                    null
            );
            if (!respuesta.text().isEmpty()) {
                String titulo = respuesta.text().replace("\n", "");
                return titulo;
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
