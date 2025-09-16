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
public class TrieParalleleClasse extends Thread{
    
    private int[] tab;
    private int debut;
    private int fin;

    public TrieParalleleClasse(int[] tab, int debut, int fin) {
        this.tab = tab;
        this.debut = debut;
        this.fin = fin;
    }
    
    
    
    @Override
    public void run() {
        if (fin-debut > 1) {
            int milieu = debut + (fin - debut) / 2;
            TrieParalleleClasse b1 = new TrieParalleleClasse(tab, debut, milieu);
            TrieParalleleClasse b2 = new TrieParalleleClasse(tab, milieu, fin);
            b1.start();
            b2.start();
            Utils.joinNoInterrupt(b1);
            Utils.joinNoInterrupt(b2);
            fusion(tab,debut,milieu,fin);
        }
        
    }
    
    public static void fusion(int[] tab,int debut,int milieu,int fin) {
        int[] buf = new int[fin-debut];
        int cur1 = debut;
        int cur2 = milieu;
        for (int i = 0 ; i < buf.length ; i ++) {
            if (cur1 >= milieu) {
                buf[i] = tab[cur2];
                cur2 ++;
            } else if (cur2 >= fin) {
                buf[i] = tab[cur1];
                cur1 ++;
            } else if (tab[cur1] <= tab[cur2]) {
                buf[i] = tab[cur1];
                cur1 ++;
            } else {
                buf[i] = tab[cur2];
                cur2 ++;
            }
        }
        for(int i = 0 ; i < fin-debut ; i ++) {
            tab[i+debut] = buf[i];
        }
    }
    
    public static int[] tabAlea(int taille,int maxVal) {
        int[] res = new int[taille];
        for(int i = 0 ; i <= res.length ; i ++) {
            res[i] = (int) (Math.random() * maxVal);
        }
        return res;
    }
    
    public static boolean testTrie(int[] tab) {
        boolean res = true;
        int i = 0;
        while (res && i < tab.length-1) {
            res = tab[i] < tab[i+1];
            i ++;
        }
        return res;
    }
    
    public static void test1() {
        int[] tab = tabAlea(10000, 100);
        long initial = System.currentTimeMillis();
//        System.out.println(Arrays.toString(tab));
        TrieParalleleClasse t = new TrieParalleleClasse(tab, 0, tab.length);
        t.start();
        Utils.joinNoInterrupt(t);
        System.out.println("ok ? : " + testTrie(tab));
        long duree = System.currentTimeMillis() - initial;
        System.out.println("en " + duree + " ms");
//        System.out.println(Arrays.toString(tab));
    }
    
    public static void main(String[] args) {
        test1();
    }
    
}
