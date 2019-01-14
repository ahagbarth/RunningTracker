package com.example.runningtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, "runs.db", null, 10);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    //Query to create the table with the columns
        db.execSQL("CREATE TABLE _runs (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "distance VARCHAR(128), " +
                "time VARCHAR(20), " +
                "averageSpeed VARCHAR(20), " +
                "initialLongitude FLOAT, " +
                "initialLatitude FLOAT, " +
                "endLongitude FLOAT, " +
                "endLatitude FLOAT," +
                "date VARCHAR(20));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS _runs " );
        onCreate(db);
    }

    public boolean insertData(String distance,  String time, String averageSpeed, float initialLongitude, float initialLatitude, float endLongitude, float endLatitude, String date){

        db = this.getWritableDatabase();

        //Store the values
        ContentValues content = new ContentValues();
        content.put("distance",distance);
        content.put("time",time);
        content.put("averageSpeed",averageSpeed);
        content.put("initialLongitude", initialLongitude);
        content.put("initialLatitude", initialLatitude);
        content.put("endLongitude", endLongitude);
        content.put("endLatitude", endLatitude);
        content.put("date", date);

        //insert the values stored
        long dataInserted = db.insert("_runs",null,content);

        //checks if data was inserted
        if(dataInserted == -1){
            return false;
        }else{
            return true;
        }
    }
    public Runs getRecipe(int id) {


        String Query = "SELECT  * FROM _runs WHERE id = " + id;

        db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(Query, null);

        //iterate through database to search row with id
        Runs recipe = new Runs();
        if (cursor.moveToFirst()) {

            recipe.setId(cursor.getInt(0));

            recipe.setDistance(cursor.getString(1));
            recipe.setTime(cursor.getString(2));
            recipe.setAverageSpeed(cursor.getString(3));
            recipe.setInitialLongitude(cursor.getFloat(4));
            recipe.setInitialLatitude(cursor.getFloat(5));
            recipe.setEndLongitude(cursor.getFloat(6));
            recipe.setEndLatitude(cursor.getFloat(7));
            recipe.setDate(cursor.getString(8));
        }

        db.close();

        return recipe;
    }


    public ArrayList<Runs> getRecipeList() {
        ArrayList<Runs> runList = new ArrayList<Runs>();

        //Select all from table, ascending order
        String Query = "SELECT  * FROM _runs ORDER BY id ASC";

        db = this.getWritableDatabase();
        Cursor c = db.rawQuery(Query, null);

        //iterate through database for all rows and columns and adding them to the ArrayList
        if (c.moveToFirst()) {

            do {

                Runs recipe = new Runs();
                recipe.setId(c.getInt(0));
                recipe.setDistance(c.getString(1));
                recipe.setTime(c.getString(2));
                recipe.setAverageSpeed(c.getString(3));
                recipe.setInitialLongitude(c.getFloat(4));
                recipe.setInitialLatitude(c.getFloat(5));
                recipe.setEndLongitude(c.getFloat(6));
                recipe.setEndLatitude(c.getFloat(7));
                recipe.setDate(c.getString(8));
                runList.add(recipe);

            } while (c.moveToNext());
        }
        db.close();
        return runList;

    }



}
