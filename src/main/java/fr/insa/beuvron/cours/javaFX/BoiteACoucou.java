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

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author francois
 */
public class BoiteACoucou extends BorderPane{
    
    public TextField tfNom;
    public TextArea taMessage;
    public Button bCoucou;
    public Button bSalut;
    public Button bAutoSend;
    
    public BoiteACoucou() {
        this.tfNom = new TextField();
        HBox hbNom = new HBox(new Label("nom : "),this.tfNom);
        this.taMessage = new TextArea();
        this.bCoucou = new Button("Coucou");
        this.bCoucou.setOnAction((t) -> {
            this.addMessage("coucou "+this.tfNom.getText());
        });
        this.bSalut = new Button("Salut");
        this.bSalut.setOnAction((t) -> {
            this.addMessage("salut "+this.tfNom.getText());
        });
        this.bAutoSend = new Button("Auto Send");
        this.bAutoSend.setOnAction((t) -> {
//    public static void testSenders(TextArea ta, long refreshRateInNanos, int nbrSender, long frequenceAleaMessagesInMs, long totTime) {
            MessageSender.testSenders(this.taMessage, 1000000, 5, 500, 5*1000);
        });
        HBox hbBoutons = new HBox(this.bCoucou,this.bSalut,this.bAutoSend);
        this.setTop(hbNom);
        this.setCenter(this.taMessage);
        this.setBottom(hbBoutons);
    }

    private void addMessage(String string) {
        this.taMessage.appendText(string+"\n");
    }
    
}
