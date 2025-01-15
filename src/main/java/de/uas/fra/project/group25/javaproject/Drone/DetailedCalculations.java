package de.uas.fra.project.group25.javaproject.Drone;
import java.util.ArrayList;
import java.util.List;

public class DetailedCalculations {
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
    public List<Double> calculateDistance(List<String> longitudes, List<String> latitudes) {
        List<Double> distances = new ArrayList<Double>();
        distances.add(0.0);
        for(int i = 0; i < longitudes.size() - 1; i++ ) {
            distances.add(distances.getLast() + haversineEquation(Double.parseDouble(longitudes.get(i)),Double.parseDouble(latitudes.get(i)),Double.parseDouble(longitudes.get(i + 1)),Double.parseDouble(latitudes.get(i + 1))));
        }
        return distances;
    }

    public List<Double> calculateAverageSpeed(List<Double> distances, long time_between_intervals){
        List<Double> averageSpeeds = new ArrayList<Double>();
        int i = 0;
        for(Double distance : distances) {
            if(distance == 0) {
                averageSpeeds.add(0.0);
            }
            else {
                averageSpeeds.add(distance / time_between_intervals * i);
            }
            i = i + 1;
        }
        return averageSpeeds;
    }

    public List<Double> calculateActiveTime(List<String> status,long time_between_intervals){
        List<Double> activeTime = new ArrayList<Double>();
        int i = 0;
        for(String stat : status){
            if(i == 0){
                activeTime.add(0.0);
                i = i + 1;
                continue;
            }
            switch(stat){
                case "ON":
                    activeTime.add(activeTime.getLast() + (time_between_intervals * i));
                    break;
                case "OF":
                    activeTime.add(activeTime.getLast());
                    break;
                case "IS":
                    //we decided that issues count as inactive time for now
                    activeTime.add(activeTime.getLast());
                    break;
            }
            i = i + 1;
        }
        return activeTime;
    }

    public List<Integer> calculateBatteryUsage(List<Integer> battery_status) {
        List<Integer> usage = new ArrayList<Integer>();
        int sum = 0;
        int temp;

        for (int i = 1; i < battery_status.size(); i++) {
            temp = 0;

            //calculate batteryusage
            if (battery_status.get(i - 1) > battery_status.get(i)) {
                temp = battery_status.get(i - 1) - battery_status.get(i);
            }
            sum += temp;
            usage.add(sum);
        }
        return usage;
    }

}
