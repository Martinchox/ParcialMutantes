package com.example.mutant.controller;

import com.example.mutant.service.MutantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mutant")
public class MutantController {

    private final MutantService mutantService;

    public MutantController(MutantService mutantService) {
        this.mutantService = mutantService;
    }

    @PostMapping("/")
    public ResponseEntity<?> isMutant(@RequestBody List<String> dna) {
        boolean isMutant = mutantService.isMutant(dna);

        if (isMutant) {
            return ResponseEntity.ok("Mutante detectado!");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No es mutante.");
        }
    }
}
