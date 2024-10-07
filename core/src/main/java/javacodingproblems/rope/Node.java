package javacodingproblems.rope;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Node {
    private Node left;
    private Node right;
    private int weight;
    private String str;


    public Node(String str) {
        this(null, null, str.length(), null);
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
