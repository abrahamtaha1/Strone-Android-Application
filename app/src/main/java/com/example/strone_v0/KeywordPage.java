package com.example.strone_v0;

import static java.lang.Math.log;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class KeywordPage extends AppCompatActivity{
    Button backBtn;
    Switch abeSwitch, jorSwitch, alarmSwitch;
    private boolean isAbeOn = false;
    private boolean isJorOn = false;
    private boolean isAlarmOn = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyword_page);
        SwitchAbe();
        SwitchAlarm();
        SwitchJor();
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KeywordPage.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });

    }
/*
    private void ClickAbe() {
        this.abeBtn = findViewById(R.id.abeBtn);
        this.abeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAbeOn = !isAbeOn;
                Toast.makeText(KeywordPage.this, "im CLicked", Toast.LENGTH_SHORT).show();
                PairingPage.remoteControl.Abe_Switch(isAbeOn);
            }
        });
    }

    private void ClickJor() {
        this.jorBtn = findViewById(R.id.jorBtn);
        this.jorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isJorOn = !isJorOn;
                Toast.makeText(KeywordPage.this, "im CLicked", Toast.LENGTH_SHORT).show();
                PairingPage.remoteControl.Jor_Switch(isJorOn);
            }
        });
    }

    private void ClickAlarm() {
        this.alarmBtn = findViewById(R.id.alarmBtn);
        this.alarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAlarmOn = !isAlarmOn;
                Toast.makeText(KeywordPage.this, "im CLicked", Toast.LENGTH_SHORT).show();
                PairingPage.remoteControl.Alarm_Switch(isAlarmOn);
            }
        });
    }
*/
    private void SwitchAbe() {
        this.abeSwitch = findViewById(R.id.abeSwitch);
        this.abeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isAbeOn = !isAbeOn;
                Toast.makeText(KeywordPage.this, "im switched", Toast.LENGTH_SHORT).show();
                PairingPage.remoteControl.Abe_Switch(isAbeOn);
            }

        });
    }

    private void SwitchJor() {
        this.jorSwitch = findViewById(R.id.jorSwitch);
        this.jorSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isJorOn = !isJorOn;
                Toast.makeText(KeywordPage.this, "im switched", Toast.LENGTH_SHORT).show();
                PairingPage.remoteControl.Jor_Switch(isJorOn);
            }

        });
    }

    private void SwitchAlarm() {
        this.alarmSwitch = findViewById(R.id.alarmSwitch);
        this.alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isAlarmOn = !isAlarmOn;
                Toast.makeText(KeywordPage.this, "im switched", Toast.LENGTH_SHORT).show();
                PairingPage.remoteControl.Alarm_Switch(isAlarmOn);
            }

        });
    }
}