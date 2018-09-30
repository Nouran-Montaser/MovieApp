package com.example.noura.movieapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    TextView releaseDate, rate, overView , originalTitle;
    ImageView posterPath, backImg;
    private final String BASE_URL = "https://api.themoviedb.org/3/";
    private final String TAG = MainActivity.class.getName();
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        int item = intent.getIntExtra("ClickedItemID",0);
        String name = intent.getStringExtra("ClickedItemName");

        setTitle(name);

        initializer();

        new DetailActivity.MovieDetail().execute(BASE_URL + "movie/"+item+"?api_key=214cc6f08673d0af40d2a203b2c32143&language=en-US");

    }


    public class MovieDetail extends AsyncTask<String, Void, Movie> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Movie doInBackground(String... strings) {
            String Date=null, vote_average=null , OverView=null , Title=null ,poster_path=null , backdrop_path = null;
            String link = strings[0];
            String result = null;
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url = new URL(link);
                HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
                InputStreamReader inputStreamReader = new InputStreamReader(httpConnection.getInputStream());
                BufferedReader reader = new BufferedReader(inputStreamReader);
                result = reader.readLine();
                while (result != null) {
                    Log.i(TAG , result);
                    stringBuilder.append(result);
                    result = reader.readLine();
                }
                Log.d(TAG, "doInBackgroundiiiiiiiiiii: " + stringBuilder.toString());
                JSONObject jsonObj = new JSONObject(stringBuilder.toString());
                backdrop_path = jsonObj.getString("backdrop_path");
                poster_path = jsonObj.getString("poster_path");
                Title = jsonObj.getString("title");
                OverView = jsonObj.getString("overview");
                vote_average = jsonObj.getString("vote_average");
                Date = jsonObj.getString("release_date");
                Log.d(TAG, "doInBackground: " + Date);


            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return  new Movie(backdrop_path,poster_path,Title,OverView,vote_average,Date);
        }

        @Override
        protected void onPostExecute(Movie movie) {
            super.onPostExecute(movie);
            progressBar.setVisibility(View.GONE);
            originalTitle.setText(movie.getTitle());
            releaseDate.setText(movie.getDate());
            rate.setText(movie.getVote_average());
            overView.setText(movie.getOverView());
            Picasso.with(DetailActivity.this).load(movie.baseImageUrl+movie.getPosterPath()).into(posterPath);
            Picasso.with(DetailActivity.this).load(movie.baseImageUrl+movie.getBackdrop_path()).into(backImg);

        }

    }

    private void initializer() {
        progressBar = findViewById(R.id.D_progressBar);
        originalTitle = findViewById(R.id.OriginalTitle);
        releaseDate = findViewById(R.id.ReleaseDate);
        rate = findViewById(R.id.UserRate);
        overView = findViewById(R.id.OverView);
        posterPath = findViewById(R.id.FrontImage);
        backImg = findViewById(R.id.BackImage);
    }

}
