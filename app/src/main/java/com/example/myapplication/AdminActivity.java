package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        SharedPreferences sp;
        sp = getSharedPreferences("Usernameprefs", Context.MODE_PRIVATE);





        Button btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(v -> {
            {
                sp.edit().putBoolean("isLoggedIn", false).apply();
                sp.edit().putBoolean("isAdmin", false).apply();
                String logged = String.valueOf(sp.getBoolean("isLogged", false));
                Toast.makeText(getApplicationContext(), logged, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);


            }

        });

        Button btn_adduser= findViewById(R.id.btn_adduser);
        btn_adduser.setOnClickListener(v -> {
            {
                Intent intent = new Intent(this, AddUserActivity.class);
                startActivity(intent);
            }

        });

        Button btn_komercja= findViewById(R.id.btn_komercja);
        btn_komercja.setOnClickListener(v -> {
            {
                Intent intent = new Intent(this, KomercjaActivity.class);
                startActivity(intent);
            }

        });

        Button btn_akademia= findViewById(R.id.btn_akademia);
        btn_akademia.setOnClickListener(v -> {
            {
                Intent intent = new Intent(this, AkademiaActivity.class);
                startActivity(intent);
            }

        });

        Button btn_bilans= findViewById(R.id.btn_bilans);
        btn_bilans.setOnClickListener(v -> {
            {
                Intent intent = new Intent(this, BilansActivity.class);
                startActivity(intent);
            }

        });
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        SharedPreferences sp;
        sp = getSharedPreferences("Usernameprefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        //Refresh your stuff here
        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://172.20.10.12/usernames.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        editor.putString("akademia_usernames", response.toString());
                        editor.apply();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }
}

