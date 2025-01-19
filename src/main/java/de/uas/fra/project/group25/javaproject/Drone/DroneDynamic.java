package de.uas.fra.project.group25.javaproject.Drone;


import java.time.OffsetDateTime;

public class DroneDynamic {
    private OffsetDateTime timestamp;
    private int speed ;
    private String alignRoll;
    private String alignPitch;
    private String alignYaw;
    private String longitude;
    private String latitude;
    private int batteryStatus;
    private OffsetDateTime lastSeen;
    private String status;

    /**
     * Method to update individual attributes of dynamics
     * @param timestamp current timestamp of located drone
     * @param speed current speed of located drone
     * @param alignRoll current align roll of located drone
     * @param alignPitch current align pitch of located drone
     * @param alignYaw current align yaw of located drone
     * @param longitude current longitude of located drone
     * @param latitude current latitude of located drone
     * @param batteryStatus current batterystatus of located drone
     * @param lastSeen Last seen timestamp of located drone
     * @param status Status of the drone. ON for online, OF for offline, IS for issues
     */
    public void updateDynamic(String timestamp,int speed, String alignRoll, String alignPitch, String alignYaw, String longitude, String latitude, int batteryStatus, String lastSeen, String status){
        this.timestamp = OffsetDateTime.parse(timestamp);
        this.speed = speed;
        this.alignRoll = alignRoll;
        this.alignPitch = alignPitch;
        this.alignYaw = alignYaw;
        this.longitude = longitude;
        this.latitude = latitude;
        this.batteryStatus = batteryStatus;
        this.lastSeen = OffsetDateTime.parse(lastSeen);

        this.status = status;
    }

    /**
     * Constructor of DroneDynamic setting attributes with the updateDynamic() method
     * @param timestamp current timestamp of located drone
     * @param speed current speed of located drone
     * @param alignRoll current align roll of located drone
     * @param alignPitch current align pitch of located drone
     * @param alignYaw current align yaw of located drone
     * @param longitude current longitude of located drone
     * @param latitude current latitude of located drone
     * @param batteryStatus current batterystatus of located drone
     * @param lastSeen Last seen timestamp of located drone
     * @param status Status of the drone. ON for online, OF for offline, IS for issues
     */
    public DroneDynamic(String timestamp,int speed, String align_roll, String align_pitch, String align_yaw, String longitude, String latitude, int battery_status, String last_seen, String status) {
        updateDynamic(timestamp,speed,align_roll,align_pitch,align_yaw,longitude,latitude,battery_status,last_seen,status);
    }



    public int getSpeed() {
        return speed;
    }

    public String getAlign_roll() {
        return alignRoll;
    }

    public String getAlign_pitch() {
        return alignPitch;
    }

    public String getAlign_yaw() {
        return alignYaw;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public int getBattery_status() {
        return batteryStatus;
    }

    public OffsetDateTime getLast_seen() {
        return lastSeen;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public String getStatus() {
        return status;
    }

    /**
     * Debug method to test result in console
     * @return String with all information
     */
    @Override
    public String toString() {  //For console debugging
        return "Dynamic{"+
                ", timestamp=" + timestamp +
                ", speed='" + speed + '\'' +
                ", align_roll='" + alignRoll + '\'' +
                ", align_pitch=" + alignPitch +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", battery_status='" + batteryStatus + '\'' +
                ", last_seen='" + lastSeen + '\'' +
                ", status='" + status + '\'' +
                "}\n";
    }
}

