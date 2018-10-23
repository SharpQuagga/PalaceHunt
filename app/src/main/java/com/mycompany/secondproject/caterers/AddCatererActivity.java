package com.mycompany.secondproject.caterers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mycompany.secondproject.AllPalacesActivity;
import com.mycompany.secondproject.MainActivity;
import com.mycompany.secondproject.Palace;
import com.mycompany.secondproject.R;

public class AddCatererActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etxtName, etxtPhone, etxtadderss, etxtCity;
    TextView txtLogIn;

    Button btnSubmit;
    DatabaseReference databaseCaterers;

    public void initViews() {
        etxtName = findViewById(R.id.editTextnameC);
        btnSubmit = findViewById(R.id.buttonC);
        etxtPhone = findViewById(R.id.edittextphoneC);
        etxtadderss = findViewById(R.id.edittextaddressC);
        etxtCity = findViewById(R.id.editTextCityC);
        txtLogIn = findViewById(R.id.textViewLogIn);
        txtLogIn.setOnClickListener(this);
        getSupportActionBar().hide();
        btnSubmit.setOnClickListener(this);
        databaseCaterers = FirebaseDatabase.getInstance().getReference("Caterers");

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_caterer);

        getSupportActionBar().hide();
        initViews();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.textViewLogIn){
            Intent intent = new Intent(AddCatererActivity.this,SignUpActivity.class);
            startActivity(intent);
        }
    }
}

