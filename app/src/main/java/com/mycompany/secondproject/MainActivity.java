package com.mycompany.secondproject;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etxtName ,etxtPhone, etxtadderss , etxtCity;

    Button btnSubmit;
    DatabaseReference databasePalace;


    public void initViews(){
        etxtName = findViewById(R.id.editTextname);
        btnSubmit = findViewById(R.id.button);
        etxtPhone = findViewById(R.id.edittextphone);
        etxtadderss = findViewById(R.id.edittextaddress);
        etxtCity = findViewById(R.id.editTextCity);
        btnSubmit.setOnClickListener(this);
        databasePalace = FirebaseDatabase.getInstance().getReference("palace");

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        initViews();
    }

    @Override
    public void onClick(View view) {
        String name = etxtName.getText().toString();
        String address = etxtadderss.getText().toString();
        String phone = etxtPhone.getText().toString();
        String imageUrl = "";
        String Book="";
        String city = etxtCity.getText().toString();
        if (!name.isEmpty()){
            String id = databasePalace.push().getKey();
            Palace palace = new Palace(id,name,address,phone,imageUrl,Book,city);
           databasePalace.child(id).setValue(palace);
            Toast.makeText(this,"data entered",Toast.LENGTH_LONG).show();
            etxtName.setText("");
            etxtadderss.setText("");
            etxtPhone.setText("");
            etxtCity.setText("");
        }else {
            Toast.makeText(this,"Please fill name first",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 101, 1, "All Palaces");
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == 101){
            Intent intent = new Intent(MainActivity.this,AllPalacesActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}


