package com.aluracursos.challergerliteralura.principal;

import com.aluracursos.challergerliteralura.modelos.Libro;
import com.aluracursos.challergerliteralura.repositorio.LibroRepositorio;
import com.aluracursos.challergerliteralura.service.*;

import java.util.List;
import java.util.Scanner;

public class Principal {

    Scanner scanner = new Scanner(System.in);
    ConsumoApi consumoApi = new ConsumoApi();
    ConvertirDatos convertirDatos = new ConvertirDatos();
    ConfigUrl configUrl = new ConfigUrl();
    ConsultaGemini consultaGemini = new ConsultaGemini();

    private final LibroRepositorio repository;


    Integer entradaInt;

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
                                2.- Lista de todo los libros en Base de Datos
                                3.- Mostrar Lista De Autores en la Base de Datos 
                                4.- Mostrar Lista de Autores vivos en un año especifico
                                5.-
                    
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
                case 2:
                    mostrarListaLibrosDB();
                    break;
                case 3:

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
        String busquedaAutor;
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
            }catch (RuntimeException e){
                System.out.println("No se encontro coicidencia exacta: " + e.getMessage());
                // todo aqui hay que crear un menu de 3 sugerencia que arroja la api; -> Pendiente
            }
        }
    }

    private void mostrarListaLibrosDB(){
        List<Libro> listaLibrosBaseDatos = repository.findAll();
        if (listaLibrosBaseDatos.isEmpty()){
            System.out.println("No hay Libros en la base de Datos");
        } else {
            System.out.println("Lista de libros DB completa");
            listaLibrosBaseDatos.forEach(System.out::println);
        }
    }

}

