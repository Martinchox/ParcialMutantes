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
        String sequence = String.join(",", dna);
        
        boolean isMutant = mutantService.isMutant(dna);
        mutantService.saveDna(sequence, isMutant);

        if (isMutant) {
            return ResponseEntity.ok("Mutantw detectado!");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No es mutante.");
        }
    }
}

