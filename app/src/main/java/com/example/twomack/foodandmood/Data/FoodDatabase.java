package com.example.twomack.foodandmood.Data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

//todo: gain an understanding of migration and get a strategy for it
@Database(entities = {Food.class}, version = 1)
public abstract class FoodDatabase extends RoomDatabase {
    public abstract FoodDao foodDao();

    private static FoodDatabase INSTANCE;

    public static FoodDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FoodDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FoodDatabase.class, "food_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
