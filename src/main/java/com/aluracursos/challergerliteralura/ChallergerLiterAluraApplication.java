package com.aluracursos.challergerliteralura;

import com.aluracursos.challergerliteralura.principal.Principal;
import com.aluracursos.challergerliteralura.repositorio.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ChallergerLiterAluraApplication implements CommandLineRunner {

    @Autowired
    Principal principal;

    @Autowired
    LibroRepositorio repository ;

    @Autowired
    private ConfigurableApplicationContext context;



    public static void main(String[] args) {

        SpringApplication.run(ChallergerLiterAluraApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        principal.muestraElMenu();

        SpringApplication.exit(context, () -> 0);
    }
}
