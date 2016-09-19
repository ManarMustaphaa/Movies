package com.example.manar_000.myapplication.app;

import android.util.Log;

import com.example.manar_000.myapplication.app.MovieInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by manar_000 on 8/12/2016.
 */
public class jsonParser implements AsyncFetchDataListener {

    ArrayList<MovieInfo> arrayOfDataFetched = new ArrayList<MovieInfo>();
    private  DataParcerListener Listener ;
    public jsonParser() {
    }

    public void setListener(DataParcerListener Listener) {
        this.Listener = Listener;
    }


    @Override
     public void onDataFetched(String MovieJsonStr)
            throws JSONException {

        if(MovieJsonStr != null) {

            JSONObject MovieJson = new JSONObject(MovieJsonStr);
            JSONArray MovieArray = MovieJson.getJSONArray("results");

            arrayOfDataFetched = new ArrayList<MovieInfo>();
            for (int i = 0; i < MovieArray.length(); i++) {

                MovieInfo info = new MovieInfo();
                JSONObject movieInfo = MovieArray.getJSONObject(i);
                info.setPosterPath(movieInfo.getString("poster_path"));
                info.setTitle(movieInfo.getString("title"));
                info.setOverview(movieInfo.getString("overview"));
                info.setReleaseDate(movieInfo.getString("release_date"));
                info.setVoteAvg(movieInfo.getString("vote_average"));
                info.setID(movieInfo.getString("id"));
                arrayOfDataFetched.add(info);
                //Log.e("posterPath", arrayOfDataFetched.get(i).getPosterPath());
                //Log.e("posterPath", arrayOfDataFetched.get(i).getOverview());
                //Log.e("posterPath", arrayOfDataFetched.get(i).getTitle());
               // Log.e("posterPath", arrayOfDataFetched.get(i).getVoteAvg());
              //  Log.e("posterPath", arrayOfDataFetched.get(i).getReleaseDate());

            }
        }

        Log.e("Array list length" , arrayOfDataFetched.size() + "");
        Listener.onDataParcer(arrayOfDataFetched);
    }
}
