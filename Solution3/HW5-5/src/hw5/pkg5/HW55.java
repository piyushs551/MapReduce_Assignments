/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw5.pkg5;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 *
 * @author sumedh
 */
public class HW55 extends Configured implements Tool{

  public static class JoinMapper1 extends Mapper<Object,Text,Text,Text>
  {
      private Text outKey = new Text();
      private Text outValue = new Text();

        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String[] separatedInput = value.toString().split("\t");
            String id = separatedInput[0].trim();
            System.out.println(id);
            System.out.println(value.toString());
            if (id == null)
            {
                return;
            }
            outKey.set(id);
            outValue.set("@"+value );
            context.write(outKey, outValue);
        }
      
  }
  
  public static class JoinMapper2 extends Mapper<Object, Text, Text, Text>
  {
       private Text outKey = new Text();
      private Text outValue = new Text();

        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            
               String id = value.toString().split(",")[0].trim();
               String Id = id.substring(1,id.length()-1);
               
            System.out.println(Id);
            System.out.println(value.toString());
               
               
               
               
           // String id = separatedInput[0];
            if (id == null)
            {
                return;
            }
            outKey.set(Id);
            outValue.set("$"+value );
            context.write(outKey, outValue);
            
        }
      
  }
  
  public static class JoinReducer extends Reducer<Text,Text,Text,Text>
          {
              private static final Text EMPTY_TEXT = new Text();
              private Text tmp = new Text();
              
              private ArrayList<Text> listA = new ArrayList<Text>();
                            private ArrayList<Text> listB = new ArrayList<Text>();
                            private String joinType = null;

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            joinType = context.getConfiguration().get("join.type");
        }

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
           listA.clear();
           listB.clear();
           
           while(values.iterator().hasNext())
           {
               tmp = values.iterator().next();
               if (tmp.charAt(0) == '@')
               {
                   listA.add(new Text(tmp.toString().substring(1)));
               } else if (tmp.charAt(0) == '$')
               {
                   listB.add(new Text(tmp.toString().substring(1)));
               }
           }
           
           executeJoinLogic(context);
           
           }

        private void executeJoinLogic(Context context) throws IOException, InterruptedException {
            if (joinType.equalsIgnoreCase("leftouter"))
            {
                for (Text A : listA) {
                    if(!listB.isEmpty())
                    {
                        for (Text B : listB) {
                            context.write(A,B);
                        }
                    }
                    else
                    {
                        context.write(A, EMPTY_TEXT);
                    }
                }
            }
                else if (joinType.equalsIgnoreCase("righttouter"))
            {
                for (Text B : listB) {
                    if(!listA.isEmpty())
                    {
                        for (Text A : listA) {
                            context.write(A,B);
                        }
                    }
                    else
                    {
                        context.write(B, EMPTY_TEXT);
                    }
                }
            }
                else  if (joinType.equalsIgnoreCase("inner"))
            {
                if(!listA.isEmpty() && !listB.isEmpty())
                {
                for (Text A : listA) {
                   
                    {
                        for (Text B : listB) {
                            context.write(A,B);
                        }
                    }
                   
                }
            }
            }
                else  if (joinType.equalsIgnoreCase("fullouter"))
            {
              if (!listA.isEmpty())
              {
                  for(Text A:listA)
                  {
                      if(!listB.isEmpty())
                      {
                          for (Text B : listB) {
                              context.write(A, B);
                          }
                      }
                      else
                      {
                          
                       context.write(A,EMPTY_TEXT);       
                  }
              }
              }
              else
              {
                  for(Text B:listB)
                      {
                          context.write(EMPTY_TEXT, B);
                      }
              }
                      
            }
                else  if (joinType.equalsIgnoreCase("anti"))
            {
              if(listA.isEmpty() ^ listB.isEmpty())
              {
                 for(Text A:listA)
                 {
                     context.write(A,EMPTY_TEXT);
                 }
                  for(Text B:listB)
                 {
                     context.write(B,EMPTY_TEXT);
                 }
                  
              }
            }
        }
        
        
                            
                            
                            
          }
              
    
    public static void main(String[] args) throws Exception {
        
        int res = ToolRunner.run(new Configuration(), new HW55(), args);

    }

    @Override
    public int run(String[] strings) throws Exception {
      Configuration conf = new  Configuration();
      Job job = Job.getInstance(conf, "ReduceJoin");
      job.setJarByClass(HW55.class);
      MultipleInputs.addInputPath(job, new Path(strings[0]), TextInputFormat.class, JoinMapper1.class);
      MultipleInputs.addInputPath(job, new Path(strings[1]), TextInputFormat.class, JoinMapper2.class); 
      job.setMapOutputKeyClass(Text.class);
      job.setMapOutputValueClass(Text.class);
      job.getConfiguration().set("join.type","inner");
      job.setReducerClass(JoinReducer.class);
      job.setOutputFormatClass(TextOutputFormat.class);
      
      TextOutputFormat.setOutputPath(job,new Path(strings[2]));
      job.setOutputKeyClass(Text.class);
      job.setOutputValueClass(Text.class);
      
      boolean success = job.waitForCompletion(true);
      return success ? 0:2;
    }
    
}
