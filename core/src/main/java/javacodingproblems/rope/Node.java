package javacodingproblems.rope;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


// When we need to handle large amounts of text (for instance, developing a text editor or a powerful text search engine
// we have to deal with a significant number of complex tasks. Among these tasks, we have to consider appending/
// concatenating strings and memory consumption.
// The Rope data structure is a special binary tree that aims to improve string operations while using memory
// efficiently (which is especially useful for large strings). Big O goals of this data structure are as follows:
// Index:   O(log n)    String: O(1)
// Insert:  O(log n)    String: O(n)
// Delete:  O(log n)    String: O(n)
// Concat:  O(log n)    String: O(n)
// Split:   O(log n)    String: O(n)

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

// Being a binary tree, a Rope can be shaped via the classical Node class
public class Node {
    private Node left;
    private Node right;
    private int weight;
    private String str;

    // Leaf nodes store small chunks of the large string

    public Node(String str) {
        this(null, null, str.length(), null);
    }

    // Next, let’s implement the main operations of a Rope, starting with searching by index. The indexAt(node, index)
    // method attempts to find the character at the given index. This is a recursive process based on a simple rule:
    // If index > (weight - 1) then index = index - weight and move to the right node.
    // If index < weight then just move to the left node.
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
