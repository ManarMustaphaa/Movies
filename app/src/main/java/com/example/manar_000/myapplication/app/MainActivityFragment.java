package com.example.manar_000.myapplication.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements DataParcerListener {

    public GridView gridview;
    public gridViewAdapter Adapter ;
    public jsonParser Parse;
    private TabletListener Listener ;

    public void setListener(TabletListener listener) {
        Listener = listener;
    }

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        super.onCreate(savedInstanceState);
        gridview = (GridView) rootView.findViewById(R.id.gridview);
        AsyncFetchData data = new AsyncFetchData();
        Parse = new jsonParser();
        data.setListener(Parse);
        Parse.setListener(this);
        data.execute("popular");
        return rootView;

    }

    @Override
    public void onDataParcer(ArrayList<MovieInfo> info) {

        final ArrayList<MovieInfo> ARRAY = info ;
        Adapter = new gridViewAdapter(getActivity() , info);
        gridview.setAdapter(Adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ((TabletListener)getActivity()).setSelectedMovie(ARRAY.get(position));

            }
        });
    }

    public void updateMovieList (){
        AsyncFetchData data = new AsyncFetchData();
        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(getActivity());

        String unitType = sharedPrefs.getString(("Sort"),
                getString(R.string.pref_sort_Most_popular));
        data.setListener(Parse);
        data.execute(unitType);
    }

    @Override
    public void onResume(){
        super.onResume();
        updateMovieList();
    }
}

