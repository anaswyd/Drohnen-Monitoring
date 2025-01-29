package de.uas.fra.project.group25.javaproject.Controllers;

import de.uas.fra.project.group25.javaproject.Drone.DroneStorage;
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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DroneDetailsController implements Initializable {

    @FXML
    private VBox parentV;
    @FXML
    private TableView<DroneDetailRow> parentTable;
    @FXML
    private AnchorPane detailsSceneAnchor;

    @FXML
    private TableColumn<DroneDetailRow, String> colTimestamp;
    @FXML
    private TableColumn<DroneDetailRow, Double> colLat;
    @FXML
    private TableColumn<DroneDetailRow, Double> colLong;
    @FXML
    private TableColumn<DroneDetailRow, Double> colTotalDistance;
    @FXML
    private TableColumn<DroneDetailRow, Double> colAvgSpeed;
    @FXML
    private TableColumn<DroneDetailRow, String> colStatus;
    @FXML
    private TableColumn<DroneDetailRow, Long> colTotalActiveTime;
    @FXML
    private TableColumn<DroneDetailRow, Integer> colBattery;
    @FXML
    private TableColumn<DroneDetailRow, Long> colTotalBattery;


    public final ToggleGroup detailSelector = new ToggleGroup();




    public void selectOnAction(int id){
        parentTable.getItems().clear();

        Thread thread = new Thread(() -> {
            List<DroneDetailRow> rows = DroneStorage.getInstance().getDetails(id);
            Platform.runLater(() -> {
                for (DroneDetailRow row : rows) {
                    parentTable.getItems().add(row);
                }
            });
            PrintWriter tablesave = null;
            try{
                tablesave = new PrintWriter(new FileWriter("./DroneDetailCSVs/drone_details" + id + ".csv"));
                for (DroneDetailRow row : rows) {
                    tablesave.println(row.toString());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            finally {
                tablesave.close();
            }
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
            }
        });
        newChild.setToggleGroup(detailSelector);
        parentV.getChildren().add(newChild);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colTimestamp.setCellValueFactory(new PropertyValueFactory<DroneDetailRow, String>("timestamp"));
        colLat.setCellValueFactory(new PropertyValueFactory<DroneDetailRow, Double>("latitude"));
        colLong.setCellValueFactory(new PropertyValueFactory<DroneDetailRow, Double>("longitude"));
        colTotalDistance.setCellValueFactory(new PropertyValueFactory<DroneDetailRow, Double>("totalDistance"));
        colAvgSpeed.setCellValueFactory(new PropertyValueFactory<DroneDetailRow, Double>("averageSpeed"));
        colStatus.setCellValueFactory(new PropertyValueFactory<DroneDetailRow, String>("status"));
        colTotalActiveTime.setCellValueFactory(new PropertyValueFactory<DroneDetailRow, Long>("totalActiveTime"));
        colBattery.setCellValueFactory(new PropertyValueFactory<DroneDetailRow, Integer>("batteryStatus"));
        colTotalBattery.setCellValueFactory(new PropertyValueFactory<DroneDetailRow, Long>("totalBatteryConsumption"));
    }
}
