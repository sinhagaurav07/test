package org.walmart.gs.backtracking;

import java.util.ArrayList;
import java.util.List;

public class NQueens {

    public List<List<String>> solveNQueens(int n) {
        String[][] board = new String[n][n];
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board.length; j++){
                board[i][j] = ".";
            }
        }

        if(!solveNQueensUtil(board, 0)){
            System.out.println("SolutionDelphix doesn't exist");
        }

        printSolution(board);
        //convert board into above format
        List<List<String>> results = new ArrayList<>();
        for(int i = 0; i < board.length; i++){
            List<String> columnValues = new ArrayList<>();
            for(int j = 0; j < board.length; j++){
                columnValues.add(board[i][j]);
            }
            results.add(columnValues);
        }
        return results;
    }

    private void printSolution(String board[][])
    {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++)
                System.out.print(" " + board[i][j]
                        + " ");
            System.out.println();
        }
    }
    private boolean solveNQueensUtil(String[][] board, int column){
        if(column >= board.length){
            return true;
        }
        for(int i = 0; i < board.length; i++){
            if(isSafe(board, i, column)){
                board[i][column] = "Q";
                if(solveNQueensUtil(board, column + 1)){
                    return true;
                }
                board[i][column] = ".";
            }
        }
        return false;
    }

    private boolean isSafe(String[][] board, int row, int column) {
        //check on the left hand side
        for (int i = 0; i < column; i++) {
            if ("Q".equals(board[row][i])) {
                return false;
            }
        }
        //check left upper diagonal
        for (int i = row, j = column; i >= 0 && j >= 0; i--, j--) {
            if ("Q".equals(board[i][j])) {
                return false;
            }
        }

        //Check lower left diagonal
        for (int i = row, j = column; i < board.length && j >= 0; i++, j--) {
            if ("Q".equals(board[i][j])) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        NQueens nQueens = new NQueens();
        nQueens.solveNQueens(4);
    }
}
