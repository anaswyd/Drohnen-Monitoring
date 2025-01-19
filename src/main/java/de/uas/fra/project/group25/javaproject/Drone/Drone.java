package de.uas.fra.project.group25.javaproject.Drone;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class Drone {
    private final int id;
    private final DroneType dronetype;
    private final OffsetDateTime created;
    private final String serialnumber;
    private final int carriageWeight;
    private final String carriageType;
    private  List<DroneDynamic> droneDynamic ;


/**
 * Constructor of a drone with parameters:
 * @param id The drone id
 * @param dronetype The drone type
 * @param created Date drone was created
 * @param serialnumber The unique serialnumber of the drone
 * @param carriage_weight Current weight of the drone
 * @param carriage_type Type of carriage: SEN for sensor, ACT for actuator, and NOT for nothing
 * @param droneDynamic List of the drone's dynamics
 */
    public Drone(int id, DroneType dronetype, String created, String serialnumber, int carriage_weight, String carriage_type, List<DroneDynamic> droneDynamic) {
        this.id = id;
        this.dronetype = dronetype;
        this.created = OffsetDateTime.parse(created);
        this.serialnumber = serialnumber;
        this.carriageWeight = carriage_weight;
        this.carriageType = carriage_type;
        this.droneDynamic = new ArrayList<DroneDynamic>();
    }
/**
 * Method to add new dronedynamic to the droneDynamic list:
 * @param dynamic the new to add dynamic
 */
    public void addDroneDynamic(DroneDynamic dynamic){
        droneDynamic.add(dynamic) ;
    }

    public void setDroneDynamic(List<DroneDynamic> droneDynamic) {
        this.droneDynamic = droneDynamic;
    }

    public List<DroneDynamic> getDynamics(){
        return droneDynamic;
    }

    public int getId() {
        return id;
    }

    public DroneType getDronetype() {
        return dronetype;
    }

    public OffsetDateTime getCreated() {
        return created;
    }

    public String getSerialnumber() {
        return serialnumber;
    }

    public int getCarriage_weight() {
        return carriageWeight;
    }

    public String getCarriage_type() {
        return carriageType;
    }
/**
 * Debug method to test result in console
 * @return String with all information
 */
    @Override
    public String toString() {
        return "Drone{" +
                "id=" + id +
                ", dronetype=" + dronetype +
                ", created='" + created + '\'' +
                ", serialnumber='" + serialnumber + '\'' +
                ", carriage_weight=" + carriageWeight +
                ", carriage_type='" + carriageType + '\'' +
                ", droneDynamic='" + droneDynamic + '\'' +
                //DYNAMICS
                "}\n";
    }

/**
 * Override hashCode() method to use id of the drone for hashing
 */
    @Override
    public int hashCode() {
        return this.id;
    }


    
    
}
