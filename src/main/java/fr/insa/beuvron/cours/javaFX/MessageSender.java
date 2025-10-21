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
package fr.insa.beuvron.cours.javaFX;

import fr.insa.beuvron.cours.multitache.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import javafx.scene.control.TextArea;

/**
 *
 * @author francois
 */
public class MessageSender extends Thread {

    public static final long DEFAUT_REFRESH_RATE_IN_NANOS = 1000000L;

    private long refreshRateInNanos;
    private TextArea taMessages;
    private ArrayList<String> messages;

    private ReentrantLock lock;
    private Condition noMessages;

    private boolean shouldStop;

    public MessageSender(TextArea taMessages, long refreshRateInNanos) {
        this.refreshRateInNanos = refreshRateInNanos;
        this.taMessages = taMessages;
        this.messages = new ArrayList<>();
        this.lock = new ReentrantLock(true);
        this.noMessages = this.lock.newCondition();
        this.shouldStop = false;
    }

    public MessageSender(TextArea taMessages) {
        this(taMessages, DEFAUT_REFRESH_RATE_IN_NANOS);
    }

    public void addMessage(String message) {
        this.lock.lock();
        try {
            this.messages.add(message);
        } finally {
            this.lock.unlock();
        }
    }

    public void requestStop() {
        this.shouldStop = true;
    }

    @Override
    public void run() {
        while (!this.shouldStop) {
            this.lock.lock();
            try {
                while (!this.shouldStop && this.messages.isEmpty()) {
                    try {
                        this.noMessages.awaitNanos(this.refreshRateInNanos);
                    } catch (InterruptedException ex) {
                        throw new Error(ex);
                    }
                }
                String concat = this.messages.stream().collect(Collectors.joining(
                        "\n","----debut transmission----\n","\n----fin transmission----\n"));
                this.taMessages.appendText(concat);
                this.messages.clear();
            } finally {
                this.lock.unlock();
            }
        }
    }

    public static class Sender extends Thread {

        MessageSender ms;
        private String nom;
        private long frequenceAleaMessagesInMs;
        private boolean shouldStop = false;
        private int messageNum = 1;

        public Sender(MessageSender ms, String nom, long frequenceAleaMessagesInMs) {
            this.ms = ms;
            this.nom = nom;
            this.frequenceAleaMessagesInMs = frequenceAleaMessagesInMs;
        }

        public void requestStop() {
            this.shouldStop = true;
        }

        @Override
        public void run() {
            while (!this.shouldStop) {
                Utils.sleepAlea(this.frequenceAleaMessagesInMs);
                this.ms.addMessage(this.nom + " : " + this.messageNum);
                this.messageNum++;
            }
        }
    }

    public static class TestSenders extends Thread {

        private TextArea ta;
        private long refreshRateInNanos;
        private int nbrSender;
        private long frequenceAleaMessagesInMs;
        private long totTime;

        private MessageSender ms;

        public TestSenders(TextArea ta, long refreshRateInNanos, int nbrSender, long frequenceAleaMessagesInMs, long totTime) {
            this.ta = ta;
            this.refreshRateInNanos = refreshRateInNanos;
            this.nbrSender = nbrSender;
            this.frequenceAleaMessagesInMs = frequenceAleaMessagesInMs;
            this.totTime = totTime;
            this.ms = new MessageSender(this.ta, this.refreshRateInNanos);
            this.ms.start();
        }

        public void run() {

            List<Sender> lesSenders = new ArrayList<Sender>(this.nbrSender);
            for (int i = 1; i <= this.nbrSender; i++) {
                lesSenders.add(new Sender(this.ms,"S" + i,this.frequenceAleaMessagesInMs));
            }
            for (var us : lesSenders) {
                us.start();
            }
            Utils.sleepNoInterrupt(this.totTime);
            for (var us : lesSenders) {
                us.requestStop();
            }
            // pour vérifier stop OK
            for (var us : lesSenders) {
                Utils.joinNoInterrupt(us);
            }
            ms.addMessage("All senders Stopped");
            ms.requestStop();
            // pour etre sur que la request à été prise en compte
            Utils.sleepNoInterrupt(refreshRateInNanos / 1000 * 2);
            ms.addMessage("ne devrait pas être affiché");
        }
    }
    
    public static void testSenders(TextArea ta, long refreshRateInNanos, int nbrSender, long frequenceAleaMessagesInMs, long totTime) {
        TestSenders testeur = new TestSenders(ta, refreshRateInNanos, nbrSender, frequenceAleaMessagesInMs, totTime);
        testeur.start();
    }
}
