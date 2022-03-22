package com.leetcode.ds.misc;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/** Compute the value of an expression in Reverse Polish Notation. Supported operators are "+", "-", "*" and "/".
 * Reverse Polish is a postfix mathematical notation in which each operator immediately follows its operands.
 * Each operand may be a number or another expression.
 * For example, 3 + 4 in Reverse Polish is 3 4 + and 2 * (4 + 1) would be written as 4 1 + 2 * or 2 4 1 + *
 *
 * @param ops a sequence of numbers and operators, in Reverse Polish Notation
 * @return the result of the computation
 * @throws IllegalArgumentException ops don't represent a well-formed RPN expression
 * @throws ArithmeticException the computation generates an arithmetic error, such as dividing by zero
 *
 * Some sample ops and their results:
 * ["4", "1", "+", "2.5", "*"] -> ((4 + 1) * 2.5) -> 12.5
 * ["5", "80", "40", "/", "+"] -> (5 + (80 / 40)) ->  7
 * ["4", "5", "-" ]
 * ["4"]
 * ["-"]
 * ["4", "-"]
 * ["4", "1", "+", "5"]
 */

public class ReversePolishNotationCalculator {

    public double rpn (List<String> ops) throws IllegalArgumentException, ArithmeticException
    {
        List<String> operatorList = new ArrayList<>();
        operatorList.add("+");operatorList.add("-");operatorList.add("*");operatorList.add("/");

        Stack<Double> operandStack = new Stack<>();

        //Implementation here
        if(CollectionUtils.isEmpty(ops)){
            throw new IllegalArgumentException("Ops list cannot be empty !");
        }

        String token = null;
        double operand1=0;
        double operand2=0;
        double res =0;

        for(int i=0; i< ops.size();i++){
            token = ops.get(i);

            //Operator use-case
            if(operatorList.contains(token)){
                if(operandStack.size() < 2){
                    throw new IllegalArgumentException("Unbalanced RPN !");
                }

                operand2 = operandStack.pop();
                operand1 = operandStack.pop();
                res = calculate(operand1, operand2, token);
                operandStack.push(res);
            }else {
                //operand use-case
                try {
                    operand1 = Double.parseDouble(token);
                    operandStack.push(operand1);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Invalid RPN !");
                }
            }
        }

        if(operandStack.size() > 1){
            throw new IllegalArgumentException("Invalid RPN !");
        }
        System.out.println("output=" + operandStack.peek());
        return operandStack.pop();
    }

    private double calculate(double op1, double op2, String operator) throws ArithmeticException{
        try{
            if("+".equals(operator)){
                return op1+op2;
            }else if("-".equals(operator)){
                return op1-op2;
            }else if("*".equals(operator)){
                return op1*op2;
            }else {
                return op1/op2;
            }
        }catch(ArithmeticException e){
            throw e;
        }
    }

    @Test
    public void testRPN1(){
        List<String> ops = new ArrayList<>();
        ops.add("4");ops.add("1");ops.add("+");ops.add("2.5");ops.add("*");
        rpn(ops);
        //Assert.assertEquals("Success", 12.5, rpn(ops));
    }

    @Test
    public void testRPN2(){
        List<String> ops = new ArrayList<>();
        ops.add("5");ops.add("80");ops.add("40");ops.add("/");ops.add("+");
        rpn(ops);
        //Assert.assertEquals("Success", 7, rpn(ops));
    }

    @Test
    public void testRPN3(){
        List<String> ops = new ArrayList<>();
        ops.add("4");ops.add("5");ops.add("-");
        rpn(ops);
        //Assert.assertEquals("Success", 7, rpn(ops));
    }

    @Test
    public void testRPN4(){
        List<String> ops = new ArrayList<>();
        ops.add("4");ops.add("1");ops.add("+");ops.add("5");
        rpn(ops);
        //Assert.assertEquals("Success", 7, rpn(ops));
    }

    /* ["5", "80", "40", "/", "+"] -> (5 + (80 / 40)) ->  7
            * ["4", "5", "-" ]
            * ["4"]
            * ["-"]
            * ["4", "-"]
            * ["4", "1", "+", "5"]
    */

}
