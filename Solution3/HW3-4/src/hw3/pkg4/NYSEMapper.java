/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw3.pkg4;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 *
 * @author pooja
 */
public class NYSEMapper extends Mapper<Object, Text, Text, CustomKeyWritable> {
     
    private Text stock_Name = new Text();
        @Override
        public void map(Object key, Text values, Context context)
        {
            if (values.toString().length()>0 && values.toString().startsWith("NYSE"))
            {
                try {
                    String value[] = values.toString().split(",");
                    CustomKeyWritable cw = new CustomKeyWritable(value[1],value[2],Integer.parseInt(value[7]),
                    Double.parseDouble(value[8]));
                    stock_Name.set(value[1].toString());
                    context.write(stock_Name,cw);
                } catch (IOException | InterruptedException ex) {
                    Logger.getLogger(NYSEMapper.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    
}
