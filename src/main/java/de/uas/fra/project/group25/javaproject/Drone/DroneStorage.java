package de.uas.fra.project.group25.javaproject.Drone;

import de.uas.fra.project.group25.javaproject.ApiConnector.*;
import de.uas.fra.project.group25.javaproject.Controllers.DroneDetailsController;
import de.uas.fra.project.group25.javaproject.WindowAppearance;
import de.uas.fra.project.group25.javaproject.WindowFactory;
import javafx.fxml.FXMLLoader;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class DroneStorage {

    private static DroneStorage droneStorage;
    private final ApiAccess apiAccess;
    private boolean updating = false;


    private DroneStorage(){
        this.apiAccess = new ApiAccess();
        updateData();
    }

    /**
     * returns Singleton class with the access to all drone specific data
     *@return DroneStorage
     */
    public static DroneStorage getInstance(){
        if (droneStorage == null){
            droneStorage = new DroneStorage();
        }
        return droneStorage;
    }

    /**
     * retrieves the latest data from dronesim.facets-labs.com
     */
    public void updateData() {
        if(!updating){
            //disable button so only one update at a time is possible
            this.updating = true;

            (WindowAppearance.getInstance().getStage().getScene().lookup("#refreshButton")).setDisable(true);

            //create a thread that updates the data
            Thread thread = new Thread(() -> {
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
            });

            thread.start();
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
        if(!this.updating){
            updating = true;
            this.apiAccess.fetchDynamics(id);
            details = this.apiAccess.getSpecificDynamic();
            updating = false;
        }

        return details;
    }


}
