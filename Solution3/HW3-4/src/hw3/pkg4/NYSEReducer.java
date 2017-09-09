/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw3.pkg4;

import java.io.IOException;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 *
 * @author pooja
 */
public class NYSEReducer extends Reducer<Text, CustomKeyWritable, Text, CustomKeyWritable> {
//     private final NullWritable text = new NullWritable();
     CustomKeyWritable t = new CustomKeyWritable();
      CustomKeyWritable t1 = new CustomKeyWritable();
       CustomKeyWritable t2 = new CustomKeyWritable();
    @Override
    protected void reduce(Text key, Iterable<CustomKeyWritable> values, Context context) throws IOException, InterruptedException {
        
    //   text.set(key.getName());
       Integer temp = 0;
       Integer temp1 = 600000;
       double temp3 = 0;
        for (CustomKeyWritable value : values) {
            
            if (value.getVolume() > temp )
            {
                temp = value.getVolume();
                t.setDate(value.getDate());
                t.setName(value.getName());
                t.setVolume(value.getVolume());
                t.setStockPriceAdj(value.getStockPriceAdj());
            }
            
               if (value.getVolume() < temp1 )
            {
                temp1 = value.getVolume();
                t1.setDate(value.getDate());
                t1.setName(value.getName());
                t1.setVolume(value.getVolume());
                t1.setStockPriceAdj(value.getStockPriceAdj());
            }
                      if (value.getStockPriceAdj() >  temp3 )
            {
                temp3 = value.getStockPriceAdj();
                t2.setDate(value.getDate());
                t2.setName(value.getName());
                t2.setVolume(value.getVolume());
                t2.setStockPriceAdj(value.getStockPriceAdj());
            }
            
            
            
        }
        
        context.write(key, t);
          context.write(key, t1);
            context.write(key, t2);
    }

  
    
    /**
     *
     * @param key
     * @param values
     * @param context
     */
 
    
    
}
