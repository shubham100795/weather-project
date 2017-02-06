package com.example.android.weather_project;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.androidquery.AQuery;

import java.util.ArrayList;

/**
 * Created by shubham arora on 25-01-2017.
 */

public class forecastAdapter extends ArrayAdapter<forecast_data> {
    public forecastAdapter(Activity context, ArrayList<forecast_data> forecastlist) {
        super(context, 0, forecastlist);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        forecast_data currentword = getItem(position);
        TextView datetextview = (TextView) listItemView.findViewById(R.id.date);
        datetextview.setText(currentword.getdate());

        TextView maxtextview = (TextView) listItemView.findViewById(R.id.max);
        maxtextview.setText(currentword.getmax()+" \u2103");

        TextView mintextview = (TextView) listItemView.findViewById(R.id.min);
        mintextview.setText(currentword.getmin()+" \u2103");

        TextView condtextview = (TextView) listItemView.findViewById(R.id.cond);
        condtextview.setText(currentword.getcond());

        TextView humidtextview = (TextView) listItemView.findViewById(R.id.humid);
        humidtextview.setText(Double.toString(currentword.gethumidity())+"%");

        TextView windtextview = (TextView) listItemView.findViewById(R.id.speed);
        windtextview.setText(Double.toString(currentword.getwind())+" kmp");

        AQuery iq = new AQuery(listItemView);
        iq.id(R.id.icon).image(currentword.geturl());

        return listItemView;

    }
}
