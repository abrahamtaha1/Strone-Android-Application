package com.example.strone_v0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button gologPg;
    Button crtAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Going to the login page using the login button in Activity
        gologPg = findViewById(R.id.gologPg);
        gologPg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, LoginPage.class);
                startActivity(intent);
                finish();
            }
        });



        crtAcc = findViewById(R.id.crtAcc);
        crtAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterPage.class);
                startActivity(intent);
                finish();
            }
        });



    }
}