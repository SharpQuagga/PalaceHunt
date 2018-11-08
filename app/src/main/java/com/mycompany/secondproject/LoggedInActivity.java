package com.mycompany.secondproject;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.mycompany.secondproject.caterers.ChoiceActivity;
import com.mycompany.secondproject.caterers.LoggedInCatererActivity;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.Calendar;

public class LoggedInActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    Palace palace;
    Button btnLOUt; FirebaseAuth auth;   FirebaseUser user;
    TextView txtBookedDates , txtSeeBooking , txtName, txtImage;
    ImageView imageView;
    DatabaseReference databaseReference;
    DatePickerDialog datePickerDialog;
    int startYear, starthMonth, startDay;
    StringBuffer dates;
    Uri mImageUri;       String downloadUrl;
    StorageTask mUploadTask;
    StorageReference mStorageRef;   ProgressDialog progressDialog;

    public String url;  String id;   public String Name;  public String address;   public String phone;
    public String booked;   public String city;  public String email; public String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        databaseReference = FirebaseDatabase.getInstance().getReference("palace");
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        initViews();
        retrieve();

        datePickerDialog = new DatePickerDialog(
                this, this, startYear, starthMonth, startDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

    }

    private void retrieve() {
        databaseReference.orderByChild("id").equalTo(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot datas: dataSnapshot.getChildren()){
//                    String details = datas.child("palace").getValue().toString();
                    Name = datas.getValue(Palace.class).getName();
                    url = datas.getValue(Palace.class).getImageUrl();
                    address = datas.getValue(Palace.class).getAddress();
                    phone =datas.getValue(Palace.class).getPhone();
                    booked = datas.getValue(Palace.class).getBooked();
                    city = datas.getValue(Palace.class).getCity();
                    email=datas.getValue(Palace.class).getEmail();
                    password= datas.getValue(Palace.class).getPassword();
                    id = datas.getValue(Palace.class).getId();

                    dates = new StringBuffer(booked);
                    Picasso.get().load(String.valueOf(url)).into(imageView);
                    Toast.makeText(LoggedInActivity.this,"id"+id,Toast.LENGTH_SHORT).show();

                    Log.e("Naaaaaaaaaaaa","name "+Name+"url "+url+" addres "+address+" phone "+phone+" city "+city+" ema "+email);
                    txtName.setText(Name);

                    if (url == null){
                        imageView.setImageResource(R.drawable.bangkok);
                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

//
//    @Override
//    protected void onStart() {
//        super.onStart();
//
////        databaseReference.addValueEventListener(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
////                    Palace palace = dataSnapshot1.getValue(Palace.class);
////                    Log.e("naaaaaaaa", "aaaca" + palace.getName());
////                }
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError databaseError) {
////                Log.e("error","d"+databaseError);
////            }
////        });
//
////        databaseReference.orderByChild("name").equalTo(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()).addListenerForSingleValueEvent(new ValueEventListener() {
////            @Override
////            public void onDataChange(DataSnapshot dataSnapshot) {
////                for(DataSnapshot datas: dataSnapshot.getChildren()){
////                    String details = datas.child("palace").getValue().toString();
////                    String firstName = dataSnapshot.getValue(Palace.class).getName();
////                    txtName.setText(firstName);
////                }
////            }
////            @Override
////            public void onCancelled(DatabaseError databaseError) {
////            }
////        });
//
//    }

    private void initViews() {
        btnLOUt = findViewById(R.id.button2);
        txtBookedDates = findViewById(R.id.textView4);
        txtName = findViewById(R.id.textView10);
        imageView = findViewById(R.id.imageView5);
        txtSeeBooking = findViewById(R.id.textView7);
        txtImage = findViewById(R.id.textView9);
        txtImage.setOnClickListener(this);
        btnLOUt.setOnClickListener(this);
        txtSeeBooking.setOnClickListener(this);
        txtBookedDates.setOnClickListener(this);
        dates = new StringBuffer();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        Log.e("naaaaaaaa","aa"+user.getDisplayName());
        Log.e("naaasssaaaaaaa","aa"+user.getUid());

        progressDialog = new ProgressDialog(LoggedInActivity.this);
        progressDialog.setMessage("Uploading");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

//
//        if (user !=null){
//            if (user.getPhotoUrl() != null){
//                String pURl = user.getPhotoUrl().toString();
//                Picasso.get().load(String.valueOf(pURl)).into(imageView);
//            }if (user.getDisplayName() != null){
//               // String Name = user.getDisplayName();
//              //  txtName.setText(Name);
//            }
//            Toast.makeText(this,"notjhing",Toast.LENGTH_SHORT).show();
//        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.button2) {
            auth.signOut();
            Intent intent = new Intent(LoggedInActivity.this, ChoiceActivity.class);
            startActivity(intent);
        }

        if (id == R.id.textView7) {
            //Booked Dates

            final AlertDialog.Builder builder = new AlertDialog.Builder(LoggedInActivity.this);
            builder.setTitle("Booked Dates");
            builder.setMessage(dates);
            builder.setCancelable(true);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.show();
        }

        if (id == R.id.textView4) {
            //date Picker
            datePickerDialog.show();
        }
        if (id == R.id.textView9){
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
        }
    }


    @Override
    public void onDateSet(DatePicker view, int i, int i1, int i2) {
        i1 = i1 + 1;
        Toast.makeText(LoggedInActivity.this,i2+" "+i1+" "+i,Toast.LENGTH_LONG).show();
        int abc=i2;


        dates.append(abc);
        dates.append(",");


        Toast.makeText(this,"this month"+dates,Toast.LENGTH_SHORT).show();
        UpdateData(id, downloadUrl, Name, address, phone, booked, city, email, password);
    }

    private String getFileExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void uploadFile() {

        if (mUploadTask != null && mUploadTask.isInProgress()) {
            Toast.makeText(LoggedInActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
        }
        else {
            if (mImageUri != null) {
                final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                        + "." + getFileExtension(mImageUri));

                progressDialog.show();
                mUploadTask = fileReference.putFile(mImageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Toast.makeText(LoggedInActivity.this, "Upload successful", Toast.LENGTH_LONG).show();

                                fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        downloadUrl = uri.toString();
                                        if (downloadUrl.isEmpty()){
                                            Toast.makeText(LoggedInActivity.this,"File Not Uploaded",Toast.LENGTH_SHORT).show();
                                        }else {
                                            UpdateData(id, downloadUrl, Name, address, phone, booked, city, email, password);
                                        }
                                    }
                                });
                                Log.e("uploaded", "AA " + downloadUrl);

                                Toast.makeText(LoggedInActivity.this, "downloadURi" + downloadUrl, Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoggedInActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                progressDialog.dismiss();
            } else {
                progressDialog.dismiss();
                Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void UpdateData(String id,String url, String name, String address, String phone, String booked, String city, String email, String password) {

        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("palace").child(id);

        booked = dates.toString();
        Palace palace1 = new Palace(id,url,name,address,phone,booked,city,email,password);
        databaseReference1.setValue(palace1);
    }


}

