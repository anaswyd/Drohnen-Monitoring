package de.uas.fra.project.group25.javaproject.ApiConnector;

import de.uas.fra.project.group25.javaproject.Drone.Drone;
import de.uas.fra.project.group25.javaproject.Drone.DroneDetailRow;
import de.uas.fra.project.group25.javaproject.Drone.DroneDynamic;
import de.uas.fra.project.group25.javaproject.Drone.DroneType;
import de.uas.fra.project.group25.javaproject.Search.DroneDataSearch;
import de.uas.fra.project.group25.javaproject.Search.DynamicsSearch;
import de.uas.fra.project.group25.javaproject.Search.SearchType;
import de.uas.fra.project.group25.javaproject.Search.WrongSearchTypeException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ApiAccess  {
    private final HashMap<Integer ,Drone> outputDrones ;
    private final HashMap<Integer, DroneType> outputCatalogue ;
    private final List<DroneDetailRow> specificDynamics;
    private final ApiConnector apiConnector;
    private final DroneDataSearch drone_type;
    private final DroneDataSearch droneType_type;
    private final DynamicsSearch dynamic_type;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * constructor of ApiAccess
     */
    public ApiAccess(){
        this.apiConnector = new ApiConnector();
        this.outputDrones  = new HashMap<Integer, Drone>();
        this.outputCatalogue = new HashMap<Integer,DroneType>();
        this.specificDynamics = new ArrayList<DroneDetailRow>();
        this.drone_type = new DroneDataSearch(SearchType.DRONE);
        this.droneType_type = new DroneDataSearch(SearchType.DRONETYPE);
        this.dynamic_type = new DynamicsSearch(SearchType.DYNAMICS);
    }

    /**
     * updating drone data
     */

    public void update(){
        logger.log(Level.INFO, "Updating api access");
        try {
            fetchCatalogue();
            logger.log(Level.INFO, "Catalogue updated");
            fetchDrones();
            logger.log(Level.INFO, "Drones updated");
            fetchStartEndDynamics();
            logger.log(Level.INFO, "DroneDynamics updated");
            logger.log(Level.INFO, "Updated api access finished");
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error updating api access");
        }

    }

    /**
     * fetches all drones by calling the ApiConnector with suitable links, creates a (Drone) object for each
     * drone and populates the (outputDrones) map with these Drone objects, using their IDs as keys
     */
    private void fetchDrones() throws Exception{
        this.outputDrones.clear();
        List<String> relevantPages = null;
        try {
            relevantPages = this.drone_type.findRelevantPages();
        }catch (Exception e){
            logger.log(Level.SEVERE, e.getMessage());
            throw e;
        }
        outputDrones.clear(); //Leeren der Liste
        StringBuilder droneTableAsJson;
            for(String page : relevantPages) {
                try{
                    droneTableAsJson= apiConnector.connect(page);
                } catch (Exception e) {
                    logger.log(Level.SEVERE, e.getMessage());
                    throw e;
                }
                JSONObject wholeFile = new JSONObject(droneTableAsJson.toString());
                JSONArray jsonFile = wholeFile.getJSONArray("results");
                JSONObject o;
                for (int i = 0; i < jsonFile.length(); i++) {
                    o = jsonFile.getJSONObject(i);
                    int id=o.getInt("id");

                    String temp=o.getString("dronetype");  //outsourcen
                    Integer typeId = Integer.parseInt(temp.replace("http://dronesim.facets-labs.com/api/dronetypes/", "").replace("/", ""));

                    DroneType dronetype = outputCatalogue.get(typeId) ;
                    String created=o.getString("created");
                    String serialnumber=o.getString("serialnumber");
                    String carriage_type=o.getString("carriage_type");
                    int carriage_weight=o.getInt("carriage_weight");
                    Drone drone = new Drone(id,dronetype,created,serialnumber,carriage_weight,carriage_type,null);
                    outputDrones.put(id,drone);
                }
            }
    }

    /**
     * fetches all droneType data by calling the ApiConnector with suitable links, creates a (DroneType) object for each
     * drone type and populates the (outputCatalogue) map with these DroneType objects, using their IDs as keys
     */

    private void fetchCatalogue() throws Exception {
        this.outputCatalogue.clear();
        List<String> relevantPages = null;
        try {
            relevantPages = this.droneType_type.findRelevantPages();
        }catch (WrongSearchTypeException e) {
            logger.log(Level.SEVERE,e.getMessage());
            throw e;
        }
        outputCatalogue.clear(); //Leeren der Liste
        StringBuilder droneTableAsJson;
        for(String page : relevantPages) {
            try {
                droneTableAsJson = this.apiConnector.connect(page);
            }catch (Exception e) {
                logger.log(Level.SEVERE, e.getMessage());
                throw e;
            }
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
                DroneType droneType = new DroneType(id,typename,manufacturer,weight,max_speed,battery_capacity,control_range,max_carriage);
                outputCatalogue.put(id,droneType);
            }
        }
    }

    /**
     * fetches all dynamics for a single drone
     * @param drone_id id of the drone
     * @return JSONObject for details
     */
    public void fetchDynamics(int drone_id) throws Exception {
        JSONObject jsonObject = null;
        try {
            String relevantPage = this.dynamic_type.findDetailedRelevantPage(drone_id);
            StringBuilder droneTableAsJson = apiConnector.connect(relevantPage);
            jsonObject = new JSONObject(droneTableAsJson.toString());
        }catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw e;
        }
        this.specificDynamics.clear();
        this.specificDynamics.add(new DroneDetailRow());
        JSONArray jsonArray = jsonObject.getJSONArray("results");
        long timeStampDifference = this.dynamic_type.getTimestampDifference();
        jsonArray.forEach(item -> {
            this.specificDynamics.add(new DroneDetailRow(((JSONObject) item), this.specificDynamics.getLast(), timeStampDifference));
        });
        this.specificDynamics.removeFirst();

    }

    public HashMap<Integer, DroneType> getOutputCatalogue() {
        return outputCatalogue;
    }

    public HashMap<Integer, Drone> getOutputDrone() {
        return outputDrones;
    }

    public List<DroneDetailRow> getSpecificDynamic() {
        return this.specificDynamics;
    }

    /**
     * fetches start and end dynamics for a single drone
     */

    private void fetchStartEndDynamics() throws Exception {
        List<String> relevantPages = null;
        try {
            relevantPages = this.dynamic_type.findRelevantPages();
        }catch (Exception e){
            logger.log(Level.SEVERE,e.getMessage());
            throw e;
        }
        StringBuilder droneTableAsJson;
            for(String page:relevantPages) {
                try {
                    droneTableAsJson = apiConnector.connect(page);
                }catch (Exception e){
                    logger.log(Level.SEVERE,e.getMessage());
                    throw e;
                }
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
                    DroneDynamic dynamic = new DroneDynamic(timestamp, speed, align_roll, align_pitch, align_yaw, longitude, latitude, battery_status, last_seen, status);
                    outputDrones.get(id).addDroneDynamic(dynamic);
                }
            }
    }
}


