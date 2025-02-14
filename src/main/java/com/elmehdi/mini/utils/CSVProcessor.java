package com.elmehdi.mini.utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.*;
import java.util.*;

public class CSVProcessor {
    public static void main(String[] args) {
        String inputFile = "src/main/files/donnees_etudiantsLight.csv";
        String outputDir = "src/main/files";

        processCSV(inputFile, outputDir);
    }

    public static void processCSV(String inputFile, String outputDir) {
        try (CSVReader reader = new CSVReader(new FileReader(inputFile))) {
            List<String[]> data = reader.readAll();

            // En-têtes et données
            String[] headers = data.get(0);
            List<String[]> rows = data.subList(1, data.size());

            // Index des colonnes
            Map<String, Integer> columnIndex = new HashMap<>();
            for (int i = 0; i < headers.length; i++) {
                columnIndex.put(headers[i], i);
            }

            // Trier par filière
            Map<String, List<String[]>> filieres = new HashMap<>();
            for (String[] row : rows) {
                String filiere = row[columnIndex.get("filiere")];
                filieres.computeIfAbsent(filiere, k -> new ArrayList<>()).add(row);
            }

            // Sauvegarder les fichiers par filière
            for (Map.Entry<String, List<String[]>> entry : filieres.entrySet()) {
                String filiere = entry.getKey();
                List<String[]> rowsFiliere = entry.getValue();
                writeCSV(outputDir + "/" + filiere + ".csv", headers, rowsFiliere);
            }

            // Création des fichiers par catégorie
            createCategoryFiles(outputDir, headers, rows, columnIndex);

            System.out.println("Fichiers CSV remplis avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createCategoryFiles(String outputDir, String[] headers, List<String[]> rows, Map<String, Integer> columnIndex) throws IOException {
        // Catégories de colonnes
        String[] personalCols = {"cne", "cin", "Nom", "Prenom", "anneeNaissance", "VilleBac"};
        String[] evaluationCols = {"cne", "filiere", "niveau", "noteS1", "noteS2", "noteS3", "noteS4", "noteS5", "noteS6"};
        String[] disciplineCols = {"cne", "filiere", "niveau", "nbrAbsences", "nbrRapportsMauvaiseConduite", "nbrRapportsTriche"};
        String[] stageCols = {"cne", "filiere", "niveau", "noteStage1", "lieuxStage1", "noteStage2", "lieuxStage2", "noteStage3", "lieuxStage3"};

        // Écrire chaque catégorie dans un fichier
        writeCategoryFile(outputDir + "/Données_personnelles.csv", headers, rows, columnIndex, personalCols);
        writeCategoryFile(outputDir + "/Evaluations.csv", headers, rows, columnIndex, evaluationCols);
        writeCategoryFile(outputDir + "/Discipline.csv", headers, rows, columnIndex, disciplineCols);
        writeCategoryFile(outputDir + "/Stages.csv", headers, rows, columnIndex, stageCols);
    }

    private static void writeCategoryFile(String filePath, String[] headers, List<String[]> rows, Map<String, Integer> columnIndex, String[] categoryCols) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            // Écrire l'entête
            writer.writeNext(categoryCols);

            // Écrire les lignes
            for (String[] row : rows) {
                String[] newRow = new String[categoryCols.length];
                for (int i = 0; i < categoryCols.length; i++) {
                    newRow[i] = row[columnIndex.get(categoryCols[i])];
                }
                writer.writeNext(newRow);
            }
        }
    }
    private static void writeCSV(String filePath, String[] headers, List<String[]> rows) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            // Écrire l'entête
            writer.writeNext(headers);

            // Écrire toutes les lignes
            writer.writeAll(rows);
        }
    }
}