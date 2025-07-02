package com.aluracursos.challergerliteralura.service;

import com.aluracursos.challergerliteralura.modelos.Autor;
import com.aluracursos.challergerliteralura.repositorio.LibroRepositorio;
import org.aspectj.apache.bcel.Repository;

import java.util.Optional;
import java.util.Scanner;

public class FuncionesAdicionales {

    Scanner scanner = new Scanner(System.in);
    private final LibroRepositorio repository;

    public FuncionesAdicionales(LibroRepositorio repository) {
        this.repository = repository;
    }


    public void comprobarNombreAutor(String autor){

        Optional<Autor> autorExiste = repository.findByTitulo(autor);
        if (autorExiste.isPresent()){
            System.out.println("El autor de este libro ya esta registrado");
        }
    }

    public int entradaAnioValido(){
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

}
