package com.example.recipes;

import android.content.Intent;
import android.os.AsyncTask;

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

public class searchForRecipe  extends AsyncTask<objekat,Void,Void> {

    String data;
    objekat finalObjekat;
    JSONObject finalJSON;

    @Override
    protected Void doInBackground(objekat... objekat) {
        try {
            finalObjekat = objekat[0];
            URL url = new URL("https://api.spoonacular.com/recipes/search?query="+ objekat[0].tekst + "&number=1&apiKey=32c3cdcb3fa041e4a3db82d18a29c67c");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            data = reader.readLine();
            JSONObject JO = new JSONObject(data);
            JSONArray jsonArray = JO.getJSONArray("results");
            JSONObject result = jsonArray.getJSONObject(0);

            url = new URL("https://api.spoonacular.com/recipes/" + result.get("id") + "/information?includeNutrition=false&apiKey=32c3cdcb3fa041e4a3db82d18a29c67c");
            connection = (HttpsURLConnection) url.openConnection();
            inputStream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            data = reader.readLine();
            finalJSON = new JSONObject(data);

        } catch (MalformedURLException | JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Intent intent = new Intent(finalObjekat.cxt, recipe.class);
        intent.putExtra("object",finalJSON.toString());
        finalObjekat.cxt.startActivity(intent);
    }
}
