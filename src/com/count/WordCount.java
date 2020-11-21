package com.count;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;

import scala.Tuple2;

import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import java.util.Arrays;


public class WordCount {

    public static void main(String[] args) {

        // Disable logging
        Logger.getLogger("org").setLevel(Level.OFF);
        Logger.getLogger("akka").setLevel(Level.OFF);

        // Create a Java Spark Context.
        SparkConf sparkConf = new SparkConf().setAppName("Hello Spark - WordCount").setMaster("local[*]");
        SparkContext sc = new SparkContext(sparkConf);

        // Load our input data.
        JavaRDD<String> lines = sc.textFile("C:\\Users\\MyPC\\Desktop\\input.txt",2).toJavaRDD();
        //JavaRDD<String,Integer> a

        // Check read data
        lines.collect().forEach(s -> System.out.println(s));

        // Split up into words.
        JavaRDD<String> words = lines.flatMap(
                new FlatMapFunction<String, String>() {
                    public Iterable<String> call(String x) {
                        return Arrays.asList(x.split(" "));
                    }});


        // Transform into word and count.
        JavaPairRDD<String, Integer> counts = words.mapToPair(
                new PairFunction<String, String, Integer>(){
                    public Tuple2<String, Integer> call(String x){
                        return new Tuple2(x, 1);
                    }}).reduceByKey(new Function2<Integer, Integer, Integer>(){
            public Integer call(Integer x, Integer y){ return x + y;}});


        // Format the word counts:
        JavaRDD<String> countsStr = counts.map( pair -> "[" + pair._1()+"] was found "+pair._2()+" time(s)");

        // Check read data
        countsStr.collect().forEach(s -> System.out.println(s));

        // Save the word counts back out to a text file
        countsStr.saveAsTextFile("C:\\Users\\MyPC\\Desktop\\output");

        JavaRDD<String> textFile = sc.textFile("C:\\Users\\MyPC\\Desktop\\input.txt",2).toJavaRDD();
        JavaPairRDD<String, Integer> countWords = textFile
                .flatMap( new FlatMapFunction<String, String>() {
                    public Iterable<String> call(String x) {
                        return Arrays.asList(x.split(" "));
                    }})
                .mapToPair(word -> new Tuple2<>(word, 1))
                .reduceByKey((a, b) -> a + b);
        countWords.foreach(p -> System.out.println(p));
        System.out.println("Total words: " + countWords.count());
    }
}
