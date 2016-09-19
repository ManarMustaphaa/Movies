package com.example.manar_000.myapplication.app;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by manar_000 on 8/12/2016.
 */
public interface AsyncFetchDataListener {

   void  onDataFetched(String str) throws JSONException;

}
