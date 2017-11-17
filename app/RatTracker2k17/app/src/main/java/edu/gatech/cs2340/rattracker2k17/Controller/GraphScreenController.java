package edu.gatech.cs2340.rattracker2k17.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
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
import edu.gatech.cs2340.rattracker2k17.Service.RatSpottingBL;

/** Controller for graph screen
 * @author Justin Z
 * @version 1.0
 */
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
        RatSpottingBL ratBL = new RatSpottingBL();
        Map<String, Integer> mapValues = ratBL.parseRatData(ratSpottings);
        Log.d(LOG_ID, "map: " + mapValues.toString());
        ArrayList<BarEntry> entries = createBarEntries(mapValues.values().toArray(new Integer[0]));
        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        BarDataSet dataSet = new BarDataSet(entries, "# of spottings");
        dataSet.setValueTextColor(getResources().getColor(R.color.colorAccent));
        dataSet.setValueTextSize(24f);
        dataSet.setColor(getResources().getColor(R.color.black));
        Log.d(LOG_ID, dataSet.toString());
        ArrayList<String> labels = new ArrayList<>(mapValues.keySet());
        BarChart chart = new BarChart(this);
        chart.setData(new BarData(dataSet));
        chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        chart.getXAxis().setGranularity(1f);
        chart.getXAxis().setGranularityEnabled(true);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        chart.getXAxis().setTextColor(getResources().getColor(R.color.colorAccent));
        chart.getDescription().setEnabled(false);
        chart.invalidate();
        setContentView(chart);
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
