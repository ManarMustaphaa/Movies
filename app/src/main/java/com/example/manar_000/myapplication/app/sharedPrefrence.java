package com.example.manar_000.myapplication.app;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by manar_000 on 9/19/2016.
 */
public class sharedPrefrence {
    public sharedPrefrence() {
        super();

    }
    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String FAVORITES = "Product_Favorite";

    public void saveFavorites(Context context, List<MovieInfo> favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();
        String jsonFavorites=null;
        Gson Json = new Gson();
        jsonFavorites = Json.toJson(favorites);


        editor.putString(FAVORITES, jsonFavorites);

        editor.commit();
    }
    public void addFavorite(Context context, MovieInfo Movie) {
        ArrayList<MovieInfo> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<MovieInfo>();
        favorites.add(Movie);
        saveFavorites(context, favorites);
    }


    public ArrayList<MovieInfo> getFavorites(Context context) {
        SharedPreferences settings;
        List<MovieInfo> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            MovieInfo[] favoriteItems = gson.fromJson(jsonFavorites, MovieInfo[].class);

            favorites = Arrays.asList(favoriteItems);


            favorites = new ArrayList<MovieInfo>(favorites);

        } else
            return null;

        return (ArrayList<MovieInfo>) favorites;

    }

}

