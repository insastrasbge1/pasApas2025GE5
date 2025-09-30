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
package fr.insa.beuvron.cours.multitache.pairImpairWait;

import fr.insa.beuvron.cours.multitache.Utils;

/**
 *
 * @author fdebertranddeb01
 */
public class PairImpair {

    public class PairThread extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < 10; i += 2) {
                Utils.sleepAlea(100);
                System.out.println("Pair : " + i);
                synchronized (PairImpair.this) {
                    PairImpair.this.notifyAll();
                }
                synchronized (PairImpair.this) {
                    try {
                        PairImpair.this.wait();
                    } catch (InterruptedException ex) {
                        throw new Error("ne devrait pas arriver ", ex);
                    }

                }
            }

        }
    }

    public class ImpairThread extends Thread {

        @Override
        public void run() {
            for (int i = 1; i < 10; i += 2) {
                synchronized (PairImpair.this) {
                    Utils.waitNoInterrupt(PairImpair.this);
                }
                Utils.sleepAlea(200);
                System.out.println("ImPair : " + i);
                synchronized (PairImpair.this) {
                    PairImpair.this.notifyAll();
                }
            }
        }

    }

    public static void test1() {
        PairImpair pi = new PairImpair();
        PairThread pair = pi.new PairThread();
        ImpairThread impair = pi.new ImpairThread();
        pair.start();
        impair.start();
        Utils.joinNoInterrupt(pair);
        Utils.joinNoInterrupt(impair);
        System.out.println("fini");
    }

    public static void main(String[] args) {
        test1();
    }

}
