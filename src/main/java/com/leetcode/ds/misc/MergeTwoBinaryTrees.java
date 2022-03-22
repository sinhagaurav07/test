package com.leetcode.ds.misc;

/**
 * You are given two binary trees root1 and root2.
 *
 * Imagine that when you put one of them to cover the other, some nodes of the two trees are overlapped while the others are not. You need to merge the two trees into a new binary tree. The merge rule is that if two nodes overlap, then sum node values up as the new value of the merged node. Otherwise, the NOT null node will be used as the node of the new tree.
 *
 * Return the merged tree.
 *
 * Note: The merging process must start from the root nodes of both trees.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: root1 = [1,3,2,5], root2 = [2,1,3,null,4,null,7]
 * Output: [3,4,5,5,4,null,7]
 * Example 2:
 *
 * Input: root1 = [1], root2 = [1,2]
 * Output: [2,2]
 *
 *
 * Constraints:
 *
 * The number of nodes in both trees is in the range [0, 2000].
 * -104 <= Node.val <= 104
 */
public class MergeTwoBinaryTrees {
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if(root1 == null && root2 == null){
            return null;
        }
        if(root1 == null){
            return root2;
        }
        if(root2 == null){
            return root1;
        }

        root1.val += root2.val;
        root1.left = mergeTrees(root1.left, root2.left);
        root1.right = mergeTrees(root1.right, root2.right);
        return root1;
    }

    public static void main(String[] args) {
        TreeNode root1 = new TreeNode(1, new TreeNode(3,new TreeNode(5,null, null),null), new TreeNode(2, null, null));
        TreeNode root2 = new TreeNode(2, new TreeNode(1,null,new TreeNode(4, null,null)), new TreeNode(3, null, new TreeNode(7,null,null)));

        MergeTwoBinaryTrees obj = new MergeTwoBinaryTrees();
        System.out.println("Tree 1 -");
        printTree(root1);

        System.out.println("\nTree 2 -");
        printTree(root2);

        TreeNode newRoot = obj.mergeTrees(root1, root2);

        System.out.println("\nMerged Tree -");
        printTree(newRoot);

    }

    private static void printTree(TreeNode newRoot) {
        if(newRoot == null)
            return;


        printTree(newRoot.left);
        System.out.print("\t" + newRoot.val);
        printTree(newRoot.right);
    }
}

class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode() {}
      TreeNode(int val) { this.val = val; }
      TreeNode(int val, TreeNode left, TreeNode right) {
          this.val = val;
          this.left = left;
          this.right = right;
      }
  }