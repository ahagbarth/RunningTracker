package com.example.runningtracker;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, "runs.db", null, 9);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE _runs (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "distance VARCHAR(128), " +
                "instructions VARCHAR(20), " +
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

    public boolean insertData(String distance,  String instructions, String averageSpeed, float initialLongitude, float initialLatitude, float endLongitude, float endLatitude, String date){

        db = this.getWritableDatabase();

        ContentValues content = new ContentValues();
        content.put("distance",distance);
        content.put("instructions",instructions);
        content.put("averageSpeed",averageSpeed);
        content.put("initialLongitude", initialLongitude);
        content.put("initialLatitude", initialLatitude);
        content.put("endLongitude", endLongitude);
        content.put("endLatitude", endLatitude);
        content.put("date", date);


        long     dataInserted = db.insert("_runs",null,content);
        if(dataInserted == -1){
            return false;
        }else{
            return true;
        }
    }
    public Runs getRecipe(int id) {

        String Query = "SELECT  * FROM _runs WHERE id = " + id;

        db = this.getWritableDatabase();

        Cursor c = db.rawQuery(Query, null);

        Runs recipe = new Runs();
        if (c.moveToFirst()) {

            recipe.setId(c.getInt(0));

            recipe.setDistance(c.getString(1));
            recipe.setInstructions(c.getString(2));
            recipe.setAverageSpeed(c.getString(3));
            recipe.setInitialLongitude(c.getFloat(4));
            recipe.setInitialLatitude(c.getFloat(5));
            recipe.setEndLongitude(c.getFloat(6));
            recipe.setEndLatitude(c.getFloat(7));
            recipe.setDate(c.getString(8));
        }

        db.close();

        return recipe;
    }


    public ArrayList<Runs> getRecipeList() {
        ArrayList<Runs> recipeList = new ArrayList<Runs>();

        String Query = "SELECT  * FROM _runs ORDER BY id ASC";

        db = this.getWritableDatabase();
        Cursor c = db.rawQuery(Query, null);

        if (c.moveToFirst()) {

            do {

                Runs recipe = new Runs();
                recipe.setId(c.getInt(0));
                recipe.setDistance(c.getString(1));
                recipe.setInstructions(c.getString(2));
                recipe.setAverageSpeed(c.getString(3));
                recipe.setInitialLongitude(c.getFloat(4));
                recipe.setInitialLatitude(c.getFloat(5));
                recipe.setEndLongitude(c.getFloat(6));
                recipe.setEndLatitude(c.getFloat(7));
                recipe.setDate(c.getString(8));
                recipeList.add(recipe);

            } while (c.moveToNext());
        }
        db.close();
        return recipeList;

    }




    public void deleteRecipe(int id) {

        db = this.getWritableDatabase();
        db.delete("_runs", "id = ?", new String[] {String.valueOf(id)});

        db.close();
    }


    public boolean updateTable(int id, String averageSpeed){

        db = this.getWritableDatabase();

        ContentValues content = new ContentValues();
        content.put("averageSpeed",averageSpeed);
        content.put("id",id);

        long result = db.update("_runs", content,"id = ?", new String[] {String.valueOf(id)});
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

}
