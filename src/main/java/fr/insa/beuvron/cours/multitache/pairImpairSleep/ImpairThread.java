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
package fr.insa.beuvron.cours.multitache.pairImpairSleep;

import fr.insa.beuvron.cours.multitache.pairImpair.*;
import fr.insa.beuvron.cours.multitache.Utils;

/**
 *
 * @author fdebertranddeb01
 */
public class ImpairThread extends Thread {

    private String nom;
    private PairThread pair;

    public ImpairThread(String nom) {
        this.nom = nom;
    }

    @Override
    public void run() {
        for (int i = 1; i < 10; i += 2) {
            try {
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException ex) {
                System.out.println("impair se reveille");
            }
            Utils.sleepAlea(200);
            System.out.println(this.getNom() + " : " + i);
            this.pair.interrupt();
        }
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return the pair
     */
    public PairThread getPair() {
        return pair;
    }

    /**
     * @param pair the pair to set
     */
    public void setPair(PairThread pair) {
        this.pair = pair;
    }

}
