package com.company.data;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents each node on the graph
 */
public class Node {
    String nodeName;
    boolean isEndNode;
    Coordinate nodeCoordinates;
    List<Coordinate> neighbors;

    public Node(final String name, final boolean isEnd){
        this.nodeName = name;
        this.isEndNode = isEnd;
        nodeCoordinates = new Coordinate(0, 0);
    }

    public Node(final int node_x, final int node_y) {
        nodeCoordinates = new Coordinate(node_x, node_y);
    }

    public void addNeighbor(final int neighbor_x, final int neighbor_y) {
        Coordinate neighbor = new Coordinate(neighbor_x, neighbor_y);
        if(neighbors == null) {
            neighbors = new ArrayList();
        }
        neighbors.add(neighbor);
    }

    public boolean isEndNode() {
        return isEndNode;
    }

    public List<Coordinate> getNeighbors() {
        return this.neighbors;
    }

    public String getName() {
        return this.nodeName;
    }

    public Coordinate getNodeCoordinate() {
        return this.nodeCoordinates;
    }
}
