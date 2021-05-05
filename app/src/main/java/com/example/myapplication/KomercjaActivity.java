package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class KomercjaActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_komercja);


        EditText paymentValue = (EditText) findViewById(R.id.paymentValue);
        SharedPreferences sp = getApplicationContext().getSharedPreferences("Usernameprefs", Context.MODE_PRIVATE);

        String actual_username = sp.getString("username","");


















        Button btn_payment = findViewById(R.id.btn_payment);
        btn_payment.setOnClickListener(v -> {
            {


                RequestQueue queue = Volley.newRequestQueue(KomercjaActivity.this);
                StringRequest sr = new StringRequest(Request.Method.POST,"http://172.20.10.13/payment.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.names().get(0).equals("success")) {
                                Toast.makeText(getApplicationContext(),"Payment added", Toast.LENGTH_SHORT).show();


                            }

                        }
                        catch (JSONException e) {
                            //  Toast.makeText(getApplicationContext(),"Please type both, username and password", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
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
                        params.put("username","komercja");
                        params.put("moneyCollected",paymentValue.getText().toString());
                        params.put("actual_username",actual_username);


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

            }});





    }

        }




