package com.elmehdi.mini.threads;

import com.elmehdi.mini.models.Etudiant;

public class TaskCalculMoy extends Thread {
    private Etudiant et;

    public TaskCalculMoy(Etudiant et) {
        this.et = et;
    }

    @Override
    public void run() {
        this.et.moyenne = (this.et.note1 + this.et.note2 + this.et.note3 +
                this.et.note4 + this.et.note5 + this.et.note6) / 6;
    }
}