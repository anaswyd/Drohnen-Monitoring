package de.uas.fra.project.group25.javaproject.Drone;

import de.uas.fra.project.group25.javaproject.ApiConnector.*;
import java.util.HashMap;

public class DroneStorage {

    private static DroneStorage droneStorage;
    private final ApiAccess apiAccess;

    private DroneStorage(){
        this.apiAccess = new ApiAccess();
    }

    public static DroneStorage getInstance(){
        if (droneStorage == null){
            droneStorage = new DroneStorage();
        }
        return droneStorage;
    }

    public void updateData() {
        this.apiAccess.update();
    }

    public HashMap<Integer, DroneType> getDroneCatalogue(){
        return this.apiAccess.getOutputCatalogue();
    }

    public HashMap<Integer, Drone> getDroneList(){
        return this.apiAccess.getOutputDrone();
    }

}
