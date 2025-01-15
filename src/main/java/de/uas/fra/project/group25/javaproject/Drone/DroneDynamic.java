package de.uas.fra.project.group25.javaproject.Drone;


import java.time.OffsetDateTime;

public class DroneDynamic {
    private OffsetDateTime timestamp;
    private int speed ;
    private String align_roll;
    private String align_pitch;
    private String align_yaw;
    private String longitude;
    private String latitude;
    private int battery_status;
    private OffsetDateTime last_seen;
    private String status;

    public void updateDynamic(String timestamp,int speed, String align_roll, String align_pitch, String align_yaw, String longitude, String latitude, int battery_status, String last_seen, String status){
        this.timestamp = OffsetDateTime.parse(timestamp);
        this.speed = speed;
        this.align_roll = align_roll;
        this.align_pitch = align_pitch;
        this.align_yaw = align_yaw;
        this.longitude = longitude;
        this.latitude = latitude;
        this.battery_status = battery_status;
        this.last_seen = OffsetDateTime.parse(last_seen);

        this.status = status;
    }

    public DroneDynamic(String timestamp,int speed, String align_roll, String align_pitch, String align_yaw, String longitude, String latitude, int battery_status, String last_seen, String status) {
        updateDynamic(timestamp,speed,align_roll,align_pitch,align_yaw,longitude,latitude,battery_status,last_seen,status);
    }



    public int getSpeed() {
        return speed;
    }

    public String getAlign_roll() {
        return align_roll;
    }

    public String getAlign_pitch() {
        return align_pitch;
    }

    public String getAlign_yaw() {
        return align_yaw;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public int getBattery_status() {
        return battery_status;
    }

    public OffsetDateTime getLast_seen() {
        return last_seen;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public String getStatus() {
        return status;
    }


    @Override
    public String toString() {  //For console debugging
        return "Dynamic{"+
                ", timestamp=" + timestamp +
                ", speed='" + speed + '\'' +
                ", align_roll='" + align_roll + '\'' +
                ", align_pitch=" + align_pitch +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", battery_status='" + battery_status + '\'' +
                ", last_seen='" + last_seen + '\'' +
                ", status='" + status + '\'' +
                "}\n";
    }
}

