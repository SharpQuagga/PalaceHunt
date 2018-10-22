package com.mycompany.secondproject.caterers;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mycompany.secondproject.AllPalacesActivity;
import com.mycompany.secondproject.R;
import com.mycompany.secondproject.SearchedPalacsActivity;
import com.mycompany.secondproject.userUIActivity;

public class CatererUIActivity extends AppCompatActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener{

    Spinner spinner;
    ArrayAdapter<String> adapter;
    Button btndate, btnsee, btnSearch;
    TextView txtDate;
    DatePickerDialog datePickerDialog;
    int startYear, starthMonth, startDay;
    LinearLayout layout;
    String InputDate;  String UserCity;

    public void initViews(){
        getSupportActionBar().hide();
        spinner = findViewById(R.id.spinner1);
        txtDate = findViewById(R.id.textViewDateC);
        btnSearch = findViewById(R.id.buttonSearchC);
        btnsee = findViewById(R.id.buttonSeeC);
        btndate = findViewById(R.id.buttonC);
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

        setContentView(R.layout.activity_caterer_ui);
//        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.scale_in);
//        layout.startAnimation(animation1);

        initViews();
        datePickerDialog = new DatePickerDialog(this, this, startYear, starthMonth, startDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        i1 = i1 + 1;
        // Toast.makeText(userUIActivity.this,i2+" "+i1+" "+i,Toast.LENGTH_LONG).show();
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
            case R.id.buttonC:
                datePickerDialog.show();
                break;
            case R.id.buttonSeeC:
                Intent intent1 = new Intent(CatererUIActivity.this, AllCaterersActivity.class);
                startActivity(intent1);

                break;
            case R.id.buttonSearchC:
                if (InputDate == null || UserCity == adapter.getItem(0)){
                    Toast.makeText(CatererUIActivity.this,"Please Select Date and City",Toast.LENGTH_LONG).show();
                }else {
                    Intent intent12 = new Intent(CatererUIActivity.this, SearchedCaterersActivity.class);
                    intent12.putExtra("InputDate", InputDate);
                    intent12.putExtra("UserCity",UserCity);
                    startActivity(intent12);
                }
                break;
        }
    }
}
