package com.mycompany.secondproject;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.mycompany.secondproject.caterers.AddCatererActivity;
import com.mycompany.secondproject.caterers.Caterer;
import com.mycompany.secondproject.caterers.SignUpCatererActivity;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etxtName ,etxtPhone, etxtadderss , etxtCity,etxtPass, etxtEmail;
    TextView txtLogIn;      ImageView imageView;
    Button btnSubmit,btnPhoto;    ProgressDialog progressDialog;
    //    Palace palace;
    FirebaseAuth auth;
    Uri mImageUri;      StorageReference mStorageRef;
    DatabaseReference databasePalace;     String downloadUrl;
    StorageTask mUploadTask;        String Email,Pass,name,address,phone,imageUrl,Book,city;

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

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Uploading");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        auth = FirebaseAuth.getInstance();

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        databasePalace = FirebaseDatabase.getInstance().getReference("palace");
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


//                palace.name = etxtName.getText().toString().trim();
//                palace.address = etxtadderss.getText().toString().trim();
//                palace.city = etxtCity.getText().toString().trim();
//                palace.phone = etxtPhone.getText().toString().trim();
//                palace.password = etxtPass.getText().toString().trim();
//                palace.email = etxtEmail.getText().toString().trim();
//                // Firebase code
//                registerUser();
            databaseUser();

        }
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1 && resultCode==RESULT_OK && data != null && data.getData()!= null){
//            mImageUri = data.getData();
//            Picasso.get().load(mImageUri).into(imageView);
//            Toast.makeText(this,"Uri"+mImageUri,Toast.LENGTH_LONG).show();
//            uploadFile();
//            // StorageUpload();
//
//        }
//
//    }

//    private String getFileExtension(Uri uri){
//        ContentResolver cr = getContentResolver();
//        MimeTypeMap mime = MimeTypeMap.getSingleton();
//        return mime.getExtensionFromMimeType(cr.getType(uri));
//    }
//
//    private void uploadFile() {
//        progressDialog.show();
//
//        if (mUploadTask != null && mUploadTask.isInProgress()) {
//            Toast.makeText(MainActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            if (mImageUri != null) {
//                final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
//                        + "." + getFileExtension(mImageUri));
//
//                mUploadTask = fileReference.putFile(mImageUri)
//                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                            @Override
//                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                                Toast.makeText(MainActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
//
//                                fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                    @Override
//                                    public void onSuccess(Uri uri) {
//                                        Uri download = uri;
//                                        downloadUrl = download.toString();
//                                    }
//                                });
//                                Log.e("uploaded", "AA " + downloadUrl);
//                                // String uploadId = mDatabaseRef.push().getKey();
//                                //mDatabaseRef.child(uploadId).setValue(upload);
//                                Toast.makeText(MainActivity.this, "downloadURi" + downloadUrl, Toast.LENGTH_LONG).show();
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                progressDialog.dismiss();
//            } else {
//                progressDialog.dismiss();
//                Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
//            }
//        }
//        progressDialog.dismiss();
//    }



    private void databaseUser() {
        name = etxtName.getText().toString();
        address = etxtadderss.getText().toString();
        phone = etxtPhone.getText().toString();
        imageUrl = String.valueOf(downloadUrl);
        Book="";
        city = etxtCity.getText().toString();
        Email = etxtEmail.getText().toString();
        Pass = etxtPass.getText().toString();
        if (!name.isEmpty() && !address.isEmpty() && !phone.isEmpty() && !city.isEmpty() && !Email.isEmpty() &&  !Pass.isEmpty() ){
            registerUser();

        }else {
            Toast.makeText(this,"Please fill all the details first",Toast.LENGTH_LONG).show();
        }

    }

    private void dataUSer() {
       // String id = databasePalace.push().getKey();
        String id = auth.getUid();
        //   Palace palace = new Palace(imageUrl,id,name,address,phone,Book,city);
        Palace palace = new Palace(id,imageUrl,name,address,phone,Book,city,Email,Pass);
        databasePalace.child(id).setValue(palace);
        Log.e("naaaaaaaa","aa"+id);
      //  Toast.makeText(this,"data entered"+palace.getName(),Toast.LENGTH_LONG).show();

        etxtName.setText("");
        etxtadderss.setText("");
        etxtPhone.setText("");
        etxtCity.setText("");
        etxtEmail.setText("");
        etxtPass.setText("");
    }

    private void registerUser() {
        progressDialog.show();

        auth.createUserWithEmailAndPassword(Email, Pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this,"User Registered"+auth.getUid(),Toast.LENGTH_LONG).show();
                            Log.e("naaaaaaaa","aa"+auth.getUid());
                            Intent intent = new Intent(MainActivity.this, LoggedInActivity.class);
                            startActivity(intent);
                            dataUSer();
                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("naaaaaaa", "na" + e.getMessage());
                e.printStackTrace();
                Toast.makeText(MainActivity.this,"User Not Registered"+e.getMessage(),Toast.LENGTH_LONG).show();
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