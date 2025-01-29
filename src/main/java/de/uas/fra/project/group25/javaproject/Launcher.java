package de.uas.fra.project.group25.javaproject;

import de.uas.fra.project.group25.javaproject.Drone.DroneStorage;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        WindowAppearance.getInstance();
        DroneStorage.getInstance();
    }

    public static void main(String[] args) {
        launch();
    }
}
