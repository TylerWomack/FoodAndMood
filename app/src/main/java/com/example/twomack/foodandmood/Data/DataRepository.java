package com.example.twomack.foodandmood.Data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

/**
 * All this class does currently is provide access to your Dao, which is created using the database object. You can call any dao method here.
 * In a more complex app, this class could be used to get data from multiple locations - the network, a database, or stored locally.
 * This pattern is useful because the DataRepository can function as a single source of truth and abstract away data retrieval.
 */

public class DataRepository {

    //an interface used to access your database
    private FoodDao foodDao;
    private LiveData<List<Food>> allFoods;

    public DataRepository(Application application) {
        FoodDatabase db = FoodDatabase.getDatabase(application);
        foodDao = db.foodDao();
        allFoods = foodDao.getAllFoods();

    }

    public LiveData<List<Food>> getAllFoods() {
        return allFoods;
    }

    //calls the insert method of the foodDao in a background thread.
    public void insert (Food food) {
        new insertAsyncTask(foodDao).execute(food);
    }

    public void update(double mood, String foodName){
        AsyncTaskParams params = new AsyncTaskParams();
        params.setMood(mood);
        params.setFoodName(foodName);
        new updateAsyncTask(foodDao).execute(params);
    }

    //calls the clearDatabase method of the foodDao in a background thread.
    public void clearDatabase(){new deleteAsyncTask(foodDao).execute();}

    //these are AsyncTasks because you shouldn't do things like database inserts on the main thread.
    private static class insertAsyncTask extends AsyncTask<Food, Void, Void> {

        private FoodDao foodDao;

        insertAsyncTask(FoodDao foodDao) {
            this.foodDao = foodDao;
        }

        @Override
        protected Void doInBackground(final Food... params) {
            foodDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Food, Void, Void> {

        private FoodDao foodDao;

        deleteAsyncTask(FoodDao foodDao) {
            this.foodDao = foodDao;
        }

        @Override
        protected Void doInBackground(final Food... params) {
            foodDao.deleteAll();
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<AsyncTaskParams, Void, Void> {

        private FoodDao foodDao;

        updateAsyncTask(FoodDao foodDao) {
            this.foodDao = foodDao;
        }

        @Override
        protected Void doInBackground(final AsyncTaskParams... params) {
            foodDao.updateFood(params[0].getMood(), params[0].getFoodName());
            return null;
        }
    }

    class AsyncTaskParams{
        String foodName;
        double mood;

        public String getFoodName() {
            return foodName;
        }

        void setFoodName(String foodName) {
            this.foodName = foodName;
        }


        public double getMood() {
            return mood;
        }

        void setMood(double mood) {
            this.mood = mood;
        }
    }
}