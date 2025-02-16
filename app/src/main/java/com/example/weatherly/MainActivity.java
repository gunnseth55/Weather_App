package com.example.weatherly;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
RelativeLayout main,actual;
ProgressBar progress;
TextView cityname1,temperaturetext,condition,forecast;
RecyclerView weather_recycle;
ArrayList<WeatherModel> weatherModelArrayList;
WeatherAdapter weatherAdapter;
ImageView search,weatherimage;
LocationManager locationManager;
String cityname;
TextInputEditText text2;
int PERMISSION=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        main=findViewById(R.id.main);
        actual=findViewById(R.id.actual);
        progress=findViewById(R.id.progress);
        cityname1=findViewById(R.id.cityname1);
        temperaturetext=findViewById(R.id.temperaturetext);
        condition=findViewById(R.id.condition);
        search=findViewById(R.id.search);
        forecast=findViewById(R.id.forecast);
        text2=findViewById(R.id.text2);
        weather_recycle=findViewById(R.id.weather_recycle);
        weatherimage=findViewById(R.id.weatherimage);
        weatherModelArrayList=new ArrayList<>();
        weatherAdapter=new WeatherAdapter(this,weatherModelArrayList);
        weather_recycle.setAdapter(weatherAdapter);
        locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},PERMISSION);
        }
        Location location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        cityname=getCity(location.getLatitude(),location.getLongitude());
        getWeatherInfo(cityname);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city=text2.getText().toString();
                if(city.isEmpty()){
                    Toast.makeText(MainActivity.this, "Enter your City name", Toast.LENGTH_SHORT).show();
                }else{
                    cityname1.setText(city);
                    getWeatherInfo(city);
                }
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==PERMISSION){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "PLEASE PROVIDE THE PERMISSION", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

    }

    String getCity(double latitude, double longitude){
        String cityname="notfound";
         Geocoder gcd=new Geocoder(getBaseContext(), Locale.getDefault());
         try{

             List<Address> addresses=gcd.getFromLocation(latitude,longitude,10);
             for(Address adr:addresses){
                 if(adr!=null){
                     String city=adr.getLocality();
                     if(city!=null && !city.equals("")){
                        cityname=city;
                     }else{
                         Log.d("TAG","city not found");
                         Toast.makeText(this, "USER CITY NOT FOUND", Toast.LENGTH_SHORT).show();
                     }
                 }
             }
         }catch(IOException e) {
             e.printStackTrace();
         }
         return cityname;
     }

     void getWeatherInfo(String city){
        String url="http://api.weatherapi.com/v1/current.json?key=526e264c022443d9a5c45744251602&q="+city+"&aqi=no";
        cityname1.setText(city);
         RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
         JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
             @Override
             public void onResponse(JSONObject response) {
                 loadingPB.setVisibilty(View.GONE);
                 homeRl.setVisibilty(View.VISIBLE);
                weatherModelArrayList.clear();

                 try {
                     String temperature= response.getJSONObject("current").getString("temp_c");
                     temperaturetext.setText(temperature+ "°C");
                 } catch (JSONException e) {
                     throw new RuntimeException(e);
                 }

             }
         }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {
                 Toast.makeText(MainActivity.this, "Please enter valid city name", Toast.LENGTH_SHORT).show();
             }
         });
         requestQueue.add(jsonObjectRequest);
     }
}