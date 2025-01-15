package de.uas.fra.project.group25.javaproject.Search;

import de.uas.fra.project.group25.javaproject.Drone.DetailedCalculations;

import java.time.OffsetDateTime;

public class test {
    public static void main(String[] args) {
        String timestamp = "2024-12-17T17:00:52.588123+01:00";
        //System.out.println(timestamp);
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(timestamp);
        System.out.println(offsetDateTime);
        DetailedCalculations test = new DetailedCalculations();
        //int a = test.haversineEquation();
    }
}
