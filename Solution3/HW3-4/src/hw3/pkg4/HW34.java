/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw3.pkg4;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 *
 * @author pooja
 */
public class HW34 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            Configuration conf = new Configuration();
            Job job = Job.getInstance(conf, "SecondarySort");
            job.setJarByClass(HW34.class);
           
            job.setMapperClass(NYSEMapper.class);
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(CustomKeyWritable.class);
            
           // job.setPartitionerClass(Lab2Partitioner.class);
            //job.setGroupingComparatorClass(Lab2GroupComparator.class);
            
            job.setReducerClass(NYSEReducer.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(CustomKeyWritable.class);
            
           // job.setNumReduceTasks(8);
            
            FileInputFormat.addInputPath(job, new Path(args[0]));
            FileOutputFormat.setOutputPath(job, new Path(args[1]));
            
            
            
            System.exit(job.waitForCompletion(true) ? 0 : 1);
            
        } catch (IOException | InterruptedException | ClassNotFoundException ex) {
            System.out.println("Erorr Message"+ ex.getMessage());
        }
    }
    
}

