package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AkademiaActivity extends AppCompatActivity{

    private NumberPicker picker;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akademia);

        picker = findViewById(R.id.numberPicker);
        TextView add_value = (TextView) findViewById(R.id.akademia_value);


        sp = getSharedPreferences("Usernameprefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();


          String akademia_usernames = sp.getString("akademia_usernames","");

        JSONArray arr = null;

        try {
            arr = new JSONArray(akademia_usernames);
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

Button btn3 = findViewById(R.id.btn_akademia_payment);

    btn3.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String actualUsername = userlist[picker.getValue()];
            String myusername = sp.getString("username","");
            RequestQueue queue = Volley.newRequestQueue(AkademiaActivity.this);
            StringRequest sr = new StringRequest(Request.Method.POST,"http://172.20.10.12/user_update.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),"Wpłacono "+add_value.getText().toString()+" zł dla "+actualUsername.toString(), Toast.LENGTH_SHORT).show();
                        add_value.setText(null);
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
                    params.put("actual_username",actualUsername);
                    params.put("amount",add_value.getText().toString());
                    params.put("myusername",myusername);


                    return params;
                }
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("Content-Type","application/x-www-form-urlencoded");
                    return params;
                }
            };

            queue.add(sr);
        }
    });


    }

}