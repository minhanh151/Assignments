package com.main;

import com.entity.DataSample;

import java.util.*;

public class DataProcessing {
    public static void main(String[] args) {
        String csvFile = "templates/hash_catid_count.csv";
        List<DataSample> dataSamples = DataSample.getDataSamples(csvFile);
        HashMap<String, Integer> map = getPopularMap(dataSamples);
        calculateFrequency(map);
        getTheMostPopularCate(map);
        getTheLargestAppearedCate(dataSamples);
    }

    public static HashMap<String, Integer> getPopularMap(List<DataSample> dataSamples)
    {
        int start = 1;
        HashMap<String, Integer> map = new HashMap<>();
        for(DataSample dataSample :dataSamples) {
            for(String cateId : dataSample.getCategory_ids()) {
                if(!map.containsKey(cateId)) {
                    map.put(cateId, start);
                } else {
                    map.put(cateId, map.get(cateId) + 1);
                }
            }
        }
        return map;
    }
    public static void getTheMostPopularCate(HashMap<String, Integer> map) {
        for (String keys : map.keySet()) {
            if(map.get(keys) == Collections.max(map.values())) {
                System.out.println("The Most Popular Category_id is: " + keys + " - Numbers: " + map.get(keys));
            }
            if(keys.equals("6")) {
                System.out.println(keys + " - " + map.get(keys));
            }
        }
        System.out.println("Category size : " + map.size());
    }

    public static void getTheLargestAppearedCate(List<DataSample> dataSamples) {
        HashMap<String, Integer> map = new HashMap<>();
        for(DataSample dataSample :dataSamples) {
            for( int i = 0; i < dataSample.getCategory_ids().size() ; i++) {
                String cateId = dataSample.getCategory_ids().get(i);
                int cateCount = dataSample.getCategory_counts().get(i);
                if(!map.containsKey(cateId)) {
                    map.put(cateId, cateCount);
                } else {
                    map.put(cateId, map.get(cateId) + cateCount);
                }
            }
        }
        for (String keys : map.keySet()) {
            if(map.get(keys) == Collections.max(map.values())) {
                System.out.println("The Largest Appeared Category_id is: " + keys + " - Numbers: " + map.get(keys));
            }
            if(keys.equals("15276")) {
                System.out.println(keys + " - " + map.get(keys));
            }
        }
    }

    public static void calculateFrequency(HashMap<String, Integer> map) {
        HashMap<String, Double> freMap = new HashMap<>();
        int total = 0;
        double fre;
        for (String keys : map.keySet()) {
            total = total + map.get(keys);
        }
        System.out.println("total = " + total);
        for (String keys : map.keySet()) {
            Double value = new Double(map.get(keys));
            Double totalD = new Double(total);
            freMap.put(keys, (double)Math.round((value * 100  / totalD) * 100000d) / 100000d);
        }
        LinkedHashMap<String, Double> sortedMap = sortHashMapByValues(freMap);
        int i = 1;
        for (String keys : sortedMap.keySet()) {
            //System.out.println("Category_id = " + keys + " - frequency = " + BigDecimal.valueOf(sortedMap.get(keys)) + "%");
        }
    }

    public static LinkedHashMap<String, Double> sortHashMapByValues(
            HashMap<String, Double> passedMap) {
        List<String> mapKeys = new ArrayList<>(passedMap.keySet());
        List<Double> mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues);
        Collections.reverse(mapValues);
        Collections.sort(mapKeys);
        Collections.reverse(mapKeys);

        LinkedHashMap<String, Double> sortedMap =
                new LinkedHashMap<>();

        Iterator<Double> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Double val = valueIt.next();
            Iterator<String> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                String key = keyIt.next();
                Double comp1 = passedMap.get(key);
                Double comp2 = val;

                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }
}
