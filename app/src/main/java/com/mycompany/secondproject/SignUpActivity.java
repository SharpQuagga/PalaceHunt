package com.mycompany.secondproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mycompany.secondproject.caterers.LoggedInCatererActivity;
import com.mycompany.secondproject.caterers.SignUpCatererActivity;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etxtEmail, etxtPass;
    Palace palace;
    Button btnLogIn;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    String Email, pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initViews();
    }

    private void initViews() {
        etxtEmail = findViewById(R.id.editText4pp);
        etxtPass = findViewById(R.id.editText5pp);
        btnLogIn = findViewById(R.id.button3);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        btnLogIn.setOnClickListener(this);
//        caterer = new Caterer();

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {

            finish();
            startActivity(new Intent(SignUpActivity.this, LoggedInActivity.class));
        }


        Email = etxtEmail.getText().toString().trim();
        pass = etxtPass.getText().toString().trim();

        Log.e("Firrrrrrr", "coooo" + Email + "dvvvvv" + pass);

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button3){
//             Firebae code
//            if (caterer.email.isEmpty() || caterer.password.isEmpty()){
//                Toast.makeText(this,"Plears fill details",Toast.LENGTH_LONG).show();
//
//            }else {
            userLogin();
//        }
        }

    }

    private void userLogin() {
        progressDialog.show();
        Log.e("Firebasesss","sfs");
        auth.signInWithEmailAndPassword(etxtEmail.getText().toString(),etxtPass.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(SignUpActivity.this,"Login SuccessFull",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SignUpActivity.this,LoggedInActivity.class);
                        startActivity(intent);
                        progressDialog.dismiss();
                        finish();
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                Toast.makeText(SignUpActivity.this,"Login UnSuccessFull"+e.getMessage(),Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                Intent intent = new Intent(SignUpActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
