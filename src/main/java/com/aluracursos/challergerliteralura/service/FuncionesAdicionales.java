package com.aluracursos.challergerliteralura.service;

import com.aluracursos.challergerliteralura.modelos.Autor;
import com.aluracursos.challergerliteralura.modelos.Idioma;
import com.aluracursos.challergerliteralura.modelos.Libro;
import com.aluracursos.challergerliteralura.repositorio.LibroRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Parser;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class FuncionesAdicionales {

    Scanner scanner = new Scanner(System.in);
    private final LibroRepositorio repository;

    public FuncionesAdicionales(LibroRepositorio repository) {
        this.repository = repository;
    }


    public void comprobarNombreAutor(String autor) {

        Optional<Autor> autorExiste = repository.findByTitulo(autor);
        if (autorExiste.isPresent()) {
            System.out.println("El autor de este libro ya esta registrado");
        }
    } // todo pedndiente evaluacion existe antes de guardar

    public int entradaAnioValido() {
        Scanner scanner = new Scanner(System.in);
        Integer anio = null;

        while (anio == null) {
            System.out.print("Ingrese un año (4 dígitos): ");
            String entrada = scanner.nextLine();

            if (entrada.isEmpty()) {
                System.out.println("❌ Error: El campo no puede estar vacío.\n");
                continue;
            }

            try {
                anio = Integer.parseInt(entrada);

                if (entrada.length() != 4) {
                    System.out.println("❌ Error: El año debe tener exactamente 4 dígitos.\n");
                    anio = null;
                } else {
                    System.out.println("✅ Año válido: " + anio);
                }

            } catch (NumberFormatException e) {
                System.out.println("❌ Error: Debes ingresar un número entero válido.\n");
            }
        }
        return anio;
    }

    public Idioma entradaSolicitudLibrosIdioma() {

        Idioma[] idiomas = Idioma.values();
        Integer idiomaSeleccion = null;

        while (idiomaSeleccion == null) {
            System.out.println("""
                    =======================*=========================
                             SELECCIONA EL IDIOMA DDIPONIBLE
                    
                    """);
            for (int i = 0; i < idiomas.length; i++) {
                System.out.printf("%d - %s%n", i + 1, idiomas[i],".\n");
            }
            System.out.println("\n========================*========================");

            var idiomaEntrada = scanner.nextLine();

            if (!idiomaEntrada.isEmpty()) {
                try {
                    idiomaSeleccion = Integer.parseInt(idiomaEntrada);
                    if (idiomaSeleccion > Idioma.values().length) {
                        System.out.println("La opcion ingresada no es correcta vueva ha intentar");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("⚠️ El valor ingresado no es un numero");
                }
            } else {
                System.out.println("❌ no ingresaste una respuesta intentalo de nuevo");
                continue;
            }
        }
        var idiomaSelecionado = idiomas[idiomaSeleccion - 1];
        return idiomaSelecionado;

    }


    @Transactional
    public List<Libro> todosLosLibrosDB() {

        List<Libro> listaLibrosDB = repository.findAll();
        if (listaLibrosDB.isEmpty()) {
            System.out.println("No hay Libros en la base de Datos");
        } else {
            System.out.println("Lista de libros DB completa");
            listaLibrosDB.forEach(System.out::println);
        }

        return listaLibrosDB;

    }

}
