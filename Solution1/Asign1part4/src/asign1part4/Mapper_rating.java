/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asign1part4;

import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class Mapper_rating extends Mapper<Object, Text, Text, IntWritable> {

    private static IntWritable userId = new IntWritable(1);
    private Text word = new Text();

    public void map(Object key, Text value, Context context
    ) throws IOException, InterruptedException {

        String inputLine = value.toString();
       String str[] = inputLine.split("::");
       
         
            word.set(str[0]);
            context.write(word, userId);
           
    }
}
