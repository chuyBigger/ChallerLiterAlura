package com.aluracursos.challergerliteralura;

import com.aluracursos.challergerliteralura.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallergerLiterAluraApplication implements CommandLineRunner {

    public static void main(String[] args) {

        SpringApplication.run(ChallergerLiterAluraApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal();
        principal.muestraElMenu();
    }
}
