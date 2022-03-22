package org.walmart.gs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConcatenatedWords {
    private List<String> result = new ArrayList<>();
    private TrieNode trie;

    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        //basic validation
        if(words == null || words.length == 0) return result;

        trie = new TrieNode();
        //build the trie
        for(String word : words){
            TrieNode node = trie;
            for(int i = 0; i < word.length(); i++){
                char c = word.charAt(i);
                if(!node.children.containsKey(c)){
                    node.children.put(c, new TrieNode());
                }
                node = node.children.get(c);
            }
            node.isWord = true;
        }

        //find the concatenated words
        for(String word : words){
            if(findWord(word, 0)){
                result.add(word);
            }
        }
        return result;
    }

    private boolean findWord(String word, int index){
        TrieNode node = trie;
        if(word.length() == 0 && index >= 2) return true;
        if(word.length() == 0) return false;
        for(int i = 0; i < word.length(); i++){
            char c = word.charAt(i);
            node = node.children.get(c);
            if(node == null) {
                return false;
            }
            if(node.isWord && findWord(word.substring(i+1), index+1))
                return true;
        }
        return false;
    }

    private class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        boolean isWord = false;
        public TrieNode(){}
    }

    public static void main(String[] args) {
        String[] list = {"cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"};
        ConcatenatedWords concatenatedWords = new ConcatenatedWords();
        concatenatedWords.findAllConcatenatedWordsInADict(list);
    }
}
