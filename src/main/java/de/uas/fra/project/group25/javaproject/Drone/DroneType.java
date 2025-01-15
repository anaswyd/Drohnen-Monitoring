package de.uas.fra.project.group25.javaproject.Drone;

public class DroneType {

    private final int id;
    private final String manufacturer;
    private final String typename;
    private final int weight;
    private final int max_speed;
    private final int battery_capacity;
    private final int control_range;
    private final int max_carriage;

    public DroneType(int id, String typename, String manufacturer, int weight, int max_speed, int battery_capacity, int control_range, int max_carriage) {
        this.id = id;
        this.typename = typename;
        this.manufacturer = manufacturer;
        this.weight = weight;
        this.max_speed = max_speed;
        this.battery_capacity = battery_capacity;
        this.control_range = control_range;
        this.max_carriage = max_carriage;
    }

    public int getId() {
        return id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getTypename() {
        return typename;
    }

    public int getWeight() {
        return weight;
    }

    public int getMaxSpeed() {
        return max_speed;
    }

    public int getBatteryCapacity() {
        return battery_capacity;
    }

    public int getControlRange() {
        return control_range;
    }

    public int getMaxCarriage() {
        return max_carriage;
    }





    @Override
    public String toString() {
        return "DroneType{" +
                "id=" + id +
                ", manufacturer='" + manufacturer + '\'' +
                ", typename='" + typename + '\'' +
                ", weight=" + weight +
                ", max_speed=" + max_speed +
                ", battery_capacity=" + battery_capacity +
                ", control_range=" + control_range +
                ", max_carriage=" + max_carriage +
                "}\n";
    }


    @Override
    public int hashCode() {
        return this.id;
    }



}
