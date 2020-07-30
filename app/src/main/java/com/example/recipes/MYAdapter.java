package com.example.recipes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

public class MYAdapter extends RecyclerView.Adapter<MYAdapter.MyViewHolder> {

    ArrayList<JSONObject> list;
    Context context;

    public MYAdapter(Context ct, ArrayList data){
        context = ct;
        list = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate((R.layout.my_row),parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            final JSONObject JO = list.get(position);
            holder.title.setText(JO.getString("title"));
            Picasso.get().load(JO.getString("image")).into(holder.image);
            holder.mainLayout.setOnClickListener( new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, recipe.class);
                    intent.putExtra("object",JO.toString());
                    context.startActivity(intent);
                }
            } );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
    TextView title;
    ImageView image;
    ConstraintLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.recipe_title);
            image = itemView.findViewById(R.id.recipe_image);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
