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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mycompany.secondproject.caterers.AddCatererActivity;
import com.mycompany.secondproject.caterers.Caterer;
import com.mycompany.secondproject.caterers.SignUpCatererActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etxtName ,etxtPhone, etxtadderss , etxtCity,etxtPass, etxtEmail;
    TextView txtLogIn;
    Button btnSubmit;    ProgressDialog progressDialog;
    FirebaseAuth auth;      Palace palace;



    public void initViews(){
        etxtName = findViewById(R.id.editTextnamep);
        btnSubmit = findViewById(R.id.button);
        etxtPhone = findViewById(R.id.edittextphonep);
        etxtadderss = findViewById(R.id.edittextaddressp);
        etxtCity = findViewById(R.id.editTextCityp);
        etxtEmail = findViewById(R.id.editTextp);
        etxtPass = findViewById(R.id.editText2p);
        txtLogIn = findViewById(R.id.textViewLogInp);
        txtLogIn.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
        palace = new Palace();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        getSupportActionBar().hide();
        initViews();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.textViewLogInp){
            Intent intent = new Intent(MainActivity.this,SignUpActivity.class);
            startActivity(intent);
        }
        if (id == R.id.button){
            Log.e("onclick","cjala");
            palace.name = etxtName.getText().toString().trim();
            palace.address = etxtadderss.getText().toString().trim();
            palace.city = etxtCity.getText().toString().trim();
            palace.phone = etxtPhone.getText().toString().trim();
            palace.password = etxtPass.getText().toString().trim();
            palace.email = etxtEmail.getText().toString().trim();
            // Firebase code
            registerUser();
        }

    }

    private void registerUser() {
        progressDialog.show();

        auth.createUserWithEmailAndPassword(palace.email ,palace.password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this,"User Registered",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivity.this,SignUpActivity.class);
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









//    DatabaseReference databasePalace;
// databasePalace = FirebaseDatabase.getInstance().getReference("palace");

// previous onClick method

//    String name = etxtName.getText().toString();
//    String address = etxtadderss.getText().toString();
//    String phone = etxtPhone.getText().toString();
//    String imageUrl = "";
//    String Book="";
//    String city = etxtCity.getText().toString();
//        if (!name.isEmpty()){
//                String id = databasePalace.push().getKey();
//                Palace palace = new Palace(id,name,address,phone,imageUrl,Book,city);
//                databasePalace.child(id).setValue(palace);
//                Toast.makeText(this,"data entered",Toast.LENGTH_LONG).show();
//                etxtName.setText("");
//                etxtadderss.setText("");
//                etxtPhone.setText("");
//                etxtCity.setText("");
//                }else {
//                Toast.makeText(this,"Please fill name first",Toast.LENGTH_LONG).show();
//                }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        menu.add(1, 101, 1, "All Palaces");
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        if(item.getItemId() == 101){
//            Intent intent = new Intent(MainActivity.this,AllPalacesActivity.class);
//            startActivity(intent);
//        }
//        return super.onOptionsItemSelected(item);
//    }