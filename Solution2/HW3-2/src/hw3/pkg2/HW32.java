/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw3.pkg2;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 *
 * @author sumedh
 */
public class HW32 {

    public static void main(String[] args) {
        Configuration conf = new Configuration();
          try{
        FileSystem hdfs = FileSystem.get(conf);
        FileSystem local = FileSystem.getLocal(conf);
        
        Path inputDir = new Path(args[0]);
        Path hdfsFile = new Path(args[1]);
        
      
            FileStatus[] inputFiles = local.listStatus(inputDir);
            FSDataOutputStream out = hdfs.create(hdfsFile);
            
            for(int i = 0; i < inputFiles.length ;i++)
            {
                System.out.println(inputFiles[i].getPath().getName());
                FSDataInputStream in = local.open(inputFiles[i].getPath());
                byte buffer[] = new byte[256];
                int bytesRead = 0;
                while((bytesRead = in.read(buffer)) > 0)
                {
                    out.write(buffer,0,bytesRead);
                }
                
                in.close();
            }
                out.close();
                
        
   
        

            Configuration conf1 = new Configuration();
            Job job = Job.getInstance(conf, "SecondarySort");
            job.setJarByClass(HW32.class);
           
            job.setMapperClass(Mapper_stock.class);
            job.setMapOutputKeyClass(Reducer_stock.class);
            job.setMapOutputValueClass(DoubleWritable.class);
            
           // job.setPartitionerClass(Lab2Partitioner.class);
            //job.setGroupingComparatorClass(Lab2GroupComparator.class);
            
            job.setReducerClass(Reducer_stock.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(DoubleWritable.class);
            
           // job.setNumReduceTasks(8);
            
            FileInputFormat.addInputPath(job, new Path(args[0]));
            FileOutputFormat.setOutputPath(job, new Path(args[1]));
            
            
            
            System.exit(job.waitForCompletion(true) ? 0 : 1);
            
        } 
        catch (IOException | InterruptedException | ClassNotFoundException ex) {
            System.out.println("Erorr Message"+ ex.getMessage());
        }
    }
}

