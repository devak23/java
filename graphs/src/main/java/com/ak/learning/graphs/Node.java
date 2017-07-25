package com.ak.learning.graphs;


import java.util.ArrayList;
import java.util.List;

/**
 * THe node implements comparable as it is going to be inserted into a
 * PriorityQueue and that accepts objects which provides a comparator. In our
 * case, the nodes in the queue have to be stored based on their distances from
 * each other. Remember that Dijkstra's Algorithm only works for positive
 * weights. In cases of negative weights, the Bellman Ford algorithm is used to
 * calculate the sortest path
 */
public class Node implements Comparable<Node> {
    // every node should contain a name
    private String name;
    // a flag that can tell us if the node was visited or not. Useful in bfs() and dfs()
    private boolean visited;

    private int inDegree;
    // should have a previous node. The source node will have the previous as null
    // think of this like a linked list. This will useful when calculating the
    // shortest path between two nodes
    private Node previous;
    // should have some edges (going out or coming in)
    private List<Edge> adjEdges;
    // should maintain the distance from the previous node. Why did we initialize
    // this to infinity?
    // It is because when many instances of this node will be connected to each
    // other, then we dont know what is the distance of node X to node Y. Thus
    // we mark the current distance as inifinity which in other 
    private Double distance = Double.POSITIVE_INFINITY;

    public Node(String name) {
        this.name = name;
        this.adjEdges = new ArrayList<>();
        this.inDegree = 0;
    }

    public List<Edge> getAdjEdges() {
        return adjEdges;
    }

    public Node setAdjEdges(List<Edge> adjEdges) {
        this.adjEdges = adjEdges;
        return this;
    }

    public Double getDistance() {
        return distance;
    }

    public Node setDistance(Double distance) {
        this.distance = distance;
        return this;
    }

    public Node getPrevious() {
        return previous;
    }

    public Node setPrevious(Node previous) {
        this.previous = previous;
        return this;
    }

    public String getName() {
        return name;
    }

    public Node setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isVisited() {
        return visited;
    }

    public Node setVisited(boolean visited) {
        this.visited = visited;
        return this;
    }

    public int getInDegree() {
        return inDegree;
    }

    public Node setInDegree(int inDegree) {
        this.inDegree = inDegree;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        return name.equals(node.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Node that) {  // required by PriorityQueue in Dijkstra's algo
        if (that == null) return 1;
        return this.distance.compareTo(that.distance);
    }
}
