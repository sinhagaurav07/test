package org.walmart.gs;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

public class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        return rSerialize(root, "");
    }

    private String rSerialize(TreeNode node, String s){
        if(node == null){
            s += "null,";
        } else {
            s += String.valueOf(node.val) + ",";
            s = rSerialize(node.left, s);
            s = rSerialize(node.right, s);
        }
        return s;
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        String[] s = data.split(",");
        LinkedList<String> list =  new LinkedList<>(Arrays.asList(s));
        return rDeserialize(list);
    }

    private TreeNode rDeserialize(List<String> l){
        if(l != null && l.get(0).equals("null")){
            l.remove(0);
            return null;
        }
        TreeNode node = new TreeNode(Integer.parseInt(l.get(0)));
        l.remove(0);
        node.left = rDeserialize(l);
        node.right = rDeserialize(l);
        return node;
    }

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(2);
        treeNode.left = new TreeNode(1);
        treeNode.right = new TreeNode(4);
        treeNode.right.left = new TreeNode(3);
        treeNode.right.right = new TreeNode(5);
        Codec codec = new Codec();
        String serialize = codec.serialize(treeNode);
        TreeNode deserialize = codec.deserialize(serialize);
        System.out.println("completed");

        LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add(2);
        linkedHashSet.add(3);
        linkedHashSet.add(4);

        if(linkedHashSet.contains(2)){
            linkedHashSet.remove(2);
        }
        System.out.println(linkedHashSet.iterator().next());
    }
}