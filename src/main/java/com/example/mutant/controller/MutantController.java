package com.example.mutant.controller;

import com.example.mutant.model.Dna;
import com.example.mutant.service.MutantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mutant")
public class MutantController {

    private final MutantService mutantService;

    public MutantController(MutantService mutantService) {
        this.mutantService = mutantService;
    }

    // Endpoint POST
    @PostMapping("/")
    public ResponseEntity<?> verificarMutante(@RequestBody List<String> adn) {
        boolean esMutante = mutantService.esMutante(adn);
        String secuencia = String.join(",", adn);
        String response = "";
        if (!mutantService.existsBySequence(secuencia)) {
            response = ", Guardado en la base de datos";
            mutantService.guardarAdn(secuencia, esMutante);
        } else {
            response = ", Dna existente en la base de datos";
        }

        if (esMutante) {
            return ResponseEntity.ok("Mutante detectado!" + response);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No es mutante!" + response);
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticas() {
        Map<String, Object> stats = mutantService.obtenerEstadisticas();
        return ResponseEntity.ok(stats);
    }
}

