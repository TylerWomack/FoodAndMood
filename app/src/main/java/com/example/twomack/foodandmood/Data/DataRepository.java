package com.example.twomack.foodandmood.Data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DataRepository {

    private FoodDao mFoodDao;
    private LiveData<List<Food>> mAllFoods;

    public DataRepository(Application application) {
        FoodDatabase db = FoodDatabase.getDatabase(application);
        mFoodDao = db.foodDao();
        mAllFoods = mFoodDao.getAllFoods();
    }

    public LiveData<List<Food>> getAllFoods() {
        return mAllFoods;
    }

    //todo:this blocks the ui? Why this, and not getAllFoods in the constructor? Probably because the getAllFoods is using liveData... :(
    public List<Food> getSpecificFood(String name){return mFoodDao.getSpecificFood(name);}

    public void insert (Food food) {
        new insertAsyncTask(mFoodDao).execute(food);
    }

    public void clearDatabase(){new deleteAsyncTask(mFoodDao).execute();}

    private static class insertAsyncTask extends AsyncTask<Food, Void, Void> {

        private FoodDao mAsyncTaskDao;

        insertAsyncTask(FoodDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Food... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Food, Void, Void> {

        private FoodDao mAsyncTaskDao;

        deleteAsyncTask(FoodDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Food... params) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
}