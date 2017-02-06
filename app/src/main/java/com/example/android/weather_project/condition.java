package com.example.android.weather_project;

/**
 * Created by shubham arora on 20-01-2017.
 */

public class condition {
    private String city;
    private String state;
    private String country;
    private double temperature;
    private String image;
    private String weather;
    private String humidity;
    private Double wind;
    private String sunrise;
    private String sunset;

    public condition(String cityy,String statee,String countryy,Double temp,String img,String weatherr,String humid,Double windd,String rise,String set)
    {
        city=cityy;
        state=statee;
        country=countryy;
        temperature=temp;
        image=img;
        weather=weatherr;
        humidity=humid;
        wind=windd;
        sunrise=rise;
        sunset=set;
    }

    public String getcity()
    {
        return city;
    }
    public String getstate()
    {
        return state;
    }
    public String getcountry()
    {
        return country;
    }
    public double gettemperature()
    {
        return temperature;
    }
    public String getimage()
    {
        return image;
    }
    public String getweather()
    {
        return weather;
    }
    public String gethumidity()
    {
        return humidity;
    }
    public Double getwind()
    {
        return wind;
    }
    public String getsunrise()
    {
        return sunrise;
    }
    public String getsunset()
    {
        return sunset;
    }
}
