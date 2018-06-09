package com.example.akylbektokonuulu.hydrizer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class goalActivity extends AppCompatActivity {
    goal Goal = new goal();

    EditText weight, exercise;
    Button calc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        weight = findViewById(R.id.weight);
        exercise = findViewById(R.id.excer);
        calc = findViewById(R.id.button);

        Goal.initialize(this);

        weight.setInputType(InputType.TYPE_NULL);
        exercise.setInputType(InputType.TYPE_NULL);

        exercise.setInputType(InputType.TYPE_CLASS_TEXT);
        exercise.requestFocus();
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.showSoftInput(exercise, InputMethodManager.SHOW_FORCED);

        weight.setInputType(InputType.TYPE_CLASS_TEXT);
        weight.requestFocus();
        InputMethodManager mgr2 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr2.showSoftInput(weight, InputMethodManager.SHOW_FORCED);

        calc.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v){
                if (weight.toString().isEmpty() || exercise.toString().isEmpty()) {
                    Toast.makeText(goalActivity.this, "Please fill in the blanks!", Toast.LENGTH_SHORT).show();
                }
                else {
                    String a, b;
                    double tmp;
                    a = weight.getText().toString();
                    b = exercise.getText().toString();

                    try {
                        tmp = (Double.parseDouble(a) * 1000.0 / 30.0);
                        tmp += (Double.parseDouble(b) * 350.0);
                        Toast.makeText(goalActivity.this, "Your goal is set to: " + String.valueOf((int)tmp) +"ml!", Toast.LENGTH_SHORT).show();
                        Goal.update(goalActivity.this, (int) tmp);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(goalActivity.this, "Input is not a number!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
