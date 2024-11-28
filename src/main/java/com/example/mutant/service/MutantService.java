package com.example.mutant.service;

import com.example.mutant.model.Dna;
import com.example.mutant.repository.DnaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MutantService {

    private final DnaRepository dnaRepository;

    public MutantService(DnaRepository dnaRepository) {
        this.dnaRepository = dnaRepository;
    }

    public boolean esMutante(List<String> secuenciaAdn) {
        //secuencia de ADN a matriz
        char[][] dnaMatrix = convertToMatrix(secuenciaAdn);

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

    private char[][] convertToMatrix(List<String> secuenciaAdn) {
        int n = secuenciaAdn.size();
        char[][] matrix = new char[n][n];
        for (int i = 0; i < n; i++) {
            matrix[i] = secuenciaAdn.get(i).toCharArray();
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

    public Dna guardarAdn(String secuencia, boolean esMutante) {
        Dna dna = new Dna();
        dna.setSequence(secuencia);
        dna.setMutant(esMutante);
        return dnaRepository.save(dna);
    }

    public boolean existsBySequence(String sequence) {
        return dnaRepository.existsBySequence(sequence);
    }

    public Map<String, Object> obtenerEstadisticas() {
        long countMutantDna = dnaRepository.countByIsMutantTrue();
        long countHumanDna = dnaRepository.countByIsMutantFalse();
        double ratio = countHumanDna == 0 ? 0 : (double) countMutantDna / countHumanDna;

        Map<String, Object> stats = new HashMap<>();
        stats.put("count_mutant_dna", countMutantDna);
        stats.put("count_human_dna", countHumanDna);
        stats.put("ratio", ratio);
        return stats;
    }
}
