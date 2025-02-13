package com.example.weatherly;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
RelativeLayout main,actual;
ProgressBar progress;
TextView cityname1,temperature,condition,forecast;
RecyclerView weather_recycle;
ImageView weatherimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        main=findViewById(R.id.main);
        actual=findViewById(R.id.actual);
        progress=findViewById(R.id.progress);
        cityname1=findViewById(R.id.cityname1);
        temperature=findViewById(R.id.temperature);
        condition=findViewById(R.id.condition);
        forecast=findViewById(R.id.forecast);
        weather_recycle=findViewById(R.id.weather_recycle);
        weatherimage=findViewById(R.id.weatherimage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}