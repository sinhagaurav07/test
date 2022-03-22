package org.walmart.gs.backtracking;

public class RatInAMaze {

    private int numberOfCells;

    public RatInAMaze(int numberOfCells) {
        this.numberOfCells = numberOfCells;
    }

    public boolean solveMaze(int maze[][]) {
        int sol[][] = new int[numberOfCells][numberOfCells];

        if (!solveMazeUtil(maze, 0, 0, sol)) {
            System.out.print("SolutionDelphix doesn't exist");
            return false;
        }
        printSolution(sol);
        return true;
    }

    void printSolution(int sol[][]) {
        for (int i = 0; i < numberOfCells; i++) {
            for (int j = 0; j < numberOfCells; j++)
                System.out.print(" " + sol[i][j] + " ");
            System.out.println();
        }
    }

    private boolean solveMazeUtil(int[][] maze, int x, int y, int[][] solution) {
        if (x == numberOfCells - 1 && y == numberOfCells - 1 && maze[x][y] == 1) {
            solution[x][y] = 1;
            return true;
        }
        if (isSafe(x, y, maze)) {
            solution[x][y] = 1;
            if (solveMazeUtil(maze, x + 1, y, solution)) {
                return true;
            }
            if (solveMazeUtil(maze, x, y + 1, solution)) {
                return true;
            }
            solution[x][y] = 0;
            return false;
        }
        return false;
    }

    private boolean isSafe(int x, int y, int[][] maze) {
        return (x >= 0 && x < numberOfCells && y >= 0 && y < numberOfCells && maze[x][y] == 1) ? true : false;
    }

    public static void main(String[] args) {
        int maze[][] = {{1, 0, 0, 0},
                {1, 1, 0, 1},
                {0, 1, 0, 0},
                {1, 1, 1, 1}};
        RatInAMaze rat = new RatInAMaze(maze.length);
        rat.solveMaze(maze);
    }
}
