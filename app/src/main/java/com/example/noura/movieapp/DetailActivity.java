package com.example.noura.movieapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noura.movieapp.Data.AppExecutors;
import com.example.noura.movieapp.Data.Movies;
import com.example.noura.movieapp.Data.MyDataBase;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.D_progressBar)
    ProgressBar progressBar;
    @BindView(R.id.OriginalTitle)
    TextView originalTitle;
    @BindView(R.id.UserRate)
    TextView rate;
    @BindView(R.id.OverView)
    TextView overView;
    @BindView(R.id.ReleaseDate)
    TextView releaseDate;
    @BindView(R.id.FrontImage)
    ImageView posterPath;
    @BindView(R.id.BackImage)
    ImageView backImg;
    @BindView(R.id.TrailersRecyclerView)
    RecyclerView trailersRecyclerView;
    @BindView(R.id.favorite)
    ImageView favImg;
    @BindView(R.id.ReviwsRecyclerView)
    RecyclerView reviwsRecyclerView;
    @BindView(R.id.TrailersContainer)
    LinearLayout tContainer;
    @BindView(R.id.ReviewsContainer)
    LinearLayout rContainer;

    private ArrayList<Review> reviews;
    private ReviewAdapter reviewAdapter;
    private ArrayList<Trailer> trailers;
    private TrailerAdapter trailerAdapter;
    private MyDataBase mDb;
    public int item;
    private final String BASE_URL = "https://api.themoviedb.org/3/";
    private final String TAG = MainActivity.class.getName();
    public String name, Date = null, vote_average = null, OverView = null, Title = null, poster_path = null, backdrop_path = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        initializer();

        Intent intent = getIntent();
        item = intent.getIntExtra("ClickedItemID", 0);
        name = intent.getStringExtra("ClickedItemName");

        setTitle(name);

        mDb = MyDataBase.getAppDatabase(getApplicationContext());


        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            connected = true;
            new DetailActivity.MovieDetail().execute(BASE_URL + "movie/" + item + "?api_key=214cc6f08673d0af40d2a203b2c32143&language=en-US");
            new DetailActivity.MovieTrailer().execute(BASE_URL + "movie/" + item + "/videos?api_key=214cc6f08673d0af40d2a203b2c32143&language=en-US");
            new DetailActivity.MovieReview().execute(BASE_URL + "movie/" + item + "/reviews?api_key=214cc6f08673d0af40d2a203b2c32143&language=en-US");

        } else {
            connected = false;

            LoadMovieViewModelFactory factory = new LoadMovieViewModelFactory(mDb , item);
            final LoadMovieViewModel viewModel = ViewModelProviders.of(this , factory).get(LoadMovieViewModel.class);
            viewModel.getMoviesLiveData().observe(this, new Observer<Movies>() {
                @Override
                public void onChanged(@Nullable Movies m) {

                    rContainer.setVisibility(View.GONE);
                    tContainer.setVisibility(View.GONE);

                            if (m != null) {
                                favImg.setImageResource(R.drawable.ic_star_black_24dp);
                                progressBar.setVisibility(View.GONE);
                                originalTitle.setText(m.getTitle());
                                releaseDate.setText(m.getDate());
                                rate.setText(m.getVote_average() + "/10");
                                overView.setText(m.getOverView());
                                Picasso.with(DetailActivity.this).load(Movie.baseImageUrl + m.getPosterPath()).into(posterPath);
                                Picasso.with(DetailActivity.this).load(Movie.baseImageUrl + m.getBackdrop_path()).into(backImg);

                            }}});

//            AppExecutors.getsInstance().getDiskIO().execute(new Runnable() {
//                @Override
//                public void run() {
//                    final Movies m = mDb.MovieDao().loadMovieById(item);
//
//                    rContainer.setVisibility(View.GONE);
//                    tContainer.setVisibility(View.GONE);
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (m != null) {
//                                progressBar.setVisibility(View.GONE);
//                                originalTitle.setText(m.getTitle());
//                                releaseDate.setText(m.getDate());
//                                rate.setText(m.getVote_average() + "/10");
//                                overView.setText(m.getOverView());
//                                Picasso.with(DetailActivity.this).load(Movie.baseImageUrl + m.getPosterPath()).into(posterPath);
//                                Picasso.with(DetailActivity.this).load(Movie.baseImageUrl + m.getBackdrop_path()).into(backImg);
//                            }
//                        }
//                    });
//                }
//            });
        }



        favImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Movies movies = new Movies(item ,name, poster_path, Title, backdrop_path, OverView, vote_average, Date /*, trailers*/);

                favImg.setImageResource(R.drawable.ic_star_black_24dp);

                AppExecutors.getsInstance().getDiskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mDb.MovieDao().insertMovie(movies);
                        finish();
                    }
                });
            }
        });


    }


    private void initializer() {
        trailers = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DetailActivity.this);
        trailersRecyclerView.setLayoutManager(linearLayoutManager);
        trailersRecyclerView.setHasFixedSize(true);

        reviews = new ArrayList<>();
        LinearLayoutManager revivewsLManager = new LinearLayoutManager(DetailActivity.this);
        reviwsRecyclerView.setLayoutManager(revivewsLManager);
        reviwsRecyclerView.setHasFixedSize(true);
    }


    public class MovieDetail extends AsyncTask<String, Void, Movie> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Movie doInBackground(String... strings) {
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
                    stringBuilder.append(result);
                    result = reader.readLine();
                }
                JSONObject jsonObj = new JSONObject(stringBuilder.toString());
                backdrop_path = jsonObj.getString("backdrop_path");
                poster_path = jsonObj.getString("poster_path");
                Title = jsonObj.getString("title");
                OverView = jsonObj.getString("overview");
                vote_average = jsonObj.getString("vote_average");
                Date = jsonObj.getString("release_date");

            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, e.getMessage(), e);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(TAG, e.getMessage(), e);
            }
            return new Movie(backdrop_path, poster_path, Title, OverView, vote_average, Date);
        }

        @Override
        protected void onPostExecute(Movie movie) {
            super.onPostExecute(movie);
            originalTitle.setText(movie.getTitle());
            releaseDate.setText(movie.getDate());
            rate.setText(movie.getVote_average() + "/10");
            overView.setText(movie.getOverView());
            Picasso.with(DetailActivity.this).load(movie.baseImageUrl + movie.getPosterPath()).into(posterPath);
            Picasso.with(DetailActivity.this).load(movie.baseImageUrl + movie.getBackdrop_path()).into(backImg);

        }

    }

    public class MovieTrailer extends AsyncTask<String, Void, ArrayList<Trailer>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Trailer> doInBackground(String... strings) {

            String site = null, trailerid = null, trailername = null, type = null, key = null;
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
                    stringBuilder.append(result);
                    result = reader.readLine();
                }


                JSONObject jsonObj = new JSONObject(stringBuilder.toString());

                trailerid = jsonObj.getString("id");

                JSONArray all = jsonObj.getJSONArray("results");

                for (int i = 0; i < all.length(); i++) {
                    JSONObject member = all.getJSONObject(i);

                    site = member.getString("site");
                    trailername = member.getString("name");
                    type = member.getString("type");
                    key = member.getString("key");

                    trailers.add(new Trailer(site, trailerid, trailername, type, key));
                }

            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, e.getMessage(), e);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(TAG, e.getMessage(), e);
            }
            return trailers;
        }

        @Override
        protected void onPostExecute(ArrayList<Trailer> t) {
            super.onPostExecute(t);
            progressBar.setVisibility(View.GONE);
            if (trailers != null) {
                trailerAdapter = new TrailerAdapter(DetailActivity.this, trailers);
                trailersRecyclerView.setAdapter(trailerAdapter);
                progressBar.setVisibility(View.GONE);
            }
        }

    }

    public class MovieReview extends AsyncTask<String, Void, ArrayList<Review>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Review> doInBackground(String... strings) {

            String content = null, author = null;
            int reviewID = 0;
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
                    stringBuilder.append(result);
                    result = reader.readLine();
                }


                JSONObject jsonObj = new JSONObject(stringBuilder.toString());

                reviewID = jsonObj.getInt("id");

                JSONArray all = jsonObj.getJSONArray("results");

                for (int i = 0; i < all.length(); i++) {
                    JSONObject member = all.getJSONObject(i);

                    author = member.getString("author");
                    content = member.getString("content");

                    reviews.add(new Review(reviewID, author, content));
                }

            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, e.getMessage(), e);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(TAG, e.getMessage(), e);
            }
            return reviews;
        }

        @Override
        protected void onPostExecute(ArrayList<Review> r) {
            super.onPostExecute(r);
            progressBar.setVisibility(View.GONE);
            if (reviews != null) {
                reviewAdapter = new ReviewAdapter(DetailActivity.this, reviews);
                reviwsRecyclerView.setAdapter(reviewAdapter);
                progressBar.setVisibility(View.GONE);
            }
        }

    }

}
