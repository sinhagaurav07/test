package com.leetcode.ds.misc;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * You are given an m x n binary matrix grid. An island is a group of 1's (representing land) connected 4-directionally (horizontal or vertical.) You may assume all four edges of the grid are surrounded by water.
 *
 * The area of an island is the number of cells with a value 1 in the island.
 *
 * Return the maximum area of an island in grid. If there is no island, return 0.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: grid = [[0,0,1,0,0,0,0,1,0,0,0,0,0],[0,0,0,0,0,0,0,1,1,1,0,0,0],[0,1,1,0,1,0,0,0,0,0,0,0,0],[0,1,0,0,1,1,0,0,1,0,1,0,0],[0,1,0,0,1,1,0,0,1,1,1,0,0],[0,0,0,0,0,0,0,0,0,0,1,0,0],[0,0,0,0,0,0,0,1,1,1,0,0,0],[0,0,0,0,0,0,0,1,1,0,0,0,0]]
 * Output: 6
 * Explanation: The answer is not 11, because the island must be connected 4-directionally.
 * Example 2:
 *
 * Input: grid = [[0,0,0,0,0,0,0,0]]
 * Output: 0
 *
 *
 * Constraints:
 *
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 50
 * grid[i][j] is either 0 or 1.
 */
public class MaxAreaofIsland {
    public static int count = 0;
    public int maxAreaOfIsland(int[][] grid) {
        int maxArea = 0;
        int area=0;
        if(grid.length<=0){
            return 0;
        }
        boolean[][] visited = new boolean[grid.length][grid[0].length];

        for(boolean[] array : visited){
            Arrays.fill(array, false);
        }

        for(int i=0;i<grid.length;i++){
            for(int j=0; j< grid[0].length;j++){
                if(grid[i][j] == 1 && !visited[i][j]){
                    area = getIslandArea(i, j, grid, visited);
                    count++;
                    if(area > maxArea){
                        maxArea =area;
                    }
                }
            }
        }
        return maxArea;
    }

    private int getIslandArea(int i, int j, int[][] grid, boolean[][] visited) {
        Queue<Cell> q = new LinkedList<>();
        q.add(new Cell(i,j,grid));
        visited[i][j] = true;
        int size =0;
        while(!q.isEmpty()){
            Cell c = q.poll();

            if(isValid(c.getX()-1, c.getY(), grid, visited) && grid[c.getX()-1][c.getY()]==1){
                q.add(new Cell(c.getX()-1, c.getY(), grid));
                visited[c.getX()-1][c.getY()] = true;
            }
            if(isValid(c.getX()+1, c.getY(), grid, visited) && grid[c.getX()+1][c.getY()]==1){
                q.add(new Cell(c.getX()+1, c.getY(), grid));
                visited[c.getX()+1][c.getY()] = true;
            }
            if(isValid(c.getX(), c.getY()-1, grid, visited) && grid[c.getX()][c.getY()-1]==1){
                q.add(new Cell(c.getX(), c.getY()-1, grid));
                visited[c.getX()][c.getY()-1] = true;
            }
            if(isValid(c.getX(), c.getY()+1, grid, visited) && grid[c.getX()][c.getY()+1]==1){
                q.add(new Cell(c.getX(), c.getY()+1, grid));
                visited[c.getX()][c.getY()+1] = true;
            }
            size++;
        }
        return size;
    }


    boolean isValid(int x, int y, int[][] grid, boolean[][] visited){
        return (x>=0 && x<grid.length && y>=0 && y<grid[0].length && !visited[x][y]);
    }
    class Cell{
        int x;
        int y;
        int[][] grid;

        public Cell(int x, int y, int[][] image){
            this.x=x;
            this.y=y;
            this.grid=image;
        }

        public void updateColor(int newColor){
            grid[x][y] = newColor;
        }
        public int getX() {
            return x;
        }
        public int getY() {
            return y;
        }
    }

    public static void main(String[] args) {
        //int[][] grid = {{0,0,1,0,0,0,0,1,0,0,0,0,0},{0,0,0,0,0,0,0,1,1,1,0,0,0},{0,1,1,0,1,0,0,0,0,0,0,0,0},{0,1,0,0,1,1,0,0,1,0,1,0,0},{0,1,0,0,1,1,0,0,1,1,1,0,0},{0,0,0,0,0,0,0,0,0,0,1,0,0},{0,0,0,0,0,0,0,1,1,1,0,0,0},{0,0,0,0,0,0,0,1,1,0,0,0,0}};
        //int[][] grid = {{0,0,0,0,0,0,0,0}};
        int[][] grid = {{1,1,0,0,0},{1,1,0,0,0},{0,0,0,1,1},{0,0,0,1,1}};
        MaxAreaofIsland m = new MaxAreaofIsland();

        //f.floodFill(image, 1,1,2);
        int max = m.maxAreaOfIsland(grid);
        print(grid);

        System.out.println("Max Area = "+max + "\t Island Count = " + count);

    }
    private static void print(int[][] image) {
        for(int[] i: image){
            for(int j:i){
                System.out.print("\t" + j);
            }
            System.out.println();
        }
    }
}
