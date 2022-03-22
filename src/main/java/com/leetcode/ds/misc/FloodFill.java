package com.leetcode.ds.misc;

import java.util.LinkedList;
import java.util.Queue;

/**
 * An image is represented by an m x n integer grid image where image[i][j] represents the pixel value of the image.
 *
 * You are also given three integers sr, sc, and newColor. You should perform a flood fill on the image starting from the pixel image[sr][sc].
 *
 * To perform a flood fill, consider the starting pixel, plus any pixels connected 4-directionally to the starting pixel of the same color as the starting pixel, plus any pixels connected 4-directionally to those pixels (also with the same color), and so on. Replace the color of all of the aforementioned pixels with newColor.
 *
 * Return the modified image after performing the flood fill.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: image = [[1,1,1],[1,1,0],[1,0,1]], sr = 1, sc = 1, newColor = 2
 * Output: [[2,2,2],[2,2,0],[2,0,1]]
 * Explanation: From the center of the image with position (sr, sc) = (1, 1) (i.e., the red pixel), all pixels connected by a path of the same color as the starting pixel (i.e., the blue pixels) are colored with the new color.
 * Note the bottom corner is not colored 2, because it is not 4-directionally connected to the starting pixel.
 * Example 2:
 *
 * Input: image = [[0,0,0],[0,0,0]], sr = 0, sc = 0, newColor = 2
 * Output: [[2,2,2],[2,2,2]]
 *
 *
 * Constraints:
 *
 * m == image.length
 * n == image[i].length
 * 1 <= m, n <= 50
 * 0 <= image[i][j], newColor < 216
 * 0 <= sr < m
 * 0 <= sc < n
 */
public class FloodFill {
    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        Queue<Cell> q = new LinkedList<>();
        q.add(new Cell(sr,sc,image));
        int originalColor = image[sr][sc];
        while (!q.isEmpty()) {
            Cell c = q.poll();
            if(isValid(c.getX()-1, c.getY(), image) && image[c.getX()-1][c.getY()]==originalColor && image[c.getX()-1][c.getY()] != newColor){
                q.add(new Cell(c.getX()-1, c.getY(), image));
            }
            if(isValid(c.getX()+1, c.getY(), image) && image[c.getX()+1][c.getY()]==originalColor && image[c.getX()+1][c.getY()] != newColor){
                q.add(new Cell(c.getX()+1, c.getY(), image));
            }
            if(isValid(c.getX(), c.getY()-1, image) && image[c.getX()][c.getY()-1]==originalColor && image[c.getX()][c.getY()-1] != newColor){
                q.add(new Cell(c.getX(), c.getY()-1, image));
            }
            if(isValid(c.getX(), c.getY()+1, image) && image[c.getX()][c.getY()+1]==originalColor && image[c.getX()][c.getY()+1] != newColor){
                q.add(new Cell(c.getX(), c.getY()+1, image));
            }
            c.updateColor(newColor);
        }
        return image;
    }
    boolean isValid(int x, int y, int[][] image){
        return (x>=0 && x<image.length && y>=0 && y<image[0].length);
    }
    class Cell{
        int x;
        int y;
        int[][] image;

        public Cell(int x, int y, int[][] image){
            this.x=x;
            this.y=y;
            this.image=image;
        }

        public void updateColor(int newColor){
            image[x][y] = newColor;
        }
        public int getX() {
            return x;
        }
        public int getY() {
            return y;
        }
    }

    public static void main(String[] args) {
        //int[][] image = {{1,1,1},{1,1,0},{1,0,1}};
        int[][] image = {{0,0,0},{0,1,1}};
        FloodFill f = new FloodFill();

        System.out.println("Before -");
        print(image);

        //f.floodFill(image, 1,1,2);
        f.floodFill(image, 1,1,1);

        System.out.println("After -");
        print(image);
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
