package org.walmart.gs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CriticalConnectionsDupl {
    int timer = 0;
    int[] ids;
    int[] low;
    boolean[] visited;
    List<List<Integer>> res;
    List<Integer>[] graph;

    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        ids = new int[n];
        low = new int[n];
        visited = new boolean[n];
        res = new ArrayList<>();
        graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (List<Integer> connection : connections) {
            graph[connection.get(0)].add(connection.get(1));
            graph[connection.get(1)].add(connection.get(0));
        }

        dfs(0, -1);
        return res;
    }

    private void dfs(int cur, int parent) {
        low[cur] = ids[cur] = timer++;
        visited[cur] = true;

        for (int to : graph[cur]) {
            if (to == parent) {
                continue;
            }
            if (visited[to] == false) {
                dfs(to, cur);
                low[cur] = Math.min(low[cur], low[to]);
                if (low[to] > ids[cur]) {
                    res.add(Arrays.asList(cur, to));
                }
            } else {
                low[cur] = Math.min(low[cur], ids[to]);
            }
        }
    }

    public static void main(String[] args) {
        CriticalConnectionsDupl criticalConnections = new CriticalConnectionsDupl();
        List<List<Integer>> res = new ArrayList<>();
        res.add(Arrays.asList(0, 1));
        res.add(Arrays.asList(1, 2));
        res.add(Arrays.asList(2, 0));
        res.add(Arrays.asList(1, 3));
        criticalConnections.criticalConnections(4, res);
    }
}
