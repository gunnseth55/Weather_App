package com.example.weatherly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<WeatherModel> weatherModelArrayList;

    public WeatherAdapter(Context context, ArrayList<WeatherModel> weatherModelArrayList) {
        this.context = context;
        this.weatherModelArrayList = weatherModelArrayList;
    }

    @NonNull
    @Override
    public WeatherAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.weatherlayout, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull WeatherAdapter.MyViewHolder holder, int position) {
        WeatherModel model= weatherModelArrayList.get(position);
        holder.temperature.setText(model.getTemperature()+"\u00B0C");

        Picasso.get().load("http:".concat(model.getIcon())).into(holder.icon);
        holder.windspeed.setText(model.getWindspeed()+"Km/Hr");
        SimpleDateFormat input=new SimpleDateFormat("yyyy-MM-dd hh:mm");
        SimpleDateFormat output=new SimpleDateFormat("hh:mm aa");
        try {
            Date date=input.parse(model.getTime());
            holder.time.setText(output.format(date));

        }catch(ParseException e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return weatherModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView temperature,time,windspeed;
        private ImageView icon;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            temperature=itemView.findViewById(R.id.temperature);
            time=itemView.findViewById(R.id.time);
            windspeed=itemView.findViewById(R.id.windspeed);
            icon=itemView.findViewById(R.id.icon);
        }
    }
}
