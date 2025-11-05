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

import fr.insa.beuvron.utils.ConsoleFdB;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author francois
 */
public class Client {

    public static void clientDirect() {
        try {
            String adr = ConsoleFdB.entreeString("adresse serveur : ");
            int port = ConsoleFdB.entreeInt("port du serveur : ");
            int forceLocalPort = ConsoleFdB.entreeInt("port local à utiliser (0 pour automatique) : ");
            Socket soc = new Socket(adr, port, null, forceLocalPort);
//            Socket soc = new Socket(adr, port);
            System.out.println("Connexion acceptée :");
            System.out.println("  Adresse serveur : " + soc.getInetAddress().getHostAddress());
            System.out.println("  Port serveur    : " + soc.getPort());
            System.out.println("  Adresse locale : " + soc.getLocalAddress().getHostAddress());
            System.out.println("  Port local     : " + soc.getLocalPort());
            try (OutputStreamWriter out = new OutputStreamWriter(soc.getOutputStream(), StandardCharsets.UTF_8)) {
                String mess = "";
                while (!mess.equals("FIN")) {
                    mess = ConsoleFdB.entreeString("message : ");
                    out.write(mess + "\n");
                    out.flush();
                }
            }
        } catch (IOException ex) {
            throw new Error(ex);
        }
    }

    public static void clientMulti() {
        try {
            String adr = ConsoleFdB.entreeString("adresse serveur : ");
            int port = ConsoleFdB.entreeInt("port du serveur : ");
            int forceLocalPort = ConsoleFdB.entreeInt("port local à utiliser (0 pour automatique) : ");
            Socket soc = new Socket(adr, port, null, forceLocalPort);
//            Socket soc = new Socket(adr, port);
            System.out.println("Connexion acceptée :");
            System.out.println("  Adresse serveur : " + soc.getInetAddress().getHostAddress());
            System.out.println("  Port serveur    : " + soc.getPort());
            System.out.println("  Adresse locale : " + soc.getLocalAddress().getHostAddress());
            System.out.println("  Port local     : " + soc.getLocalPort());
            String name = ConsoleFdB.entreeString("nom du client : ");
            try (OutputStreamWriter out = new OutputStreamWriter(soc.getOutputStream(), StandardCharsets.UTF_8)) {
                out.write(name + "\n");
                String mess = "";
                while (!mess.equals("FIN")) {
                    mess = ConsoleFdB.entreeString("message : ");
                    out.write(mess + "\n");
                    out.flush();
                }
            }
        } catch (IOException ex) {
            throw new Error(ex);
        }
    }

    public static void main(String[] args) {
//        clientDirect();
        clientMulti();
    }

}
