package com.mycompany.secondproject.caterers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.mycompany.secondproject.MainActivity;
import com.mycompany.secondproject.R;

public class ChoiceActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView ib1 , ib2 , ib3 , ib4;

    public void initViews(){
        ib1 = findViewById(R.id.imageView3);
        ib2 = findViewById(R.id.imageView4);
        ib3 = findViewById(R.id.imageView);
        ib4 = findViewById(R.id.imageView2);
        ib3.setOnClickListener(this);
        ib4.setOnClickListener(this);
        ib1.setOnClickListener(this);
        ib2.setOnClickListener(this);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        getSupportActionBar().hide();
        initViews();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.imageView3 )  {
            Intent intent = new Intent(ChoiceActivity.this,com.mycompany.secondproject.userUIActivity.class);
            startActivity(intent);
        }
        if (id == R.id.imageView4) {
            Intent intent1 = new Intent(ChoiceActivity.this,CatererUIActivity.class);
            startActivity(intent1);
        }

        if (id == R.id.imageView2 ){
            Intent intent1 = new Intent(ChoiceActivity.this,AddCatererActivity.class);
            startActivity(intent1);
        }

        if (id == R.id.imageView){
            Intent intent1 = new Intent(ChoiceActivity.this,MainActivity.class);
            startActivity(intent1);
        }
    }
}