package com.mycompany.secondproject.caterers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mycompany.secondproject.R;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etxtPhone , etxtPass;
    Button btnLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initViews();
    }

    private void initViews() {
        etxtPass = findViewById(R.id.textView5);
        etxtPhone = findViewById(R.id.textView9);
        btnLogIn = findViewById(R.id.button3);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button3){
            // Firebae code

        }
    }
}
