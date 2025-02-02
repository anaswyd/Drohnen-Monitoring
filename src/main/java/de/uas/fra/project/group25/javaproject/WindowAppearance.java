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

    final int WINDOWMINHEIGHT = 640;
    final int WINDOWMINWIDTH = 800;

    private static WindowAppearance windowAppearance;
    private final WindowFactory windowFactory;
    private final Stage mainStage;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * Private constructor because class is singleton.
     * Initialises the stage.
     */
    private WindowAppearance(Stage stage){
        this.windowFactory = new WindowFactory();
        Parent root = null;

        try{
            root = new FXMLLoader(getClass().getResource("ApplicationContainer.fxml")).load();
        }catch (IOException e){
            logger.log(Level.SEVERE, "Could not load root fxml");
        }

        stage.setScene(new Scene(root, WINDOWMINWIDTH, WINDOWMINHEIGHT-40));
        stage.setMinHeight(WINDOWMINHEIGHT);
        stage.setMinWidth(WINDOWMINWIDTH);
        stage.setMaximized(true);
        stage.setTitle("  Drone Simulation Group 25");
        stage.getIcons().add(new Image("titleIcon.png"));
        stage.show();
        mainStage = stage;
    }

    /**
     * Fetches the instance of WindowAppearance
     * @return WindowAppearance
     */
    public static WindowAppearance getInstance(){
        if (windowAppearance == null) {
            windowAppearance = new WindowAppearance(new Stage());
        }
        return windowAppearance;
    }

    /**
     * Fetches the WindowFactory of WindowAppearance
     * @return WindowFactory
     */
    public WindowFactory getWindowFactory() {
        return windowFactory;
    }

    /**
     * Fetches the current stage
     * @return Stage
     */
    public Stage getStage() {
        return mainStage;
    }
}