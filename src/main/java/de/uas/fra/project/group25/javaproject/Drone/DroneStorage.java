package de.uas.fra.project.group25.javaproject.Drone;

import de.uas.fra.project.group25.javaproject.ApiConnector.*;
import de.uas.fra.project.group25.javaproject.WindowAppearance;
import javafx.application.Platform;
import javafx.scene.layout.BorderPane;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DroneStorage {
    private final ApiAccess apiAccess;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private static DroneStorage droneStorage;
    private boolean updating = false;

    /**
     * Private constructor because class is singleton.
     */
    private DroneStorage(){
        this.apiAccess = new ApiAccess();
        updateData();
    }

    /**
     * Returns Singleton class with the access to all drone specific data
     *@return de.uas.fra.project.group25.javaproject.Drone.DroneStorage
     */
    public static DroneStorage getInstance(){
        if (droneStorage == null){
            droneStorage = new DroneStorage();
        }
        return droneStorage;
    }

    /**
     * Retrieves the latest data from dronesim.facets-labs.com
     */
    public void updateData() {
        if(!updating){
            //disable button so only one update at a time is possible
            this.updating = true;

            (WindowAppearance.getInstance().getStage().getScene().lookup("#refreshButton")).setDisable(true);

            //create a thread that updates the data
            Thread thread = new Thread(() -> {
                logger.log(Level.INFO, "DroneData update Thread has been started. Thread ID = " + Thread.currentThread().getId());

                apiAccess.update();
                //check what the current screen is

                BorderPane currentPane = ((BorderPane) WindowAppearance.getInstance().getStage().getScene().getRoot());
                switch (currentPane.getCenter().getId()){

                    case("homeSceneAnchor"):
                        Platform.runLater(()->currentPane.setCenter(WindowAppearance.getInstance().getWindowFactory().getHomeView()));
                        break;
                    case ("listSceneAnchor"):
                        Platform.runLater(()->currentPane.setCenter(WindowAppearance.getInstance().getWindowFactory().getListView()));
                        break;
                    case ("catalogueSceneAnchor"):
                        Platform.runLater(()->currentPane.setCenter(WindowAppearance.getInstance().getWindowFactory().getCatalogueView()));
                        break;
                    case ("detailsSceneAnchor"):
                        Platform.runLater(()->currentPane.setCenter(WindowAppearance.getInstance().getWindowFactory().getDetailsView()));
                        break;
                }
                this.updating = false;

                //reactivate button
                (WindowAppearance.getInstance().getStage().getScene().lookup("#refreshButton")).setDisable(false);
                logger.log(Level.INFO, "DroneData update Thread has ended. Thread ID = " + Thread.currentThread().getId());
            });
            thread.start();
        }
        else {
            logger.log(Level.INFO, "Couldn't update data. Currently retrieving details for a specific drone.");
        }
    }

    /**
     * Hashmap with all the  different drone types (key value is the drone type id from dronesim.facets-labs.com)
     * @return droneCatalogue as Hashmap<Integer, DroneType>
     */
    public HashMap<Integer, DroneType> getDroneCatalogue(){
        HashMap<Integer, DroneType> droneCatalogue;
        //do nothing if updating
        if (this.updating){
            return null;
        }
        droneCatalogue = this.apiAccess.getOutputCatalogue();
        return droneCatalogue;
    }

    /**
     * Hashmap with all drones (key value is the drone id from dronesim.facets-labs.com)
     * @return droneList as HashMap<Integer, Drone>
     */
    public HashMap<Integer, Drone> getDroneList() {
        HashMap<Integer, Drone> outputDrone;
        //do nothing if updating
        if (this.updating){
            return null;
        }

        outputDrone = this.apiAccess.getOutputDrone();


        return outputDrone;
    }

    public List<DroneDetailRow> getDetails(int id){
        List<DroneDetailRow> details = null;
        if(!this.updating) {
            updating = true;
            try {
                this.apiAccess.fetchDynamics(id);
            }catch (Exception e){
                return null;
            }
            details = this.apiAccess.getSpecificDynamic();
            updating = false;
        }
        else {
            logger.log(Level.INFO, "Couldn't retrieve details. DroneData is currently updating.");
        }

        return details;
    }
}
