package com.example.manar_000.myapplication.app;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by manar_000 on 8/12/2016.
 */
public class AsyncFetchData extends AsyncTask <String , Void , String> {

    private AsyncFetchDataListener listener;
    private final String LOG_TAG = " ";

    public void setListener(AsyncFetchDataListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String MovieJsonStr = null;
        String MOVIE_BASE_URL = "";
        String KEY_PARAM = "";

        try {
            String line;
            //MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/popular?";
            if(params[0].equals("popular")) {
                MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/popular?";

            }
            else if(params[0].equals("top_rated")){
                MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/top_rated?";

            }

            KEY_PARAM = "api_key";
            Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                    .appendQueryParameter(KEY_PARAM, "7448f1f9463135f0ceb2575d2fd1be5d")
                    .build();
            Log.e("The Url",builtUri.toString());
            URL url = new URL(builtUri.toString());
            Log.v(LOG_TAG, "Built URI" + builtUri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //Read the input Stream into a string
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                //nth to do
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                return null;
            }
            MovieJsonStr = buffer.toString();
            Log.e("JsonString", MovieJsonStr);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error Closing stream", e);
                }
            }
        }
        return MovieJsonStr;
    }

    @Override
    protected void onPostExecute(String string) {
        super.onPostExecute(string);
        try {
            listener.onDataFetched(string);
        }catch (Exception e){
            Log.e( "onPostExecuteMessage","onPostExecute Error");
        }
    }
}