package com.aluracursos.challergerliteralura.principal;

import com.aluracursos.challergerliteralura.modelos.DatosLibro;
import com.aluracursos.challergerliteralura.modelos.Libro;
import com.aluracursos.challergerliteralura.repositorio.LibroRepositorio;
import com.aluracursos.challergerliteralura.service.*;

import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Principal {

    Scanner scanner = new Scanner(System.in);
    ConsumoApi consumoApi = new ConsumoApi();
    ConvertirDatos convertirDatos = new ConvertirDatos();
    ConfigUrl configUrl = new ConfigUrl();
    ConsultaGemini consultaGemini = new ConsultaGemini();

    private final LibroRepositorio repository;


    Integer entradaInt;
    String url;

    public Principal(LibroRepositorio repository) {
        this.repository = repository;
    }


    public void muestraElMenu() {

        System.out.println("Hola bienvenido al sistema 'LiterLibrary'");

        do {
            System.out.println("""
                     ==============================================
                     ªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªª
                                1.- Buscar Libros por titulo
                                2.- 
                                3.-
                    
                                0.- Salir;
                    
                    ===============================================                                
                    
                    """);
            String entrada = scanner.nextLine();
            try {
                entradaInt = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("El Valor ingresado no es correcto vuelve ha intentarlo...");
                continue;
            }

            switch (entradaInt) {
                case 1:
                    buscarLibroTitulo();
                    break;
                case 0:
                    System.out.println("Gracias por usar nuestro sistema");
                    System.out.println("Hasta Luego ...");
                    break;
            }
        } while (true);
    }

    // funcion para buscar libro
    private void buscarLibroTitulo() {
        String busquedaTitulo;
        String busquedaAutor;
        // ciclo de consulta para saber si esta en la DB o hay qeu buscar en el api

        System.out.println("Para buscar un libro por favor ingresa su titulo 📘");
        busquedaTitulo = scanner.nextLine();
        System.out.println("ahora ingresa el autor si lo sabes para mejorar la busqueda 🔎");
        busquedaAutor = scanner.nextLine();

        if (!busquedaTitulo.isEmpty()) {
            if (!busquedaAutor.isEmpty()) {
                url = configUrl.urlTituloAutor(busquedaTitulo, busquedaAutor);
            } else {
                url = configUrl.urlTitulo(busquedaTitulo);
            }
        } else {
            System.out.println("⚠️ Error No ingreso un valor intenta de nuevo");
            return;
        }

        // consulta a Gemini si el titulo es correcto y lo traduce al ingles ya que la api tiene por lo general los titulos en ingles
        String tituloTraducidoVerificado = ConsultaGemini.verificarBusquedaTraduccion(busquedaTitulo, busquedaAutor);
        System.out.println("Esta es la respsuta de gemini\n" + tituloTraducidoVerificado + "Toda esta ...");
        // verifica si la respuesta es valida o nula
        if (tituloTraducidoVerificado == null || tituloTraducidoVerificado.isBlank()) {
            System.out.println("⚠️ No se encontró una coincidencia clara para ese libro.");
            return;
        }
        // consulta a la DB si esta el registro
        System.out.println("buscando libro en la base de Datos 🔎🔎");
        var libroEncontrado = repository.findByTituloContainsIgnoreCase(tituloTraducidoVerificado);
        if (libroEncontrado.isPresent()) {
            System.out.println("El libro ya esta registrado en la base de Datos");
            System.out.println(libroEncontrado.get());
        } else {
            // si no se encuertra el registro en la DB hay que solicitar consulta a la Api
            System.out.println("El libro no se encuentra registrado en la Base de Datos");
            System.out.println("Buscando Libro en el Api 🔎🔎");
            String json = consumoApi.obtenerDatos(url);
            Libro nuevoLibro = convertirDatos.obtnerDatosLibro1(json); // todo Vamos aqui convietiendo datos vamos a buscar dentro de la list de "title" coincidencia exacta

            if (nuevoLibro != null) {
                // si nuevo libro no da null lo guarda en la base de Datos
                repository.save(nuevoLibro);
                System.out.println("Libro guardado en la base de datos");
                System.out.println(nuevoLibro);
            }
        }


    }

}

