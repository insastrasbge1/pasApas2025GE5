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
package fr.insa.beuvron.cours.multitache.compute;

import fr.insa.beuvron.cours.multitache.Utils;

/**
 *
 * @author fdebertranddeb01
 */
public class ComputeRunnable {
    
    public static class Compute implements Runnable{

        private long entree;
        private long resultat;
        
        public Compute(long entree) {
            this.entree = entree;
        }
        
        @Override
        public void run() {
            for (long i = 0 ; i < this.getEntree() ; i ++) {
                
            }
            this.resultat = this.getEntree();
            
        }   

        /**
         * @return the entree
         */
        public long getEntree() {
            return entree;
        }

        /**
         * @return the resultat
         */
        public long getResultat() {
            return resultat;
        }
    }
    
    public static void test(int nt,long ne) {
        long debut = System.currentTimeMillis();
        Compute[] runnables = new Compute[nt];
        Thread[] executeurs = new Thread[nt];
        for(int i = 0 ; i < nt ; i ++) {
            runnables[i] = new Compute(ne);
            executeurs[i] = new Thread(runnables[i]);
            executeurs[i].start();
        }
        long tot = 0;
        for(int i = 0 ; i < nt ; i ++) {
            Utils.joinNoInterrupt(executeurs[i]);
            tot = tot + runnables[i].getResultat();
        }
        System.out.println("done in " + 
                (System.currentTimeMillis()-debut) + " ms");
        System.out.println("res : " + tot);
    }
    
    public static void main(String[] args) {
        test(1000, 1000000);
    }
    
}
