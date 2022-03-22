package org.walmart.gs;

import java.util.LinkedList;
import java.util.Queue;

public class ShortestPathInBinaryMatrix {
    int[][] directions = new int[][]{{1, -1}, {0, -1}, {-1, -1}, {1, 0}, {-1, 0}, {1, 1}, {0, 1}, {-1, 1}};

    public int shortestPathBinaryMatrix(int[][] grid) {

        if (grid == null || grid.length == 0)
            return 0;

        int rows = grid.length;
        int cols = grid[0].length;

        int shortestLen = 0;
        Queue<int[]> queue = new LinkedList<>() ;

        if (grid[0][0] == 1 || grid[rows - 1][cols - 1] == 1)
            return -1;

        queue.add(new int[]{0, 0});
        grid[0][0] = 1;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] pair = queue.remove();

                int row = pair[0];
                int col = pair[1];

                //if destination is reached
                if (row == rows - 1 && col == cols - 1)
                    return shortestLen + 1;

                //find 8 neighbors
                for (int[] direction : directions) {
                    int r = row + direction[0];
                    int c = col + direction[1];

                    if (r < rows && r >= 0 && c < cols && c >= 0 && grid[r][c] == 0) {
                        queue.add(new int[]{r, c});
                        grid[r][c] = 1;
                    }
                }
            }
            shortestLen++;
        }
        return -1;
    }

    public static void main(String[] args) {
        ShortestPathInBinaryMatrix shortestPathInBinaryMatrix = new ShortestPathInBinaryMatrix();
        int[][] grid = {{0,0,0},{1,1,0},{1,1,0}};
        shortestPathInBinaryMatrix.shortestPathBinaryMatrix(grid);
    }
}
