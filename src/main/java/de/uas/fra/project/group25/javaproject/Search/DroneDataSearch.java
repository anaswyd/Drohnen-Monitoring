package de.uas.fra.project.group25.javaproject.Search;

import java.util.ArrayList;
import java.util.List;

public class DroneDataSearch extends AbstractSearch {

    public DroneDataSearch(SearchType searchType) throws WrongSearchTypeException {
        super(searchType);
        if (TYPE_CHECK.equals(searchType.getSearchType())){
            throw new WrongSearchTypeException(TYPE_CHECK + " not allowed");
        }
    }

    @Override
    public List<String> findRelevantPages() {
        List<String> list = new ArrayList<String>();
        String newLink = currentPage;
        do {
            list.add(newLink);
        }while ((newLink = navigate()) != null);
        this.currentPage = START_URL;
        return list;
    }
}
