package com.example.twomack.foodandmood;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.media.Rating;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.twomack.foodandmood.Data.DataRepository;
import com.example.twomack.foodandmood.Data.Food;
import com.example.twomack.foodandmood.Data.FoodDao;
import com.example.twomack.foodandmood.Data.FoodDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.foodEntryText)
    EditText foodEntryText;

    @BindView(R.id.ratingBar)
    RatingBar ratingBar;

    private FoodDao mFoodDao;
    private List<Food> mAllFoods;
    private ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
    }

    public void getFoods(View v){
        final LiveData<List<Food>> foodList = viewModel.getAllFoods();
        final MediatorLiveData mediatorLiveData = new MediatorLiveData<>();

        Observer<List<Food>> observer = new Observer<List<Food>>() {
            @Override
            public void onChanged(@Nullable List<Food> foods) {
                //launchFoodHistoryActivity();
                mediatorLiveData.removeSource(foodList);
            }
        };

        mediatorLiveData.addSource(foodList, observer);
        mediatorLiveData.observe(this, observer);
    }

    public void launchFoodHistoryActivity(View v){
        Intent intent = new Intent(this, FoodHistoryActivity.class);
        startActivity(intent);
    }

    //todo: I don't like this. I don't think I should need to get my entire list of foods here, in the activity, to update a few. Can't seem to
    //get it to work otherwise though.
    public void submitFoods(View v) {

        final LiveData<List<Food>> foodList = viewModel.getAllFoods();
        final MediatorLiveData mediatorLiveData = new MediatorLiveData<>();

        Observer<List<Food>> observer = new Observer<List<Food>>() {
            @Override
            public void onChanged(@Nullable List<Food> foods) {
                String foodString = foodEntryText.getText().toString();
                double mood = (double) ratingBar.getRating();
                mediatorLiveData.removeSource(foodList);
                viewModel.insertFoods(foods, foodString, mood);
            }
        };

        mediatorLiveData.addSource(foodList, observer);
        mediatorLiveData.observe(this, observer);
    }

    public void clearDataBase(View v){
        viewModel.clearDataBase();

    }
}
