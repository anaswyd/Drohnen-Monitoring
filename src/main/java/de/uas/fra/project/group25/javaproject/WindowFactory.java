package de.uas.fra.project.group25.javaproject;

import de.uas.fra.project.group25.javaproject.Controllers.*;
import de.uas.fra.project.group25.javaproject.Drone.*;
import de.uas.fra.project.group25.javaproject.Drone.DroneType;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WindowFactory {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private AnchorPane homeView;
    private AnchorPane droneListView;
    private AnchorPane catalogueView;
    private AnchorPane detailsView;

    /**
     * Loads the fxml file for the home scene and returns it
     * @return javafx.scene.layout.AnchorPane
     */
    public AnchorPane getHomeView() {
        try{
            //Load fxml file
            homeView = new FXMLLoader(getClass().getResource("HomeScreen.fxml")).load();
        }catch (IOException e){
            logger.log(Level.SEVERE, "Failed to load HomeScreen.fxml. Program cannot continue.");
        }
        return homeView;
    }

    /**
     * Loads the fxml file for the drone list scene, fills the scene with information from Drone objects, and returns it.
     * @return javafx.scene.layout.AnchorPane
     */
    public AnchorPane getListView() {
        try{
            //Load fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DroneList.fxml"));
            droneListView = loader.load();
            //Create instance of droneListController to gain access to "createDroneElement()" method
            DroneListController droneListController = loader.getController();
            if(!(DroneStorage.getInstance().getDroneList() == null)) {
                for (Drone drone : DroneStorage.getInstance().getDroneList().values()) {
                    droneListController.createDroneElement(drone);
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to load DroneList.fxml. Program cannot continue.");
        }
        return droneListView;
    }

    /**
     * Loads the fxml file for the drone list scene, fills the scene with information from DroneType objects, and returns it.
     * @return javafx.scene.layout.AnchorPane
     */
    public AnchorPane getCatalogueView() {
        try{
            //Load fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DroneCatalogue.fxml"));
            catalogueView = loader.load();
            //Create instance of droneCatalogueController to gain access to "createDroneElement()" method
            DroneCatalogueController droneCatalogueController = loader.getController();
            if(!(DroneStorage.getInstance().getDroneList() == null)) {
                for(DroneType dt : DroneStorage.getInstance().getDroneCatalogue().values()){
                    droneCatalogueController.createDroneElement(dt);
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to load DroneCatalogue.fxml. Program cannot continue.");
        }
        return catalogueView;
    }

    /**
     * Loads the fxml file for the drone detail scene, fills the right side menu with the drone IDs from Drone objects, and returns it.
     * @return javafx.scene.layout.AnchorPane
     */
    public AnchorPane getDetailsView() {
        try{
            //Load fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DroneDetails.fxml"));
            detailsView = loader.load();
            //Create instance of droneDetailsController to gain access to "createDroneElement()" method
            DroneDetailsController droneDetailsController = loader.getController();
            if(!(DroneStorage.getInstance().getDroneList() == null)) {
                for (Drone drone : DroneStorage.getInstance().getDroneList().values()) {
                    droneDetailsController.createDroneElement(drone.getId());
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Could not load DroneDetails.fxml. Program cannot continue.");
        }
        return detailsView;
    }
}
