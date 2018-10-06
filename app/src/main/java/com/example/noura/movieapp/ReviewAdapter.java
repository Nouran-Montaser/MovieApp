package com.example.noura.movieapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {

    private Context context;
    private ArrayList<Review> reviewList;


    public ReviewAdapter(Context context, ArrayList<Review> reviewList) {
        this.context = context;
        this.reviewList = reviewList;
    }

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_review,parent,false);
        return new ReviewHolder(view);
    }


    @Override
    public void onBindViewHolder(ReviewHolder holder, final int position){


        holder.reviewAuthor.setText(reviewList.get(position).getAuthor());
        holder.reviewContent.setText(reviewList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public static class ReviewHolder extends RecyclerView.ViewHolder{

        TextView reviewAuthor;
        TextView reviewContent;

        public ReviewHolder(View itemView) {
            super(itemView);
            reviewContent = itemView.findViewById(R.id.reviewContent);
            reviewAuthor = itemView.findViewById(R.id.reviewAuthor);
        }
    }
}


