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
package fr.insa.beuvron.cours.multitache.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author francois
 */
public class Serveur {

    public static final int PORT = 50001;

    public static void sansThread() {
        try {
            Inet4Address host = INetAdressUtil.premiereAdresseNonLoopback();
            ServerSocket ss = new ServerSocket(PORT, 10, host);
            System.out.println("Serveur en attente :");
            System.out.println("ip : " + host.getHostAddress());
            System.out.println("port : " + PORT);
            Socket soc = ss.accept();
            System.out.println("Connexion acceptée :");
            System.out.println("  Adresse client : " + soc.getInetAddress().getHostAddress());
            System.out.println("  Port client    : " + soc.getPort());
            System.out.println("  Adresse locale : " + soc.getLocalAddress().getHostAddress());
            System.out.println("  Port local     : " + soc.getLocalPort());
            try (BufferedReader in = new BufferedReader(new InputStreamReader(soc.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println("reçu : " + line + "\n");
                }
            }
        } catch (IOException ex) {
            throw new Error(ex);
        }
    }

    public static class GereClient extends Thread {

        private String clientName;
        private Socket conn;

        public GereClient(Socket conn) {
            this.conn = conn;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(this.conn.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                this.clientName = in.readLine();
                while ((line = in.readLine()) != null) {
                    System.out.println("reçu from : " + this.clientName + " : " + line + "\n");
                }
            } catch (IOException ex) {
                throw new Error(ex);
            }

        }
    }

    public static void multiClient() {
        try {
            Inet4Address host = INetAdressUtil.premiereAdresseNonLoopback();
            ServerSocket ss = new ServerSocket(PORT, 10, host);
            System.out.println("Serveur en attente :");
            System.out.println("ip : " + host.getHostAddress());
            System.out.println("port : " + PORT);
            while (true) {
                Socket soc = ss.accept();
                new GereClient(soc).start();
            }
        } catch (IOException ex) {
            throw new Error(ex);
        }

    }

    public static void main(String[] args) {
        sansThread();
//        multiClient();
    }

}
