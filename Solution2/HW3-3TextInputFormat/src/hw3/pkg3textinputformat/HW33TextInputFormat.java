/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw3.pkg3textinputformat;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.NLineInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 *
 * @author sumedh
 */
public class HW33TextInputFormat {

    /**
     * @param args the command line arguments
     */
    

    public static void main(String[] args) {
     
            Configuration conf = new Configuration();
        //conf.setInt(NLineInputFormat.LINES_PER_MAP,2);
        Job job;
        try {
            job = Job.getInstance(conf, "word count");
        
        job.setJarByClass(HW33TextInputFormat.class);
        job.setMapperClass(WordCount_Mapper.class);
        job.setCombinerClass(WordCount_Reducer.class);
        job.setReducerClass(WordCount_Reducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        
        job.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.addInputPath(job, new Path(args[0]));

        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
        }
        catch (IOException|InterruptedException|ClassNotFoundException ex) {
            Logger.getLogger(HW33TextInputFormat.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
