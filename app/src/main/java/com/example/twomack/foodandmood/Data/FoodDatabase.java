package com.example.twomack.foodandmood.Data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

//entities = the types of objects that are allowed in the database.
@Database(entities = {Food.class}, version = 2)
public abstract class FoodDatabase extends RoomDatabase {
    public abstract FoodDao foodDao();

    private static FoodDatabase INSTANCE;

    //singleton
    public static FoodDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            //synchronized means only one thread can access this resource at a time.
            synchronized (FoodDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FoodDatabase.class, "food_database")
                             //on migration, delete old data - possible implementation if you need to change the database schema.
                             //.fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
