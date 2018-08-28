package com.appodeal.support.test.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.NativeAd;
import com.appodeal.ads.native_ad.views.NativeAdViewAppWall;
import com.appodeal.ads.native_ad.views.NativeAdViewContentStream;
import com.appodeal.support.test.R;
import com.appodeal.support.test.mvp.model.Weather;
import com.squareup.picasso.Picasso;

import java.util.List;

class ListViewHolder extends RecyclerView.ViewHolder {

    TextView textView;
    ImageView imageView;
    Context context;
    LinearLayout linearLayout;

    public ListViewHolder(Context context,View itemView) {
        super(itemView);
        this.context = context;
        textView = itemView.findViewById(R.id.weather_name);
        imageView = itemView.findViewById(R.id.weather_image);
        linearLayout = itemView.findViewById(R.id.card_linear_layout);
    }
}

public class ListWeatherAdapter extends RecyclerView.Adapter<ListViewHolder> {
    private List<Weather> weatherList;
    private NativeAdViewContentStream contentStream;

    public ListWeatherAdapter(List<Weather> weathers) {
        this.weatherList = weathers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.weather_card_layout, parent, false);
        return new ListViewHolder(inflater.getContext(),itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {


        if (position == 3 && !Appodeal.getNativeAds(1).isEmpty()) { //for example replace on native
            NativeAdViewAppWall nativeAdView = new NativeAdViewAppWall(holder.context, Appodeal.getNativeAds(1).get(0));
            holder.linearLayout.addView(nativeAdView);

        } else {
            holder.textView.setText(weatherList.get(position).getName());
            holder.imageView.setImageResource(weatherList.get(position).getIdImage());
        }
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }
}



