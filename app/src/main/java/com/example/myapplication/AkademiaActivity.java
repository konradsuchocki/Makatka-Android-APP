package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.NumberPicker;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AkademiaActivity extends AppCompatActivity {

    private NumberPicker picker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akademia);

        picker = findViewById(R.id.numberPicker);

       //List<String> userlist = Arrays.asList("adam", "konrad", "lawryn", "woda");


        String jsonarray = "[{\"username\":\"adam\"},{\"username\":\"tomasz\"},{\"username\":\"komercja\"},{\"username\":\"krysia\"},{\"username\":\"lawryn\"},{\"username\":\"konrad\"},{\"username\":\"becek123\"},{\"username\":\"konradek\"},{\"username\":\"woda\"}]";
        JSONArray arr = null;
        try {
            arr = new JSONArray(jsonarray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        List<String> list = new ArrayList<String>();
        for(int i = 0; i < arr.length(); i++){
            try {
                list.add(arr.getJSONObject(i).getString("username"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        picker.setMinValue(0);
        picker.setMaxValue(arr.length()-1);
        String [] userlist = list.toArray(new String[0]);
        picker.setDisplayedValues(userlist);





    }
}