package de.uas.fra.project.group25.javaproject.Search;

public enum SearchType {
    DRONE("drones"),
    DRONETYPE("dronetypes"),
    DYNAMICS("dronedynamics");

    private final String searchType;
    private SearchType(String searchType) {
        this.searchType = searchType;
    }
    public String getSearchType() {
        return searchType;
    }
}
