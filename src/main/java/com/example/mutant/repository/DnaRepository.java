package com.example.mutant.repository;

import com.example.mutant.model.Dna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DnaRepository extends JpaRepository<Dna, Long> {
    boolean existsBySequence(String sequence);

    long countByIsMutantTrue();

    long countByIsMutantFalse();

}
