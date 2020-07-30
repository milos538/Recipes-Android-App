package com.example.recipes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<userRecipe> recipe;
    Activity activity;

    CustomAdapter(Activity activity, Context context,ArrayList recipe){
        this.context = context;
        this.recipe = recipe;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final userRecipe userRecipe = recipe.get(position);
        holder.name.setText(String.valueOf(userRecipe.name));
        holder.image.setImageURI(Uri.parse(userRecipe.image));
        holder.desc.setText(String.valueOf(userRecipe.desc));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateRecipe.class);
                intent.putExtra("name",userRecipe.name);
                intent.putExtra("desc",userRecipe.desc);
                intent.putExtra("image",userRecipe.image);
                intent.putExtra("id",userRecipe.id);
                activity.startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipe.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView name,desc;
        ImageView image;
        ConstraintLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.recipe_title);
            image = itemView.findViewById(R.id.recipe_image);
            desc = itemView.findViewById(R.id.opisRecepta);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
