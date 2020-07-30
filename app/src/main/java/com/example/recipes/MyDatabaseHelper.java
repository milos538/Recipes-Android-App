package com.example.recipes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;


public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Recipes.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "my_recipes";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "recipe_title";
    private static final String COLUMN_DESC = "recipe_desc";
    private static final String COLUMN_IMAGE = "recipe_image";


    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(@NotNull SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TITLE + " TEXT, " +
                        COLUMN_IMAGE + " TEXT, " +
                        COLUMN_DESC + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addRecipe(String title, String desc, String image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_DESC, desc);
        cv.put(COLUMN_IMAGE, image);
        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed to insert data.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Added successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROm " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateData(String name,String desc,String image,String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE,name);
        cv.put(COLUMN_DESC, desc);
        cv.put(COLUMN_IMAGE,image);

        long result = db.update(TABLE_NAME, cv,"id=?",new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to update data.", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Updated successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME,"id=?",new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to delete recipe.", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Deleted successfully!", Toast.LENGTH_SHORT).show();
        }
    }
}
