package de.uas.fra.project.group25.javaproject.Controllers;

import de.uas.fra.project.group25.javaproject.Drone.DroneType;
import de.uas.fra.project.group25.javaproject.WindowAppearance;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class DroneCatalogueController implements Initializable, Refittable {
    @FXML
    private AnchorPane catalogueSceneAnchor;
    @FXML
    private ScrollPane parentScroll;
    @FXML
    private FlowPane parentFlow;

    /**
     * Creates new visual drone catalogue element from passed DroneType
     * @param dt DroneType object for which a visual element is supposed to be created
     */
    public void createDroneElement(DroneType dt){

        //Base of new element
        AnchorPane newChild = new AnchorPane();
        newChild.setMaxSize(385, 182);
        newChild.setPrefSize(385, 182);

        //Adds the Drone Icon
        ImageView icon = new ImageView(String.valueOf(getClass().getResource("/Icons/1177291-200.png")));
        icon.setFitHeight(42);
        icon.setFitWidth(42);
        AnchorPane.setTopAnchor(icon, 70.0);
        AnchorPane.setLeftAnchor(icon, 14.0);
        AnchorPane.setBottomAnchor(icon, 70.0);
        newChild.getChildren().add(icon);

        //Labels that don't change
        VBox vBoxL = new VBox(5);
        vBoxL.getChildren().add(new Label("ID :"));
        vBoxL.getChildren().add(new Label("Manufacturer :"));
        vBoxL.getChildren().add(new Label("Type :"));
        vBoxL.getChildren().add(new Label("Weight :"));
        vBoxL.getChildren().add(new Label("Max Speed :"));
        vBoxL.getChildren().add(new Label("Battery Capacity :"));
        vBoxL.getChildren().add(new Label("Range :"));
        vBoxL.getChildren().add(new Label("Max Carriage :"));
        vBoxL.setAlignment(Pos.valueOf("CENTER_LEFT"));
        AnchorPane.setTopAnchor(vBoxL, 0.0);
        AnchorPane.setLeftAnchor(vBoxL, 70.0);
        AnchorPane.setBottomAnchor(vBoxL, 0.0);
        newChild.getChildren().add(vBoxL);

        //Labels that are gotten from passed Argument
        VBox vBoxR = new VBox(5);
        vBoxR.getChildren().add(new Label(String.valueOf(dt.getId())));
        vBoxR.getChildren().add(new Label(dt.getManufacturer()));
        vBoxR.getChildren().add(new Label(dt.getTypename()));
        vBoxR.getChildren().add(new Label(String.valueOf(dt.getWeight())));
        vBoxR.getChildren().add(new Label(String.valueOf(dt.getMaxSpeed())));
        vBoxR.getChildren().add(new Label(String.valueOf(dt.getBatteryCapacity())));
        vBoxR.getChildren().add(new Label(String.valueOf(dt.getControlRange())));
        vBoxR.getChildren().add(new Label(String.valueOf(dt.getMaxCarriage())));
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
        catalogueSceneAnchor.heightProperty().addListener((observable, oldValue, newValue) -> {
            fitCenter();
        });
        //Changes the size of scroll and flow pane when scene changes width (e.g. when the side menu is collapsed)
        catalogueSceneAnchor.widthProperty().addListener((observable, oldValue, newValue) -> {
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