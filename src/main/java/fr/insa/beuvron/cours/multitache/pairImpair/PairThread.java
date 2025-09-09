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
package fr.insa.beuvron.cours.multitache.pairImpair;

import fr.insa.beuvron.cours.multitache.Utils;

/**
 *
 * @author fdebertranddeb01
 */
public class PairThread extends Thread {
    
    private String nom;

    public PairThread(String nom) {
        this.nom = nom;
    }
    

    @Override
    public void run() {
        for (int i = 0; i < 10; i += 2) {
            Utils.sleepAlea(100);
            System.out.println(this.nom + " : " + i);
        }
    }

    public static void main(String[] args) {
        PairThread p1 = new PairThread("p1");
//        p1.run();
        p1.start();
        PairThread p2 = new PairThread("p2");
        p2.start();
        System.out.println("c'est fini");

    }

}
