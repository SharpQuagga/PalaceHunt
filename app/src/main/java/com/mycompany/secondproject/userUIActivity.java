package com.mycompany.secondproject;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelStore;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;


public class userUIActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

    Spinner spinner;
    ArrayAdapter<String> adapter;
    Button btndate, btnsee, btnSearch;
    TextView txtDate;
    DatePickerDialog datePickerDialog;
    int startYear, starthMonth, startDay;
    LinearLayout layout;
    String InputDate;  String UserCity;         ArrayList<String> Favourites = new ArrayList<>();

    public void initViews() {
        getSupportActionBar().hide();
        spinner = findViewById(R.id.spinner);
        txtDate = findViewById(R.id.textViewDate);
        btnSearch = findViewById(R.id.buttonSearch);
        btnsee = findViewById(R.id.buttonSee);
        btndate = findViewById(R.id.button);
        btnSearch.setOnClickListener(this);
        btnsee.setOnClickListener(this);
        btndate.setOnClickListener(this);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        adapter.add("---city---");
        adapter.add("Ludhiana");
        adapter.add("Delhi");
        adapter.add("Sangrur");
        adapter.add("Chandigarh");
        spinner.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_ui);
        layout = findViewById(R.id.layoutOut);
        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.scale_in);
        layout.startAnimation(animation1);

        initViews();
        datePickerDialog = new DatePickerDialog(
                this, this, startYear, starthMonth, startDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);


    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            i1 = i1 + 1;
             Toast.makeText(userUIActivity.this,i2+" "+i1+" "+i,Toast.LENGTH_LONG).show();
            txtDate.setText("Selected Date: " + i2 + "/" + i1 + "/" + i);
            int abc=i2;
        InputDate=Integer.toString(abc);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.blink_anim);
        btnSearch.startAnimation(animation);

    }
    @Override
    public void onClick (View view){
        UserCity = spinner.getSelectedItem().toString();
        Log.e("CityUser",UserCity);
        int id = view.getId();
        switch (id) {
            case R.id.button:
                datePickerDialog.show();
                break;
            case R.id.buttonSee:
                Intent intent1 = new Intent(userUIActivity.this, AllPalacesActivity.class);
                 startActivity(intent1);

                break;
            case R.id.buttonSearch:
                if (InputDate == null || UserCity == adapter.getItem(0)){
                    Toast.makeText(userUIActivity.this,"Please Select Date and City",Toast.LENGTH_LONG).show();
                }else {
                    Intent intent12 = new Intent(userUIActivity.this, SearchedPalacsActivity.class);
                    intent12.putExtra("InputDate", InputDate);
                    intent12.putExtra("UserCity",UserCity);
                    startActivity(intent12);
                }
                 break;
            }
        }


}
