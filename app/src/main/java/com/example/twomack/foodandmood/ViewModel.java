package com.example.twomack.foodandmood;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.twomack.foodandmood.Data.DataRepository;
import com.example.twomack.foodandmood.Data.Food;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewModel extends AndroidViewModel {

    private DataRepository mRepository;
    private LiveData<List<Food>> mAllFoods;

    public ViewModel (Application application) {
        super(application);
        mRepository = new DataRepository(application);
        mAllFoods = mRepository.getAllFoods();
    }

    LiveData<List<Food>> getAllFoods() { return mAllFoods; }

    public void insertFoods(List<Food> allFoods, String foods, double mood){
        List<String> tempList = Arrays.asList(foods.split(","));

        tempList = cleanStrings(tempList);

        ArrayList<String> foodNames = new ArrayList<>(tempList);
        ArrayList<Food> foodsToUpdate = new ArrayList<>();
        ArrayList<Food> newFoodsToAdd = new ArrayList<>();

        for (Food food : allFoods){
            if (foodNames.contains(food.getName())){
                Food updatedFood = new Food();
                updatedFood.setName(food.getName());
                updatedFood.setTimesEaten(food.getTimesEaten() + 1);
                updatedFood.setTotalScore(food.getTotalScore() + mood);
                if (!foodsToUpdate.contains(updatedFood))
                foodsToUpdate.add(updatedFood);
            }
        }

        for (String foodname : foodNames){
            boolean isInInsertionList = false;
            for (Food food : foodsToUpdate){
                if (foodname.equals(food.getName())){
                    isInInsertionList = true;
                }
            }
            if (isInInsertionList == false){
                Food newFood = new Food();
                newFood.setTimesEaten(1);
                newFood.setName(foodname);
                newFood.setTotalScore(mood);
                newFoodsToAdd.add(newFood);
            }
        }

        foodsToUpdate.addAll(newFoodsToAdd);

        for (Food food : foodsToUpdate){
            mRepository.insert(food);
        }
    }

    public ArrayList<String> cleanStrings(List<String> strings){
        ArrayList<String> simplifiedStrings = new ArrayList<>();
        for (String string : strings){
            string = string.toLowerCase();
            string = string.trim();
            if (!string.isEmpty())
            simplifiedStrings.add(string);
        }
        return simplifiedStrings;
    }

    public void clearDataBase(){
        mRepository.clearDatabase();
    }
}