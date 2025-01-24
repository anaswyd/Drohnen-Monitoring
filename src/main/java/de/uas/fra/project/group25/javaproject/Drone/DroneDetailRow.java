package de.uas.fra.project.group25.javaproject.Drone;

import org.json.JSONObject;

public class DroneDetailRow {
    private String latitude;
    private String longitude;
    private String totalDistance;
    private String averageSpeed;
    private String status;
    private String totalActiveTime;
    private String batteryStatus;
    private String totalBatteryConsumption;

    public DroneDetailRow(){
        this.latitude = "0";
        this.longitude = "0";
        this.totalDistance = "0";
        this.averageSpeed = "0";
        this.status = "0";
        this.totalActiveTime = "0";
        this.batteryStatus = "0";
        this.totalBatteryConsumption = "0";
    }

    public DroneDetailRow(JSONObject jsonObject, DroneDetailRow previousRow, long timeStampDifference) {
        this.latitude = jsonObject.getString("latitude");
        this.longitude = jsonObject.getString("longitude");
        this.totalDistance = jsonObject.getString("totalDistance");
        this.averageSpeed = jsonObject.getString("averageSpeed")
        ;
        this.status = jsonObject.getString("status");
        this.totalActiveTime = jsonObject.getString("totalActiveTime");
        this.batteryStatus = jsonObject.getString("batteryStatus");
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getTotalDistance() {
        return totalDistance;
    }

    public String getAverageSpeed() {
        return averageSpeed;
    }

    public String getStatus() {
        return status;
    }

    public String getTotalActiveTime() {
        return totalActiveTime;
    }

    public String getBatteryStatus() {
        return batteryStatus;
    }

    public String getTotalBatteryConsumption() {
        return totalBatteryConsumption;
    }
}
