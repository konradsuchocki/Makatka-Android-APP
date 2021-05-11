
package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        final String[] amount = new String[1];
        SharedPreferences sp;
        sp = getSharedPreferences("Usernameprefs", Context.MODE_PRIVATE);
        String actualUsername = sp.getString("username","");

        TextView moneyLeft = (TextView) findViewById(R.id.moneyLeft);

        RequestQueue queue = Volley.newRequestQueue(UserActivity.this);
        StringRequest sr = new StringRequest(Request.Method.POST,"http://172.20.10.12/user_update.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                try {
                    JSONObject jsonObject = new JSONObject(response);

                    moneyLeft.setText("Pozostało Ci : " + jsonObject.getString("money")+" zł.");


                }
                catch (JSONException e) {
                    //  Toast.makeText(getApplicationContext(),"Please type both, username and password", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),"Błąd" + e.toString(), Toast.LENGTH_SHORT).show();
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
                params.put("actual_username",actualUsername);
//                params.put("amount","");


                return params;
            }
        };

        queue.add(sr);




        Button btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(v -> {
            {
                sp.edit().putBoolean("isLoggedIn", false).apply();
                sp.edit().putBoolean("isAdmin", false).apply();
                String logged = String.valueOf(sp.getBoolean("isLogged", false));
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);


            }

        });
    }
}