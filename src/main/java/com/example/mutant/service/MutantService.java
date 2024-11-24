package com.example.mutant.service;

import com.example.mutant.model.Dna;
import com.example.mutant.repository.DnaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MutantService {

    private final DnaRepository dnaRepository;

    public MutantService(DnaRepository dnaRepository) {
        this.dnaRepository = dnaRepository;
    }

    public boolean isMutant(List<String> dnaSequence) {
        // Lógica para detectar si es mutante
        return detectMutant(dnaSequence);
    }

    public Dna saveDna(String sequence, boolean isMutant) {
        Dna dna = new Dna();
        dna.setSequence(sequence);
        dna.setMutant(isMutant);
        return dnaRepository.save(dna);
    }

    private boolean detectMutant(List<String> dnaSequence) {
        // Implementar isMutant
        return false;
    }
}
