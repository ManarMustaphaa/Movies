package com.example.manar_000.myapplication.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements TabletListener{

    boolean mTwoPane = false ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
     //   setSupportActionBar(toolbar);

        FrameLayout frame = (FrameLayout) findViewById(R.id.movie_detail_container);
        if (frame != null){

            // The detail container view will be present only in the large-screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            mTwoPane = true;
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.

        }else{
            mTwoPane = false;
        }
        if(savedInstanceState == null){
            MainActivityFragment mainFragment = new MainActivityFragment();
            mainFragment.setListener(this);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_movie ,mainFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity( new Intent(this , SettingsActivity.class));
           // new MainActivityFragment().updateMovieList();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setSelectedMovie( MovieInfo movieinfo) {

        if(mTwoPane){
            DetailActivityFragment detailFragment = new DetailActivityFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("MovieObject",movieinfo);
            detailFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container ,detailFragment).commit();

        }else{
            Intent intent = new Intent(this , DetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("MovieObject",movieinfo);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}
