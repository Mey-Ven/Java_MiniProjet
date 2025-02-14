package com.elmehdi.mini.threads;
import com.elmehdi.mini.models.Etudiant; // Importation de la classe Etudiant

  //Classe TaskCalculMoy représente une tâche de calcul de la moyenne d'un étudiant.
  //Elle hérite de la classe Thread pour permettre une exécution parallèle.

public class TaskCalculMoy extends Thread {

    // Attribut privé pour stocker l'objet étudiant dont on va calculer la moyenne
    private Etudiant et;

     // Constructeur de la classe TaskCalculMoy.
    public TaskCalculMoy(Etudiant et) {
        this.et = et;
    }

    //Méthode `run()` qui est exécutée lorsque le thread est lancé, elle calcule la moyenne de l'étudiant en additionnant ses 6 notes et en divisant par 6.
    @Override
    public void run() {
        this.et.moyenne = (this.et.note1 + this.et.note2 + this.et.note3 +
                this.et.note4 + this.et.note5 + this.et.note6) / 6;
    }
}