package org.walmart.gs;

import java.util.HashMap;
import java.util.Map;

public class ConstructTree {

    int preIdx = 0;
    int[] preOrder;
    int[] inOrder;
    Map<Integer, Integer> idxMap = new HashMap<>();

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        this.preOrder = preorder;
        this.inOrder = inorder;

        int idx = 0;
        for(Integer val : inorder) {
            idxMap.put(val, idx++);
        }
        return helper(0, inorder.length);
    }

    private TreeNode helper(int left, int right) {
        if(left >= right) return null;
        if(left == right) return null;

        int rootVal = preOrder[preIdx];
        TreeNode root = new TreeNode(rootVal);

        //root splits inorder list
        int index = idxMap.get(rootVal);
        preIdx++;
        root.left = helper(left, index);
        root.right = helper(index+1, right);
        return root;
    }

    public static void main(String[] args) {
        ConstructTree con = new ConstructTree();
        int[] in = new int[]{4, 8, 10, 12, 14, 20, 22};
        int[] level = new int[]{20, 8, 22, 4, 12, 10, 14};
        con.buildTree(in, level);
    }
}
