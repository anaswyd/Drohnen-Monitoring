package de.uas.fra.project.group25.javaproject.ApiConnector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ApiConnector {
    private final String TOKEN = "Token 64380548480b62bfc42181f19df477ef92839670";
    private final String USER_AGENT = "Google Chrome";
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * establishes connection to RESTApi
     * @param endpointUrl Api endpoint to connect to
     * @return response Stringbuilder of fetched data
     * @throws Exception
     */
    public StringBuilder connect(String endpointUrl) throws Exception {
        URL url;
        HttpURLConnection connection = null;
        StringBuilder response = null;
        try {
            url = new URL(endpointUrl);
            logger.info("Start connection");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Authorization", TOKEN);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", USER_AGENT);


            int responseCode = connection.getResponseCode();
            if(responseCode != HttpURLConnection.HTTP_OK){
                throw new ResponseException("HTTP connection mistake: ",responseCode) ; //In case page could not be found
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();


        } catch (MalformedURLException e) {
            logger.log(Level.SEVERE,"MalformedURLException" + e.getLocalizedMessage());
            throw new MalformedURLException();//?
        } catch (ConnectException e) {
            //Exception occurs if User is not connected to the VPN
            logger.log(Level.SEVERE, "ConnectionException: " + e.getLocalizedMessage());
            throw new VpnConnectionException();
        }finally {
            if (connection != null){
                connection.disconnect();
                logger.info("Connection closed!");
            }
        }
        return response;
    }
}
