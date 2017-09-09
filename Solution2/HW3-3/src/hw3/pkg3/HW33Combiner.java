/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw3.pkg3;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.CombineFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FixedLengthInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 *
 * @author sumedh
 */
public class HW33Combiner {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        
        
     


   
                         
   Configuration conf = new Configuration();
        conf.set("mapreduce.input.keyvaluelinerecordreader.key.value.separator", ",");
        Job job = Job.getInstance(conf, "word count");
        job.setJarByClass(HW33FixedLength.class);
        job.setMapperClass(WordCount_Mapper.class);
        job.setCombinerClass(WordCount_Reducer.class);
        job.setReducerClass(WordCount_Reducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        job.setInputFormatClass(KeyValueTextInputFormat.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
        
        
        
        
        
        
                      
//        Configuration conf = new Configuration();
//        conf.setInt(FixedLengthInputFormat.FIXED_RECORD_LENGTH, 5);
//        Job job = Job.getInstance(conf, "word count");
//        job.setJarByClass(HW33FixedLength.class);
//        job.setMapperClass(WordCount_Mapper.class);
//        job.setCombinerClass(WordCount_Reducer.class);
//        job.setReducerClass(WordCount_Reducer.class);
//        job.setOutputKeyClass(Text.class);
//        job.setOutputValueClass(IntWritable.class);
//        FileInputFormat.addInputPath(job, new Path(args[0]));
//        FileOutputFormat.setOutputPath(job, new Path(args[1]));
//        System.exit(job.waitForCompletion(true) ? 0 : 1);
//    }
//              
//   Configuration conf = new Configuration();
//        Job job = Job.getInstance(conf, "word count");
//        CombineFileInputFormat cf = new CombineFileInputFormat() {
//       @Override
//       public RecordReader createRecordReader(InputSplit is, TaskAttemptContext tac) throws IOException {
//           throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//       }
//   };
//        job.setJarByClass(HW33Combiner.class);
//        job.setMapperClass(WordCount_Mapper.class);
//        job.setCombinerClass(WordCount_Reducer.class);
//        job.setReducerClass(WordCount_Reducer.class);
//        job.setOutputKeyClass(Text.class);
//        job.setOutputValueClass(IntWritable.class);
//        job.setInputFormatClass(CombineFileInputFormat.class);
//        cf.setMaxInputSplitSize(job,444444l);
//        
//        CombineFileInputFormat.addInputPath(job, new Path(args[0]));
//        FileOutputFormat.setOutputPath(job, new Path(args[1]));
//        System.exit(job.waitForCompletion(true) ? 0 : 1);
//    }
    
}
