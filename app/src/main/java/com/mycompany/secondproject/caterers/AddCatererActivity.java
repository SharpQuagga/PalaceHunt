package com.mycompany.secondproject.caterers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mycompany.secondproject.AllPalacesActivity;
import com.mycompany.secondproject.MainActivity;
import com.mycompany.secondproject.Palace;
import com.mycompany.secondproject.R;

public class AddCatererActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etxtName, etxtPhone, etxtadderss, etxtCity;

    Button btnSubmit;
    DatabaseReference databaseCaterers;

    public void initViews() {
        etxtName = findViewById(R.id.editTextnameC);
        btnSubmit = findViewById(R.id.buttonC);
        etxtPhone = findViewById(R.id.edittextphoneC);
        etxtadderss = findViewById(R.id.edittextaddressC);
        etxtCity = findViewById(R.id.editTextCityC);
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
    public void onClick(View view) {
        String name = etxtName.getText().toString();
        String address = etxtadderss.getText().toString();
        String phone = etxtPhone.getText().toString();
        String Book = "";
        String city = etxtCity.getText().toString();
        if (!name.isEmpty()) {
            String id = databaseCaterers.push().getKey();
            Caterer caterer = new Caterer(id ,name,address,phone,Book,city);
            databaseCaterers.child(id).setValue(caterer);
            Toast.makeText(this, "data entered", Toast.LENGTH_LONG).show();
            etxtName.setText("");
            etxtadderss.setText("");
            etxtPhone.setText("");
            etxtCity.setText("");
        } else {
            Toast.makeText(this, "Please fill name first", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 101, 1, "All Caterers");
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == 101) {
            Intent intent = new Intent(AddCatererActivity.this, AllCaterersActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}

