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
        //secuencia de ADN a matriz
        char[][] dnaMatrix = convertToMatrix(dnaSequence);

        int sequencesFound = 0;

        //horizontal y vertical
        for (int i = 0; i < dnaMatrix.length; i++) {
            for (int j = 0; j < dnaMatrix[i].length; j++) {
                if (checkHorizontal(dnaMatrix, i, j) || 
                    checkVertical(dnaMatrix, i, j) || 
                    checkDiagonalRight(dnaMatrix, i, j) || 
                    checkDiagonalLeft(dnaMatrix, i, j)) {
                    sequencesFound++;
                    if (sequencesFound > 1) {
                        return true; 
                    }
                }
            }
        }

        return false; 
    }

    private char[][] convertToMatrix(List<String> dnaSequence) {
        int n = dnaSequence.size();
        char[][] matrix = new char[n][n];
        for (int i = 0; i < n; i++) {
            matrix[i] = dnaSequence.get(i).toCharArray();
        }
        return matrix;
    }

    private boolean checkHorizontal(char[][] matrix, int row, int col) {
        if (col + 3 >= matrix[row].length) return false;
        return matrix[row][col] == matrix[row][col + 1] &&
               matrix[row][col] == matrix[row][col + 2] &&
               matrix[row][col] == matrix[row][col + 3];
    }

    private boolean checkVertical(char[][] matrix, int row, int col) {
        if (row + 3 >= matrix.length) return false;
        return matrix[row][col] == matrix[row + 1][col] &&
               matrix[row][col] == matrix[row + 2][col] &&
               matrix[row][col] == matrix[row + 3][col];
    }

    private boolean checkDiagonalRight(char[][] matrix, int row, int col) {
        if (row + 3 >= matrix.length || col + 3 >= matrix[row].length) return false;
        return matrix[row][col] == matrix[row + 1][col + 1] &&
               matrix[row][col] == matrix[row + 2][col + 2] &&
               matrix[row][col] == matrix[row + 3][col + 3];
    }

    private boolean checkDiagonalLeft(char[][] matrix, int row, int col) {
        if (row + 3 >= matrix.length || col - 3 < 0) return false;
        return matrix[row][col] == matrix[row + 1][col - 1] &&
               matrix[row][col] == matrix[row + 2][col - 2] &&
               matrix[row][col] == matrix[row + 3][col - 3];
    }

    public Dna saveDna(String sequence, boolean isMutant) {
        Dna dna = new Dna();
        dna.setSequence(sequence);
        dna.setMutant(isMutant);
        return dnaRepository.save(dna);
    }
}

