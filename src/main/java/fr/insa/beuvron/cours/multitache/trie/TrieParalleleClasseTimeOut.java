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
package fr.insa.beuvron.cours.multitache.trie;

import fr.insa.beuvron.cours.multitache.Utils;
import java.util.Arrays;

/**
 *
 * @author fdebertranddeb01
 */
public class TrieParalleleClasseTimeOut extends Thread {

    public static enum Statut {
        OK, TIMEOUT
    }

    private int[] tab;
    private int debut;
    private int fin;
    private long initial;
    private long timeout;
    private Statut status;

    public TrieParalleleClasseTimeOut(int[] tab, int debut, int fin, long initial, long timeout) {
        this.tab = tab;
        this.debut = debut;
        this.fin = fin;
        this.initial = initial;
        this.timeout = timeout;
    }

    @Override
    public void run() {
        if (System.currentTimeMillis() - initial > timeout) {
            this.status = Statut.TIMEOUT;
        } else if (fin - debut > 1000) {
            int milieu = debut + (fin - debut) / 2;
            TrieParalleleClasseTimeOut b1
                    = new TrieParalleleClasseTimeOut(tab, debut, milieu, initial, timeout);
            TrieParalleleClasseTimeOut b2
                    = new TrieParalleleClasseTimeOut(tab, milieu, fin, initial, timeout);
            b1.start();
            b2.start();
            Utils.joinNoInterrupt(b1);
            Utils.joinNoInterrupt(b2);
            if (b1.status == Statut.OK && b2.status == Statut.OK) {
                TrieSequentiel.fusion(tab, debut, milieu, fin);
                this.status = Statut.OK;
            } else {
                this.status = Statut.TIMEOUT;
            }
        } else {
            TrieSequentiel.trie(tab, debut, fin);
            this.status = Statut.OK;
        }

    }

    public static void test1() {
        int[] tab = TrieSequentiel.tabAlea(1000000, 100);
        long initial = System.currentTimeMillis();
//        System.out.println(Arrays.toString(tab));
        TrieParalleleClasseTimeOut t = new TrieParalleleClasseTimeOut(tab, 0, tab.length,
                initial, 100);
        t.start();
        Utils.joinNoInterrupt(t);
        if (t.status == Statut.OK) {
            System.out.println("ok ? : " + TrieSequentiel.testTrie(tab));
            long duree = System.currentTimeMillis() - initial;
            System.out.println("en " + duree + " ms");
//        System.out.println(Arrays.toString(tab));
        } else {
            System.out.println("Timeout");
        }
    }

    public static void main(String[] args) {
        test1();
    }

}
