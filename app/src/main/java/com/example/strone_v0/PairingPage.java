package com.example.strone_v0;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PairingPage extends AppCompatActivity implements BLEControllerListener {
    Button backBtn;
    private TextView logView;
    private Button connectButton;
    private Button disconnectButton;
   // private Button switchLEDButton;

    public com.example.strone_v0.BLEController bleController;
    public static RemoteControl remoteControl;
    private String deviceAddress;

    private boolean isLEDOn = false;

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pairing_page);

        this.bleController = com.example.strone_v0.BLEController.getInstance(this);
        this.remoteControl = new RemoteControl(this.bleController);

        this.logView = findViewById(R.id.logView);
        this.logView.setMovementMethod(new ScrollingMovementMethod());

        initConnectButton();
        initDisconnectButton();
      //  initSwitchLEDButton();

        checkBLESupport();
        checkPermissions();

        //disableButtons();


        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PairingPage.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void initConnectButton() {
        this.connectButton = findViewById(R.id.connectButton);
        this.connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectButton.setEnabled(false);
                log("Connecting...");
                bleController.connectToDevice(deviceAddress);
            }
        });
    }

    private void initDisconnectButton() {
        this.disconnectButton = findViewById(R.id.disconnectButton);
        this.disconnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disconnectButton.setEnabled(false);
                log("Disconnecting...");
                bleController.disconnect();
            }
        });
    }
/*
    private void initSwitchLEDButton() {
        this.switchLEDButton = findViewById(R.id.switchButton);
        this.switchLEDButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLEDOn = !isLEDOn;
                remoteControl.switchLED(isLEDOn);
                log("LED switched " + (isLEDOn ? "On" : "Off"));
            }
        });
    }
    */

    private void disableButtons() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                connectButton.setEnabled(false);
                disconnectButton.setEnabled(false);
             //   switchLEDButton.setEnabled(false);
            }
        });
    }

    private void log(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                logView.setText(logView.getText() + "\n" + text);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            log("\"Access Fine Location\" permission not granted yet!");
            log("Whitout this permission Blutooth devices cannot be searched!");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    42);
        }

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.BLUETOOTH_SCAN)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.BLUETOOTH_SCAN},
                    42);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 42);
        }


    }

    private void checkBLESupport() {
        // Check if BLE is supported on the device.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "BLE not supported!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivityForResult(enableBTIntent, 1);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.deviceAddress = null;
        this.bleController = com.example.strone_v0.BLEController.getInstance(this);
        this.bleController.addBLEControllerListener(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            log("[BLE]\tSearching for Arduino Nano...");
            this.bleController.init();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.bleController.removeBLEControllerListener(this);
    }

    @Override
    public void BLEControllerConnected() {
        log("[BLE]\tConnected");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                disconnectButton.setEnabled(true);
                //switchLEDButton.setEnabled(true);
            }
        });
    }

    @Override
    public void BLEControllerDisconnected() {
        log("[BLE]\tDisconnected");
        disableButtons();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                connectButton.setEnabled(true);
            }
        });
        this.isLEDOn = false;
    }

    @Override
    public void BLEDeviceFound(String name, String address) {
        log("Device " + name + " found with address " + address);
        this.deviceAddress = address;
        this.connectButton.setEnabled(true);
    }
}