/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asign1;

import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class Mapper_stock extends Mapper<Object, Text, Text, DoubleWritable> {

    private static DoubleWritable avgValue = new DoubleWritable();
    private Text word = new Text();

    public void map(Object key, Text value, Context context
    ) throws IOException, InterruptedException {

        String inputLine = value.toString();
       String str[] = inputLine.split(",");
       
          if(str[0].contains("NYSE")) {
            word.set(str[1]);
            avgValue.set(Double.parseDouble(str[4]));
            context.write(word, avgValue);
            }
    }
}
