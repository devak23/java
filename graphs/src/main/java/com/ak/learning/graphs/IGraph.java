package com.ak.learning.graphs;

import java.util.Collection;

public interface IGraph {

    void connectNodes(Node source, Node target, boolean directed);

    IGraph addNode(Node node);

    int numWaysRecursive(Node source, Node target, int maxHops);

    int numWaysLinear(Node source, Node target, int maxHops);

    void connectNodes(Node source, Node target);

    void connectNodes(Node source, Node target, boolean directed, double cost);

    void bfs();

    void dfs();

    boolean hasCycle();

    Collection<Node> getNodes();

    Node getSourceNode();

    void setSourceNode(Node source);

    Collection<Edge> getEdges();
}
