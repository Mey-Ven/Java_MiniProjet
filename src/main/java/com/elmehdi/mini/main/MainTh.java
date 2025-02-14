package com.elmehdi.mini.main;

import com.elmehdi.mini.models.Etudiant;
import com.elmehdi.mini.threads.TaskCalculMoy;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;

public class MainTh {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long startTime = System.currentTimeMillis(); // Début du chronomètre

        // Création des étudiants
        Etudiant et1 = new Etudiant("cne1", "smaili", 14, 12, 11, 9, 20, 12);
        Etudiant et2 = new Etudiant("cne2", "hassani", 10, 14, 12, 11, 9, 20);
        Etudiant et3 = new Etudiant("cne3", "hamdani", 9, 10, 14, 12, 17, 5);
        Etudiant et4 = new Etudiant("cne4", "talbi", 16, 14, 12, 11, 9, 20);
        Etudiant et5 = new Etudiant("cne5", "saadani", 12, 14, 5, 17, 19, 11);
        Etudiant et6 = new Etudiant("cne6", "malhawi", 11, 14, 15, 12, 20, 8);
        Etudiant et7 = new Etudiant("cne7", "nouri", 14, 2, 12, 17, 15, 18);
        Etudiant et8 = new Etudiant("cne8", "mounir", 13, 20, 19, 11, 12, 14);

        // Création d'un ThreadPoolExecutor avec 4 threads
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
        Collection<Future<?>> futures = new LinkedList<>();

        // Exécution des tâches
        futures.add(executor.submit(new TaskCalculMoy(et1)));
        futures.add(executor.submit(new TaskCalculMoy(et2)));
        futures.add(executor.submit(new TaskCalculMoy(et3)));
        futures.add(executor.submit(new TaskCalculMoy(et4)));
        futures.add(executor.submit(new TaskCalculMoy(et5)));
        futures.add(executor.submit(new TaskCalculMoy(et6)));
        futures.add(executor.submit(new TaskCalculMoy(et7)));
        futures.add(executor.submit(new TaskCalculMoy(et8)));

        // Attendre la fin des threads
        for (Future<?> f : futures) {
            f.get();
        }

        // Affichage des résultats
        System.out.println(et1.cne + " : " + et1.moyenne);
        System.out.println(et2.cne + " : " + et2.moyenne);
        System.out.println(et3.cne + " : " + et3.moyenne);
        System.out.println(et4.cne + " : " + et4.moyenne);
        System.out.println(et5.cne + " : " + et5.moyenne);
        System.out.println(et6.cne + " : " + et6.moyenne);
        System.out.println(et7.cne + " : " + et7.moyenne);
        System.out.println(et8.cne + " : " + et8.moyenne);

        // Fermeture de l'executor
        executor.shutdown();

        long endTime = System.currentTimeMillis(); // Fin du chronomètre
        System.out.println("Temps d'exécution total : " + (endTime - startTime) + " ms");
    }
}