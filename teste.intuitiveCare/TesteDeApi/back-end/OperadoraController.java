package com.example.operadorasapi.controller;

import com.example.operadorasapi.model.Operadora;
import com.example.operadorasapi.service.OperadoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operadoras")
@CrossOrigin(origins = "*") // Permitir CORS para o Vue.js
public class OperadoraController {

    @Autowired
    private OperadoraService operadoraService;

    @GetMapping("/buscar")
    public List<Operadora> buscarOperadoras(@RequestParam String termo) {
        return operadoraService.buscarOperadoras(termo);
    }
}