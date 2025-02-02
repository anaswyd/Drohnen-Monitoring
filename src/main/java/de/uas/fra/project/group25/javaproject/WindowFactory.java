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
     * Loads the fxml file for the home screen and returns it
     * @return AnchorPane
     */
    public AnchorPane getHomeView() {
        try{
            homeView = new FXMLLoader(getClass().getResource("HomeScreen.fxml")).load();
        }catch (IOException e){
            logger.log(Level.SEVERE, "Could not load home screen fxml");
        }

        return homeView;
    }

    public AnchorPane getListView() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DroneList.fxml"));
            droneListView = loader.load();
            DroneListController droneListController = loader.getController();
            if(!(DroneStorage.getInstance().getDroneList() == null)) {
                for (Drone drone : DroneStorage.getInstance().getDroneList().values()) {
                    droneListController.createDroneElement(drone);
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Could not load DroneList.fxml");
        }
        return droneListView;
    }

    public AnchorPane getCatalogueView() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DroneCatalogue.fxml"));
            catalogueView = loader.load();
            DroneCatalogueController droneCatalogueController = loader.getController();
            if(!(DroneStorage.getInstance().getDroneList() == null)) {
                for(DroneType dt : DroneStorage.getInstance().getDroneCatalogue().values()){
                    droneCatalogueController.createDroneElement(dt);
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not load DroneCatalogue.fxml");
        }
        return catalogueView;
    }

    public AnchorPane getDetailsView() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DroneDetails.fxml"));
            detailsView = loader.load();
            DroneDetailsController droneDetailsController = loader.getController();
            if(!(DroneStorage.getInstance().getDroneList() == null)) {
                for (Drone drone : DroneStorage.getInstance().getDroneList().values()) {
                    droneDetailsController.createDroneElement(drone.getId());
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Could not load DroneDetails.fxml");
        }
        return detailsView;
    }
}
