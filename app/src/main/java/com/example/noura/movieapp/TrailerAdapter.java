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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.ArrayList;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerHolder> {

    private Context context;
    private ArrayList<Trailer> trailerList;


    public TrailerAdapter(Context context, ArrayList<Trailer> trailerList) {
        this.context = context;
        this.trailerList = trailerList;
    }

    @Override
    public TrailerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_trailer,parent,false);
        return new TrailerHolder(view);
    }


    @Override
    public void onBindViewHolder(TrailerHolder holder, final int position){

        String num = position+1+"";
        holder.trailerNum.setText(holder.trailerNum.getText()+num);
        holder.trailerImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trailerIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Trailer.TRAILER_BASE_URL+trailerList.get(position).getKey()));
                trailerIntent.putExtra("force_fullscreen",true);
                context.startActivity(trailerIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    public static class TrailerHolder extends RecyclerView.ViewHolder{

        TextView trailerNum;
        ImageView trailerImg;

        public TrailerHolder(View itemView) {
            super(itemView);
            trailerNum = itemView.findViewById(R.id.trailerNumber);
            trailerImg = itemView.findViewById(R.id.trailerImage);
        }
    }
}


