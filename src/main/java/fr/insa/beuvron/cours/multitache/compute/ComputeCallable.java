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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fdebertranddeb01
 */
public class ComputeCallable {

    public static class Compute implements Callable<Long> {

        private long entree;

        public Compute(long entree) {
            this.entree = entree;
        }

        @Override
        public Long call() throws Exception {
            for (long i = 0; i < this.entree; i++) {
            }
            return this.entree;
        }
    }

    public static void test(int nt, long ne) {
        long debut = System.currentTimeMillis();
        ExecutorService exec = Executors.newFixedThreadPool(
                Math.min(1,
                        Runtime.getRuntime().availableProcessors() - 1));
        List<Callable<Long>> toDo = new ArrayList<>(nt);
        for (int i = 0; i < nt; i++) {
            toDo.add(new Compute(ne));
        }
        try {
            List<Future<Long>> res = exec.invokeAll(toDo);
            long tot = 0;
            for (var v : res) {
                tot = tot + v.get();
            }
            System.out.println("done in "
                    + (System.currentTimeMillis() - debut) + " ms");
            System.out.println("res : " + tot);
        } catch (InterruptedException ex) {
            throw new Error("pas d'interrupt", ex);
        } catch (ExecutionException ex) {
            throw new Error(ex);
        }
    }

    public static void main(String[] args) {
        test(100000, 10000);

    }

}
