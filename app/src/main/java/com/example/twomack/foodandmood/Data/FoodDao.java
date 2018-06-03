package com.example.twomack.foodandmood.Data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.ABORT;
import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;
import static android.arch.persistence.room.OnConflictStrategy.ROLLBACK;

@Dao
public interface FoodDao {

    @Insert(onConflict = IGNORE)
    void insert(Food food);

    @Query("SELECT * from food_table ORDER BY totalScore/timesEaten DESC, timesEaten ASC")
    LiveData<List<Food>> getAllFoods();

    @Query("UPDATE food_table SET timesEaten = timesEaten + 1, totalScore = totalScore + :mood WHERE Food LIKE :foodName")
    void updateFood(double mood, String foodName);

    @Query("DELETE FROM food_table")
    void deleteAll();

}
