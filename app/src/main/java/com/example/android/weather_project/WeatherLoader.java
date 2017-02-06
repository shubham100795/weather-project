package com.example.android.weather_project;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

/**
 * Created by shubham arora on 21-01-2017.
 */


public class WeatherLoader extends AsyncTaskLoader<condition> {

    private static final String LOG_TAG = WeatherLoader.class.getName();
    private String mUrl;
    condition  current_condition = new condition("","","",0.0,"","","",0.0,"","");

    public WeatherLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public condition loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        Log.i(LOG_TAG,"on loadinbackground method");

        current_condition=QueryUtils.fetchweatherdata(mUrl);
        return current_condition;

    }


}

