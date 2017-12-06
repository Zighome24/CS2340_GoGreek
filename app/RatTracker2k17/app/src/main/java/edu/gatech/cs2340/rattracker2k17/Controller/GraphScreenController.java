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
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;

import edu.gatech.cs2340.rattracker2k17.Model.Month;
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

        List<Month> months = parseRatData();
        Log.d(LOG_ID, "months: " + months.toString());
        ArrayList<BarEntry> entries = createBarEntries(months);
        BarDataSet dataSet = new BarDataSet(entries, "# of spottings");
        dataSet.setValueTextColor(getResources().getColor(R.color.colorAccent));
        dataSet.setValueTextSize(24f);
        dataSet.setColor(getResources().getColor(R.color.black));
        Log.d(LOG_ID, dataSet.toString());
        ArrayList<String> labels = getMonthLabels(months);
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

    @SuppressWarnings("FeatureEnvy")
    private List<Month> parseRatData() {
        Log.d(LOG_ID, "onCreate():parseRatData(): has been started");
        Map<String, Integer> map = new HashMap<>();
        List<Month> months = new ArrayList<>();
        // Sort ratSpottings in descending order
        ratSpottings.sort(Comparator.comparing(RatSpotting::getDate));
        Log.d(LOG_ID, "ratSpottings size(): " + ratSpottings.size());
        Queue<String> order = new LinkedList<>();
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
                    order.add(key);
                }
                i++;
                if (i < ratSpottings.size()) {
                    rat = ratSpottings.get(i);
                }
            }
        }
        for (String key : order) {
            int spottings = map.get(key);
            String[] date = key.split(" ");
            months.add(new Month(date[0], date[1], spottings));
        }
        Log.d(LOG_ID, "parseRatData():map: " + map.toString());
        return months;
    }

    private ArrayList<BarEntry> createBarEntries(List<Month> months) {
        ArrayList<BarEntry> list = new ArrayList<>();
        for (int i = 0; i < months.size(); i++) {
            float val = (float) months.get(i).getRatspottings();
            list.add(new BarEntry(i, val));
        }
        Log.d(LOG_ID, "createBarEntires: " + list.toString());
        return list;
    }

    private ArrayList<String> getMonthLabels(List<Month> months) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < months.size(); i++) {
            list.add(months.get(i).getMonth() + " " + months.get(i).getYear());
        }
        Log.d(LOG_ID, "getMonthLabels: " + list.toString());
        return list;
    }


}
