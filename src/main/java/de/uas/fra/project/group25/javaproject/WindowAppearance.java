package de.uas.fra.project.group25.javaproject;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class WindowAppearance {

    final int WINDOWMINHEIGHT = 640;
    final int WINDOWMINWIDTH = 800;

    private static WindowAppearance windowAppearance;
    private final WindowFactory windowFactory;
    private final Stage mainStage;

    private WindowAppearance(Stage stage){
        this.windowFactory = new WindowFactory();
        Parent root = null;

        try{
            root = new FXMLLoader(getClass().getResource("ApplicationContainer.fxml")).load();
        }catch (IOException e){
            //must crash
            e.printStackTrace();
        }

        stage.setScene(new Scene(root, WINDOWMINWIDTH, WINDOWMINHEIGHT-40));
        stage.setMinHeight(WINDOWMINHEIGHT);
        stage.setMinWidth(WINDOWMINWIDTH);
        stage.setMaximized(true);
        stage.setTitle("");
        //stage.getIcons().add(new Image("C:/Users/chuan_ioxs6dv/IdeaProjects/thereshope/src/main/resources/Icons/2541514.png"));
        stage.show();
        mainStage = stage;
    }

    public static WindowAppearance getInstance(){
        if (windowAppearance == null) {
            windowAppearance = new WindowAppearance(new Stage());
        }
        return windowAppearance;
    }

    public WindowFactory getWindowFactory() {
        return windowFactory;
    }

    public Stage getStage() {
        return mainStage;
    }
}