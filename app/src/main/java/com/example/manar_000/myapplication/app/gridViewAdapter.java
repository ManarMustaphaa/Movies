package com.example.manar_000.myapplication.app;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by manar_000 on 8/12/2016.
 */
public class gridViewAdapter extends BaseAdapter {
    private Context mContext;
    ArrayList<MovieInfo> movieInfo ;
   // private final LayoutInflater Inflater;
    public gridViewAdapter(Context  passedContext , ArrayList<MovieInfo> movieInfo){
        //this.Inflater = LayoutInflater.from(passedContext);
        this.movieInfo = movieInfo;
        mContext=passedContext;
    }

    @Override
    public int getCount() {

        return movieInfo.size();
    }

    @Override
    public Object getItem(int position) {

        return movieInfo.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent ) {

        ImageView imageview ;
        if (convertView == null){

            convertView = ((Activity)(mContext)).getLayoutInflater().inflate(R.layout.imageview,null);
        }
        else {
            imageview = (ImageView)convertView;
        }

        imageview = (ImageView) convertView.findViewById(R.id.imageView);
        Log.e("PosterPath" ,"http://image.tmdb.org/t/p/"+"w185"+movieInfo.get(position).getPosterPath());
        Picasso.with(mContext)
                .load("http://image.tmdb.org/t/p/"+"w300"+movieInfo.get(position).getPosterPath())
                .into(imageview);
        return convertView;
    }
}
