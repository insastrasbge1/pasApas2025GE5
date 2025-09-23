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
package fr.insa.beuvron.cours.multitache.incremente;

import fr.insa.beuvron.cours.multitache.Utils;
import static fr.insa.beuvron.cours.multitache.incremente.IncrementeurStatique.compteur;

/**
 *
 * @author fdebertranddeb01
 */
public class IncrementeurAttribut extends Thread {

    public static class ClasseContenantLeCompteur {

        public long compteur;
    }

    private ClasseContenantLeCompteur leCompteur;

    public IncrementeurAttribut(ClasseContenantLeCompteur leCompteur) {
        this.leCompteur = leCompteur;
    }

    @Override
    public void run() {
        for (long i = 0; i < 100000000; i++) {
            synchronized (this.leCompteur) {
                this.leCompteur.compteur++;
            }
        }
    }

    public static void main(String[] args) {
        long debut = System.currentTimeMillis();
        IncrementeurAttribut.ClasseContenantLeCompteur c1 = new ClasseContenantLeCompteur();
        IncrementeurAttribut i1 = new IncrementeurAttribut(c1);
        IncrementeurAttribut i2 = new IncrementeurAttribut(c1);
        i1.start();
        i2.start();
        Utils.joinNoInterrupt(i1);
        Utils.joinNoInterrupt(i2);
        System.out.println("compteur : " + c1.compteur);
        System.out.println("elapsed time : " + (System.currentTimeMillis() - debut));
    }

}
