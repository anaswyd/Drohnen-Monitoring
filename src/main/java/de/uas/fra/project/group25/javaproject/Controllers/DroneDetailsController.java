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
import javafx.scene.layout.VBox;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DroneDetailsController implements Initializable {

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    @FXML
    private VBox parentV;
    @FXML
    private TableView<DroneDetailRow> parentTable;

    @FXML
    private TableColumn<DroneDetailRow, String> colTimestamp;
    @FXML
    private TableColumn<DroneDetailRow, String> colLastSeen;
    @FXML
    private TableColumn<DroneDetailRow, Integer> colSpeed;
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
    @FXML
    private TableColumn<DroneDetailRow, String> colARoll;
    @FXML
    private TableColumn<DroneDetailRow, String> colAPitch;
    @FXML
    private TableColumn<DroneDetailRow, String> colAYaw;

    public final ToggleGroup detailSelector = new ToggleGroup();


    /**
     * Method that is called when a Drone is selected in the "Drone Details" Scene
     * Fetches and Displays every Drone Dynamic of the selected Drone
     * @param id ID of the selected drone
     */
    public void selectOnAction(int id){
        //Begin on empty table
        parentTable.getItems().clear();

        //fetching and displaying details can be done without freezing the whole program (might take a while)
        Thread thread = new Thread(() -> {
            logger.log(Level.INFO, "Starting thread to retrieve and display drone details. Thread ID = " + Thread.currentThread().getId());
            //Fetch relevant information
            List<DroneDetailRow> rows = DroneStorage.getInstance().getDetails(id);
            Platform.runLater(() -> {
                for (DroneDetailRow row : rows) {
                    //Create row for each drone dynamic that is fetched
                    parentTable.getItems().add(row);
                }
            });

            try {
                Files.createDirectories(Paths.get("./DroneDetailCSVs"));
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Failed to create directory \"DroneDetailCSVs\"", e);
            }
            logger.log(Level.INFO, "start saving drone details");
            PrintWriter tablesave = null;
            try{
                //Saves the drone details as a csv file
                tablesave = new PrintWriter(new FileWriter("./DroneDetailCSVs/drone_details" + id + ".csv"));
                for (DroneDetailRow row : rows) {
                    tablesave.println(row.toString());
                }
                logger.log(Level.INFO, "finish saving drone details");
            }catch (Exception e){
                logger.log(Level.WARNING, "Failed to create CSV File of Drone Detail with ID " + id);
            }
            finally {
                if (tablesave != null) {
                    tablesave.close();
                }
                logger.log(Level.INFO, "Thread to retrieve and display drones has ended. Thread ID = " + Thread.currentThread().getId());
            }
        });

        thread.start();
    }

    /**
     * Create RadioButtons from passed ID for the right side menu
     * @param droneID ID of Drone
     */
    public void createDroneElement(int droneID){
        //Base of new element
        RadioButton newChild = new RadioButton("Drone " + droneID);
        newChild.setId(String.valueOf(droneID));
        newChild.setPrefSize(180, 20);
        newChild.setNodeOrientation(NodeOrientation.valueOf("RIGHT_TO_LEFT"));
        newChild.setAlignment(Pos.valueOf("CENTER_RIGHT"));

        //Define what happens when element is selected
        newChild.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                selectOnAction(Integer.parseInt(newChild.getId()));
            }
        });

        //Set ToggleGroup of child so that only one of the element can be selected
        newChild.setToggleGroup(detailSelector);

        //Add new child to list
        parentV.getChildren().add(newChild);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Relate attributes to columns
        colTimestamp.setCellValueFactory(new PropertyValueFactory<DroneDetailRow, String>("timestamp"));
        colLastSeen.setCellValueFactory(new PropertyValueFactory<DroneDetailRow, String>("lastSeen"));
        colSpeed.setCellValueFactory(new PropertyValueFactory<DroneDetailRow, Integer>("speed"));
        colLat.setCellValueFactory(new PropertyValueFactory<DroneDetailRow, Double>("latitude"));
        colLong.setCellValueFactory(new PropertyValueFactory<DroneDetailRow, Double>("longitude"));
        colTotalDistance.setCellValueFactory(new PropertyValueFactory<DroneDetailRow, Double>("totalDistance"));
        colAvgSpeed.setCellValueFactory(new PropertyValueFactory<DroneDetailRow, Double>("averageSpeed"));
        colStatus.setCellValueFactory(new PropertyValueFactory<DroneDetailRow, String>("status"));
        colTotalActiveTime.setCellValueFactory(new PropertyValueFactory<DroneDetailRow, Long>("totalActiveTime"));
        colBattery.setCellValueFactory(new PropertyValueFactory<DroneDetailRow, Integer>("batteryStatus"));
        colTotalBattery.setCellValueFactory(new PropertyValueFactory<DroneDetailRow, Long>("totalBatteryConsumption"));
        colARoll.setCellValueFactory(new PropertyValueFactory<DroneDetailRow, String>("alignRoll"));
        colAPitch.setCellValueFactory(new PropertyValueFactory<DroneDetailRow, String>("alignPitch"));
        colAYaw.setCellValueFactory(new PropertyValueFactory<DroneDetailRow, String>("alignYaw"));
    }
}
