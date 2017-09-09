/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw3.pkg3keyivalueinputformat;

import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 *
 * @author pooja
 */
public class WordCount_Mapper extends Mapper<Text, Text, Text, IntWritable> {

    private final static IntWritable one = new IntWritable();
    private Text word = new Text();

    public void map(Text key,Text value, Context context
    ) throws IOException, InterruptedException {

     
            String abc = value.toString();
            Integer value1 = Integer.parseInt(abc);
            one.set(value1);
            context.write(key, one);
        
    }
}
