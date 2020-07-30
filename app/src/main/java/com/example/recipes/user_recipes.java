package com.example.recipes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

class userRecipe{
    String name;
    String desc;
    String image;
    String id;

    public userRecipe(String name, String desc, String image,String id) {
        this.name = name;
        this.desc = desc;
        this.image = image;
        this.id = id;
    }
}
public class user_recipes extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton addButton;
    MyDatabaseHelper myDB;
    ArrayList<userRecipe> recipe;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_recipes);
        recyclerView = findViewById(R.id.recycleView);
        addButton = findViewById(R.id.add_button);
        myDB = new MyDatabaseHelper(user_recipes.this);
        recipe = new ArrayList<>();

        storeDataFromDB();
        customAdapter = new CustomAdapter(user_recipes.this,user_recipes.this, recipe);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(user_recipes.this));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(user_recipes.this, addRecipe.class);
                startActivityForResult(intent,1);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        recreate();
    }

    void storeDataFromDB(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                recipe.add(new userRecipe(cursor.getString(1),cursor.getString(3),cursor.getString(2),cursor.getString(0)));
            }
        }
    }
}
