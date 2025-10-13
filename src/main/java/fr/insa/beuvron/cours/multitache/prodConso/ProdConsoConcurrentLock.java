/*
Copyright 2000- Francois de Bertrand de Beuvron

This file is part of CoursBeuvron.

CoursBeuvron is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

CoursBeuvron is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with CoursBeuvron.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.insa.beuvron.cours.multitache.prodConso;

import fr.insa.beuvron.cours.multitache.Utils;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author fdebertranddeb01
 */
public class ProdConsoConcurrentLock {

    private LinkedList<String> buffer;

    private ReentrantLock lock;
    private Condition condEmpty;
    private Condition condFull;

    private int maxElems;
    private long tempsProd;
    private long tempsConso;

    private Random rand = new Random();

    public ProdConsoConcurrentLock(int maxElems, long tempsProd, long tempsConso) {
        this.buffer = new LinkedList<>();
        this.maxElems = maxElems;
        this.tempsProd = tempsProd;
        this.tempsConso = tempsConso;
        this.lock = new ReentrantLock(true);
        this.condEmpty = this.lock.newCondition();
        this.condFull = this.lock.newCondition();
    }

    public void add(String toAdd) {
        this.lock.lock();
        try {
            while (this.buffer.size() >= this.maxElems) {
                System.out.println("buffer plein ==> attente consomateurs");
                Utils.awaitNoInterrupt(this.condFull);
            }
            this.buffer.offer(toAdd);
            System.out.println("j'ajoute " + toAdd + " --> elems : " + this.buffer.size());
            this.condEmpty.signal();
        } finally {
            this.lock.unlock();
        }
    }

    public String recup() {
        this.lock.lock();
        try {
            while (this.buffer.isEmpty()) {
                System.out.println("buffer vide ==> attente producteurs");
                Utils.awaitNoInterrupt(this.condEmpty);
            }
            String res = this.buffer.poll();
            System.out.println("recup : " + res + " --> elems : " + this.buffer.size());
            this.condFull.signal();
            return res;
        } finally {
            this.lock.unlock();
        }
    }

    public class Producteur extends Thread {

        private String nom;

        public Producteur(String nom) {
            this.nom = nom;
        }

        @Override
        public void run() {
            while (true) {
                Utils.sleepNoInterrupt(tempsProd);
                String toAdd = Integer.toHexString(rand.nextInt());
                System.out.println(nom + " : a produit " + toAdd);
                add(toAdd);
            }
        }
    }

    public class Consommateur extends Thread {

        private String nom;

        public Consommateur(String nom) {
            this.nom = nom;
        }

        @Override
        public void run() {
            while (true) {
                Utils.sleepNoInterrupt(tempsConso);
                System.out.println(this.nom + " : veut recup");
                String recup = recup();
                System.out.println(this.nom + " obtient " + recup);
            }
        }
    }

    public static void test(int tailleBuffer, int nbrProd, int nbrConso,
            long tempsProd, long tempsConso) {
        ProdConsoConcurrentLock pc = new ProdConsoConcurrentLock(tailleBuffer, tempsProd, tempsConso);
        for (int i = 0; i < nbrProd; i++) {
            Producteur p = pc.new Producteur("P" + i);
            p.start();
        }
        for (int i = 0; i < nbrConso; i++) {
            Consommateur c = pc.new Consommateur("C" + i);
            c.start();
        }

    }

    public static void main(String[] args) {
        // test basique pour voir ce qui se passe
//        test(5, 1, 1, 100, 200);
        // multi prod/conso
        // trop de producteurs
        test(5, 10, 5, 100, 100);
        // trop de consommateurs
//        test(5, 5, 10, 100, 100);

    }

}
