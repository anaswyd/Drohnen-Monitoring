package de.uas.fra.project.group25.javaproject.Drone;

import de.uas.fra.project.group25.javaproject.ApiConnector.*;
import de.uas.fra.project.group25.javaproject.WindowAppearance;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.HashMap;

public class DroneStorage {

    private static DroneStorage droneStorage;
    private final ApiAccess apiAccess;

    private DroneStorage(){
        this.apiAccess = new ApiAccess();
        updateData();
    }

    /**
     *@return Singleton class with the access to all drone specific data
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
        try {
            (WindowAppearance.getInstance().getStage().getScene().lookup("#refreshButton")).setDisable(true);
        }catch (Exception e){
            e.printStackTrace();
        }

        Thread thread = new Thread(()->{
            apiAccess.update();
            try {
                (WindowAppearance.getInstance().getStage().getScene().lookup("#refreshButton")).setDisable(false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
    }

    /**
     * @return Hashmap with all the  different drone types (key value is the drone type id from dronesim.facets-labs.com)
     */
    public HashMap<Integer, DroneType> getDroneCatalogue(){
        return this.apiAccess.getOutputCatalogue();
    }

    /**
     * @return Hashmap with all drones (key value is the drone id from dronesim.facets-labs.com)
     */
    public HashMap<Integer, Drone> getDroneList() {
        HashMap<Integer, Drone> outputDrone;

        do {
            outputDrone = this.apiAccess.getOutputDrone();
            try {
                Thread.sleep(500);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        } while (outputDrone.isEmpty());

        return outputDrone;
    }

}
