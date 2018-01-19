package com.company.helper;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sprabhakar on 1/18/18.
 */
public class MazeVerifier {

    static String RESPONSE_KEY_SUCCESS = "success";
    static String RESPONSE_SUCCESS_VALUE = "true";

    public boolean verifyGuess(final String mazeId, final String guess) {
        try {
            String context = Strings.MAZE_CHECK_CONTEXT + "?s=" + mazeId + "&guess=" + guess;
            URL obj = new URL(Strings.DOMAIN + context);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            if(conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("Network error while verifying maze id:" + mazeId);
                return false;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer responseBody = new StringBuffer();
            String line;

            while ((line = in.readLine()) != null) {
                responseBody.append(line);
            }
            in.close();

            JSONParser parser = new JSONParser();
            JSONObject responseJSON = (JSONObject)parser.parse(responseBody.toString());
            if(responseJSON.containsKey(RESPONSE_KEY_SUCCESS)) {
                if(responseJSON.get(RESPONSE_KEY_SUCCESS).toString().equalsIgnoreCase(RESPONSE_SUCCESS_VALUE)) {
                    return true;
                }
            }
        } catch(Exception e) {
            System.out.println("Error while verifying maze id:" + mazeId + ", " + e);
            return false;
        }
        return false;
    }
}
