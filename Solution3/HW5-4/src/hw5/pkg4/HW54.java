/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw5.pkg4;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 *
 * @author sumedh
 */
public class HW54 extends Configured implements Tool {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception  {
        
        int res = ToolRunner.run(new Configuration(), new HW54(), args);
    }
    
    @Override
    public int run(String[] strings) throws Exception {
        Configuration conf = new  Configuration();
      Job job = Job.getInstance(conf, "Binning Pattern");
      job.setJarByClass(HW54.class);
        job.setMapperClass(Mapper1.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);
      MultipleOutputs.addNamedOutput(job, "bins", TextOutputFormat.class, Text.class, NullWritable.class);
      //MultipleInputs.addInputPath(job, new Path(strings[0]), TextInputFormat.class, JoinMapper1.class);
      //MultipleInputs.addInputPath(job, new Path(strings[1]), TextInputFormat.class, JoinMapper2.class); 
      MultipleOutputs.setCountersEnabled(job, true);
      job.setNumReduceTasks(0);
      //job.getConfiguration().set("join.type","fullouter");
      //job.setReducerClass(JoinReducer.class);
      //job.setOutputFormatClass(TextOutputFormat.class);
      
      TextOutputFormat.setOutputPath(job,new Path(strings[1]));
      job.setOutputKeyClass(Text.class);
      job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path(strings[0]));
      boolean success = job.waitForCompletion(true);
      return success ? 0:2;
      }
    
    
    public static class Mapper1 extends Mapper<Object,Text,
            Text,NullWritable>
    {
         private Text details = new Text();
     private IntWritable month11 = new IntWritable();
      private String pattern = "dd/MMM/yyyy";
      SimpleDateFormat format = new SimpleDateFormat(pattern);
      private MultipleOutputs<Text,NullWritable> m = null;

        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            
             try {
                 String inputLine = value.toString();
                 String str[] = inputLine.split(" ");
                 String s = str[3];
                 String requiredString = s.substring(s.indexOf("[") + 1, s.indexOf(":"));
                 Calendar cal = Calendar.getInstance();
                 
                 cal.setTime(format.parse(requiredString));
                 int month = cal.get(Calendar.MONTH);
                 
                 if (month == 0)m.write("bins", value, NullWritable.get(), "Jan");
                 if (month == 1)m.write("bins", value, NullWritable.get(), "Feb");
                 if (month == 2)m.write("bins", value, NullWritable.get(), "Mar");
                     if (month == 3)m.write("bins", value, NullWritable.get(), "Apr");
                         if (month == 4)m.write("bins", value, NullWritable.get(), "May");
                             if (month == 5)m.write("bins", value, NullWritable.get(), "Jun");
                                 if (month == 6)m.write("bins", value, NullWritable.get(), "Jul");
                                     if (month == 7)m.write("bins", value, NullWritable.get(), "Aug");
                     if (month == 8)m.write("bins", value, NullWritable.get(), "Sep");
                         if (month == 9)m.write("bins", value, NullWritable.get(), "Oct");
                             if (month == 10)m.write("bins", value, NullWritable.get(), "Nov");
                                 if (month == 11)m.write("bins", value, NullWritable.get(), "Dec");
                 
               
                 
                 
                 
                
             } catch (ParseException ex) {
                 Logger.getLogger(HW54.class.getName()).log(Level.SEVERE, null, ex);
             }
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
                m.close();
        }

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
           m = new MultipleOutputs(context);
        }
      
      
        
    }
    
    
    
    
}
