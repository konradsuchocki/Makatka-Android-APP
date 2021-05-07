package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
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

public class MainActivity extends AppCompatActivity {


    SharedPreferences sp;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sp = getSharedPreferences("Usernameprefs", Context.MODE_PRIVATE);

        if(!sp.getString("username", "").isEmpty() && sp.getBoolean("isLoggedIn",false))
        {
            if(sp.getBoolean("isAdmin",false))
            {
                startActivity(new Intent(getApplicationContext(), AdminActivity.class));
            }
            else
                startActivity(new Intent(getApplicationContext(), UserActivity.class));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        EditText username = (EditText) findViewById(R.id.loginID);
        EditText password = (EditText) findViewById(R.id.pswdID);

        username.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                username.setHint("");
                return false;
            }
        });

        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    username.setHint("login");
                }
            }
        });


        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                password.setHint("");
                return false;
            }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    password.setHint("password");
                }
            }
        });


        Button btn = findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//


                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                SharedPreferences.Editor editor = sp.edit();
                StringRequest sr = new StringRequest(Request.Method.POST,"http://192.168.1.35/user_login.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                           JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.names().get(0).equals("admin")) {
                               Toast.makeText(getApplicationContext(),jsonObject.getString("admin"), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                                editor.putString("username", username.getText().toString());
                                editor.putBoolean("isLoggedIn", true);
                                editor.putBoolean("isAdmin", true);
                                editor.commit();

                            }
                            else if (jsonObject.names().get(0).equals("user")) {
                                Toast.makeText(getApplicationContext(),jsonObject.getString("user"), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), UserActivity.class));
                                editor.putString("username", username.getText().toString());
                                editor.putBoolean("isLoggedIn", true);
                                editor.commit();

                            } else if(jsonObject.names().get(0).equals("error_login")){
                                Toast.makeText(getApplicationContext(),"Wrong username", Toast.LENGTH_SHORT).show();

                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Wrong password", Toast.LENGTH_SHORT).show();

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

                        Toast.makeText(getApplicationContext(),"Please type both, username and password", Toast.LENGTH_SHORT).show();
                    }
                })
                {
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("username",username.getText().toString());
                        params.put("password",password.getText().toString());


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
};



