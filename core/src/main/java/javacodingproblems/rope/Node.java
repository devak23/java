package javacodingproblems.rope;

public record Node(Node left, Node right, int weight, String str) {
    public Node(String str) {
        this(null, null, str.length(), str);
    }

    public Node(Node left, Node right, int weight) {
        this(left, right, weight, null);
    }

    public char indexAt(Node node, int index) {
        if (index > node.weight - 1) {
            return indexAt(node.right, index - node.weight);
        } else if (node.left !=null) {
            return indexAt(node.left, index);
        } else {
            return node.str.charAt(index);
        }
    }

}
