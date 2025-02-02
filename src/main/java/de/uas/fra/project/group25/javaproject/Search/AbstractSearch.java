package de.uas.fra.project.group25.javaproject.Search;

import de.uas.fra.project.group25.javaproject.ApiConnector.ApiConnector;
import org.json.JSONObject;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class AbstractSearch {
    private static final String USER_AGENT = "MOzilla FIrefox Awesome version";
    private static final String TOKEN = "Token 64380548480b62bfc42181f19df477ef92839670";
    protected static final String TYPE_CHECK = SearchType.DYNAMICS.getSearchType();
    protected static final String HUB_URL = "http://dronesim.facets-labs.com/api/";
    protected final String START_URL;
    protected String currentPage;
    protected ApiConnector apiConnector = new ApiConnector();
    protected Logger logger = Logger.getLogger(this.getClass().getName());

    public AbstractSearch(SearchType searchType){
        this.START_URL = HUB_URL + searchType.getSearchType();
        this.currentPage = START_URL;
    }
    protected String navigate() {
        StringBuilder response = new StringBuilder();
        try {
            response = new ApiConnector().connect(currentPage);
        }catch (Exception e){
            logger.log(Level.SEVERE, e.getMessage());
            return null;
        }
        if(response == null){
            return null;
        }
        this.currentPage = getNextPage(response.toString());
        return currentPage;
    }

    /**
     *
     * @param jsonInput
     * @return
     */
    private String getNextPage(String jsonInput) {
        JSONObject jsonFile = new JSONObject(jsonInput);
        String nextPage;
        try {
            nextPage = jsonFile.getString("next");
        } catch(org.json.JSONException e) {
            nextPage = null;
        }

        return nextPage;
    }
    public abstract List<String> findRelevantPages();
}
