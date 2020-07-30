package com.example.recipes;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class recipe extends AppCompatActivity {

    JSONObject object;
    ImageView image;
    TextView title,desc,vegetarian,vegan,ready,gluten,cheap,popular;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        try {
            getData();
            setData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getData() throws JSONException {
        if(getIntent().hasExtra("object")){
            object = new JSONObject(getIntent().getStringExtra("object"));
        }
        else{
            Toast.makeText(this, "No data!", Toast.LENGTH_SHORT).show();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setData() throws JSONException {
        image = (ImageView) findViewById(R.id.singleRecipe_image);
        title = (TextView) findViewById(R.id.singleRecipe_name);
        desc = (TextView) findViewById(R.id.singleRecipe_desc);
        vegetarian = (TextView) findViewById(R.id.vegetarian_answer);
        vegan = (TextView) findViewById(R.id.vegan_answer);
        ready = (TextView) findViewById(R.id.ready_answer);
        gluten = (TextView) findViewById(R.id.gluten_answer);
        cheap = (TextView) findViewById(R.id.cheap_answer);
        popular = (TextView) findViewById(R.id.popular_answer);

        Picasso.get().load(object.getString("image")).into(image);
        title.setText(object.getString("title"));
        desc.setText(Html.fromHtml(object.getString("summary"), Html.FROM_HTML_MODE_COMPACT));
        vegetarian.setText(getAnswer(object.getString("vegetarian")));
        vegan.setText(getAnswer(object.getString("vegan")));
        gluten.setText(getAnswer(object.getString("glutenFree")));
        cheap.setText(getAnswer(object.getString("cheap")));
        popular.setText(getAnswer(object.getString("veryPopular")));
        ready.setText(object.getString("readyInMinutes"));




    }
    private String getAnswer(String string){
        if(string == "false"){
            return "NO";
        }else{
            return "YES";
        }
    }
}
