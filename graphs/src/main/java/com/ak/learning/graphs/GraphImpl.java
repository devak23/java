package com.ak.learning.graphs;

import java.util.*;

public class GraphImpl implements IGraph {
    /** A graph will have a source node */
    private Node sourceNode;
    /** and a collection of nodes */
    private Collection<Node> nodes = new ArrayList<>();
    /** and a collection of edges. This is used internally to navigate the nodes */
    private Collection<Edge> edges = new ArrayList<>();

    public GraphImpl() {
    }

    public IGraph addNode(Node node) {
        if (!nodes.contains(node)) {
            nodes.add(node);
        }
        return this;
    }

    @Override
    public Collection<Node> getNodes() {
        return nodes;
    }

    public GraphImpl(Node sourceNode) {
        this.setSourceNode(sourceNode);
    }

    public Node getSourceNode() {
        return sourceNode;
    }

    public void setSourceNode(Node sourceNode) {
        this.sourceNode = sourceNode;
    }

    public void connectNodes(Node source, Node target) {
        this.connectNodes(source, target,false,  1.0d);
    }

    public void connectNodes(Node source, Node target, boolean directed) {
        this.connectNodes(source, target, directed, 1.0);
    }

    public void connectNodes(Node source, Node target, boolean directed, double distance) {
        if (!nodes.contains(source)) {
            nodes.add(source);
        }
        if (!nodes.contains(target)) {
            nodes.add(target);
        }
        Edge sourceToTargetEdge = new Edge(source, target, distance);
        source.getAdjEdges().add(sourceToTargetEdge);
        edges.add(sourceToTargetEdge);
        if (!directed) {
            Edge targetToSourceEdge = new Edge(target, source, distance);
            target.getAdjEdges().add(targetToSourceEdge);
            edges.add(targetToSourceEdge);
        }
    }

    public void bfs() {
        System.out.println("----------------------BFS---------------------");
        Node source = this.getSourceNode();
        Queue<Node> ll = new LinkedList<Node>();
        System.out.println(source);
        source.setVisited(true);
        ll.add(source);
        while (!ll.isEmpty()) {
            Node current = ll.poll();
            Node child = null;
            while ((child = this.getUnvisitedChildNode(current)) != null) {
                child.setVisited(true);
                System.out.println(child);
                ll.add(child);
            }
        }
        clearNodes(sourceNode);
    }

    public Node getUnvisitedChildNode(Node node) {
        return node.getAdjEdges().stream()
                .filter(e -> !e.getTarget().isVisited())
                .findFirst().map(Edge::getTarget).orElse(null);

    }

    public void dfs() {
        System.out.println("----------------------DFS---------------------");
        Node source = this.getSourceNode();
        Stack<Node> stack = new Stack<>();
        source.setVisited(true);
        System.out.println(source);
        stack.push(source);
        while (!stack.isEmpty()) {
            Node current = stack.peek();
             Node child = this.getUnvisitedChildNode(current);
             if (child != null) {
                 child.setVisited(true);
                 System.out.println(child);
                 stack.push(child);
             } else {
                 stack.pop();
                 clearNodes(sourceNode);
             }

        }

        clearNodes(sourceNode);
    }

    /**
     * So we start by calculating the starting inDegree for each Vertex. Each Vertex with
     * inDegree == 0 is put into a queue. These Vertices are starting points for processing. We
     * begin the search from a Vertex in the queue, following the Edges and keeping a count of the
     * Vertices visited. We decrement the inDegree of Vertices as their in-bound Edges are processed.
     * Vertices whose inDegree goes to zero are put into the queue. When we reach the end of a branch
     * where there are no more out-bound Edges, we take another Vertex from the queue and traverse. If
     * we empty the queue before we’ve visited all the Vertices then we’ve found a cycle.
     *
     * The algorithm is a modified topological sort algo. It is based on a breadth-first search (BFS) stategy
     * – all sibling outEdges of a Vertex are processed before moving down to the next layer of Vertices.
     * It has a linear running time of O (|V| + |E|). In the worst case scenario there would be no cycle in the
     * graph and we’d visit each Vertex and each Edge.
     * Courtesy: https://tekjutsu.wordpress.com/2010/02/03/3/
     *
     * @return true if the graph has cycle or false otherwise.
     */
    @Override
    public boolean hasCycle() {
        if (nodes == null || nodes.isEmpty()) return false;
        Queue<Node> queue = new LinkedList<>();

        // initialize all the in-degrees to zero for source node and increment
        // for the nodes where there is an inbound edge
        nodes.forEach(node -> {
            node.getAdjEdges().forEach(edge -> {
                Node target = edge.getTarget();
                target.setInDegree(target.getInDegree() + 1);
            });
        });

        // find all the nodes where there is no in-bound edge and add it to the queue
        nodes.forEach(node -> {
            if (node.getInDegree() == 0) {
                queue.offer(node);
            }
        });

        if (queue.isEmpty()) {
            return true; // all nodes have in-bound edges
        }

        // traverse the queue counting the nodes visited
        int count = 0;
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            current.getAdjEdges().forEach(edge -> {
                Node target = edge.getTarget();
                target.setInDegree(target.getInDegree()-1);
                if (target.getInDegree() == 0) {
                    queue.offer(target);
                }
            });
            count++;
        }

        return count != nodes.size(); // a cycle is found
    }

    /**
     * THe number of ways to go from "node A" to "node B" in at maximum 'X' hops. Note this method
     * could be a part of the Node class itself. It's a design dilemma where to put this method
     * @param source - source node
     * @param destination - destination node
     * @param maxHops - the number of steps allowed to reach Node B from Node A
     * @return the number of paths
     */
    public int numWaysRecursive(Node source, Node destination, int maxHops) {
        if (maxHops <= 0) return 0;
        int matchFound = 0;
        for (Edge edge : source.getAdjEdges()) {
            if (edge.getTarget().equals(destination)) {
                matchFound++;
            }
            matchFound += numWaysRecursive(edge.getTarget(),destination, (int) (maxHops - edge.getCost()));
        }
        return matchFound;
    }

    /**
     * The number of ways to reach from node A to node B in 'X' steps should have a non-recursive solution.
     * Ideally, the DFS strategy should be able to do that. That's the attempt in the method below
     * //TODO: Need to fix the algo. The output isn't correct.
     *
     * @param source  - source node in question
     * @param target - target node in question
     * @param maxHops - number of hops (from a given node to its target)
     * @return number of ways in which you can go from node A to node B in X hops
     */
    public int numWaysLinear(Node source, Node target, int maxHops) {
        int matchFound = 0;
        Stack<Node> stack = new Stack<>();
        stack.push(source);
        while (!stack.isEmpty()) {
            Node current = stack.pop();
            if (current.getDistance() < maxHops) {
                for (Edge edge : current.getAdjEdges()) {
                    if (edge.getTarget().equals(target)) {
                        matchFound++;
                    }
                    current.setDistance(current.getDistance() + edge.getCost());
                    stack.push(edge.getTarget());
                }
            }
        }

        return matchFound;
    }

    @Override
    public Collection<Edge> getEdges() {
        return edges;
    }

    private void clearNodes(Node node) {
        nodes.forEach(n -> n.setVisited(false));
    }
}
