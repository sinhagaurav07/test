package com.leetcode.ds.feb;

import java.util.Stack;

/**
 * Given a balanced parentheses string s, return the score of the string.
 *
 * The score of a balanced parentheses string is based on the following rule:
 *
 * "()" has score 1.
 * AB has score A + B, where A and B are balanced parentheses strings.
 * (A) has score 2 * A, where A is a balanced parentheses string.
 *
 *
 * Example 1:
 *
 * Input: s = "()"
 * Output: 1
 * Example 2:
 *
 * Input: s = "(())"
 * Output: 2
 * Example 3:
 *
 * Input: s = "()()"
 * Output: 2
 *
 *
 * Constraints:
 *
 * 2 <= s.length <= 50
 * s consists of only '(' and ')'.
 * s is a balanced parentheses string.
 */
public class Day17ScoreOfParentheses {
    public static int scoreOfParentheses(String s) {
        Stack<Integer> stack = new Stack<>();
        int score = 0;
        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if(c == '('){
                stack.push(score);
                score = 0;
            }
            else {
                score = stack.pop() + Math.max(2 * score, 1);
            }
        }
        return score;
    }

    public static void main(String[] args) {
        System.out.println(scoreOfParentheses("()"));
        System.out.println(scoreOfParentheses("(())"));
        System.out.println(scoreOfParentheses("()()"));
    }
}
