package com.mycompany.secondproject.caterers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
import android.util.Log;
import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mycompany.secondproject.R;

public class AddCatererActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etxtName, etxtPhone, etxtadderss, etxtCity ,etxtPass, etxtEmail;
    TextView txtLogIn;

    Button btnSubmit;      ProgressDialog progressDialog;
   FirebaseAuth auth;   Caterer caterer;

    public void initViews() {
        etxtName = findViewById(R.id.editTextnameC);
        btnSubmit = findViewById(R.id.buttonC);
        etxtPhone = findViewById(R.id.edittextphoneC);
        etxtadderss = findViewById(R.id.edittextaddressC);
        etxtCity = findViewById(R.id.editTextCityC);
        txtLogIn = findViewById(R.id.textViewLogIn);
        etxtPass = findViewById(R.id.editText2p);
        etxtEmail = findViewById(R.id.editTextp);
        txtLogIn.setOnClickListener(this);
        getSupportActionBar().hide();
        btnSubmit.setOnClickListener(this);
        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
        caterer = new Caterer();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_caterer);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        getSupportActionBar().hide();
        initViews();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.textViewLogIn){
            Intent intent = new Intent(AddCatererActivity.this,SignUpCatererActivity.class);
            startActivity(intent);
        }
        if (id == R.id.buttonC){
            Log.e("onclick","cjala");
            caterer.name = etxtName.getText().toString().trim();
            caterer.address = etxtadderss.getText().toString().trim();
            caterer.city = etxtCity.getText().toString().trim();
            caterer.phone = etxtPhone.getText().toString().trim();
            caterer.password = etxtPass.getText().toString().trim();
            caterer.email = etxtEmail.getText().toString().trim();
            // Firebase code
            registerUser();
        }

    }

    private void registerUser() {
        progressDialog.show();

        auth.createUserWithEmailAndPassword(caterer.email ,caterer.password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(AddCatererActivity.this,"User Registered",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AddCatererActivity.this,SignUpCatererActivity.class);
                    startActivity(intent);
                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("na","na"+e.getMessage());
                e.printStackTrace();
                progressDialog.dismiss();
            }
        });

    }
}

