package org.walmart.gs.backtracking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {

    class TrieNode {
        Map<Character, TrieNode> children;
        String word = null;

        public TrieNode() {
            children = new HashMap<>();
        }
    }

    private List<String> results = new ArrayList<>();
    private char[][] board;

    public List<String> findWords(char[][] board, String[] words) {
        if (board == null || board.length == 0) return results;

        TrieNode root = new TrieNode();
        //build the trie
        for (String word : words) {
            TrieNode node = root;
            for (Character c : word.toCharArray()) {
                if (node.children.containsKey(c)) {
                    node = node.children.get(c);
                } else {
                    TrieNode newNode = new TrieNode();
                    node.children.put(c, newNode);
                    node = newNode;
                }
            }
            node.word = word;
        }

        this.board = board;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (root.children.containsKey(board[i][j]))
                    backtrack(i, j, root);
            }
        }
        return results;
    }

    private void backtrack(int row, int col, TrieNode root) {
        Character c = board[row][col];
        TrieNode currentNode = root.children.get(c);

        if (currentNode.word != null) {
            results.add(currentNode.word);
        }

        board[row][col] = '#';
        int[] rowSet = {-1, 0, 1, 0};
        int[] colSet = {0, 1, 0, -1};
        for (int k = 0; k < 4; k++) {
            int newRow = row + rowSet[k];
            int newCol = col + colSet[k];
            if (newRow < 0 || newRow >= this.board.length || newCol < 0
                    || newCol >= this.board[0].length) {
                continue;
            }
            if (currentNode.children.containsKey(this.board[newRow][newCol])) {
                backtrack(newRow, newCol, currentNode);
            }
        }

        board[row][col] = c;
    }

    public static void main(String[] args) {
        char[][] board = {
                {'o', 'a', 'a', 'n'},
                {'e', 't', 'a', 'e'},
                {'i', 'h', 'k', 'r'},
                {'i', 'f', 'l', 'v'}
                };
        String words[] = {"oath","pea","eat","rain"};
        Solution sol = new Solution();
        sol.findWords(board, words);
    }
}
