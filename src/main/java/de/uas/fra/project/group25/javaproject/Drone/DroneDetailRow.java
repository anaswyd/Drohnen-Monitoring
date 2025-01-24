package de.uas.fra.project.group25.javaproject.Drone;

import org.json.JSONObject;


public class DroneDetailRow {
    private final Double latitude;
    private final Double longitude;
    private final Double totalDistance;
    private final Double averageSpeed;
    private final String status;
    private final long totalActiveTime;
    private final int batteryStatus;
    private final long totalBatteryConsumption;
    private final String timestamp;

    public DroneDetailRow(){
        this.latitude = 0.0;
        this.longitude = 0.0;
        this.totalDistance = 0.0;
        this.status = "0";
        this.totalActiveTime = 0;
        this.averageSpeed = 0.0;
        this.batteryStatus = 0;
        this.totalBatteryConsumption = 0;
        this.timestamp = "";
    }

    public DroneDetailRow(JSONObject jsonObject, DroneDetailRow previousRow, long timeStampDifference) {
        this.latitude = Double.parseDouble((jsonObject.getString("latitude")));
        this.longitude = Double.parseDouble(jsonObject.getString("longitude"));
        this.totalDistance = haversineEquation(this.longitude, this.latitude, previousRow.getLongitude(), previousRow.getLongitude());
        this.status = jsonObject.getString("status");
        this.totalActiveTime = calculateActiveTime(previousRow.getStatus(), previousRow.getTotalActiveTime(), timeStampDifference);
        this.averageSpeed = calculateAverageSpeed(this.totalDistance, this.totalActiveTime);
        this.batteryStatus = jsonObject.getInt("battery_status");
        this.totalBatteryConsumption = calculateBatteryUsage(this.batteryStatus, previousRow.getBatteryStatus(),previousRow.getTotalBatteryConsumption());
        this.timestamp = jsonObject.getString("timestamp");
    }

    /**
     * calculate the distance between 2 Coordinates
     * @param lon1 longitude from the first Coordinate
     * @param lat1 latitude from the first Coordinate
     * @param lon2 longitude from the second Coordinate
     * @param lat2 latitude from the second Coordinate
     * @return kilometer from point one to point two
     */
    private double haversineEquation(double lon1 ,double lat1 ,double lon2 ,double lat2){

        //transform to radians
        double lon1Rad = Math.toRadians(lon1);
        double lat1Rad = Math.toRadians(lat1);
        double lon2Rad = Math.toRadians(lon2);
        double lat2Rad = Math.toRadians(lat2);

        //calculate delta radians
        double deltaLon = lon2Rad - lon1Rad;
        double deltaLat = lat2Rad - lat1Rad;

        //Haversine-equation
        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad)
                * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        //distance in km
        //since there are 3 different radii we settled on 6378.137 because it's the first google result
        double EARTH_RADIUS = 6378.137;
        return EARTH_RADIUS * c;

    }

    /**
     *
     * @param status List of Status for each timestamp
     * @param time_between_intervals
     * @return
     */
    private long calculateActiveTime(String status, long previousActiveTime, long time_between_intervals){
        long activeTime = 0;
        switch(status) {
            case "ON":
                activeTime = previousActiveTime + time_between_intervals;
                break;
            case "OF":
                activeTime = previousActiveTime;
                break;
            case "IS":
                //we decided that issues count as inactive time for now
                activeTime = previousActiveTime;
                break;
        }

        return activeTime;
    }


    /**
     * calculates average speed over time as m/s for each time stamp
     * @param distances List of all distances
     * @param time_between_intervals time in seconds between interval
     * @return List of average speed for each timestamp
     */
    private Double calculateAverageSpeed(double totalDistance, long totalActiveTime){
        double averageSpeed = 0.0;
        if(totalActiveTime != 0){
            averageSpeed = (totalDistance/1000)/ totalActiveTime;
        }
        return averageSpeed;
    }


    /**
     * Calculates for each drone the batteryusage
     * @param battery_status
     * @return
     */
    private long calculateBatteryUsage(int batteryStatus, int previousBatteryStatus, long totalBatteryConsumption) {
        //calculate batteryusage
        if(batteryStatus < previousBatteryStatus){
            totalBatteryConsumption = totalBatteryConsumption + ( previousBatteryStatus - batteryStatus );
        }
        return totalBatteryConsumption;
    }



    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getTotalDistance() {
        return totalDistance;
    }

    public Double getAverageSpeed() {
        return averageSpeed;
    }

    public String getStatus() {
        return status;
    }

    public Long getTotalActiveTime() {
        return totalActiveTime;
    }

    public Integer getBatteryStatus() {
        return batteryStatus;
    }

    public Long getTotalBatteryConsumption() {
        return totalBatteryConsumption;
    }

    public String getTimestamp() {return timestamp;}
}
