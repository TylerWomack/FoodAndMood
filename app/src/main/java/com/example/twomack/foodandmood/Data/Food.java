package com.example.twomack.foodandmood.Data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.HashMap;


@Entity(tableName = "food_table")
public class Food {

    @ColumnInfo(name = "timesEaten")
    private int timesEaten;

    @ColumnInfo(name = "totalScore")
    private double totalScore;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "food")
    private String name;

    public Food(@NonNull String name) {
        this.name = name;
        timesEaten = 0;
        totalScore = 0;
    }

    public int getTimesEaten() {
        return timesEaten;
    }

    public void setTimesEaten(int timesEaten) {
        this.timesEaten = timesEaten;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public double getAverageScore(){
        return (getTotalScore()/getTimesEaten());
    }
}




/*

@Entity(tableName = "food_table")
public class Food implements Parcelable {


    private int timesEaten;
    private double totalScore;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "Food")
    private String name;

    protected Food(Parcel in) {
        timesEaten = in.readInt();
        totalScore = in.readDouble();
        name = in.readString();
    }

    public Food(){}

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

    public int getTimesEaten() {
        return timesEaten;
    }

    public void setTimesEaten(int timesEaten) {
        this.timesEaten = timesEaten;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public double getAverageScore(){
        return (getTotalScore()/getTimesEaten());
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(timesEaten);
        dest.writeDouble(totalScore);
        dest.writeString(name);
    }

    */
//}
