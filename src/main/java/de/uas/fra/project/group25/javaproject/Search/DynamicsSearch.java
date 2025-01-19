package de.uas.fra.project.group25.javaproject.Search;

import de.uas.fra.project.group25.javaproject.ApiConnector.ApiConnector;
import org.json.JSONObject;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DynamicsSearch extends AbstractSearch{
    private int droneCount;
    private long timestampDifference;
    private long starttime;
    private long endtime;

    /**
     * creates a DynamicsSearch
     * @param searchType enum DYNAMICS required
     * @throws WrongSearchTypeException if wrong searchType was used
     */
    public DynamicsSearch(SearchType searchType) throws WrongSearchTypeException {
        super(searchType);
        if (!Objects.equals(TYPE_CHECK, searchType.getSearchType())){
            throw new WrongSearchTypeException("only " + TYPE_CHECK + "allowed");
        }
    }

    /**
     * fetches the count of a json document
     * @param link to the json file
     * @return returns the count on the json file
     */
    private int fetchCount (String link){
        StringBuilder response = new StringBuilder("lol");
        try {
            response = this.apiConnector.connect(link);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (response == null){
            //do smth like throw "Website not accessible"
            return 0;
        }
        //Or handle it as exception in ApiConnector
        // System.out.println(response);

        //System.out.println(response);
        JSONObject jsonFile = new JSONObject(response.toString());
        return jsonFile.getInt("count");
    }

    /**
     * transforms a given timestamp in unix seconds
     * @param timestamp to calculate
     * @return seconds from unix start
     */
    private long timeToSeconds(String timestamp) {
        //transform timestamp to unixtime
        OffsetDateTime time = OffsetDateTime.parse(timestamp);
        return time.toEpochSecond();
    }

    /**
     * calculates the time between 2 given timestamps by tranforming them into unix time and then subtracting them
     * @param pages number of pages
     * @param starttime first timestamp of the json document
     * @param endtime last timestamp of the json document
     */
    private void calculateTimestampDifference(int pages, String starttime, String endtime){
        //transform timestamp in long so we can calculate
        this.starttime = timeToSeconds(starttime);
        this.endtime = timeToSeconds(endtime);
        //mean is the time between timestamps because this time is consistent
        this.timestampDifference = (long) ((this.endtime - this.starttime)/pages);
    }

    /**
     * finds the first and last page of the dynamics at dronesim.facets-labs.com to get all the start and latest dynamics
     * @return returns 2 links to download
     */
    @Override
    public List<String> findRelevantPages() {
        this.droneCount = fetchCount(HUB_URL + SearchType.DRONE.getSearchType());
        ApiConnector apiConnector = this.apiConnector;
        List<String> relevantPages = new ArrayList<String>();
        relevantPages.add(new StringBuilder().append(START_URL).append("/?limit=").append(droneCount).toString());
        //System.out.println(relevantPages);
        JSONObject firstJson = null;
        JSONObject lastJson = null;
        String firstTimeStamp = "";
        String lastTimeStamp = "";
        try {
            StringBuilder firstPage = apiConnector.connect(relevantPages.getFirst().toString());
            firstJson = new JSONObject(firstPage.toString());
        }catch (Exception e){
            e.printStackTrace();
        }

        int count = firstJson.getInt("count");
        //retrieve first timestamp
        try {
            firstTimeStamp = ((JSONObject) firstJson.getJSONArray("results").get(0)).getString("timestamp");
            relevantPages.add(new StringBuilder().append(relevantPages.getFirst()).append("&offset=").append(count - droneCount).toString());
        }catch(Exception e){
            e.printStackTrace();
        }
            //System.out.println(apiConnector.connect(relevantPages.get(1).toString()));

        //retrieve last timestamp
        try {
            StringBuilder lastPage = apiConnector.connect(relevantPages.getLast().toString());
            lastJson = new JSONObject(lastPage.toString());
            lastTimeStamp = ((JSONObject) lastJson.getJSONArray("results").get(0)).getString("timestamp");
        }catch (Exception e){
            e.printStackTrace();
        }


        //calculate timestampDifference
        calculateTimestampDifference((count / this.droneCount) - 1, firstTimeStamp, lastTimeStamp);
        //System.out.println(this.timestampDifference);

        return relevantPages;
    }

    /**
     * find the specific dynamics for a given timestamp
     * @param timestamp to look for
     * @return link where the dynamics can be downloaded
     */
    public String findTimestampedRelevantPage(String timestamp){
        //
        int offset = (int) (((timeToSeconds(timestamp) - this.starttime) / timestampDifference) * droneCount);
        StringBuilder page = new StringBuilder().append(START_URL).append("/?limit=").append(droneCount).append("&offset=").append(offset);
        return page.toString();
    }

    /**
     * finds all dynamics for one specific drone
     * @param id of the drone to look for
     * @return link where the dynamics can be downloaded from
     */
    public String findDetailedRelevantPage(int id){
        int count = fetchCount(HUB_URL + id + SearchType.DYNAMICS.getSearchType() + "/" );
        return new StringBuilder().append(HUB_URL).append(id).append("/?limit=").append(count).toString();
    }
}
