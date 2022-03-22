package org.walmart.gs;

import java.util.Stack;

public class InfixTree {

    private class TreeNode {
        char val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
            this.val = ' ';
        }

        TreeNode(char val) {
            this.val = val;
        }

        TreeNode(char val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public TreeNode expTree(String s) {
        s = '(' + s + ')';
        Stack<TreeNode> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (!Character.isDigit(c) && c != '(') {
                TreeNode right = stack.pop();
                if (c == '*' || c == '/') {
                    if (stack.peek().val == '*' || stack.peek().val == '/') {
                        TreeNode ops = stack.pop();
                        TreeNode left = stack.pop();
                        ops.left = left;
                        ops.right = right;
                        right = ops;
                    }
                } else {
                    while (stack.peek().val != '(') {
                        TreeNode ops = stack.pop();
                        TreeNode left = stack.pop();
                        ops.left = left;
                        ops.right = right;
                        right = ops;
                    }
                    if (c == ')') {
                        stack.pop();
                    }
                }
                stack.push(right);
            }

            if (c != ')') {
                stack.push(new TreeNode(c));
            }
        }
        return stack.pop();
    }

    public static void main(String[] args) {
        String s = "2+3*5"; //"2-3/(5*2)+1";
        InfixTree tree = new InfixTree();
        TreeNode node = tree.expTree(s);
        System.out.println(node);
    }
}
