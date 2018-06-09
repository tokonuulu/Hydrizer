package com.example.akylbektokonuulu.hydrizer;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Calendar;

public class last {
    int Last;
    int day, month, year;

    public void initialize (Context context, TextView textView) {
        Last = 0;
        day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        month = Calendar.getInstance().get(Calendar.MONTH);
        year = Calendar.getInstance().get(Calendar.YEAR);

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("last.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);

                    if (receiveString.length() > 0) {
                        String[] temp = receiveString.split(" ");
                        day = Integer.parseInt(temp[0]);
                        month = Integer.parseInt(temp[1]);
                        year = Integer.parseInt(temp[2]);
                        Last = Integer.parseInt(temp[3]);
                    }
                }

                inputStream.close();
                textView.setText(String.valueOf(Last) + " ml");
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
    }

    public void update (Context context, TextView textView, int d, int m, int y, int amt) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("last.txt", Context.MODE_PRIVATE));
            day = d;
            month = m;
            year = y;
            Last = amt;
            String data = String.valueOf(day) + " " + String.valueOf(month) + " " + String.valueOf(year) + " " + String.valueOf(Last);
            outputStreamWriter.write(data);
            outputStreamWriter.close();
            textView.setText(String.valueOf(Last) + " ml");
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

}
