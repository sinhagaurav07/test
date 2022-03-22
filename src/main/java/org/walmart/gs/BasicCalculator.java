package org.walmart.gs;

import java.util.Stack;

public class BasicCalculator {

    public int calculate(String s) {
        if(s == null || s.length() == 0) {
            return 0;
        }
        s = s.replaceAll("\\s+", "");
        if(s.length() == 0) {
            return 0;
        }
        char[] chars = s.toCharArray();
        int firstNum = getNextNumber(chars, 0);

        Stack<Integer> stack = new Stack<>();
        stack.push(firstNum);

        int start = String.valueOf(firstNum).length();
        while(start < chars.length) {
            char sign = chars[start];
            int nextNum = getNextNumber(chars, ++start);
            if(sign == '+') {
                stack.push(nextNum);
            } else if(sign == '-') {
                stack.push(-nextNum);
            } else if(sign == '*') {
                stack.push(stack.pop() * nextNum);
            } else if(sign == '/') {
                stack.push(stack.pop() / nextNum);
            }
            start += String.valueOf(nextNum).length();
        }

        int res = 0;
        while(!stack.isEmpty()) {
            res += stack.pop();
        }
        return res;
    }

    private int getNextNumber(char[] chars, int start) {
        int num = 0;
        while(start < chars.length && Character.isDigit(chars[start])) {
            num = num * 10 + Character.getNumericValue(chars[start++]);
        }
        return num;
    }

    public static void main(String[] args) {
        BasicCalculator basicCalculator = new BasicCalculator();
        basicCalculator.calculate("33+2*2");
    }
}
