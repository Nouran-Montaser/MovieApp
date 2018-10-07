package com.example.noura.movieapp.Data;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.noura.movieapp.DetailActivity;
import com.example.noura.movieapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {

    private Context context;
    private List<Movies> movies;


    public Adapter(Context context, List<Movies> details) {
        this.context = context;
        movies= details;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_movie,parent,false);
        return new Holder( view);
    }


    @Override
    public void onBindViewHolder(final Holder holder, final int position){

        holder.movieName.setText(movies.get(position).getTitle());
        Picasso.with(context).load(Movies.baseImageUrl+movies.get(position).getPosterPath()).placeholder(R.drawable.placeholder).error(R.drawable.error).into(holder.moviePoster);


        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(context , DetailActivity.class);
                detailIntent.putExtra("ClickedItemID",movies.get(position));
                context.startActivity(detailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public List<Movies> getMovie()
    {
        return movies;
    }

    public static class Holder extends RecyclerView.ViewHolder{

        ImageView moviePoster;
        TextView movieName;
        LinearLayout linearLayout;

        public Holder(View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.SingleMovie_img);
            movieName = itemView.findViewById(R.id.SingleMovie_nametxt);
            linearLayout = itemView.findViewById(R.id.container);
        }
    }
}


