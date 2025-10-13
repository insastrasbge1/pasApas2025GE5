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

/**
 *
 * @author francois
 */
public class ProdConsoList {
    
    
    
    private LinkedList<String> buffer;
    private int maxObjInList;
    
    private Random rand = new Random();
    
    public void add(String toAdd) {
        synchronized(this.buffer) {
            while (this.buffer.size() >= this.maxObjInList) {
                Utils.waitNoInterrupt(this.buffer);
            }
            System.out.println("adding " + toAdd);
            this.buffer.offer(toAdd);
            this.buffer.notify();
        }
    }
    
    public String remove() {
        synchronized(this.buffer) {
            while (this.buffer.isEmpty()) {
                Utils.waitNoInterrupt(this.buffer);
            }
            String found = this.buffer.poll();
            System.out.println("found "+found);
            this.buffer.notify();
            return found;
        }
    }
    
}
