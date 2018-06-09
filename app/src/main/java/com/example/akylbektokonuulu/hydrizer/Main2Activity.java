package com.example.akylbektokonuulu.hydrizer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    BarChart chart;
    String curMonth = "May";
    history History = new history();

    Calendar calendar = Calendar.getInstance();
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    int month = calendar.get(Calendar.MONTH);
    int year = calendar.get(Calendar.YEAR);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        chart = (BarChart) findViewById(R.id.chart);

        History.initialize(this);

        //History.update(this, 17, 4, 2018, 2500, true);
        /*History.update(this, 18, 4, 2018, 800, false);
        History.update(this, 19, 4, 2018, 2000, true);
        History.update(this, 20, 4, 2018, 1500, false);
        History.update(this, 21, 4, 2018, 300, false);
        History.update(this, 22, 4, 2018, 2500, true);
        History.update(this, 23, 4, 2018, 800, false);
        History.update(this, 24, 4, 2018, 2000, true);
        History.update(this, 25, 4, 2018, 1500, false);
        History.update(this, 26, 4, 2018, 300, false);*/

        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return (String.valueOf((int) value) + " " + curMonth);
            }

            // we don't draw numbers, so no decimal digits needed
            /*@Override
            public int getDecimalDigits() {  return 0; }*/
        };

        Description description = new Description();

        description.setText("");
        description.setEnabled(false);
        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(2f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);
        chart.setDrawGridBackground(false);
        chart.setDrawBorders(false);
        chart.setNoDataText("History is not available");
        chart.setDescription(description);

        updateChart();
    }

    void updateChart () {
        List<BarEntry> entries = new ArrayList<>();
        for (int i=0; i<History.List.size(); i++) {
            history_entry tmp = History.List.get(i);

            if (tmp.month == month) {
                entries.add(new BarEntry((float)tmp.day, (float)tmp.drunk));
            }
        }

        BarDataSet set = new BarDataSet(entries, "Daily water intake");

        BarData data = new BarData(set);
        data.setValueTextSize(10f);
        chart.setData(data);
        chart.invalidate(); // refresh
    }
}
