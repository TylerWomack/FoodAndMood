package com.example.twomack.foodandmood.recyclerView;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;


import com.example.twomack.foodandmood.Data.Food;
import com.example.twomack.foodandmood.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainRecyclerViewAdapter  extends RecyclerView.Adapter<MainViewHolder>{

    private List<Food> foodList;


    private OnFoodSelectedListener listener;
    public interface OnFoodSelectedListener {
        void onFoodClicked(int position);
    }

    public MainRecyclerViewAdapter(OnFoodSelectedListener listener) {
        this.listener = listener;
    }


    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_list_item, parent, false);
        return new MainViewHolder(view);
    }

    //@Override
    @SuppressLint("SetTextI18n")
    public void onBindViewHolder(final MainViewHolder holder, int position) {
        String name = foodList.get(position).getName();
        //formatting here instead of in database because database entries should be as standardized as possible.
        String Capitalized = name.substring(0, 1).toUpperCase() + name.substring(1);
        holder.foodName.setText(Capitalized);
        holder.ratingBar.setRating((float) foodList.get(position).getAverageScore());
        holder.ratingBar.setClickable(false);
        holder.timesEaten.setText(String.valueOf(foodList.get(position).getTimesEaten()) + "x");

        //todo: (optional) implement this click listener, add a new feature when a food is clicked
        
        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onFoodClicked(holder.getAdapterPosition());
            }
        });

    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(foodList != null) {
            return foodList.size();
        }
        return 0;
    }
}
