package com.example.practiconr2services;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private Intent intentService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkCallingOrSelfPermission(Manifest.permission.READ_SMS) != PackageManager.PERMISSION_DENIED){
            requestPermissions(new String[]{Manifest.permission.READ_SMS}, 1000);
        }
        //instancio un intent de la clase q extiende Servicio
        intentService = new Intent(this, MensajeService.class);
        //Inicio el servicio
        startService(intentService);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(intentService);// dentengo el servicio

    }
}