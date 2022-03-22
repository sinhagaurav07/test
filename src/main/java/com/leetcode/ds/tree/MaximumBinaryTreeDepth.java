package com.leetcode.ds.tree;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * Given the root of a binary tree, return its maximum depth.
 *
 * A binary tree's maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.
 *
 * Example 1:
 *
 *
 * Input: root = [3,9,20,null,null,15,7]
 * Output: 3
 * Example 2:
 *
 * Input: root = [1,null,2]
 * Output: 2
 *
 *
 * Constraints:
 *
 * The number of nodes in the tree is in the range [0, 104].
 * -100 <= Node.val <= 100
 */
public class MaximumBinaryTreeDepth {
    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     *     int val;
     *     TreeNode left;
     *     TreeNode right;
     *     TreeNode() {}
     *     TreeNode(int val) { this.val = val; }
     *     TreeNode(int val, TreeNode left, TreeNode right) {
     *         this.val = val;
     *         this.left = left;
     *         this.right = right;
     *     }
     * }
     */

    public int maxDepth(TreeNode root) {
        Queue<TreeNode> traversalQueue = new LinkedList<>();
        if(Objects.isNull(root)){
            return 0;
        }
        traversalQueue.offer(root);

        int level=0;
        int queueSize =0;
        while(!traversalQueue.isEmpty()){
            queueSize = traversalQueue.size();
            for(int i=0; i< queueSize; i++){
                TreeNode node = traversalQueue.poll();

                if(Objects.nonNull(node.left)){
                    traversalQueue.add(node.left);
                }
                if(Objects.nonNull(node.right)){
                    traversalQueue.add(node.right);
                }
            }
                level++;

        }
        return level;
    }

    public class TreeNode {
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

      @Test
      public void test1(){
        TreeNode root = new TreeNode(0);
        root.left = new TreeNode(2);
        root.right = new TreeNode(4);
        root.left.left=new TreeNode(1);
        root.right.left = new TreeNode(3);
        root.right.right = new TreeNode(-1);
        root.left.left.left= new TreeNode(5);
        root.left.left.right = new TreeNode(1);
        root.right.left.right = new TreeNode(6);
        root.right.right.right = new TreeNode(8);

          Assert.assertEquals("Success", 4, maxDepth(root));

      }

    @Test
    public void test2(){
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);

        Assert.assertEquals("Success", 3, maxDepth(root));

    }

    @Test
    public void test3(){
        TreeNode root = new TreeNode(1);


        Assert.assertEquals("Success", 1, maxDepth(root));

    }

    @Test
    public void test4(){
        Assert.assertEquals("Success", 0, maxDepth(null));

    }
}
