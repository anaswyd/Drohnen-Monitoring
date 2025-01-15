package de.uas.fra.project.group25.javaproject.Drone;

import java.util.ArrayList;
import java.util.List;

public class Drone {
    private final int id;
    private final DroneType dronetype;
    private final String created;
    private final String serialnumber;
    private final int carriage_weight;
    private final String carriage_type;
    private  List<DroneDynamic> droneDynamic ;

    public Drone(int id, DroneType dronetype, String created, String serialnumber, int carriage_weight, String carriage_type, List<DroneDynamic> droneDynamic) {
        this.id = id;
        this.droneDynamic = droneDynamic;
        this.dronetype = dronetype;
        this.created = created;
        this.serialnumber = serialnumber;
        this.carriage_weight = carriage_weight;
        this.carriage_type = carriage_type;
        this.droneDynamic = new ArrayList<DroneDynamic>() ; //!!!!
    }

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

    public String getCreated() {
        return created;
    }

    public String getSerialnumber() {
        return serialnumber;
    }

    public int getCarriage_weight() {
        return carriage_weight;
    }

    public String getCarriage_type() {
        return carriage_type;
    }

    @Override
    public String toString() {
        return "Drone{" +
                "id=" + id +
                ", dronetype=" + dronetype +
                ", created='" + created + '\'' +
                ", serialnumber='" + serialnumber + '\'' +
                ", carriage_weight=" + carriage_weight +
                ", carriage_type='" + carriage_type + '\'' +
                ", droneDynamic='" + droneDynamic + '\'' +
                //DYNAMICS
                "}\n";
    }


    @Override
    public int hashCode() {
        return this.id;
    }

    public void clearDynamics() {
    }
    
    
}
