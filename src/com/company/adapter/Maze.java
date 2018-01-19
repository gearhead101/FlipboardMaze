package com.company.adapter;

import com.company.data.Coordinate;
import com.company.data.Node;
import com.company.helper.ResponseParser;
import com.company.helper.Strings;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Represents Maze and maintains it's current state
 */
public class Maze {

    String mazeId = "";
    String solution = "";
    Map<Coordinate, Boolean> visitedCoordinates; // Used for BFS
    Queue<Coordinate> unprocessedCoordinates;  // Used for BFS

    public Maze() {
        try{
            Node startingNode = callAndParseMazeURI(Strings.MAZE_START_CONTEXT);
            if(startingNode == null) {
                System.out.println("Processing maze id:" + mazeId + " failed.");
            }
            visitedCoordinates = new HashMap();
            unprocessedCoordinates = new LinkedList();
            unprocessedCoordinates.add(startingNode.getNodeCoordinate());
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public String solve() {
        while(!unprocessedCoordinates.isEmpty()) {

            Coordinate currentCoordinate = unprocessedCoordinates.remove();
            visitedCoordinates.put(currentCoordinate, true);
            Node currentNode = makeMove(currentCoordinate.getX(), currentCoordinate.getY(), currentCoordinate.getPath());

            if(currentNode.isEndNode()) {
                System.out.println("Maze id:" + mazeId);
                solution = currentCoordinate.getPath() + currentNode.getName();
                return solution;
            } else {
                for(Coordinate neighborCoordinates: currentNode.getNeighbors()) {
                    if(!visitedCoordinates.containsKey(neighborCoordinates)) {
                        neighborCoordinates.setPath(currentCoordinate.getPath() + currentNode.getName());
                        unprocessedCoordinates.add(neighborCoordinates);
                    } else {
                        //System.out.println("skipping visited neighbor");
                    }
                }
            }
        }
        return solution;
    }

    public Node makeMove(final int x, final int y, final String currentPath) {
        Node movedToNode = null;
        try{
            movedToNode = callAndParseMazeURI(Strings.MAZE_STEP_CONTEXT + "?s=" + mazeId + "&x=" + x + "&y=" + y);
            if(movedToNode == null) {
                System.out.println("Processing maze id:" + mazeId + " failed.");
            }
            movedToNode.getNodeCoordinate().setPath(currentPath + movedToNode.getName());
        }catch(Exception e) {
            e.printStackTrace();
        }
        return movedToNode;
    }

    private Node callAndParseMazeURI(final String context){
        Node currentNode = null;

        try {
            URL obj = new URL(Strings.DOMAIN + context);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            if(conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            String redirectedURL = conn.getURL().toURI().toString();
            Pattern pattern = Pattern.compile("s=(\\d+\\.\\d+)");
            Matcher m = pattern.matcher(redirectedURL);
            m.find();
            mazeId = m.group(0).replaceAll("^s=", "");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer responseBody = new StringBuffer();
            String line;

            while ((line = in.readLine()) != null) {
                responseBody.append(line);
            }
            in.close();

            JSONParser parser = new JSONParser();
            JSONObject responseJSON = (JSONObject)parser.parse(responseBody.toString());
            currentNode = ResponseParser.parse(responseJSON);

        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        return currentNode;
    }

    public String getId() {
        return this.mazeId;
    }
}
