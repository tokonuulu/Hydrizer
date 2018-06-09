package com.example.akylbektokonuulu.hydrizer;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;

public class history {
    LinkedList <history_entry> List;

    public void initialize (Context context) {
        String ret = "";

        try {
            List = new LinkedList<history_entry>();
            InputStream inputStream = context.openFileInput("history.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);

                    if (receiveString.length() > 0) {
                        String[] temp = receiveString.split(" ");
                        history_entry tmp = new history_entry();

                        tmp.day = Integer.parseInt(temp[0]);
                        tmp.month = Integer.parseInt(temp[1]);
                        tmp.year = Integer.parseInt(temp[2]);
                        tmp.drunk = Integer.parseInt(temp[3]);
                        tmp.goalReached = Integer.parseInt(temp[4]) == 1;

                        List.addLast(tmp);
                    }
                }

                inputStream.close();
                //ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
    }

    public void update (Context context, int day, int month, int year, int amt, boolean reached) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("history.txt", Context.MODE_APPEND));
            outputStreamWriter.write(String.valueOf(day) + " " + String.valueOf(month) + " " + String.valueOf(year) + " "
                                        +String.valueOf(amt) + " " + (reached ? "1" : "0") + "\n");
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
