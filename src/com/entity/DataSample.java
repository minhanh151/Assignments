package com.entity;

import com.utils.CSVUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataSample {
    private String object_id;
    private ArrayList<String> category_ids;
    private ArrayList<Integer> category_counts;

    static String cvsSplitBy = "\\[";
    static String cvsSplitArrayBy = ",";

    public DataSample() {

    }

    public DataSample(String object_id, ArrayList<String> category_ids, ArrayList<Integer> category_counts) {
        this.object_id = object_id;
        this.category_ids = category_ids;
        this.category_counts = category_counts;
    }

    public static List<DataSample> getDataSamples(String csvFile) {
        List<DataSample> dataSamples = new ArrayList<>();
        List<String> lines = CSVUtils.getLines(csvFile);
        for(String line : lines){
            String[] info = line.split(cvsSplitBy);
            String object_id = info[0].substring(0, info[0].length()-1);
            String category_ids = info[1].substring(0, info[1].length() - 2);
            String category_counts = info[2].substring(0, info[2].length() - 1);
//            if(category_ids.endsWith("10813") || object_id.startsWith("129492819428359")){
//                System.out.println(line + " - " + object_id + " - " + category_ids + " - " + category_counts);
//                System.out.println(line + " - " + info[0] + " - " + info[1] + " - " + info[2]);
//            }
            String[] category_id = category_ids.split(cvsSplitArrayBy);
            String[] category_count = category_counts.split(cvsSplitArrayBy);
            ArrayList<String> category_id_ar = new ArrayList<>();
            ArrayList<Integer> category_count_ar = new ArrayList<>();
            category_id_ar.addAll(Arrays.asList(category_id));
            for(String count : category_count){
                Integer count_int = Integer.parseInt(count);
                category_count_ar.add(count_int);
            }
            dataSamples.add(new DataSample(object_id, category_id_ar, category_count_ar));
        }
        return dataSamples;
    }

    public String getObject_id() {
        return object_id;
    }

    public ArrayList<String> getCategory_ids() {
        return category_ids;
    }

    public ArrayList<Integer> getCategory_counts() {
        return category_counts;
    }

    public void setObject_id(String object_id) {
        this.object_id = object_id;
    }

    public void setCategory_ids(ArrayList<String> category_ids) {
        this.category_ids = category_ids;
    }

    public void setCategory_counts(ArrayList<Integer> category_counts) {
        this.category_counts = category_counts;
    }
}
