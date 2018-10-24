package com.mycompany.secondproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mycompany.secondproject.caterers.ChoiceActivity;
import com.mycompany.secondproject.caterers.LoggedInCatererActivity;

public class LoggedInActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLOUt; FirebaseAuth auth;   FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);


        btnLOUt = findViewById(R.id.button2C);
        btnLOUt.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
    }

    @Override
    public void onClick(View v) {
        auth.signOut();
        Intent intent = new Intent(LoggedInActivity.this,ChoiceActivity.class);
        startActivity(intent);
    }
}

