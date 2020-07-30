package com.example.recipes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class UpdateRecipe extends AppCompatActivity {

    EditText name,desc;
    ImageView imageView;
    Uri image;
    Button updateImageButton,updateRecipeButton,deleteButton;
    String sName,sDesc,sImage,sId;
    Boolean clicked;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery();
                }
                else{
                    Toast.makeText(this,"Permission denied.",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private void pickImageFromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            image = data.getData();
            imageView.setImageURI(data.getData());
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_recipe);
        name = findViewById(R.id.updateRecipe_name);
        desc = findViewById(R.id.updateRecipe_desc);
        imageView = findViewById(R.id.imageView91);
        updateImageButton = findViewById(R.id.updateRecipe_addImage);
        updateRecipeButton = findViewById(R.id.updateRecipe_updateRecipeButton);
        deleteButton = findViewById(R.id.updateRecipe_delete);
        clicked = false;
        getIntentData();
        setData();

        updateRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDb = new MyDatabaseHelper(UpdateRecipe.this);
                myDb.updateData(name.getText().toString(),desc.getText().toString(),image.toString(),sId);
                clicked = true;
            }
        });
        updateImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions,PERMISSION_CODE);
                    }
                    else{
                        pickImageFromGallery();
                    }
                }
                else{
                    pickImageFromGallery();
                }
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(clicked){
            setResult(1);
        }
    }

    void getIntentData(){
        if(getIntent().hasExtra("name") && getIntent().hasExtra("desc") && getIntent().hasExtra("image") && getIntent().hasExtra("id")){
            sName = getIntent().getStringExtra("name");
            sDesc = getIntent().getStringExtra("desc");
            sImage = getIntent().getStringExtra("image");
            sId = getIntent().getStringExtra("id");
        }
        else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }
    void setData(){
        name.setText(sName);
        desc.setText(sDesc);
        imageView.setImageURI(Uri.parse(sImage));
    }
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + sName +" ?");
        builder.setMessage("Are you sure you want to delete: " + sName + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabaseHelper myDb = new MyDatabaseHelper(UpdateRecipe.this);
                myDb.deleteRow(sId);
                finish();
            }
        });
        builder.setNegativeButton("Go back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}
