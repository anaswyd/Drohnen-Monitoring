package de.uas.fra.project.group25.javaproject;

import de.uas.fra.project.group25.javaproject.Drone.DroneStorage;
import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {

    //Creates the two singleton classes
    @Override
    public void start(Stage stage){
        DroneStorage.getInstance();
        WindowAppearance.getInstance();
    }

    //launch() calls start() and launches the application
    public static void main(String[] args) {
        launch();
    }
}
