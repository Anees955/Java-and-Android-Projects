package com.anees.callrecoder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
 private Intent intentService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intentService = new Intent(this, CallRecordingService.class);
        startService(intentService);
        Toast.makeText(this,"Recording Started",Toast.LENGTH_LONG).show();




    }
}
