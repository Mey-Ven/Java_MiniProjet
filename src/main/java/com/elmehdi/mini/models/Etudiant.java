package com.elmehdi.mini.models;

public class Etudiant {
    public String cne;
    public String nom;
    public double moyenne;
    public double note1;
    public double note2;
    public double note3;
    public double note4;
    public double note5;
    public double note6;


    //Constructeur permettant de créer un étudiant avec ses informations et ses notes

    public Etudiant(String cne, String nom, double note1, double note2, double note3, double note4, double note5, double note6) {
        this.cne = cne;
        this.nom = nom;
        this.note1 = note1;
        this.note2 = note2;
        this.note3 = note3;
        this.note4 = note4;
        this.note5 = note5;
        this.note6 = note6;
    }
}