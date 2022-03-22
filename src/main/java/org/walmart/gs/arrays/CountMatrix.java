package org.walmart.gs.arrays;

public class CountMatrix {

    public int countSquares(int[][] matrix) {
        int n = matrix.length;
        if(n <= 0) return 0;
        int m = matrix[0].length;
        if(m <= 0) return 0;
        int[][] dp = new int[n+1][m+1];
        int count = 0;
        for(int i = 1; i <= n; i++) {
            for(int j = 1; j <= m; j++) {
                if(matrix[i-1][j-1] == 1) {
                    int edgeSize = Math.min(dp[i-1][j-1],
                            Math.min(dp[i-1][j], dp[i][j-1])) + 1;
                    count += edgeSize - 1 + 1;
                    dp[i][j] = edgeSize;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        int[][] matrix = {{0, 1, 1, 1}, {1, 1, 1, 1}, {0, 1, 1, 1}};
        CountMatrix countMatrix = new CountMatrix();
        countMatrix.countSquares(matrix);

    }
}
