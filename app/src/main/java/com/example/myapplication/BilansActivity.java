package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class BilansActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bilans);

        TextView maciej_value = findViewById(R.id.text_maciej);
        TextView konrad_value = findViewById(R.id.text_konrad);
        TextView piotr_value = findViewById(R.id.text_piotr);
        TextView sum_value = findViewById(R.id.text_sum);
        NumberPicker picker = findViewById(R.id.numberPicker);
        Button btn_refresh = findViewById(R.id.btn_refresh);



        picker.setMinValue(1);
        picker.setMaxValue(12);
        String [] months = {"Styczeń","Luty","Marzec","Kwiecień","Maj","Czerwiec","Lipiec","Sierpień","Wrzesień","Październik","Listopad","Grudzień"};
        picker.setDisplayedValues(months);

        btn_refresh.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String month_number = String.valueOf(picker.getValue());
                RequestQueue queue = Volley.newRequestQueue(BilansActivity.this);
                StringRequest sr = new StringRequest(Request.Method.POST,"http://172.20.10.12/moneycollected.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getString("maciej") != "null")
                                maciej_value.setText(jsonObject.getString("maciej"));
                            else{
                                maciej_value.setText("0");
                            }
                            if(jsonObject.getString("konrad") != "null")
                                konrad_value.setText(jsonObject.getString("konrad"));
                            else{
                                konrad_value.setText("0");
                            }
                            if(jsonObject.getString("piotr") != "null")
                                piotr_value.setText(jsonObject.getString("piotr"));
                            else{
                                piotr_value.setText("0");
                            }


//
                            Integer sum = Integer.valueOf(maciej_value.getText().toString()) + Integer.valueOf(konrad_value.getText().toString()) + Integer.valueOf(piotr_value.getText().toString());
                            String sum_string = sum.toString();
                            sum_value.setText("Suma : " + sum_string + "zł");
                            Toast.makeText(getApplicationContext(),month_number ,Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.toString() ,Toast.LENGTH_SHORT).show();
                    }
                })
                {
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("month",month_number);
                        return params;
                    }
                };

                queue.add(sr);
            }
        });









    }
}