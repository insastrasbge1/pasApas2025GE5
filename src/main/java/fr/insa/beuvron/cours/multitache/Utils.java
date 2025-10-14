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
package fr.insa.beuvron.cours.multitache;

import java.util.concurrent.locks.Condition;

/**
 *
 * @author fdebertranddeb01
 */
public class Utils {

    public static void sleepNoInterrupt(long dureeInMs) {
        try {
            Thread.sleep(dureeInMs);
        } catch (InterruptedException ex) {
            throw new Error("interruption non attendue");
        }
    }

    public static void waitNoInterrupt(Object verrou) {
        try {
            verrou.wait();
        } catch (InterruptedException ex) {
            throw new Error("interruption non attendue");
        }
    }

    public static void awaitNoInterrupt(Condition cond) {
        try {
            cond.await();
        } catch (InterruptedException ex) {
            throw new Error("interruption non attendue");
        }
    }

    public static void sleepAlea(long dureeBase) {
        try {
            double alea = Math.random() * 1.5 + 0.5;
            long duree = (long) alea * dureeBase;
            Thread.sleep(duree);
        } catch (InterruptedException ex) {
            throw new Error("interruption non attendue");
        }
    }

    public static void joinNoInterrupt(Thread aAttendre) {
        try {
            aAttendre.join();
        } catch (InterruptedException ex) {
            throw new Error("interruption non attendue");
        }
    }

}
