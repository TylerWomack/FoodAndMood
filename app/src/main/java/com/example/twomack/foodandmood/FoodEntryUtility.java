package com.example.twomack.foodandmood;

import com.example.twomack.foodandmood.Data.Food;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FoodEntryUtility {

    /**
     * Finds and formats food names by splitting and formatting a comma-separated string.
     * @param string User-submitted String containing comma separated food names.
     * @return An ArrayList<String> containing cleaned and standardized food names.
     */
    public ArrayList<String> foodListFromString(String string){
        List<String> tempList = Arrays.asList(string.split(","));
        tempList = cleanStrings(tempList);
        return new ArrayList<>(tempList);
    }

    /**
     * Converts all string in the ArrayList to lowercase, removes all empty Strings, trims trailing and leading whitespace.
     * This method works to eliminate duplicate database entries due to capitalization and spacing.
     * @param strings A List<String> of food names
     * @return An ArrayList of Strings that have been standardised and formatted.
     */
    private ArrayList<String> cleanStrings(List<String> strings){
        ArrayList<String> simplifiedStrings = new ArrayList<>();
        for (String string : strings){
            string = string.toLowerCase();
            string = string.trim();
            if (!string.isEmpty())
                simplifiedStrings.add(string);
        }
        return simplifiedStrings;
    }

    /**
     * Cycles through all of the foods in the database, and entries that match user submitted foods.
     * Updates the matches by incrementing the number of times eaten and updating their score, returns an
     * ArrayList with the updated entries.
     * @param allFoodsFromDatabase all the foods that were found in the database
     * @param newFoodNames the String names of the foods the user submitted
     * @param mood the user's current mood
     * @return An ArrayList containing updated entries for all submitted foods that have been logged before
     */
    public ArrayList<Food> updatePreviouslyEatenFoods(List<Food> allFoodsFromDatabase, ArrayList<String> newFoodNames, double mood){

        ArrayList<Food> updatedFoods = new ArrayList<>();

        for (Food previouslyLoggedFood : allFoodsFromDatabase){
            if (newFoodNames.contains(previouslyLoggedFood.getName())){
                Food updatedFood = new Food(previouslyLoggedFood.getName());
                updatedFood.setTimesEaten(previouslyLoggedFood.getTimesEaten() + 1);
                updatedFood.setTotalScore(previouslyLoggedFood.getTotalScore() + mood);
                updatedFoods.add(updatedFood);
            }
        }
        return updatedFoods;
    }

    /**
     * Using the newFoodNames, this method generates new Foods and adds them to the foodsToSubmit ArrayList.
     * @param newFoodNames a list of food names that haven't been previously submitted to the database
     * @param foodsToSubmit a list of foods that will be submitted to the database later
     * @param mood the user's current mood
     * @return an updated version of foodsToSubmit, with new Foods generated from newFoodNames added.
     */
    public ArrayList<Food> addNewFoodsToSubmitList(ArrayList<String> newFoodNames, ArrayList<Food> foodsToSubmit, double mood){

        List<Food> newFoodsToAdd = new ArrayList<>();

        for (String foodname : newFoodNames){
            boolean isInInsertionList = false;
            for (Food food : foodsToSubmit){
                if (foodname.equals(food.getName())){
                    isInInsertionList = true;
                }
            }
            if (!isInInsertionList){
                Food newFood = new Food(foodname);
                newFood.setTimesEaten(1);
                newFood.setTotalScore(mood);
                newFoodsToAdd.add(newFood);
            }
        }

        foodsToSubmit.addAll(newFoodsToAdd);
        return foodsToSubmit;
    }

}
