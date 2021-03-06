package com.example.noura.movieapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noura.movieapp.Data.Movies;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.myHolder> {

    private Context context;
    private ArrayList<Movie> MovieList;


    public MovieAdapter(Context context, ArrayList<Movie> details) {
        this.context = context;
        this.MovieList = details;
    }

    @Override
    public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_movie,parent,false);
        return new myHolder(view);
    }


    @Override
    public void onBindViewHolder(final myHolder holder, final int position){
        final Movie movie=MovieList.get(position);
        holder.movieName.setText(MovieList.get(position).getTitle());
        Picasso.with(context).load(MovieList.get(position).getBaseImageUrl()+MovieList.get(position).getPosterPath()).placeholder(R.drawable.placeholder).error(R.drawable.error).into(holder.moviePoster);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(context , DetailActivity.class);

                detailIntent.putExtra("ClickedItemID",new Movies(MovieList.get(position).getID(),MovieList.get(position).getName(),MovieList.get(position).backdrop_path,MovieList.get(position).getTitle(),MovieList.get(position).getBackdrop_path(),MovieList.get(position).getOverView(),MovieList.get(position).getVote_average(),MovieList.get(position).getDate()));
                context.startActivity(detailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return MovieList.size();
    }

    public static class myHolder extends RecyclerView.ViewHolder{

        ImageView moviePoster;
        TextView movieName;
        LinearLayout linearLayout;
        ImageView favImg;

        public myHolder(View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.SingleMovie_img);
            movieName = itemView.findViewById(R.id.SingleMovie_nametxt);
            linearLayout = itemView.findViewById(R.id.container);
            favImg = itemView.findViewById(R.id.favorite);
        }
    }
}


