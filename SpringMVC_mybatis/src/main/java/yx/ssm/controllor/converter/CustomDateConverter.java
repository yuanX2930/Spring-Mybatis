package yx.ssm.controllor.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
/**
 * 日期转换器
 * @author Administrator
 *
 */
public class CustomDateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String arg0) {
        // 实现将日期串转换成日期类型
        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        try {
            date=sd.parse(arg0);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return date;
    }



}
