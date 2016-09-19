package com.example.manar_000.myapplication.app;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    ArrayAdapter<String> ReviewAdaptor;
    ArrayAdapter<String> TrailerAdapter;
    ArrayList<String> TrailerArray ;
    ArrayList<String> MovieReviews;
    ListView listView;
    MovieInfo movieObject ;
    RequestQueue queue;

    public DetailActivityFragment() {
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        Intent intent = getActivity().getIntent();
        final View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        if(bundle != null){
            movieObject =(MovieInfo)bundle.getSerializable("MovieObject");

        }else{
            movieObject =(MovieInfo)intent.getSerializableExtra("MovieObject");

        }
            ((TextView) rootView.findViewById(R.id.Title)).setText(movieObject.getTitle());
            ((TextView) rootView.findViewById(R.id.Overview)).setText(movieObject.getOverview());
            ((TextView) rootView.findViewById(R.id.Release_Date)).setText(movieObject.getReleaseDate());

            ImageView imageview = (ImageView) rootView.findViewById(R.id.poster_path);
            Picasso.with(getContext())
                    .load("http://image.tmdb.org/t/p/"+"w780"+movieObject.getPosterPath())
                    .into(imageview);

            queue = Volley.newRequestQueue(getContext());
            Button reviewButton = (Button) rootView.findViewById(R.id.reviewButton);
            reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                      Log.e("ButtonOnClick", "reviewButton is Clicked");
                      fetchReview(rootView);
            }
        });

        Button trailerButton = (Button) rootView.findViewById(R.id.trailerButton);
        trailerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("ButtonOnClick", "trailerButton is Clicked");
                fetchTrailer(rootView);
            }
        });

        return rootView;
    }

    public  void fetchReview(final View rootView){

        Log.e("The Movie ID" , movieObject.getID());
        String ReviewURL = "http://api.themoviedb.org/3/movie/"+movieObject.getID()+"/reviews?api_key=7448f1f9463135f0ceb2575d2fd1be5d";
        Log.e("The Review URL" , ReviewURL);
        JsonObjectRequest jsonObjectRequestReview = new JsonObjectRequest(Request.Method.GET,ReviewURL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("on response ", response.toString());

                        ReviewAdaptor = new ArrayAdapter<String>(
                                getActivity(),
                                R.layout.list_item_review,
                                R.id.list_item_review_textview,
                                new ArrayList<String>());

                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            MovieReviews = new ArrayList<String>();

                            Log.e("JsonArray Lenght", jsonArray.length() + "");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String Author = jsonObject.getString("author");
                                String Content = jsonObject.getString("content");
                                Review newReview = new Review();
                                newReview.setAuthor(Author);
                                newReview.setContent(Content);
                                MovieReviews.add(newReview.toString());

                            }


                            Log.e("The Review ArrayList", MovieReviews.toString());

                        } catch (JSONException e) {
                            Log.e("Catch", "Review Try Failed");
                            e.printStackTrace();
                        }


                        String Review ="";
                        for (int i = 0; i <MovieReviews.size() ; i++) {
                            Review += MovieReviews.get(i);
                        }

                       // listView.setAdapter(ReviewAdaptor);
                       // Log.e("The reviewAdapter", ReviewAdaptor.getCount()+"");
                        ((TextView) rootView.findViewById(R.id.textView)).setText(Review);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(),"Error" , Toast.LENGTH_SHORT);
                error.printStackTrace();
            }
        });

        queue.add(jsonObjectRequestReview);

    }

    public  void fetchTrailer(final View rootView) {

        String TrailerURL = "http://api.themoviedb.org/3/movie/"+movieObject.getID()+"/videos?api_key=7448f1f9463135f0ceb2575d2fd1be5d";
        Log.e("The Trailer URL" , TrailerURL);
        JsonObjectRequest jsonObjectRequestTrailer = new JsonObjectRequest(Request.Method.GET,TrailerURL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("on response ",response.toString());

                        String Trailer = "";
                        try{
                            JSONArray jsonArray=response.getJSONArray("results");
                            TrailerArray  = new ArrayList<String>();
                            Log.e("JsonArray Lenght" , jsonArray.length()+"");

                                String Key = "";
                                JSONObject jsonObject=jsonArray.getJSONObject(0);
                                Key = jsonObject.getString("key");
                                Trailer = "https://www.youtube.com/watch?v="+Key+"\n\n";

                            Log.e("The Trailer Array List" , TrailerArray.toString());
                        }catch (JSONException e){
                            Log.e("Catch" , "Trailer Try Failed");
                            e.printStackTrace();
                        }
                        Intent intent=new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(Trailer));
                        startActivity(intent);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(),"Error" , Toast.LENGTH_SHORT);
                error.printStackTrace();
            }
        });

        queue.add(jsonObjectRequestTrailer);
    }

        @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // inflater.inflate(R.menu.detailfragment, menu);
    }

}
