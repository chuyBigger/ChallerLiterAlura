package com.aluracursos.challergerliteralura.principal;

import com.aluracursos.challergerliteralura.modelos.Autor;
import com.aluracursos.challergerliteralura.modelos.Idioma;
import com.aluracursos.challergerliteralura.modelos.Libro;
import com.aluracursos.challergerliteralura.repositorio.AutorRepositorio;
import com.aluracursos.challergerliteralura.repositorio.LibroRepositorio;
import com.aluracursos.challergerliteralura.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Principal {

    private LibroRepositorio repository = null;
    private AutorRepositorio autorRepository = null;

    Scanner scanner = new Scanner(System.in);
    ConsumoApi consumoApi = new ConsumoApi();
    ConvertirDatos convertirDatos = new ConvertirDatos();
    ConfigUrl configUrl = new ConfigUrl();
    ConsultaGemini consultaGemini = new ConsultaGemini();
    private final FuncionesAdicionales funcionesAdicionales;

    @Autowired
    public Principal(LibroRepositorio repository, AutorRepositorio autorRepository, FuncionesAdicionales funcionesAdicionales) {

        this.repository = repository;
        this.autorRepository = autorRepository;
        this.funcionesAdicionales = funcionesAdicionales;
    }


    public void muestraElMenu() {

        Integer entradaInt;

        System.out.println("Hola bienvenido al sistema 'LiterLibrary'");

        do {
            System.out.println("""
                     =======================================*======================================
                     ªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªªª
                                1.- Buscar Libros por titulo
                                2.- Lista de todo los libros en Base de Datos
                                3.- Mostrar Lista De Autores en la Base de Datos 
                                4.- Mostrar Lista de Autores vivos en un año especifico
                                5.- Mostrar Lista de libros Por idioma seleccionado 
                                6.- Mostrar Cantidad de Libros Por idioma en base de datos 
                                7.- Mostrar Lista de Autores vivos en un año especifico (Usando "Derived Query")
                    
                                0.- Salir;
                    
                    ========================================*======================================                                
                    
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
                case 2:
                    mostrarListaLibrosDB();
                    break;
                case 3:
                    mostrarListaAutores();
                    break;
                case 4:
                    mostrarListaAutoresVivosEn();
                    break;
                case 5:
                    mostrarListaIdiomaSeleccionado();
                    break;
                case 6:
                    mostrarListaLibrosTodosIdiomas();
                    break;
                case 7:
                    mostrarAutoresVivosEnDerivedQuery();
                    break;
                case 0:
                    System.out.println("Gracias por usar nuestro sistema");
                    System.out.println("Hasta Luego ...");
                    return;
            }
        } while (true);
    }

    // funcion para buscar libro
    private void buscarLibroTitulo() {
        String busquedaTitulo;
        String tituloTraducidoVerificado;
        String url;
        // ciclo de consulta para saber si esta en la DB o hay qeu buscar en el api

        System.out.println("Para buscar un libro por favor ingresa su titulo 📘");
        busquedaTitulo = scanner.nextLine();
        if (!busquedaTitulo.isEmpty()) {
            // consulta a Gemini si el titulo es correcto y lo traduce al ingles ya que la api tiene por lo general los titulos en ingles
            tituloTraducidoVerificado = consultaGemini.verificarBusquedaTraduccion(busquedaTitulo);
            System.out.println("Esta es la respsuta de gemini\n" + tituloTraducidoVerificado + "Toda esta ...\n");
        } else {
            System.out.println("⚠️ Error No ingreso un valor intenta de nuevo");
            return;
        }
        // verifica si la respuesta es valida o nula
        if (tituloTraducidoVerificado == null || tituloTraducidoVerificado.isBlank()) {
            System.out.println("⚠️ No se encontró una coincidencia para ese libro.");
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
            url = configUrl.urlTitulo(tituloTraducidoVerificado);
            System.out.println(url);
            System.out.println("Buscando Libro en el Api 🔎🔎");
            String json = consumoApi.obtenerDatos(url);
            System.out.println(json);
            Libro nuevoLibro;
            try {  //  buscar dentro de la list de "title" coincidencia exacta
                nuevoLibro = convertirDatos.obtenerLibroArray(json, tituloTraducidoVerificado);
                System.out.println(nuevoLibro);
                repository.save(nuevoLibro);
                System.out.println("Libro guardado con exito !!");
            } catch (RuntimeException e) {
                System.out.println("No se encontro coicidencia exacta: " + e.getMessage());
                // todo aqui hay que crear un menu de 3 sugerencia que arroja la api; -> Pendiente
            }
        }
    }

    private void mostrarListaLibrosDB() {

        List<Libro> LibrosDB = funcionesAdicionales.todosLosLibrosDB();

    }

    private void mostrarListaAutores() {
        List<Autor> listaAutoresDB = repository.findAllAutores();
        if (listaAutoresDB.isEmpty()) {
            System.out.println("⚠️ No hay autores registrados en la base de datos...");
        } else {
            System.out.println("Lista de Autores Completa: ");
            listaAutoresDB.forEach(System.out::println);
        }
    }

    private void mostrarListaAutoresVivosEn() {
        Integer anio = funcionesAdicionales.entradaAnioValido();
        List<Autor> autoresVivosEn = repository.autoresVivosEn(anio);
        if (autoresVivosEn.isEmpty()) {
            System.out.println("⚠️ No se encontraron autores vivos en el año: " + anio);
        } else {
            System.out.println("Los autores que vivieron en el mismo año son: ");
            autoresVivosEn.forEach(System.out::println);
        }
    }

    private void mostrarListaIdiomaSeleccionado() {
        Idioma idiomaBusqueda = funcionesAdicionales.entradaSolicitudLibrosIdioma();
        List<Libro> listaLibrosIdioma = repository.findByIdioma(idiomaBusqueda);
        if (!listaLibrosIdioma.isEmpty()) {
            System.out.println("Estos son los libros en el idioma: " + idiomaBusqueda);
            listaLibrosIdioma.forEach(System.out::println);
        } else {
            System.out.println("⚠️ No hay libros disponibles en ese idioma.");
        }

    }

    private void mostrarListaLibrosTodosIdiomas() {
        List<Object[]> cantidadLibrosporidioma = repository.contarLibrosIdioma();
        System.out.println("""
                ==============================================
                * LIBROS  /  * iDIOMA
                ______________________________________________
                """);

        for (Object[] resultado : cantidadLibrosporidioma) {

            Idioma idioma = (Idioma) resultado[0];
            Long cantidad = (Long) resultado[1];
            System.out.printf("%-10s | %d%n", idioma.name(), cantidad);

        }

        System.out.println(" ______________________________________________");

    }
    // todo Por uso de Derived Query se creo autorRepositorio se puede realizar una refactorizacion de repositorios
    private void mostrarAutoresVivosEnDerivedQuery() {

        Integer anio = funcionesAdicionales.entradaAnioValido();
        List<Autor> listaAutoresVivosEn = funcionesAdicionales.listaAutoresDefuntosAño(anio);

        if (!listaAutoresVivosEn.isEmpty()) {
            System.out.println("Los autores vivos en el año: " + anio + ".");
            listaAutoresVivosEn.forEach(System.out::println);
        } else {
            System.out.println("No hay autores vivos en el año: " + anio + " registrados en la base de datos.");
        }

    }


}

