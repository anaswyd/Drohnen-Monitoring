package de.uas.fra.project.group25.javaproject.ApiConnector;

public class ResponseException extends Exception {
    private final int responseCode;

    public ResponseException(String message, int responseCode) {
        super(message + "response code: " + responseCode);
        this.responseCode = responseCode ;
    }

}
