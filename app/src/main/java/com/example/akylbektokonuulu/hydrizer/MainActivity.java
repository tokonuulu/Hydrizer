package com.example.akylbektokonuulu.hydrizer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jesusm.holocircleseekbar.lib.HoloCircleSeekBar;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    last Last = new last();
    goal Goal = new goal();
    history History = new history();

    TextView amount;
    Button enter;
    Button hist;
    HoloCircleSeekBar picker;

    Calendar calendar = Calendar.getInstance();
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    int month = calendar.get(Calendar.MONTH);
    int year = calendar.get(Calendar.YEAR);

    ImageView healthy;
    ImageView bad1;
    ImageView bad2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amount = findViewById(R.id.amount);
        enter = findViewById(R.id.enter);
        picker = findViewById(R.id.picker);
        hist = findViewById(R.id.history);

        healthy = (ImageView) findViewById(R.id.healthy);
        bad1 = (ImageView) findViewById(R.id.bad1);
        bad2 = (ImageView) findViewById(R.id.bad2);

        /* Initialize classes */
        Last.initialize(this, amount);
        Goal.initialize(this);
        History.initialize(this);

        //Last.update(this, amount, day, month, year, 0);
        //Goal.update(this, 2200);

        /* updating history */
        if (day < Last.day ||
                month < Last.month ||
                year < Last.year) {
            History.update(this, Last.day, Last.month, Last.year, Last.Last, Last.Last > Goal.Goal);
            Last.update(this, amount, day, month, year, 0);
        }

        /* Select picture */
        if (Last.Last < Goal.Goal ) {
            int sz = History.List.size();
            switch (sz) {
                case 0:
                    healthy.setVisibility(View.VISIBLE);
                    bad1.setVisibility(View.INVISIBLE);
                    bad2.setVisibility(View.INVISIBLE);
                    break;
                case 1:
                    if (History.List.getLast().goalReached) {
                        healthy.setVisibility(View.VISIBLE);
                        bad1.setVisibility(View.INVISIBLE);
                        bad2.setVisibility(View.INVISIBLE);
                    } else {
                        bad1.setVisibility(View.VISIBLE);
                        healthy.setVisibility(View.INVISIBLE);
                        bad2.setVisibility(View.INVISIBLE);
                    }
                    break;
                default:
                    if (!History.List.getLast().goalReached && !History.List.get(sz - 1).goalReached) {
                        bad2.setVisibility(View.VISIBLE);
                        healthy.setVisibility(View.INVISIBLE);
                        bad1.setVisibility(View.INVISIBLE);
                    }
                    else if (History.List.getLast().goalReached && History.List.get(sz - 1).goalReached) {
                        bad1.setVisibility(View.INVISIBLE);
                        healthy.setVisibility(View.VISIBLE);
                        bad2.setVisibility(View.INVISIBLE);
                    }
                    else {
                        bad1.setVisibility(View.VISIBLE);
                        healthy.setVisibility(View.INVISIBLE);
                        bad2.setVisibility(View.INVISIBLE);
                    }
                    break;
            }
        }

        else {
            int sz = History.List.size();
            switch (sz) {
                case 0:
                    healthy.setVisibility(View.VISIBLE);
                    bad1.setVisibility(View.INVISIBLE);
                    bad2.setVisibility(View.INVISIBLE);
                    break;
                default:
                    if (History.List.getLast().goalReached) {
                        healthy.setVisibility(View.VISIBLE);
                        bad1.setVisibility(View.INVISIBLE);
                        bad2.setVisibility(View.INVISIBLE);
                    } else {
                        bad1.setVisibility(View.VISIBLE);
                        healthy.setVisibility(View.INVISIBLE);
                        bad2.setVisibility(View.INVISIBLE);
                    }
                    break;
            }
        }

        enter.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v){
                int tmp = picker.getValue();
                userEnter(tmp);
            }
        });

        hist.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                openHistory ();
            }
        });

        amount.setOnClickListener(new View.OnClickListener(){
            public void onClick (View view) {
                setGoal();
            }
        });

    }

    private void userEnter (int amt) {
        Last.update(this, amount, day, month, year, Last.Last + amt);
        if (Last.Last > Goal.Goal) {
            updateMan();
            congratulate();
        }
    }

    private void openHistory() {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }
    void setGoal () {
        startActivity(new Intent(this, goalActivity.class));
    }
    void congratulate () {
        startActivity(new Intent(this, pop.class));
    }
    void updateMan () {
        if (bad1.getVisibility() == View.VISIBLE) {
            bad1.setVisibility(View.INVISIBLE);
            healthy.setVisibility(View.VISIBLE);
        }
        if (bad2.getVisibility() == View.VISIBLE) {
            bad2.setVisibility(View.INVISIBLE);
            bad1.setVisibility(View.VISIBLE);
        }
    }
}
