package org;

import com.google.common.collect.Sets;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Test {

    public static void main(String[] args) {
        Map<String, Map<String, Set<String>>> first = new HashMap<>();
        /*Map<String, Set<String>> firstTags = new HashMap<>();
        firstTags.put("tag1", Sets.newHashSet("3", "2"));
        first.put("offer1", firstTags);*/
        Map<String, Map<String, Set<String>>> second = new HashMap<>();
        Map<String, Set<String>> secondTags = new HashMap<>();
        secondTags.put("tag1", Sets.newHashSet("1", "2"));
        secondTags.put("tag2", Sets.newHashSet("3", "4"));
        second.put("offer1", secondTags);
        mergeRestrictions(first, second);
    }

    public static Map<String, Map<String, Set<String>>> mergeRestrictions(Map<String, Map<String, Set<String>>> offerRestrictionPathsMapOne, Map<String, Map<String, Set<String>>> offerRestrictionPathsMapTwo) {
        Map<String, Map<String, Set<String>>> merged = new HashMap<>();
        if(MapUtils.isNotEmpty(offerRestrictionPathsMapOne)){
            merged.putAll(offerRestrictionPathsMapOne);
        }
        if(MapUtils.isNotEmpty(offerRestrictionPathsMapTwo)) {
            offerRestrictionPathsMapTwo.keySet().stream().forEach(offerId -> {
                Map<String, Set<String>> mergedTags = merged.getOrDefault(offerId, new HashMap<>());
                Map<String, Set<String>> tagToPathMap = offerRestrictionPathsMapTwo.getOrDefault(offerId, new HashMap<>());
                if(MapUtils.isNotEmpty(tagToPathMap)){
                    tagToPathMap.keySet().stream().forEach(tag -> {
                        Set<String> tagSet = mergedTags.getOrDefault(tag, new HashSet<>());
                        tagSet.addAll(tagToPathMap.get(tag));
                        mergedTags.put(tag, tagSet);
                    });
                }
                merged.put(offerId, mergedTags);
            });
        }
        return merged;
    }
}
