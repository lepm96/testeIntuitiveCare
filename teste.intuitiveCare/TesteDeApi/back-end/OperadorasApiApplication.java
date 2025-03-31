package com.example.operadorasapi;

import com.example.operadorasapi.service.OperadoraService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OperadorasApiApplication implements CommandLineRunner {

    private final OperadoraService operadoraService;

    public OperadorasApiApplication(OperadoraService operadoraService) {
        this.operadoraService = operadoraService;
    }

    public static void main(String[] args) {
        SpringApplication.run(OperadorasApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        operadoraService.carregarDadosIniciais();
    }
}