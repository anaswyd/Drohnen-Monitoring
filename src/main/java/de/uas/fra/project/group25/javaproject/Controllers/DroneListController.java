package de.uas.fra.project.group25.javaproject.Controllers;

import de.uas.fra.project.group25.javaproject.Drone.Drone;
import de.uas.fra.project.group25.javaproject.Drone.DroneStorage;
import de.uas.fra.project.group25.javaproject.Drone.DroneType;
import de.uas.fra.project.group25.javaproject.WindowAppearance;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DroneListController implements Initializable {
    @FXML
    private AnchorPane listSceneAnchor;
    @FXML
    private ScrollPane parentScroll;
    @FXML
    private FlowPane parentFlow;


    public void createDroneElement(Drone drone){
        /*
            Create new drone element
        */

        AnchorPane newChild = new AnchorPane();
        newChild.setMaxSize(385, 182);
        newChild.setPrefSize(385, 182);

        ImageView icon = new ImageView(String.valueOf(getClass().getResource("/Icons/1177291-200.png")));
        icon.setFitHeight(42);
        icon.setFitWidth(42);
        AnchorPane.setTopAnchor(icon, 50.0);
        AnchorPane.setLeftAnchor(icon, 14.0);
        AnchorPane.setBottomAnchor(icon, 50.0);
        newChild.getChildren().add(icon);

        VBox vBoxL = new VBox(5);
        vBoxL.getChildren().add(new Label("ID :"));
        vBoxL.getChildren().add(new Label("Serial number :"));
        vBoxL.getChildren().add(new Label("Carriage Type :"));
        vBoxL.getChildren().add(new Label("Carriage Weight :"));
        vBoxL.getChildren().add(new Label("Created :"));
        vBoxL.getChildren().add(new Label("Longitude :"));
        vBoxL.getChildren().add(new Label("Latitude :"));

        vBoxL.setAlignment(Pos.valueOf("CENTER_LEFT"));
        AnchorPane.setTopAnchor(vBoxL, 0.0);
        AnchorPane.setLeftAnchor(vBoxL, 70.0);
        AnchorPane.setBottomAnchor(vBoxL, 0.0);
        newChild.getChildren().add(vBoxL);

        VBox vBoxR = new VBox(5);
        vBoxR.getChildren().add(new Label(String.valueOf(drone.getId())));
        vBoxR.getChildren().add(new Label(drone.getSerialnumber()));
        vBoxR.getChildren().add(new Label(drone.getCarriage_type()));
        vBoxR.getChildren().add(new Label(String.valueOf(drone.getCarriage_weight())));
        vBoxR.getChildren().add(new Label(drone.getCreated().toString()));
        vBoxR.getChildren().add(new Label(String.valueOf(drone.getDynamics().get(1).getLongitude())));
        vBoxR.getChildren().add(new Label(String.valueOf(drone.getDynamics().get(1).getLatitude())));

        vBoxR.setAlignment(Pos.valueOf("CENTER_RIGHT"));
        AnchorPane.setTopAnchor(vBoxR, 0.0);
        AnchorPane.setRightAnchor(vBoxR, 14.0);
        AnchorPane.setBottomAnchor(vBoxR, 0.0);
        newChild.getChildren().add(vBoxR);

        parentFlow.getChildren().add(newChild);
    }


    private void fitSize(){

        /*
        Explanation:    1. Get BorderPane "ApplicationContainer" with "WindowAppearance.getInstance().getStage().getScene().getRoot()"
                        2. Cast to BorderPane to "getCenter()"
                        3. Cast "center" to AnchorPane to get "heightProperty()" and "widthProperty()"
                        4. Bind Size of ScrollPane to step 3
                        5. Bind size of FlowPane to ScrollPane
        */

        parentScroll.prefHeightProperty().bind((WindowAppearance.getInstance().getStage()).heightProperty());
        parentScroll.prefWidthProperty().bind((WindowAppearance.getInstance().getStage()).widthProperty());
        parentFlow.prefHeightProperty().bind(parentScroll.heightProperty());
        parentFlow.prefWidthProperty().bind(parentScroll.widthProperty());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        listSceneAnchor.heightProperty().addListener((observable, oldValue, newValue) -> {
            //System.out.println("height changed");
            fitSize();
        });
        listSceneAnchor.widthProperty().addListener((observable, oldValue, newValue) -> {
            //System.out.println("width changed");
            fitSize();
        });
        WindowAppearance.getInstance().getStage().heightProperty().addListener((observable, oldValue, newValue) -> {
            //System.out.println("Window height changed");
            fitSize();
        });

        WindowAppearance.getInstance().getStage().widthProperty().addListener((observable, oldValue, newValue) -> {
            //System.out.println("Window width changed");
            fitSize();
        });
    }
}