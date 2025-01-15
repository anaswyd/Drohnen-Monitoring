package de.uas.fra.project.group25.javaproject.Search;

import de.uas.fra.project.group25.javaproject.ApiConnector.ApiConnector;
import org.json.JSONObject;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class DynamicsSearch extends AbstractSearch{
    private int droneCount;
    private long timestampDifference;
    private long starttime;
    private long endtime;
    public DynamicsSearch(SearchType searchType) throws WrongSearchTypeException {
        super(searchType);
        if (TYPE_CHECK != searchType.getSearchType()){
            throw new WrongSearchTypeException("only " + TYPE_CHECK + "allowed");
        }
    }
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

    private long timeToSeconds(String timestamp) {
        //transform timestamp to unixtime
        OffsetDateTime time = OffsetDateTime.parse(timestamp);
        return time.toEpochSecond();
    }

    private void calculateTimestampDifference(int pages, String starttime, String endtime){
        //transform timestamp in long so we can calculate
        this.starttime = timeToSeconds(starttime);
        this.endtime = timeToSeconds(endtime);
        //mean is the time between timestamps because this time is consistent
        this.timestampDifference = (long) ((this.endtime - this.starttime)/pages);
    }

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

    public String findTimestampedRelevantPage(String timestamp){
        //
        int offset = (int) (((timeToSeconds(timestamp) - this.starttime) / timestampDifference) * droneCount);
        StringBuilder page = new StringBuilder().append(START_URL).append("/?limit=").append(droneCount).append("&offset=").append(offset);
        return page.toString();
    }
    public String findDetailedRelevantPage(int id){
        int count = fetchCount(HUB_URL + id + SearchType.DYNAMICS.getSearchType() + "/" );
        return new StringBuilder().append(HUB_URL).append(id).append("/?limit=").append(count).toString();
    }
}
