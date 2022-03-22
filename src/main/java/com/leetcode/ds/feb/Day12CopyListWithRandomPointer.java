package com.leetcode.ds.feb;

import java.util.HashMap;
import java.util.Map;

/**
 * A linked list of length n is given such that each node contains an additional random pointer, which could point to any node in the list, or null.
 *
 * Construct a deep copy of the list. The deep copy should consist of exactly n brand new nodes, where each new node has its value set to the value of its corresponding original node. Both the next and random pointer of the new nodes should point to new nodes in the copied list such that the pointers in the original list and copied list represent the same list state. None of the pointers in the new list should point to nodes in the original list.
 *
 * For example, if there are two nodes X and Y in the original list, where X.random --> Y, then for the corresponding two nodes x and y in the copied list, x.random --> y.
 *
 * Return the head of the copied linked list.
 *
 * The linked list is represented in the input/output as a list of n nodes. Each node is represented as a pair of [val, random_index] where:
 *
 * val: an integer representing Node.val
 * random_index: the index of the node (range from 0 to n-1) that the random pointer points to, or null if it does not point to any node.
 * Your code will only be given the head of the original linked list.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
 * Output: [[7,null],[13,0],[11,4],[10,2],[1,0]]
 * Example 2:
 *
 *
 * Input: head = [[1,1],[2,1]]
 * Output: [[1,1],[2,1]]
 * Example 3:
 *
 *
 *
 * Input: head = [[3,null],[3,0],[3,null]]
 * Output: [[3,null],[3,0],[3,null]]
 *
 *
 * Constraints:
 *
 * 0 <= n <= 1000
 * -104 <= Node.val <= 104
 * Node.random is null or is pointing to some node in the linked list.
 *
 */
public class Day12CopyListWithRandomPointer {
    public static Node copyRandomList(Node head) {

        if(head == null){
            return head;
        }

        Map<Node, Node> randomPointerMap = new HashMap<>();
        //create copy of all nodes in original list
        for(Node ptr=head; ptr!=null; ptr=ptr.next){
            randomPointerMap.put(ptr, new Node(ptr.val));
        }

        //setup new list pointer
        for(Node ptr = head; ptr!= null; ptr=ptr.next){
            Node newNode = randomPointerMap.get(ptr);
            newNode.next = randomPointerMap.get(ptr.next);
            newNode.random = randomPointerMap.get(ptr.random);
            randomPointerMap.put(ptr, newNode);
        }
        return randomPointerMap.get(head);
    }

    public static void main(String[] args) {
        Node head, node1, node2, node3, node4, node5;
        node5 = new Node(1, null);
        node4 = new Node(10, node5);
        node3 = new Node(11, node4);
        node2 = new Node(13, node3);
        node1 = new Node(7, node2);

        head = node1;
        node2.setRandom(node1);
        node3.setRandom(node5);
        node4.setRandom(node3);
        node5.setRandom(node1);

        for (Node ptr=head; ptr != null ; ptr=ptr.next){
            System.out.print("->" + ptr + "(" + ptr.random + ")");
        }
        System.out.println();
        Node newHead = copyRandomList(head);
        for (Node ptr=newHead; ptr != null ; ptr=ptr.next){
            System.out.print("->" + ptr + "(" + ptr.random + ")");
        }
    }

}
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
    public Node(int val, Node next) {
        this.val = val;
        this.next = next;
        this.random = random;
    }
    public void setRandom(Node random){
        this.random = random;
    }
}