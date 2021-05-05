package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.NumberPicker;

public class AkademiaActivity extends AppCompatActivity {

    private NumberPicker picker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akademia);

        picker = findViewById(R.id.numberPicker);
        picker.setMinValue(0);
    }
}