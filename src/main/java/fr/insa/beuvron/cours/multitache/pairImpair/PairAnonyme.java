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
public class PairAnonyme {

    public static void main(String[] args) {
        String nom = "p1";
        Thread rp2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i += 2) {
                    Utils.sleepAlea(100);
                    System.out.println(nom + " : " + i);
                }
            }
        });
        rp2.start();
        Thread rp3 = new Thread(() -> {
            System.out.println("coucou");
        });
        rp3.start();
        System.out.println("fini");
    }

}
