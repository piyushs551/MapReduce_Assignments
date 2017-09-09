

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw3.pkg4;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableUtils;

/**
 *
 * @author pooja
 */
public class CustomKeyWritable implements Writable, WritableComparable<CustomKeyWritable> {

   
    private String name;
    private String date;
    private Integer volume;
    private Double stockPriceAdj;

    public double getStockPriceAdj() {
        return stockPriceAdj;
    }

    public void setStockPriceAdj(Double stockPriceAdj) {
        this.stockPriceAdj = stockPriceAdj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }
    
    public CustomKeyWritable()
    {
        
    }
    
    public CustomKeyWritable(String n, String d,Integer v,Double s)
    {
        this.name =n;
        this.date = d;
        this.volume = v;
        this.stockPriceAdj = s;
    }


    
    
    
    @Override
    public void write(DataOutput d) throws IOException {
        WritableUtils.writeString(d, name);
        WritableUtils.writeString(d, date);
        WritableUtils.writeVInt(d, volume);
        d.writeDouble(stockPriceAdj);
    }

    @Override
    public void readFields(DataInput di) throws IOException {
        name = WritableUtils.readString(di);
        date = WritableUtils.readString(di);
        volume = WritableUtils.readVInt(di);
        stockPriceAdj = di.readDouble();
    }

    @Override
    public int compareTo(CustomKeyWritable o) {
        int result = volume.compareTo(o.volume);
//        if(result == 0)
//        {
//            result = lastName.compareTo(o.lastName);
//        }
        return result;
    }
    
    public String toString()
    {
        return (new StringBuilder().append(name).append("\t").append(date)
                .append("\t").append(volume).append("\t").append(stockPriceAdj)
                .toString());
    }
    
}
