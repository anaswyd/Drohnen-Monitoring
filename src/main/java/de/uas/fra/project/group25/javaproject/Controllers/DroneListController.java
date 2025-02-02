package de.uas.fra.project.group25.javaproject.Controllers;

import de.uas.fra.project.group25.javaproject.Drone.Drone;
import de.uas.fra.project.group25.javaproject.WindowAppearance;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

public class DroneListController implements Initializable,Refittable {
    @FXML
    private AnchorPane listSceneAnchor;
    @FXML
    private ScrollPane parentScroll;
    @FXML
    private FlowPane parentFlow;

    /**
     * Creates new visual drone catalogue element from passed DroneType
     * @param drone Drone object for which a visual element is supposed to be created
     */
    public void createDroneElement(Drone drone){

        //Base of new element
        AnchorPane newChild = new AnchorPane();
        newChild.setMaxSize(385, 182);
        newChild.setPrefSize(385, 182);

        //Adds the Drone Icon
        ImageView icon = new ImageView(String.valueOf(getClass().getResource("/Icons/1177291-200.png")));
        icon.setFitHeight(42);
        icon.setFitWidth(42);
        AnchorPane.setTopAnchor(icon, 50.0);
        AnchorPane.setLeftAnchor(icon, 14.0);
        AnchorPane.setBottomAnchor(icon, 50.0);
        newChild.getChildren().add(icon);

        //Labels that don't change
        VBox vBoxL = new VBox(5);
        vBoxL.getChildren().add(new Label("ID :"));
        vBoxL.getChildren().add(new Label("Drone Type :"));
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

        //Labels that are gotten from passed Argument
        VBox vBoxR = new VBox(5);
        vBoxR.getChildren().add(new Label(String.valueOf(drone.getId())));
        vBoxR.getChildren().add(new Label(String.valueOf(drone.getDronetype().getTypename())));
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

        //Add newly created element to flow pane
        parentFlow.getChildren().add(newChild);
    }

    /**
     * Implementation of fitCenter() from Refittable Interface.
     * Binds size of scroll pane and flow pane to the window size
     */
    @Override
    public void fitCenter(){

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

        //Changes the size of scroll and flow pane when scene changes height (e.g. when the side menu is collapsed)
        listSceneAnchor.heightProperty().addListener((observable, oldValue, newValue) -> {
            fitCenter();
        });
        //Changes the size of scroll and flow pane when scene changes width (e.g. when the side menu is collapsed)
        listSceneAnchor.widthProperty().addListener((observable, oldValue, newValue) -> {
            fitCenter();
        });
        //Changes the size of scroll and flow pane when window changes height
        WindowAppearance.getInstance().getStage().heightProperty().addListener((observable, oldValue, newValue) -> {
            fitCenter();
        });
        //Changes the size of scroll and flow pane when window changes width
        WindowAppearance.getInstance().getStage().widthProperty().addListener((observable, oldValue, newValue) -> {
            fitCenter();
        });
    }
}