package de.uas.fra.project.group25.javaproject.Controllers;

import de.uas.fra.project.group25.javaproject.Drone.Drone;
import de.uas.fra.project.group25.javaproject.Drone.DroneStorage;
import de.uas.fra.project.group25.javaproject.WindowAppearance;
import de.uas.fra.project.group25.javaproject.Drone.DroneDetailRow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

public class DroneDetailsController implements Initializable {

    @FXML
    private VBox parentV;
    @FXML
    private TableView<DroneDetailRow> parentTable;
    @FXML
    private AnchorPane detailsSceneAnchor;

    @FXML
    private TableColumn<DroneDetailRow, String> colLat;
    @FXML
    private TableColumn<DroneDetailRow, String> colLong;
    @FXML
    private TableColumn<DroneDetailRow, String> colTotalDistance;
    @FXML
    private TableColumn<DroneDetailRow, String> colAvgSpeed;
    @FXML
    private TableColumn<DroneDetailRow, String> colStatus;
    @FXML
    private TableColumn<DroneDetailRow, String> colTotalActiveTime;
    @FXML
    private TableColumn<DroneDetailRow, String> colBattery;
    @FXML
    private TableColumn<DroneDetailRow, String> colTotalBattery;


    public final ToggleGroup detailSelector = new ToggleGroup();




    public void selectOnAction(int id){
        parentTable.getItems().clear();

        Thread thread = new Thread(() -> {
            //DroneStorage.getInstance().getDetails(id);
            DroneDetailRow newRow = new DroneDetailRow();
            Platform.runLater(() -> {
                for (int i = 0; i < 10; i++) {
                    parentTable.getItems().add(newRow);
                }
            });
        });
        thread.start();
    }

    public void createDroneElement(int droneID){
        RadioButton newChild = new RadioButton("Drone " + droneID);
        newChild.setId(String.valueOf(droneID));
        newChild.setPrefSize(180, 20);
        newChild.setNodeOrientation(NodeOrientation.valueOf("RIGHT_TO_LEFT"));
        newChild.setAlignment(Pos.valueOf("CENTER_RIGHT"));

        newChild.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                selectOnAction(Integer.parseInt(newChild.getId()));
                //System.out.println(newChild.getId());
            }
        });

        newChild.setToggleGroup(detailSelector);

        parentV.getChildren().add(newChild);
    }


//
//    private void fitSize(){
//
//        /*
//        Explanation:    1. Get BorderPane "ApplicationContainer" with "WindowAppearance.getInstance().getStage().getScene().getRoot()"
//                        2. Cast to BorderPane to "getCenter()"
//                        3. Cast "center" to AnchorPane to get "heightProperty()" and "widthProperty()"
//                        4. Bind Size of ScrollPane to step 3
//                        5. Bind size of FlowPane to ScrollPane
//        */
//
//        parentScroll.prefHeightProperty().bind((WindowAppearance.getInstance().getStage()).heightProperty());
//        parentScroll.prefWidthProperty().bind((WindowAppearance.getInstance().getStage()).widthProperty());
//        parentTable.prefHeightProperty().bind(parentScroll.heightProperty());
//        parentTable.prefWidthProperty().bind(parentScroll.widthProperty());
//    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//
//        detailsSceneAnchor.heightProperty().addListener((observable, oldValue, newValue) -> {
//            //System.out.println("height changed");
//            fitSize();
//        });
//        detailsSceneAnchor.widthProperty().addListener((observable, oldValue, newValue) -> {
//            //System.out.println("width changed");
//            fitSize();
//        });
//        WindowAppearance.getInstance().getStage().heightProperty().addListener((observable, oldValue, newValue) -> {
//            //System.out.println("Window height changed");
//            fitSize();
//        });
//        WindowAppearance.getInstance().getStage().widthProperty().addListener((observable, oldValue, newValue) -> {
//            //System.out.println("Window width changed");
//            fitSize();
//        });
        colLat.setCellValueFactory(new PropertyValueFactory<DroneDetailRow, String>("latitude"));
        colLong.setCellValueFactory(new PropertyValueFactory<DroneDetailRow, String>("longitude"));
        colTotalDistance.setCellValueFactory(new PropertyValueFactory<DroneDetailRow, String>("totalDistance"));
        colAvgSpeed.setCellValueFactory(new PropertyValueFactory<DroneDetailRow, String>("averageSpeed"));
        colStatus.setCellValueFactory(new PropertyValueFactory<DroneDetailRow, String>("status"));
        colTotalActiveTime.setCellValueFactory(new PropertyValueFactory<DroneDetailRow, String>("totalActiveTime"));
        colBattery.setCellValueFactory(new PropertyValueFactory<DroneDetailRow, String>("batteryStatus"));
        colTotalBattery.setCellValueFactory(new PropertyValueFactory<DroneDetailRow, String>("totalBatteryConsumption"));
    }
}
