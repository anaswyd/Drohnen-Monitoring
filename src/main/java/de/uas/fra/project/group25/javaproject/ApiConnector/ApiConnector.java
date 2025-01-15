package de.uas.fra.project.group25.javaproject.ApiConnector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class ApiConnector {
    final String TOKEN = "Token 64380548480b62bfc42181f19df477ef92839670";
    final String USER_AGENT = "Google Chrome";

    public StringBuilder connect(String endpointUrl) throws Exception {
        URL url;
        HttpURLConnection connection = null;
        StringBuilder response = null;
        try {
            url = new URL(endpointUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Authorization", TOKEN);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = connection.getResponseCode();

            if(responseCode != HttpURLConnection.HTTP_OK){
                throw new ResponseException("HTTP connection mistake: ",responseCode) ;
            }

            //System.out.println("Response Code " + responseCode); //Response Code handle???
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            /*if(connection == 202) {
                throw new Exception("Nicht zum VPN connected");
            }*/

        } catch (MalformedURLException e) {
            System.err.println("Malformed URL: " + e.getLocalizedMessage());
            e.printStackTrace();
        /*} catch (ConnectException e) { //Exception not connecting possible
            System.out.println("Connection not possible: " + e.getLocalizedMessage());
            e.printStackTrace(); */
        } catch (IOException e) {
            System.out.println("General IO Exception: " + e.getLocalizedMessage());
            e.printStackTrace();
        }


        return response;
    }
}
