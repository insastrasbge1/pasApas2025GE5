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

import fr.insa.beuvron.cours.multitache.Utils;

/**
 *
 * @author fdebertranddeb01
 */
public class PairThread extends Thread {
    
    private String nom;
    private ImpairThread impair;

    public PairThread(String nom) {
        this.nom = nom;
    }
    

    @Override
    public void run() {
        for (int i = 0; i < 10; i += 2) {
            Utils.sleepAlea(100);
            System.out.println(this.getNom() + " : " + i);
            this.impair.interrupt();
            try {
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException ex) {
                System.out.println("pair se reveille");
            }
            
        }
    }

    public static void main(String[] args) {
        PairThread p1 = new PairThread("p1");
        ImpairThread p2 = new ImpairThread("p2");
        p1.setImpair(p2);
        p2.setPair(p1);
        p1.start();
        p2.start();
        Utils.joinNoInterrupt(p1);
        Utils.joinNoInterrupt(p2);
        System.out.println("c'est fini");

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
     * @return the impair
     */
    public ImpairThread getImpair() {
        return impair;
    }

    /**
     * @param impair the impair to set
     */
    public void setImpair(ImpairThread impair) {
        this.impair = impair;
    }

}
