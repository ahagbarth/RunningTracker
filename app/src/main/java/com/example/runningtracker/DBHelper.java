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
        super(context, "recipe_book.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE _recipes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title VARCHAR(128), " +
                "instructions VARCHAR(128)," +
                "rating INTEGER" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS _recipes " );
        onCreate(db);
    }

    public boolean insertData(String title,  String instructions){

        db = this.getWritableDatabase();

        ContentValues content = new ContentValues();
        content.put("title",title);
        content.put("instructions",instructions);

        long     dataInserted = db.insert("_recipes",null,content);
        if(dataInserted == -1){
            return false;
        }else{
            return true;
        }
    }
    public Runs getRecipe(int id) {

        String Query = "SELECT  * FROM _recipes WHERE id = " + id;

        db = this.getWritableDatabase();

        Cursor c = db.rawQuery(Query, null);

        Runs recipe = new Runs();
        if (c.moveToFirst()) {

            recipe.setId(c.getInt(0));

            recipe.setTitle(c.getString(1));
            recipe.setInstructions(c.getString(2));
            recipe.setRating(c.getFloat(3));
        }

        db.close();

        return recipe;
    }


    public ArrayList<Runs> getRecipeList() {
        ArrayList<Runs> recipeList = new ArrayList<Runs>();

        String Query = "SELECT  * FROM _recipes ORDER BY title DESC";

        db = this.getWritableDatabase();
        Cursor c = db.rawQuery(Query, null);

        if (c.moveToFirst()) {

            do {

                Runs recipe = new Runs();
                recipe.setId(c.getInt(0));
                recipe.setTitle(c.getString(1));
                recipe.setInstructions(c.getString(2));
                recipeList.add(recipe);

            } while (c.moveToNext());
        }
        db.close();
        return recipeList;

    }


    public void deleteRecipe(int id) {

        db = this.getWritableDatabase();
        db.delete("_recipes", "id = ?", new String[] {String.valueOf(id)});

        db.close();
    }


    public boolean updateTable(int id, String rating){

        db = this.getWritableDatabase();

        ContentValues content = new ContentValues();
        content.put("rating",rating);
        content.put("id",id);

        long result = db.update("_recipes", content,"id = ?", new String[] {String.valueOf(id)});
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

}
