package com.example.android.weather_project;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static android.text.TextUtils.isEmpty;


public final class QueryUtils {

    public static String response;

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();
    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }


    public static condition fetchweatherdata(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        response=jsonResponse;
        // Extract relevant fields from the JSON response and create an {@link Event} object
       condition current_condition = extractFeatureFromJson(jsonResponse);


        if(current_condition==null)
        {
            return null;
        }
        // Return the {@link Event}
        return current_condition;
    }

    // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
    // is formatted, a JSONException exception object will be thrown.
    // Catch the exception so the app doesn't crash, and print the error message to the logs.
    @Nullable
    public static condition extractFeatureFromJson(String json_response)
    {
        condition current_condition=new condition("","","",0.0,"","","",0.0,"","");

        if (isEmpty(json_response)){
            return null;
        }
        try {

            JSONObject reader = new JSONObject(json_response);
            JSONObject location=reader.optJSONObject("location");
            JSONObject observation=reader.optJSONObject("current_observation");
            JSONObject sun=reader.optJSONObject("sun_phase");
            JSONObject rise=sun.optJSONObject("sunrise");
            JSONObject set=sun.optJSONObject("sunset");
            String city=location.optString("city");
            String state=location.optString("state");
            String country=location.optString("country_name");
            Double temperature=observation.optDouble("temp_c");
            String image=observation.optString("icon_url");
            String weather=observation.optString("weather");
            String humidity=observation.optString("relative_humidity");
            Double wind=observation.optDouble("wind_kph");
            String sunrise=rise.optString("hour")+":"+rise.optString("minute");
            String sunset=set.optString("hour")+":"+set.optString("minute");


            condition current=new condition(city,state,country,temperature,image,weather,humidity,wind,sunrise,sunset);
            current_condition=current;

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the weather JSON results", e);
        }

        return current_condition;
    }
    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            //urlConnection.setReadTimeout(10000 /* milliseconds */);
            // urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    @NonNull
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
    public static ArrayList<forecast_data> fetchForecastData()
    {
        ArrayList<forecast_data> forecastlist=new ArrayList<>();
        if (isEmpty(response)){
            return null;
        }
        try
        {
            JSONObject reader=new JSONObject(response);
            JSONObject forecast=reader.optJSONObject("forecast");
            JSONObject simpleforecast=forecast.optJSONObject("simpleforecast");
            JSONArray ar=simpleforecast.optJSONArray("forecastday");
            for(int i=0;i<ar.length();i++)
            {
                JSONObject obj=ar.optJSONObject(i);
                JSONObject date=obj.optJSONObject("date");
                int d=date.optInt("day");
                String m=date.optString("monthname_short");
                int y=date.optInt("year");
                String pf;//postfix
                if(d==1||d==21||d==31)
                {
                    pf="st";
                }
                else if(d==2||d==22)
                {
                    pf="nd";
                }
                else if(d==3||d==23)
                {
                    pf="rd";
                }
                else
                {
                    pf="th";
                }

                String dt=Integer.toString(d)+pf+" "+m+" "+Integer.toString(y);

                JSONObject high=obj.optJSONObject("high");
                String max=high.optString("celsius");

                JSONObject low=obj.optJSONObject("low");
                String min=low.optString("celsius");

                String cond=obj.optString("conditions");
                String icon=obj.optString("icon_url");

                Double humidity=obj.optDouble("avehumidity");

                JSONObject wind=obj.optJSONObject("avewind");
                Double speed=wind.optDouble("kph");

                forecastlist.add(new forecast_data(dt,max,min,cond,icon,humidity,speed));
            }

        }
        catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the weather JSON results", e);
        }
        return forecastlist;
    }

}