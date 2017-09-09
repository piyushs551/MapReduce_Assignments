/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw3.pkg5;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 *
 * @author sumedh
 */
public class HW35 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  {
        try {
            Configuration conf = new Configuration();
            Job job1 = Job.getInstance(conf,"Chaining");
            job1.setJarByClass(HW35.class);
            job1.setMapperClass(Map1.class);
            job1.setMapOutputKeyClass(IntWritable.class);
            job1.setMapOutputValueClass(DoubleWritable.class);
            
            job1.setReducerClass(Reduce1.class);
            job1.setOutputKeyClass(IntWritable.class);
            job1.setOutputValueClass(DoubleWritable.class);
            
            FileInputFormat.addInputPath(job1,new Path(args[0]));
            FileOutputFormat.setOutputPath(job1, new Path(args[1]));
          //  System.exit(job1.waitForCompletion(true) ? 0:1);
            
            boolean complete = job1.waitForCompletion(true);
            
            Configuration conf2 = new Configuration();
            Job job2 = Job.getInstance(conf2 ,"chaining");
            if(complete)
            {
            
            
            job2.setJarByClass(HW35.class);
            job2.setMapperClass(Map2.class);
            job2.setMapOutputKeyClass(DoubleWritable.class);
            job2.setMapOutputValueClass(IntWritable.class);
            
            job2.setReducerClass(Reduce2.class);
            job2.setOutputKeyClass(IntWritable.class);
            job2.setOutputValueClass(DoubleWritable.class);
            
            FileInputFormat.addInputPath(job2,new Path(args[1]));
            FileOutputFormat.setOutputPath(job2, new Path(args[2]));
            System.exit(job2.waitForCompletion(true) ? 0:1);
           }
    } catch (IOException ex) {
            Logger.getLogger(HW35.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(HW35.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HW35.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }
    
    
    public static class Map1 extends Mapper<Object,Text,IntWritable,DoubleWritable>{

        
        
        
        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            
            String row[] = value.toString().split("::");
            
            IntWritable movieId = new IntWritable(Integer.parseInt(row[0]));
            DoubleWritable rating = new DoubleWritable(Double.parseDouble(row[2]));

           //UserID::MovieID::Rating::Timestamp
            
            try{
               // LongWritable movieIdW = new LongWritable(movieId);
                 // LongWritable ratingW = new LongWritable(rating);
                context.write(movieId, rating);
            }
            catch(Exception e)
            {
            }
           
        }
    }
        
        public static class Reduce1 extends Reducer<IntWritable,DoubleWritable,IntWritable,DoubleWritable>{
        
        private DoubleWritable totalRating = new DoubleWritable();

           
            protected void reduce(IntWritable key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
               double sum = 0;
                for (DoubleWritable val : values) {
                    
                  sum += val.get();
                    
                }
                totalRating.set(sum);
                context.write(key, totalRating);
            }
        
        
    }
        
        
        public static class Map2 extends Mapper<Object, Text, DoubleWritable, IntWritable>
        {

    
        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
         String row[] = value.toString().split("\\t");
         String movieId = row[0].trim();
         String rating = row[1].trim();
            IntWritable movieIdW = new IntWritable(Integer.parseInt(movieId));
            DoubleWritable ratingW = new DoubleWritable(Double.parseDouble(rating));
         try{
         
             context.write(ratingW, movieIdW);
         }
         catch(Exception e)
         {
         }
        }
            
        }
        
        public static class Reduce2 extends Reducer<DoubleWritable, IntWritable, IntWritable, DoubleWritable>
        {

       
        protected void reduce(DoubleWritable key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
       
            for (IntWritable val : values) {
                context.write(val,key);
            }
            
            
        }
            
        }
        
        
    
    
}

