package com.example.android.weather_project;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class forecast extends AppCompatActivity {

    TextView mEmptyStateTextView;
    forecastAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forecast);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        mAdapter = new forecastAdapter(this, new ArrayList<forecast_data>());
        ListView lv = (ListView) findViewById(R.id.list);
        lv.setEmptyView(mEmptyStateTextView);
        MYAsyncTask task = new MYAsyncTask();
        task.execute();
    }

    private class MYAsyncTask extends AsyncTask<Void, Void, ArrayList<forecast_data>> {
        @Override
        protected ArrayList<forecast_data> doInBackground(Void... params) {

            ArrayList<forecast_data> forecastlist = QueryUtils.fetchForecastData();
            if (forecastlist == null) {
                return null;
            }
            return forecastlist;
        }

        protected void onPostExecute(ArrayList<forecast_data> forecastlist) {
            mAdapter.clear();
            ProgressBar spin = (ProgressBar) findViewById(R.id.loading_spinner);
            spin.setVisibility(View.GONE);
            mEmptyStateTextView.setText("NO DATA FOUND");
            if (forecastlist != null && !forecastlist.isEmpty()) {
                ListView lv = (ListView) findViewById(R.id.list);
                mAdapter.addAll(forecastlist);
                lv.setAdapter(mAdapter);
            }
        }


    }
}
