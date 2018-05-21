package com.example.twomack.foodandmood.recyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.twomack.foodandmood.R;


public class MainViewHolder extends RecyclerView.ViewHolder{

    ImageView imageView;
    public TextView foodName;
    public RatingBar ratingBar;
    public TextView timesEaten;

    public MainViewHolder(View itemView) {
        super(itemView);
        foodName = itemView.findViewById(R.id.food_name);
        ratingBar = itemView.findViewById(R.id.food_rating);
        timesEaten = itemView.findViewById(R.id.times_eaten);

        //imageView = itemView.findViewById(R.id.listImageView);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public View getItemView() {
        return itemView;
    }


}
