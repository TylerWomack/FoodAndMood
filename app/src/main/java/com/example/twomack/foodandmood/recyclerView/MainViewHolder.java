package com.example.twomack.foodandmood.recyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.twomack.foodandmood.R;


public class MainViewHolder extends RecyclerView.ViewHolder{

    public TextView foodName;
    public RatingBar ratingBar;
    public TextView timesEaten;

    MainViewHolder(View itemView) {
        super(itemView);
        foodName = itemView.findViewById(R.id.food_name);
        ratingBar = itemView.findViewById(R.id.food_rating);
        timesEaten = itemView.findViewById(R.id.times_eaten);

        }

    public View getItemView() {
        return itemView;
    }


}
