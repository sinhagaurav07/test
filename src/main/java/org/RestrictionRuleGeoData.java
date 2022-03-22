package org;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RestrictionRuleGeoData {

    public static void main(String[] args) throws IOException {
        Map<String, List<String>> stateCodes = new HashMap<>();
        Map<String, List<String>> zipCodes = new HashMap<>();
        Set<String> values = new HashSet<>();
        Map<String, String> definition = new HashMap<>();

        String fileName = "/Users/a0b07fa/Documents/restriction_trule_geo_15032021.csv";
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(line -> {
                String[] lines = line.split(";");
                values.add(lines[0]);
                if(lines[1].equals("STATE")) {
                    List<String> states = stateCodes.getOrDefault(lines[0], new ArrayList<>());
                    states.add(lines[2]);
                    stateCodes.put(lines[0], states);
                }
                if(lines[1].equals("ZIP")) {
                    List<String> zips = zipCodes.getOrDefault(lines[0], new ArrayList<>());
                    zips.add(lines[2]);
                    zipCodes.put(lines[0], zips);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        String geoFileName = "/Users/a0b07fa/Documents/restriction_tag_metadata_15032021.csv";
        try (Stream<String> stream = Files.lines(Paths.get(geoFileName))) {
            stream.forEach(line -> {
                String[] lines = line.split(";");
                values.add(lines[0]);
                System.out.println(lines);
                String restricted = StringUtils.EMPTY;
                if(stateCodes.containsKey(lines[0]) && zipCodes.containsKey(lines[0])){
                    restricted = "BOTH";
                } else if(stateCodes.containsKey(lines[0]) && !zipCodes.containsKey(lines[0])) {
                    restricted = "STATE";
                } else if(!stateCodes.containsKey(lines[0]) && zipCodes.containsKey(lines[0])){
                   restricted = "ZIP";
                }
                definition.put(lines[0], StringUtils.join(lines[1], "|", restricted));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder stringBuilder = new StringBuilder();
        values.stream().forEach(value -> {
            String states = StringUtils.EMPTY;
            if(stateCodes.containsKey(value) && !stateCodes.get(value).isEmpty()) {
                states = stateCodes.get(value).stream().collect(Collectors.joining(","));
            }
            String zips = StringUtils.EMPTY;
            if(zipCodes.containsKey(value) && !zipCodes.get(value).isEmpty()) {
                zips = zipCodes.get(value).stream().collect(Collectors.joining(","));
            }
            stringBuilder.append(StringUtils.join(value, "|", definition.get(value), "|", states, "|", zips)).append("\n");
        });
        fileName = "/Users/a0b07fa/Documents/limo_restriction_rules_geo_15032021.txt";
        FileOutputStream fos = new FileOutputStream(fileName);
        DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos));
        outStream.writeBytes(stringBuilder.toString());
        outStream.close();
    }
}
