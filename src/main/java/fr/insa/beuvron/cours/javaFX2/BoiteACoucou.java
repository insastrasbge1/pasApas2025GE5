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
package fr.insa.beuvron.cours.javaFX2;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author fdebertranddeb01
 */
public class BoiteACoucou extends VBox{
    
    private Label lNom;
    private TextField tfNom;
    private TextArea taMessages;
    private Button bCoucou;
    private Button bSalut;
    
    
    public BoiteACoucou() {
        this.lNom = new Label("nom :");
        this.tfNom = new TextField();
        HBox hbNom = new HBox(this.lNom,this.tfNom);
        this.getChildren().add(hbNom);
        
        this.taMessages = new TextArea();
        this.getChildren().add(this.taMessages);
        
        this.bCoucou = new Button("Coucou");
        this.bCoucou.setOnMouseClicked((t) -> {
            this.doCoucou();
        });
        this.bSalut = new Button("Salut");
        this.bSalut.setOnMouseClicked((t) -> {
            this.doSalut();
        });
        HBox hbBoutons = new HBox(this.bCoucou,this.bSalut);
        this.getChildren().add(hbBoutons);
    }

    public void doCoucou() {
        String nom = this.tfNom.getText();
        this.taMessages.appendText("coucou " + nom + "\n");
    }
    
    public void doSalut() {
        String nom = this.tfNom.getText();
        this.taMessages.appendText("salut " + nom + "\n");
    }
    
    
    
}
