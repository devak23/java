package com.ak.learning.graphs;

/**
 * This is a very simple Pojo which has no intelligence at all.
 */
public class Edge {
    // Every edge has a node to its end, or else it wouldn't be in a network!
    private Node target;
    // It also has a source Node
    private Node source;
    // and there is a cost associated by navigating from source to target Borivali and
    // Bandra can be considered as two nodes and there are 2 roads connecting them. One
    // via highway and one S.V. Road. The "cost" of both the paths are different.
    // This is where the problem becomes tricky. The highway could be a faster
    // route (less time) but longer distance whereas, S.V. Road would be a shorter
    // distance but a slower route (more time). Hence this parameter could vary
    // with different problem definition. Or there could be more than one costs!
    private Double cost;

    public Edge(Node source, Node target, Double cost) {
        this.source = source;
        this.target = target;
        this.cost = cost;
    }

    public Node getTarget() {
        return target;
    }

    public Edge setTarget(Node target) {
        this.target = target;
        return this;
    }

    public Double getCost() {
        return cost;
    }

    public Edge setCost(Double cost) {
        this.cost = cost;
        return this;
    }

    public Node getSource() {
        return source;
    }

    public Edge setSource(Node source) {
        this.source = source;
        return this;
    }

    @Override
    public String toString() {
        return source + " ---> " + target;
    }
}
