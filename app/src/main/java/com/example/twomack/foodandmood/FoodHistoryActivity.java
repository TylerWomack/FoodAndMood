package com.example.twomack.foodandmood;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.twomack.foodandmood.Data.Food;
import com.example.twomack.foodandmood.recyclerView.MainRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodHistoryActivity extends AppCompatActivity implements MainRecyclerViewAdapter.OnFoodSelectedListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private MainRecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_history);
        ButterKnife.bind(this);

        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        adapter = new MainRecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);

        ViewModel viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.getAllFoods().observe(this, new Observer<List<Food>>() {
            @Override
            public void onChanged(@Nullable List<Food> foods) {
                adapter.setFoodList(foods);
            }
        });
    }

    @Override
    public void onFoodClicked(int position) {

    }
}
