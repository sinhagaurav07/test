package com.leetcode.ds.misc;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Write a function to generate all possible n pairs of balanced parentheses.
 *
 * Examples:
 *
 * Input: n=1
 * Output: {}
 * Explanation: This the only sequence of balanced
 * parenthesis formed using 1 pair of balanced parenthesis.
 *
 * Input : n=2
 * Output:
 * {}{}
 * {{}}
 * Explanation: This the only two sequences of balanced
 * parenthesis formed using 2 pair of balanced parenthesis.
 *
 */
public class AllCombinationsOfBalancedParentheses {

    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        generate("", 0, 0, n, res);
        return res;
    }

    private void generate(String s, int openCount, int closeCount, int n, List<String> result){
        if(closeCount==n){
            result.add(s);
        }

        if(openCount < n){
            generate(s+"{", openCount+1, closeCount, n, result);
        }
        if(openCount > closeCount) {
            generate(s + "}", openCount, closeCount + 1, n, result);
        }
    }

    @Test
    public void test1(){
        Assert.assertEquals("success", 5, generateParenthesis(3).size());
    }
}
