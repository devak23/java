package com.ak.learning.graphs.algorithms;

import com.ak.learning.graphs.Edge;
import com.ak.learning.graphs.GraphImpl;
import com.ak.learning.graphs.IGraph;
import com.ak.learning.graphs.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Bellman-Ford-Moore (BFM) is an improvement over Dijkstra's Algo, but fails if the graph has
 * a negative cycle. However, if there is no cycle detected, BFM will give you the shortest path
 * even when the weight of an edge is negative. Dijkstra's algorithm doesn't work when the edge
 * bears a negative weight. BFM is slower to execute than Dijkstra's as it iterates over all the
 * nodes N-1 times. There are multiple for-loops in its execution thereby making it O(n^2) operation.
 */
public class BellmanFord {
    // reference of the graph implementation
    private IGraph graph;

    public BellmanFord(IGraph graph) {
        this.graph = graph;
    }

    /**
     * BFM will run over all the 'N' nodes for N-1 times. During each iteration it's going to
     * 'relax' the edges. The understanding is by the N-1th iteration, all the edges have been
     * relaxed and we get the shortest path.
     *
     * However if you're able to relax any edge further, then it means that the graph has a
     * negative cycle and BFM cannot give you the shortest path. This is because you can go over
     * the negative path again and again and to get shorter paths.
     *
     * Now what is 'relaxing the edges' ? It simply means checking if there is any other distance
     * shorter than the current distance... something similar we did in Dijkstra's algorithm
     */
    public void execute() {
        int size = graph.getNodes().size();
        graph.getSourceNode().setDistance(0.d);

        for (int i = 0; i< size-1; i++) {
            for (Node v : graph.getNodes()) {
                for (Edge edge : v.getAdjEdges()) {
                    Node u = edge.getTarget();
                    Double computedTargetDistance = edge.getCost() + v.getDistance();
                    if (u.getDistance() > computedTargetDistance) { // this is relaxing.
                        u.setDistance(computedTargetDistance);
                        u.setPrevious(v);
                    }
                }
            }
        }
    }

    public List<Node> getShortestPath(Node target) {
        List<Node> path = new ArrayList<>();
        for (Node n = target; n != null; n = n.getPrevious()) {
            path.add(n);
        }
        Collections.reverse(path);
        return path;
    }

    /**
     * Figure out if there is any other edge that can be relaxed.
     * @return true if the graph has a negative cycle or false if otherwise.
     */
    public boolean hasNegativeCycle() {
        for (Edge edge : graph.getEdges()) {
            Node source = edge.getSource();
            Node target = edge.getTarget();

            if (source.getDistance() + edge.getCost() < target.getDistance()) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        Node S = new Node("S");
        Node B = new Node("B");
        Node A = new Node("A");
        Node C = new Node("C");
        Node D = new Node("D");

        IGraph graph = new GraphImpl();
        graph.connectNodes(S,A,true,4.0);
        graph.connectNodes(S,B,true, -8.0);

        graph.connectNodes(B,A,true,12.0);
        graph.connectNodes(B,C,true,3.0);
        graph.connectNodes(B,D,true,5.0);

        graph.connectNodes(A,B,true,2.0);
        graph.connectNodes(A,D,true,10.0);

        graph.connectNodes(D,C,true,9.0);
        graph.connectNodes(D,B,true,5.0);

        graph.connectNodes(C,B,true,5.0);
        graph.connectNodes(C,S,true,14.0);
        graph.connectNodes(C,A,true,4.0);

        Node source = S;
        graph.setSourceNode(source);

        Node target = D;

        BellmanFord bf = new BellmanFord(graph);
        bf.execute();
        if (!bf.hasNegativeCycle()) {
            List<Node> path = bf.getShortestPath(target);
            System.out.println("Distance of " + source + " to " + target + " is = " + target.getDistance());
            System.out.println("Shortest Path from " + source + " to " + target + " is = " + path);
        } else {
            System.out.println("Negative cycle detected in the graph. Cannot calcuate the shortest path via BellmanFord");
        }

    }
}
