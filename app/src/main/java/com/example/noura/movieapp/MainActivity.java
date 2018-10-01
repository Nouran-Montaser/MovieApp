package com.example.noura.movieapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view) RecyclerView movieRecyclerView;

    private static final String MY_PREFS_NAME = "MyPrefsFile";
    private static final int SETTINGS_RESULT = 9999;
    private GridLayoutManager gridLayoutManager;
    private ArrayList<Movie> details;
    private MovieAdapter movieAdapter;
//    private RecyclerView movieRecyclerView;
    private int NUM_OF_COLUMNS = 2;
    private final String BASE_URL = "https://api.themoviedb.org/3/";
    private final String TAG = MainActivity.class.getName();
    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor sharedPrefsEditor;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initializer();

        setUpPref();

    }

    public  void setUpPref()
    {
        sharedPrefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String sortBy = sharedPrefs.getString("SortBY", null);
        if (sortBy != null) {
            if (sortBy.equals(getString(R.string.top_rated)))
                new MovieAppTask().execute(BASE_URL + "movie/top_rated?api_key=214cc6f08673d0af40d2a203b2c32143&language=en-US&page=1");
            else if (sortBy.equals(getString(R.string.most_popular)))
                new MovieAppTask().execute(BASE_URL + "movie/popular?api_key=214cc6f08673d0af40d2a203b2c32143&language=en-US&page=1");
        } else
            new MovieAppTask().execute(BASE_URL + "movie/now_playing?api_key=214cc6f08673d0af40d2a203b2c32143&language=en-US&page=1");
    }

    private void initializer() {
        details = new ArrayList<>();
        gridLayoutManager = new GridLayoutManager(MainActivity.this, NUM_OF_COLUMNS);
        movieRecyclerView.setLayoutManager(gridLayoutManager);
        movieRecyclerView.setHasFixedSize(true);
        progressBar = findViewById(R.id.Progressbar);
    }


    public class MovieAppTask extends AsyncTask<String, Integer , ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            details.clear();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... urls) {
            String link = urls[0];
            String result = null;
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url = new URL(link);
                HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
                InputStreamReader inputStreamReader = new InputStreamReader(httpConnection.getInputStream());
                BufferedReader reader = new BufferedReader(inputStreamReader);
                result = reader.readLine();
                while (result != null) {
                    stringBuilder.append(result);
                    result = reader.readLine();
                }


                JSONObject jsonObj = new JSONObject(stringBuilder.toString());
                JSONArray all = jsonObj.getJSONArray("results");


                String name, poster;
                int ID;

                for (int i = 0; i < all.length(); i++) {
                    JSONObject member = all.getJSONObject(i);

                    name = member.getString("title");
                    poster = member.getString("poster_path");
                    ID = member.getInt("id");

                    details.add(new Movie(ID, name, poster));
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, e.getMessage());
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(TAG, e.getMessage());

            }
            return details;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);
            if (details != null) {
                movieAdapter = new MovieAdapter(MainActivity.this, details);
                movieRecyclerView.setAdapter(movieAdapter);
                progressBar.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Top_Rated: {
                sharedPrefsEditor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                sharedPrefsEditor.putString("SortBY", getString(R.string.top_rated));
                sharedPrefsEditor.apply();
                setUpPref();
                break;
            }
            case R.id.Most_Popular: {
                sharedPrefsEditor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                sharedPrefsEditor.putString("SortBY", getString(R.string.most_popular));
                sharedPrefsEditor.apply();
                setUpPref();
                break;
            }
            default:
                break;
        }

        return super.onOptionsItemSelected(item);


    }

}

