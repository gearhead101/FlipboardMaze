package com.company.helper;

import com.company.data.Node;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * JSON Parser for Flipboard Maze
 */
public class ResponseParser {

    static String RESPONSE_KEY_END = "end";
    static String RESPONSE_KEY_NAME = "letter";
    static String RESPONSE_KEY_ADJ = "adjacent";
    static String RESPONSE_KEY_X = "x";
    static String RESPONSE_KEY_Y = "y";

    public static Node parse(final JSONObject inputJSON) {
        String nodeName = null;
        boolean endValue = false;

        Node parsedNode = null;

        if(inputJSON.containsKey(RESPONSE_KEY_END)) {
            endValue = Boolean.parseBoolean(inputJSON.get(RESPONSE_KEY_END).toString());
        }
        if(inputJSON.containsKey(RESPONSE_KEY_NAME)) {
            nodeName = inputJSON.get(RESPONSE_KEY_NAME).toString();
        }
        parsedNode = new Node(nodeName, endValue);
        if(inputJSON.containsKey(RESPONSE_KEY_ADJ)) {
            JSONArray adjacencyArray = (JSONArray)inputJSON.get(RESPONSE_KEY_ADJ);
            for(int i=0; i<adjacencyArray.size(); i++) {
                JSONObject adjacentNode = (JSONObject)adjacencyArray.get(i);
                if(adjacentNode.containsKey(RESPONSE_KEY_X) && adjacentNode.containsKey(RESPONSE_KEY_Y)) {
                    int adjNodeX = Integer.parseInt(adjacentNode.get(RESPONSE_KEY_X).toString());
                    int adjNodeY = Integer.parseInt(adjacentNode.get(RESPONSE_KEY_Y).toString());
                    parsedNode.addNeighbor(adjNodeX, adjNodeY);
                }

            }
        }
        return parsedNode;
    }
}
