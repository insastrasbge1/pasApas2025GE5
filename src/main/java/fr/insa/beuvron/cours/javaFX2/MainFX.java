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

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author fdebertranddeb01
 */
public class MainFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        BoiteACoucou bac = new BoiteACoucou();
        Scene sc = new Scene(bac);
        primaryStage.setScene(sc);
        primaryStage.setTitle("Boite A coucou");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
