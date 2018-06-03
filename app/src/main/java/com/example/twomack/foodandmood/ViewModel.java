package com.example.twomack.foodandmood;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Transaction;

import com.example.twomack.foodandmood.Data.DataRepository;
import com.example.twomack.foodandmood.Data.Food;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewModel extends AndroidViewModel {

    private DataRepository mRepository;
    private LiveData<List<Food>> mAllFoods;
    private FoodEntryUtility foodEntryUtility;

    public ViewModel (Application application) {
        super(application);
        foodEntryUtility = new FoodEntryUtility();
        mRepository = new DataRepository(application);
        mAllFoods = mRepository.getAllFoods();
    }

    LiveData<List<Food>> getAllFoods() { return mAllFoods; }

    public void updateFoods(String foods, double mood){
        ArrayList<String> foodNames = foodEntryUtility.foodListFromString(foods);
        for (String name : foodNames){
            Food food = new Food(name);
            mRepository.insert(food);
            mRepository.update(mood, name);
        }
    }

    /**
     * This method:
     * 1. Cleans, standardises and extracts the names of the foods submitted by the user
     * 2. Checks foods in the database against the names of the foods submitted by the user.
     * If the user has logged the food before, it updates the previous entries to reflect the new submission.
     * 3. Creates entries for all previously unknown foods.
     * 4. Updates the database.
     * @param foods a string containing foods the user has eaten recently, separated by commas
     * @param mood a rating for mood, from 0-5
     */
    public void insertFoods(String foods, double mood){

        //step 1.
        ArrayList<String> newFoodNames = foodEntryUtility.foodListFromString(foods);
        ArrayList<Food> foodsToSubmit = new ArrayList<>();

        //step 2.
        List<Food> allFoodsFromDatabase = getAllFoods().getValue();
        if (allFoodsFromDatabase != null)
        foodsToSubmit = foodEntryUtility.updatePreviouslyEatenFoods(allFoodsFromDatabase, newFoodNames, mood);

        //step 3.
        foodsToSubmit = foodEntryUtility.addNewFoodsToSubmitList(newFoodNames, foodsToSubmit, mood);

        //step 4.
        for (Food food : foodsToSubmit){
            mRepository.insert(food);
        }
    }

    public void clearDataBase(){
        mRepository.clearDatabase();
    }
}