package de.uas.fra.project.group25.javaproject;

import de.uas.fra.project.group25.javaproject.Controllers.DroneCatalogueController;
import de.uas.fra.project.group25.javaproject.Controllers.DroneListController;
import de.uas.fra.project.group25.javaproject.Drone.*;
import de.uas.fra.project.group25.javaproject.Drone.DroneType;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class WindowFactory {
    private WindowAppearance windowAppearance;
    private AnchorPane homeView;
    private AnchorPane mapView;
    private AnchorPane droneListView;
    private AnchorPane catalogueView;
    private AnchorPane detailsView;


    public WindowFactory() {}

    public AnchorPane getHomeView() {
        if (homeView == null) {
            try{
                homeView = new FXMLLoader(getClass().getResource("HomeScreen.fxml")).load();
            }catch (IOException e){
                e.printStackTrace(); //Maybe changed
            }
        }
        return homeView;
    }

    public AnchorPane getMapView() {
        if (mapView == null) {
            try{
                mapView = new FXMLLoader(getClass().getResource("DroneMap.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mapView;
    }

    public AnchorPane getListView() {

        if (droneListView == null) {
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("DroneList.fxml"));
                droneListView = loader.load();
                DroneListController droneListController = loader.getController();
                for(Drone drone : DroneStorage.getInstance().getDroneList().values()){
                    droneListController.createDroneElement(drone);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return droneListView;
    }

    public AnchorPane getCatalogueView() {
        if (catalogueView == null) {
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("DroneCatalogue.fxml"));
                catalogueView = loader.load();
                DroneCatalogueController droneCatalogueController = loader.getController();
                for(DroneType dt : DroneStorage.getInstance().getDroneCatalogue().values()){
                    droneCatalogueController.createDroneElement(dt);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return catalogueView;
    }

    public AnchorPane getDetailsView() {
        if (detailsView == null) {
            try{
                detailsView = new FXMLLoader(getClass().getResource("DroneDetails.fxml")).load();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return detailsView;
    }
}
