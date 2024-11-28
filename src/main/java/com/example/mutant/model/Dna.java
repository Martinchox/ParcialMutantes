package com.example.mutant.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Dna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    @Column(nullable = false, unique = true)
    private String sequence; 

    @Column(nullable = false)
    private boolean isMutant; 
}
