package edu.gatech.cs2340.rattracker2k17.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import edu.gatech.cs2340.rattracker2k17.Model.RatSpotting;
import edu.gatech.cs2340.rattracker2k17.Model.RatSpottingsServiceTransfer;
import edu.gatech.cs2340.rattracker2k17.R;

public class GraphScreenController extends AppCompatActivity {
    private static final String LOG_ID = "GraphScreenController";
    private List<RatSpotting> ratSpottings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NonFullScreen);
        if (getIntent().getExtras() != null) {
            RatSpottingsServiceTransfer ratTransfer = (RatSpottingsServiceTransfer)
                    getIntent().getExtras().getSerializable("rats");
            if (ratTransfer != null) {
                ratSpottings = ratTransfer.getRatSpottings();
            } else {
                Log.d(LOG_ID, "The RatSpottingServiceTransfer was null.");
            }
        } else {
            Log.d(LOG_ID, "The calling Intent did not pass in any extras");
        }

        Map<String, Integer> mapValues = parseRatData();
        Log.d(LOG_ID, "map: " + mapValues.toString());
        ArrayList<BarEntry> entries = createBarEntries(mapValues.values().toArray(new Integer[0]));

        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        BarDataSet dataSet = new BarDataSet(entries, "# of spottings");
        Log.d(LOG_ID, dataSet.toString());
        ArrayList<String> labels = new ArrayList<>(mapValues.keySet());
        BarChart chart = new BarChart(this);
        chart.setData(new BarData(dataSet));
        chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        chart.getXAxis().setGranularity(1f);
        chart.getXAxis().setGranularityEnabled(true);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        chart.getXAxis().setTextColor(R.color.colorAccent);
        chart.getDescription().setEnabled(false);
        chart.invalidate();
        setContentView(chart);
    }

    private Map<String, Integer> parseRatData() {
        Log.d(LOG_ID, "onCreate():parseRatData(): has been started");
        Map<String, Integer> map = new HashMap<>();
        // Sort ratSpottings in descending order
        ratSpottings.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
        Log.d(LOG_ID, "ratSpottings size(): " + ratSpottings.size());
        for (int i = 0; i < ratSpottings.size();) {
            Log.d(LOG_ID, ratSpottings.get(i).toString());
            Calendar date = ratSpottings.get(i).getDate();
            int year = date.get(Calendar.YEAR);
            RatSpotting rat = ratSpottings.get(i);
            while (year == rat.getDate().get(Calendar.YEAR) && i < ratSpottings.size()) {
                Log.d(LOG_ID, Integer.toString(i));
                String key = String.format(Locale.getDefault(),"%s %d",
                        rat.getDate().getDisplayName(
                                Calendar.MONTH, Calendar.LONG, Locale.getDefault()),
                        year);
                if (map.containsKey(key)) {
                    map.put(key, map.get(key) + 1);
                } else {
                    map.put(key, 1);
                }
                i++;
                if (i < ratSpottings.size()) {
                    rat = ratSpottings.get(i);
                }
            }
        }
        Log.d(LOG_ID, "parseRatData():map: " + map.toString());
        return map;
    }

    private ArrayList<BarEntry> createBarEntries(Integer[] arr) {
        ArrayList<BarEntry> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            float val = (float) arr[i];
            list.add(new BarEntry(i, val));
        }
        Log.d(LOG_ID, "createBarEntires: " + list.toString());
        return list;
    }


}