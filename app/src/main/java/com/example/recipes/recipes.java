package com.example.recipes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

class objekat{
    Context cxt;
    RecyclerView rv;
    String tekst;

    public objekat(Context cxt, RecyclerView rv,String tekst) {
        this.cxt = cxt;
        this.rv = rv;
        this.tekst = tekst;
    }
}

public class  recipes extends AppCompatActivity {
    public static ArrayList lista;
    RecyclerView recyclerView;
    Button button;
    TextView tekst,search;
    objekat zaSlanje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        button = (Button) findViewById(R.id.button4);
        tekst = (TextView) findViewById(R.id.jsontekst);
        search = (TextView) findViewById(R.id.searchInput);
        recyclerView = findViewById(R.id.recyclerView);
        fetchRandomRecipes process = new fetchRandomRecipes();
        zaSlanje = new objekat(this, recyclerView,"");
        process.execute(zaSlanje);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchForRecipe search_process = new searchForRecipe();
                zaSlanje.tekst = search.getText().toString();
                search_process.execute(zaSlanje);
            }
        });

    }
}
