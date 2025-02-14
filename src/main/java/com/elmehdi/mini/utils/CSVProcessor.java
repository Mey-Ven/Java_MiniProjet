package com.elmehdi.mini.utils;

// Importation des bibliothèques nécessaires pour la gestion des fichiers CSV
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.*;
import java.util.*;


 // Classe CSVProcessor permettant de lire, trier et enregistrer des étudiants à partir d'un fichier CSV.
 // Trie les étudiants par filière et génère des fichiers correspondants.
 // Divise les données en plusieurs fichiers selon des catégories spécifiques.

public class CSVProcessor {
    public static void main(String[] args) {
        // Définition des chemins des fichiers d'entrée et de sortie
        String inputFile = "src/main/files/donnees_etudiantsLight.csv"; // Fichier source contenant tous les étudiants
        String outputDir = "src/main/files"; // Dossier où les fichiers générés seront stockés

        // Exécution du traitement du fichier CSV
        processCSV(inputFile, outputDir);
    }

     //Méthode permettant de lire un fichier CSV, trier les étudiants par filière et créer des fichiers catégorisés.
     //inputFile  Chemin du fichier CSV source.
     //outputDir  Dossier de sortie où les fichiers seront générés.

    public static void processCSV(String inputFile, String outputDir) {
        try (CSVReader reader = new CSVReader(new FileReader(inputFile))) {
            List<String[]> data = reader.readAll(); // Lecture de toutes les lignes du fichier CSV

            // Extraction des en-têtes et des données
            String[] headers = data.get(0); // Première ligne du fichier = les noms des colonnes
            List<String[]> rows = data.subList(1, data.size()); // Toutes les lignes sauf la première

            // Création d'une structure de données pour stocker l'index des colonnes
            Map<String, Integer> columnIndex = new HashMap<>();
            for (int i = 0; i < headers.length; i++) {
                columnIndex.put(headers[i], i);
            }

            // Trie des étudiants par filière
            Map<String, List<String[]>> filieres = new HashMap<>();
            for (String[] row : rows) {
                String filiere = row[columnIndex.get("filiere")]; // Récupérer la filière de l'étudiant
                filieres.computeIfAbsent(filiere, k -> new ArrayList<>()).add(row);
            }

            // Création des fichiers CSV pour chaque filière
            for (Map.Entry<String, List<String[]>> entry : filieres.entrySet()) {
                String filiere = entry.getKey(); // Nom de la filière
                List<String[]> rowsFiliere = entry.getValue(); // Liste des étudiants de cette filière
                writeCSV(outputDir + "/" + filiere + ".csv", headers, rowsFiliere); // Enregistrement dans un fichier
            }

            // Création des fichiers par catégorie (données personnelles, évaluations, etc.)
            createCategoryFiles(outputDir, headers, rows, columnIndex);

            System.out.println("Fichiers CSV remplis avec succès !");
        } catch (Exception e) {
            e.printStackTrace(); // Affichage des erreurs en cas de problème
        }
    }

     // Méthode permettant de créer des fichiers catégorisés (ex : Données personnelles, Évaluations...).
     // outputDir   Dossier de sortie.
     // headers     En-têtes du fichier CSV.
     // rows        Liste des étudiants sous forme de tableaux de chaînes.
     // columnIndex Index des colonnes pour un accès rapide.
    private static void createCategoryFiles(String outputDir, String[] headers, List<String[]> rows, Map<String, Integer> columnIndex) throws IOException {
        // Définition des catégories et des colonnes correspondantes
        String[] personalCols = {"cne", "cin", "Nom", "Prenom", "anneeNaissance", "VilleBac"};
        String[] evaluationCols = {"cne", "filiere", "niveau", "noteS1", "noteS2", "noteS3", "noteS4", "noteS5", "noteS6"};
        String[] disciplineCols = {"cne", "filiere", "niveau", "nbrAbsences", "nbrRapportsMauvaiseConduite", "nbrRapportsTriche"};
        String[] stageCols = {"cne", "filiere", "niveau", "noteStage1", "lieuxStage1", "noteStage2", "lieuxStage2", "noteStage3", "lieuxStage3"};

        // Génération des fichiers CSV pour chaque catégorie
        writeCategoryFile(outputDir + "/Données_personnelles.csv", headers, rows, columnIndex, personalCols);
        writeCategoryFile(outputDir + "/Evaluations.csv", headers, rows, columnIndex, evaluationCols);
        writeCategoryFile(outputDir + "/Discipline.csv", headers, rows, columnIndex, disciplineCols);
        writeCategoryFile(outputDir + "/Stages.csv", headers, rows, columnIndex, stageCols);
    }

     //Méthode permettant de filtrer et enregistrer uniquement les colonnes nécessaires pour une catégorie spécifique.
     // filePath    Chemin du fichier de sortie.
     // headers     En-têtes du fichier CSV.
     // rows        Liste des étudiants sous forme de tableaux de chaînes.
     // columnIndex Index des colonnes pour un accès rapide.
     // categoryCols Liste des colonnes à inclure dans le fichier.
    private static void writeCategoryFile(String filePath, String[] headers, List<String[]> rows, Map<String, Integer> columnIndex, String[] categoryCols) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            // Écrire l'en-tête du fichier CSV
            writer.writeNext(categoryCols);

            // Filtrer les colonnes demandées et écrire chaque ligne
            for (String[] row : rows) {
                String[] newRow = new String[categoryCols.length];
                for (int i = 0; i < categoryCols.length; i++) {
                    newRow[i] = row[columnIndex.get(categoryCols[i])];
                }
                writer.writeNext(newRow);
            }
        }
    }

     //Méthode générique permettant d'écrire un fichier CSV.
     // filePath Chemin du fichier CSV de sortie.
     // headers  En-têtes du fichier CSV.
     // rows     Données à écrire dans le fichier.
    private static void writeCSV(String filePath, String[] headers, List<String[]> rows) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            // Écriture de l'en-tête
            writer.writeNext(headers);

            // Écriture des données
            writer.writeAll(rows);
        }
    }
}