package de.uas.fra.project.group25.javaproject.ApiConnector;

import de.uas.fra.project.group25.javaproject.Drone.Drone;
import de.uas.fra.project.group25.javaproject.Drone.DroneDynamic;
import de.uas.fra.project.group25.javaproject.Drone.DroneType;
import de.uas.fra.project.group25.javaproject.Search.DroneDataSearch;
import de.uas.fra.project.group25.javaproject.Search.DynamicsSearch;
import de.uas.fra.project.group25.javaproject.Search.SearchType;
import de.uas.fra.project.group25.javaproject.Search.WrongSearchTypeException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;


public class ApiAccess  {
    HashMap<Integer ,Drone> outputDrones ;
    HashMap<Integer, DroneType> outputCatalogue ;
    ApiConnector apiConnector;
    DroneDataSearch drone_type;
    DroneDataSearch droneType_type;
    DynamicsSearch dynamic_type;


    public ApiAccess(){
        this.apiConnector = new ApiConnector();
        this.outputDrones  = new HashMap<Integer, Drone>();
        this.outputCatalogue = new HashMap<Integer,DroneType>();
        this.drone_type = new DroneDataSearch(SearchType.DRONE);
        this.droneType_type = new DroneDataSearch(SearchType.DRONETYPE);
        this.dynamic_type = new DynamicsSearch(SearchType.DYNAMICS);
        update();
    }

    public void update(){
        fetchCatalogue();
        fetchDrones();
        fetchStartEndDynamics();
    }

