package org.walmart.gs.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class FileSharing {

    public static void main(String[] args) {
       FileSharing fileSharing = new FileSharing(14);
       fileSharing.join(Arrays.asList(3,9,12,2,7,13,6,1,11,14));
       fileSharing.leave(1);
       fileSharing.join(Arrays.asList(2, 11));
       fileSharing.join(Arrays.asList(14,10,3,2,13));
       fileSharing.join(Arrays.asList(13));
       fileSharing.leave(2);
       fileSharing.leave(1);
       fileSharing.leave(3);
       fileSharing.join(Arrays.asList(14,12,1,2,9,5,8,4,6));
       fileSharing.leave(1);
    }

    private Map<Integer, Set<Integer>> chunkUserMap = new HashMap<>();
    private Map<Integer, Set<Integer>> userChunkMap = new HashMap<>();

    private int id = 1;
    private Set<Integer> availableIds = new TreeSet<>();

    public FileSharing(int m) {
        for (int i = 1; i <= m; i++) {
            chunkUserMap.put(i, new HashSet<>());
        }
    }

    public int join(List<Integer> ownedChunks) {
        int userId = 0;
        if (ownedChunks == null || ownedChunks.isEmpty()) {
            userId = 1;
            return userId;
        } else {
            userId = getUserId();
        }
        Set<Integer> set = new HashSet<>(ownedChunks);
        userChunkMap.put(userId, set);
        for (int chunk : set) {
            if(chunkUserMap.containsKey(chunk))
                chunkUserMap.get(chunk).add(userId);
        }
        return userId;
    }

    public void leave(int userID) {
        //Remove the chunks from chunkMap
        Set<Integer> chunks = userChunkMap.get(userID);
        if (chunks != null && !chunks.isEmpty()) {
            for (int chunk : chunks) {
                chunkUserMap.remove(chunk);
            }
        }
        availableIds.add(userID);
    }

    public List<Integer> request(int userID, int chunkID) {
        List<Integer> result = new ArrayList<>();
        if (chunkUserMap.containsKey(chunkID)) {
            if (userChunkMap.containsKey(userID)) {
                userChunkMap.get(userID).add(chunkID);
            }
            for (int id : chunkUserMap.get(chunkID)) {
                result.add(id);
            }
            chunkUserMap.get(chunkID).add(userID);
        }
        return result;
    }

    private int getUserId() {
        if (availableIds.isEmpty()) {
            return id++;
        } else {
            int userId = -1;
            for (int i : availableIds) {
                userId = i;
                break;
            }
            availableIds.remove(userId);
            return userId;
        }
    }
}
