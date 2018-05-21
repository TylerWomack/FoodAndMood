package com.example.twomack.foodandmood.Data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import java.util.List;

@Dao
public interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Food food);

    @Query("SELECT * from food_table ORDER BY Food ASC")
    LiveData<List<Food>> getAllFoods();

    @Query("SELECT * from food_table WHERE Food LIKE :foodName")
    List<Food> getSpecificFood(String foodName);

    @Query("DELETE FROM food_table")
    public void deleteAll();
}