    private void fetchDrones() {
        try {
            List<String> relevantPages = this.drone_type.findRelevantPages();
            outputDrones.clear(); //Leeren der Liste
            for(String page : relevantPages) {
                StringBuilder droneTableAsJson = apiConnector.connect(page);
                JSONObject wholeFile = new JSONObject(droneTableAsJson.toString());
                JSONArray jsonFile = wholeFile.getJSONArray("results");
                JSONObject o;
                for (int i = 0; i < jsonFile.length(); i++) {
                    o = jsonFile.getJSONObject(i);
                    int id=o.getInt("id");
                    //DroneType dronetype= new DroneType();
                    //DroneType dronetype=o.getString("dronetype"); !!!

                    //CURRENTLY ADDING DRONETYPE VIA HASHMAP

                    String temp=o.getString("dronetype");  //outsourcen
                    Integer typeId = Integer.parseInt(temp.replace("http://dronesim.facets-labs.com/api/dronetypes/", "").replace("/", "")); //temp should contain DroneType ID

                    DroneType dronetype = outputCatalogue.get(typeId) ;
                    String created=o.getString("created");
                    String serialnumber=o.getString("serialnumber");
                    String carriage_type=o.getString("carriage_type");
                    int carriage_weight=o.getInt("carriage_weight");
                    Drone d = new Drone(id,dronetype,created,serialnumber,carriage_weight,carriage_type,null);
                    outputDrones.put(id,d);
                }
            }
        } catch (WrongSearchTypeException e) {
            throw new RuntimeException(e);
        }
        catch (ResponseException e){
            throw new RuntimeException(e);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private void fetchCatalogue() {
        try {
            List<String> relevantPages = this.droneType_type.findRelevantPages();
            outputCatalogue.clear(); //Leeren der Liste
            for(String page : relevantPages) {
                StringBuilder droneTableAsJson = this.apiConnector.connect(page);
                JSONObject wholeFile = new JSONObject(droneTableAsJson.toString());
                JSONArray jsonFile = wholeFile.getJSONArray("results");
                JSONObject o;
                for (int i = 0; i < jsonFile.length(); i++) {
                    o = jsonFile.getJSONObject(i);
                    int id=o.getInt("id");
                    int battery_capacity=o.getInt("battery_capacity");
                    int control_range=o.getInt("control_range");
                    int weight=o.getInt("weight");
                    int max_carriage=o.getInt("max_carriage");
                    int max_speed=o.getInt("max_speed");
                    String manufacturer=o.getString("manufacturer");
                    String typename=o.getString("typename");
                    DroneType dt = new DroneType(id,typename,manufacturer,weight,max_speed,battery_capacity,control_range,max_carriage);
                    outputCatalogue.put(id,dt);
                }
            }
        } catch (WrongSearchTypeException e) {
            throw new RuntimeException(e);
        }
        catch (ResponseException e){
            throw new RuntimeException(e);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private JSONObject fetchDynamics(int drone_id){
        try {
            String relevantPage = this.dynamic_type.findDetailedRelevantPage(drone_id);
            StringBuilder droneTableAsJson = apiConnector.connect(relevantPage);
            JSONObject wholeFile = new JSONObject(droneTableAsJson.toString());
            return wholeFile;
        } catch (WrongSearchTypeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public HashMap<Integer, DroneType> getOutputCatalogue() {
        return outputCatalogue;
    }

    public HashMap<Integer, Drone> getOutputDrone() {
        return outputDrones;
    }


//Waiting for Map implementation
//    private void fetchTimestampDynamics(String timestamp){
//        try {
//            String relevantPage = this.dynamic_type.findTimestampedRelevantPage(timestamp);
//            StringBuilder droneTableAsJson = apiConnector.connect(relevantPage);
//            JSONObject wholeFile = new JSONObject(droneTableAsJson.toString());
//            JSONArray jsonFile = wholeFile.getJSONArray("results");
//            JSONObject o;
//            for (int i = 0; i < jsonFile.length(); i++) {
//                o = jsonFile.getJSONObject(i);
//                //String timestamp=this.timestamp;
//                int speed=o.getInt("speed");
//                String align_roll=o.getString("align_roll");
//                String align_pitch=o.getString("align_pitch");
//                String align_yaw=o.getString("align_yaw");
//                String longitude=o.getString("longitude");
//                String latitude=o.getString("latitude");
//                int battery_status=o.getInt("battery_status");
//                String last_seen=o.getString("last_seen");
//                String status=o.getString("status");
//                DroneDynamic d = new DroneDynamic(timestamp,speed,align_roll,align_pitch,align_yaw,longitude,latitude,battery_status,last_seen,status);
//                outputDynamics.add(d);
//            }
//
//        } catch (WrongSearchTypeException e) {
//            throw new RuntimeException(e);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    private void fetchStartEndDynamics(){
        try {
            List<String> relevantPages = this.dynamic_type.findRelevantPages();
            for(String page:relevantPages) {
                StringBuilder droneTableAsJson = apiConnector.connect(page);
                JSONObject wholeFile = new JSONObject(droneTableAsJson.toString());
                JSONArray jsonFile = wholeFile.getJSONArray("results");
                JSONObject o;
                for (int i = 0; i < jsonFile.length(); i++) {
                    o = jsonFile.getJSONObject(i);
                    String timestamp = o.getString("timestamp");
                    int speed = o.getInt("speed");
                    String align_roll = o.getString("align_roll");
                    String align_pitch = o.getString("align_pitch");
                    String align_yaw = o.getString("align_yaw");
                    String longitude = o.getString("longitude");
                    String latitude = o.getString("latitude");
                    int battery_status = o.getInt("battery_status");
                    String last_seen = o.getString("last_seen");
                    String status = o.getString("status");
                    String temp=o.getString("drone");
                    Integer id = Integer.parseInt(temp.replace("http://dronesim.facets-labs.com/api/drones/", "").replace("/", "")); //temp should contain DroneType ID
                    DroneDynamic d = new DroneDynamic(timestamp, speed, align_roll, align_pitch, align_yaw, longitude, latitude, battery_status, last_seen, status);
                    outputDrones.get(id).clearDynamics();
                    outputDrones.get(id).addDroneDynamic(d);
                }
            }
        } catch (WrongSearchTypeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}


