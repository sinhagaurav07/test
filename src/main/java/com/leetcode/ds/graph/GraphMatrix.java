package com.leetcode.ds.graph;

import java.util.Arrays;

public class GraphMatrix {

    int[][] graph;

    public static void main(String[] args) {
        // Creating a graph with 5 vertices
        int V = 5;
        GraphMatrix graph = new GraphMatrix(V);

        // Adding edges one by one
        graph.addEdge(0, 1);
        graph.addEdge(0, 4);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);

        graph.printGraphMatrix();
        System.out.println();System.out.println();
        graph.printGraphEdges();
    }
    public GraphMatrix(int V){
        graph = new int[V][V];
        for(int i=0; i< graph.length;i++){
            Arrays.fill(graph[i],0);
        }
    }

    public void addEdge(int u, int v){
        if(u > graph.length || v > graph.length || u < 0 || v < 0)
            System.out.println("Invalid Edge");

        graph[u][v] = 1;
        graph[v][u] = 1;
    }

    public void printGraphMatrix(){
        for(int i=0; i<graph.length;i++){
            for(int j=0; j< graph[i].length;j++){
                System.out.print(graph[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public void printGraphEdges(){
        for(int i=0; i<graph.length;i++){
            System.out.println("Edges for Node=" + i);
            System.out.print("head ");
            for(int j=0; j< graph[i].length;j++){
                if(graph[i][j] == 1)
                    System.out.print(" -> " + j);
            }
            System.out.println();
        }
    }

}
