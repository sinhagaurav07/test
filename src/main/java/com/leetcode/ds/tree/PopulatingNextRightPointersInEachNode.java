package com.leetcode.ds.tree;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class PopulatingNextRightPointersInEachNode {

    public Node connect(Node root) {
        Queue<Node> traversalQueue = new LinkedList<>();
        if(Objects.isNull(root)){
            return root;
        }
        traversalQueue.add(root);
        int queueSize = 0;
        while(!traversalQueue.isEmpty()){
            queueSize = traversalQueue.size();
            for(int i=0; i< queueSize; i++){
                Node node = traversalQueue.remove();
                if(i<queueSize-1) {
                    node.next = traversalQueue.peek();
                }
                if(Objects.nonNull(node.left)){
                    traversalQueue.add(node.left);
                }
                if(Objects.nonNull(node.right)){
                    traversalQueue.add(node.right);
                }
            }
            queueSize =0;
        }

        return root;
    }

    public static void main(String[] args) {
        Node root = new Node(1, new Node(2, new Node(4, null, null,null), new Node(5, null, null, null), null), new Node(3, new Node(6, null, null, null), new Node(7, null,null,null), null), null);

        PopulatingNextRightPointersInEachNode obj = new PopulatingNextRightPointersInEachNode();
        System.out.println("-- Before --");
        printTree(root);
        root = obj.connect(root);
        System.out.println("-- After --");
        printTree(root);

    }

    private static void printTree(Node newRoot) {
        if(newRoot == null)
            return;


        printTree(newRoot.left);
        System.out.print("\t" + newRoot.val + "("+newRoot+")" + "("+newRoot.next+")");
        printTree(newRoot.right);
    }
}
class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
};