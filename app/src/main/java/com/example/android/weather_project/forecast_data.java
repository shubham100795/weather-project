package com.example.android.weather_project;

/**
 * Created by shubham arora on 25-01-2017.
 */

public class forecast_data {
    private String date;
    private String high;
    private String low;
    private String condition;
    private String url;
    private Double humidity;
    private Double wind;

    public forecast_data(String datee,String max,String min,String cond,String img,Double humid,Double speed)
    {
        date=datee;
        high=max;
        low=min;
        condition=cond;
        url=img;
        humidity=humid;
        wind=speed;
    }

    public String getdate()
    {
        return date;
    }
    public String getmax()
    {
        return high;
    }
    public String getmin()
    {
        return low;
    }
    public String getcond()
    {
        return condition;
    }
    public String geturl()
    {
        return url;
    }
    public Double gethumidity()
    {
        return humidity;
    }
    public Double getwind()
    {
        return wind;
    }
}
