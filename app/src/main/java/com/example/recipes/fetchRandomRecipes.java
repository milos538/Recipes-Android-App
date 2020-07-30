package com.example.recipes;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class fetchRandomRecipes extends AsyncTask<objekat,Void,Void> {
    String data;
    JSONArray jsonArray;
    ArrayList<JSONObject> lista;
    Boolean finished;
    Context kontekst;
    RecyclerView recyclerView;
    @Override
    protected Void doInBackground(objekat...objekat) {
        try {
            URL url = new URL("https://api.spoonacular.com/recipes/random?number=10&apiKey=32c3cdcb3fa041e4a3db82d18a29c67c");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            data = reader.readLine();
            JSONObject JO = new JSONObject(data);
            JSONArray jsonArray = JO.getJSONArray("recipes");
            lista = new ArrayList();
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject object = jsonArray.getJSONObject(i);
                lista.add(object);
            }
            finished = true;
            kontekst = objekat[0].cxt;
            recyclerView = objekat[0].rv;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            finished = false;
        } catch (IOException e) {
            e.printStackTrace();
            finished = false;
        } catch (JSONException e) {
            e.printStackTrace();
            finished = false;
        }
        return null;
    }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        recipes.lista = this.lista;
        if(finished == true){
            MYAdapter myAdapter = new MYAdapter(kontekst,lista);
            recyclerView.setAdapter(myAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(kontekst));
        }
    }
}
