package com.ak.learning.graphs.algorithms;

import com.ak.learning.graphs.Edge;
import com.ak.learning.graphs.GraphImpl;
import com.ak.learning.graphs.IGraph;
import com.ak.learning.graphs.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Dijkstra {
    private IGraph graph;

    public Dijkstra(IGraph graph) {
        this.graph = graph;
    }

    /**
     * This is the way we compute paths between each of the nodes in the graph.
     * Note, the algo is very very similar to the BFS (breadth first search) algo
     * used to traverse a graph. In BFS, a normal Queue (LinkedList in Java) is
     * used. With Dijkstra, a PriorityQueue is used to pull out the nodes based
     * on their "priority". This priority comparison is implemented in the
     * <code>Node</code> class
     */
    public void execute() {
        PriorityQueue<Node> queue = new PriorityQueue<>();
        Node source = this.graph.getSourceNode();
        // we need to start somewhere. So we start at the source and say: Since we
        // are at source, the distance of source from source is zero! Doh!!
        source.setDistance(0.0d);
        queue.add(source);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            // poll will remove the element from the front of queue. Why not remove()?
            // remove() and poll() both remove from the front of the queue. remove()
            // throws an exception if queue is empty, but poll() returns you a null.
            // so why dont we need to check currentNode for null ?
            // Because if the current node is null, then it means that the queue is
            // empty. So the control will not flow inside the while loop. Note:
            // PriorityQueue does NOT allow null entries (interview question!)

            // for each adjacent edge to current node.
            for (Edge edge : current.getAdjEdges()) {
                // get the node at the end of the edge.
                Node target = edge.getTarget();

                // initial distance to this node from the currentNode was assumed to be
                // infinite (check the Node class)...however we know that's only the
                // starting condition. So now actually calculate the new distance to the
                // target node from the currentNode node
                Double newTargetDistance = current.getDistance() + edge.getCost();

                // infinite > finite (starting condition)
                if (target.getDistance() > newTargetDistance) {
                    // so now we have a new distance that's lesser than our original
                    // assumption/computation. This is a new path, so we need to modify
                    // the target node.
                    queue.remove(target);
                    target.setDistance(newTargetDistance);
                    target.setPrevious(current);
                    queue.add(target);
                    // Remember, In a queue - add() operation happens from behind the queue
                    // Its FIFO!
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

    public static void main(String[] args) {
        Node S = new Node("S");
        Node B = new Node("B");
        Node A = new Node("A");
        Node C = new Node("C");
        Node D = new Node("D");

        IGraph graph = new GraphImpl();
        graph.connectNodes(S,A,true,4.0);
        graph.connectNodes(S,B,true, 8.0);

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

        graph.bfs();

        Node target = D;


        Dijkstra dj = new Dijkstra(graph);
        dj.execute();
        List<Node> path = dj.getShortestPath(target);
        System.out.println("Distance of " + source + " to " + target + " is = " + target.getDistance());
        System.out.println("Shortest Path from " + source + " to " + target + " is = " + path);
    }
}
