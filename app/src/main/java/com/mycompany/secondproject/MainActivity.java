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
    FirebaseAuth auth;      Palace palace;

    Uri mImageUri;      StorageReference mStorageRef;
    DatabaseReference databasePalace;     String downloadUrl;
    StorageTask mUploadTask;

    public void initViews(){
        etxtName = findViewById(R.id.editTextnamep);
        btnSubmit = findViewById(R.id.button);
        etxtPhone = findViewById(R.id.edittextphonep);
        etxtadderss = findViewById(R.id.edittextaddressp);
        etxtCity = findViewById(R.id.editTextCityp);
        etxtEmail = findViewById(R.id.editTextp);
        etxtPass = findViewById(R.id.editText2p);
        imageView = findViewById(R.id.imageButton);
        txtLogIn = findViewById(R.id.textViewLogInp);
        btnPhoto = findViewById(R.id.buttonPhoto);
        btnPhoto.setOnClickListener(this);
        txtLogIn.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
        palace = new Palace();

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

        if (id == R.id.buttonPhoto){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent,1);

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode==RESULT_OK && data != null && data.getData()!= null){
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(imageView);
            Toast.makeText(this,"Uri"+mImageUri,Toast.LENGTH_LONG).show();
            uploadFile();
           // StorageUpload();

        }

    }

    private String getFileExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void uploadFile() {
        if (mUploadTask != null && mUploadTask.isInProgress()) {
            Toast.makeText(MainActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
        } else {
            if (mImageUri != null) {
                final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                        + "." + getFileExtension(mImageUri));

                mUploadTask = fileReference.putFile(mImageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Toast.makeText(MainActivity.this, "Upload successful", Toast.LENGTH_LONG).show();

                                fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Uri download = uri;
                                        downloadUrl = download.toString();
                                    }
                                });
                                Log.e("uploaded", "AA " + downloadUrl);
                                // String uploadId = mDatabaseRef.push().getKey();
                                //mDatabaseRef.child(uploadId).setValue(upload);
                                Toast.makeText(MainActivity.this, "downloadURi" + downloadUrl, Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void StorageUpload() {
        mStorageRef.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

              //downloadUrl =String.valueOf(taskSnapshot.getStorage().getDownloadUrl());
             downloadUrl= taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();


              Log.e("upload","uploaded na"+downloadUrl);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                exception.printStackTrace();
                Log.e("Error","d"+exception.getMessage());
                // Handle unsuccessful uploads
                // ...
            }
        });
        Toast.makeText(this,"downloadURi"+downloadUrl,Toast.LENGTH_LONG).show();
    }

    public void StoragePart2(){
        mStorageRef.child(String.valueOf(mImageUri)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                downloadUrl = String.valueOf(uri);
                Log.e("uploaded","aa"+uri);
                // Got the download URL for 'users/me/profile.png'
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        Toast.makeText(this,"downloadURi"+downloadUrl,Toast.LENGTH_LONG).show();

// Alternatively way to get download URL
//     downloadUrl = String.valueOf(mStorageRef.child(String.valueOf(mImageUri)).getDownloadUrl().getResult());
//        Toast.makeText(this,"downloadURi"+downloadUrl,Toast.LENGTH_LONG).show();
    }


    private void databaseUser() {
        String name = etxtName.getText().toString();
        String address = etxtadderss.getText().toString();
        String phone = etxtPhone.getText().toString();
        String  imageUrl = String.valueOf(downloadUrl);
        String Book="";
        String city = etxtCity.getText().toString();
        String Email = etxtEmail.getText().toString();
        String Pass = etxtPass.getText().toString();
            if (!name.isEmpty()){
                    String id = databasePalace.push().getKey();
                 //   Palace palace = new Palace(imageUrl,id,name,address,phone,Book,city);
                Palace palace = new Palace(id,imageUrl,name,address,phone,Book,city,Email,Pass);
                    databasePalace.child(id).setValue(palace);
                    Toast.makeText(this,"data entered",Toast.LENGTH_LONG).show();

                    etxtName.setText("");
                    etxtadderss.setText("");
                    etxtPhone.setText("");
                    etxtCity.setText("");
                    etxtEmail.setText("");
                    etxtPass.setText("");
                        }else {
                        Toast.makeText(this,"Please fill name first",Toast.LENGTH_LONG).show();
                        }

    }

    private void registerUser() {
        progressDialog.show();

        if (etxtEmail.getText().toString().endsWith(".com") ||  !etxtPass.getText().toString().contains("0-9") ) {


            auth.createUserWithEmailAndPassword(palace.email, palace.password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                // Toast.makeText(MainActivity.this,"User Registered",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                                startActivity(intent);
                            }
                        }
                    }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("na", "na" + e.getMessage());
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            });
        }else{
            Toast.makeText(this,"Wrong Details Please Check Again",Toast.LENGTH_LONG).show();
        }

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