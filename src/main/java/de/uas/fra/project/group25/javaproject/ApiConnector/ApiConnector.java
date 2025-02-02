package de.uas.fra.project.group25.javaproject.ApiConnector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApiConnector {
    private final String TOKEN = "Token 64380548480b62bfc42181f19df477ef92839670";
    private final String USER_AGENT = "Google Chrome";
    private final Logger Connectorlogger = Logger.getLogger(this.getClass().getName());

    /**
     * Establishes connection to REST API.
     *
     * @param endpointUrl API endpoint to connect to
     * @return StringBuilder containing the fetched data
     * @throws IOException            if an I/O error occurs
     * @throws ResponseException      if the HTTP response code is not OK
     * @throws VpnConnectionException if the connection fails due to VPN issues
     */
    public StringBuilder connect(String endpointUrl) throws IOException, ResponseException, VpnConnectionException {
        URL url;
        HttpURLConnection connection = null;
        StringBuilder response = new StringBuilder();

        try {
            url = new URL(endpointUrl);
            Connectorlogger.info("Starting connection to: " + endpointUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Authorization", TOKEN);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {               //Checking for debug purposes if page found
                Connectorlogger.severe("URL Error: " + responseCode);
                throw new ResponseException("HTTP connection mistake: ", responseCode);
            }

            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }

        } catch (ConnectException e) {
            Connectorlogger.log(Level.SEVERE, "ConnectionException: Failed to connect to " + endpointUrl);
            throw new VpnConnectionException();
        } catch (IOException e) {
            Connectorlogger.log(Level.SEVERE, "IOException: Failed to process connection to " + endpointUrl);
            throw new IOException("An I/O error occurred: " + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
                Connectorlogger.info("Connection closed successfully!");
            }
        }

        return response;
    }
}