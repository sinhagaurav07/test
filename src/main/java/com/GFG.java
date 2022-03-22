package com;

import java.util.*;

// Java program to find minimum steps to reach to
// specific cell in minimum moves by Knight
class GFG {

    // Class for storing a cell's data
    static class cell {
        int x, y;
        int dis;

        public cell(int x, int y, int dis)
        {
            this.x = x;
            this.y = y;
            this.dis = dis;
        }
    }

    // Utility method returns true if (x, y) lies
    // inside Board
    static boolean isInside(int x, int y, int N)
    {
        if (x >= 0 && x < N && y >= 0 && y < N)
            return true;
        return false;
    }

    // Method returns minimum step
    // to reach target position
    static int minStepToReachTarget(
            int knightPos[], int targetPos[],
            int N)
    {
        // x and y direction, where a knight can move
        int dx[] = { -2, -1, 1, 2, -2, -1, 1, 2 };
        int dy[] = { -1, -2, -2, -1, 1, 2, 2, 1 };

        // queue for storing states of knight in board
        Vector<cell> q = new Vector<>();

        // push starting position of knight with 0 distance
        q.add(new cell(knightPos[0], knightPos[1], 0));

        cell t;
        int x, y;
        boolean visit[][] = new boolean[N][N];

        // make all cell unvisited
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                visit[i][j] = false;

        // visit starting state
        visit[knightPos[0]][knightPos[1]] = true;

        // loop until we have one element in queue
        while (!q.isEmpty()) {
            t = q.firstElement();
            q.remove(0);

            // if current cell is equal to target cell,
            // return its distance
            if (t.x == targetPos[0] && t.y == targetPos[1])
                return t.dis;

            // loop for all reachable states
            for (int i = 0; i < 8; i++) {
                x = t.x + dx[i];
                y = t.y + dy[i];

                // If reachable state is not yet visited and
                // inside board, push that state into queue
                if (isInside(x, y, N) && !visit[x][y]) {
                    visit[x][y] = true;
                    q.add(new cell(x, y, t.dis + 1));
                }
            }
        }
        return Integer.MAX_VALUE;
    }

    // Driver code
    public static void main(String[] args)
    {
        int N = 7;
        int knightPos[] = { 6, 6 };
        int targetPos[] = { 0, 1 };


       /* int N=6;
        int knightPos[] ={};
        int targetPos[] ={};
        */

        System.out.println(
                minStepToReachTarget(
                        knightPos, targetPos, N));
    }
}
