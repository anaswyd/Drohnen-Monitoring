package de.uas.fra.project.group25.javaproject;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WindowAppearance {

    final int WINDOWMINHEIGHT = 800;
    final int WINDOWMINWIDTH = 1000;

    private static WindowAppearance windowAppearance;
    private final WindowFactory windowFactory;
    private final Stage mainStage;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * Private constructor because class is singleton.
     * Initialises the stage with ApplicationContainer.fxml as root scene.
     */
    private WindowAppearance(Stage stage){
        this.windowFactory = new WindowFactory();
        Parent root = null;

        try{
            root = new FXMLLoader(getClass().getResource("ApplicationContainer.fxml")).load();
        }catch (IOException e){
            logger.log(Level.SEVERE, "Failed to load ApplicationContainer.fxml. Program cannot continue.");
        }

        stage.setScene(new Scene(root, WINDOWMINWIDTH, WINDOWMINHEIGHT-40));
        stage.setMinHeight(WINDOWMINHEIGHT);
        stage.setMinWidth(WINDOWMINWIDTH);
        stage.setMaximized(true);
        stage.setTitle("  Drone Simulation Group 25");
        try{
            stage.getIcons().add(new Image("titleIcon.png"));
        }catch (NullPointerException e){
            logger.log(Level.WARNING, "Failed to load title icon.");
        }
        stage.show();
        mainStage = stage;
    }

    /**
     * Fetches the instance of WindowAppearance
     * @return de.uas.fra.project.group25.javaproject.WindowAppearance
     */
    public static WindowAppearance getInstance(){
        if (windowAppearance == null) {
            windowAppearance = new WindowAppearance(new Stage());
        }
        return windowAppearance;
    }

    /**
     * Fetches the WindowFactory of WindowAppearance
     * @return de.uas.fra.project.group25.javaproject.WindowFactory
     */
    public WindowFactory getWindowFactory() {
        return windowFactory;
    }

    /**
     * Fetches the current stage
     * @return javafx.stage.Stage
     */
    public Stage getStage() {
        return mainStage;
    }
}