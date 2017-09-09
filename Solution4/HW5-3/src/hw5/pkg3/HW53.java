/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw5.pkg3;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.SortedMapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

/**
 *
 * @author sumedh
 */
public class HW53 {

     public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
         Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Distinct");
        job.setJarByClass(HW53.class);
        
        job.setMapperClass(Map1.class);
        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(Text.class);
         
  //        job.setCombinerClass(Reduce1.class);
          job.setPartitionerClass(MonthPartitioner.class);
          job.setReducerClass(Reduce1.class);
        
          job.setOutputKeyClass(IntWritable.class);
          job.setOutputValueClass(Text.class);
          job.setNumReduceTasks(12);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        SequenceFileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
    
    public static class Map1 extends Mapper<Object,Text,IntWritable,Text>
    {
         
    private Text details = new Text();
     private IntWritable month11 = new IntWritable();
      private String pattern = "dd/MMM/yyyy";
      SimpleDateFormat format = new SimpleDateFormat(pattern);
   
    public void map(Object key, Text value, Context context
    ) throws IOException, InterruptedException {
       
        String inputLine = value.toString();
        String str[] = inputLine.split(" ");
        String s = str[3];
        String requiredString = s.substring(s.indexOf("[") + 1, s.indexOf(":"));
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(format.parse(requiredString));
            int month = cal.get(Calendar.MONTH);
             month11.set(month);
        } catch (ParseException ex) {
            Logger.getLogger(HW53.class.getName()).log(Level.SEVERE, null, ex);
        }
           
           
            details.set(value);
            context.write(month11, details); 
          
    }
               
        }
        
        
        
    
    
    public static class Reduce1 extends Reducer<IntWritable,Text,IntWritable,Text>
    {

        @Override
        protected void reduce(IntWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            for (Text value : values) {
                context.write(key,value);
            }
        }

      
       
 
       
    }
    
}
