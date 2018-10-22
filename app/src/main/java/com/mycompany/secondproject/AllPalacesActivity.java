package com.mycompany.secondproject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllPalacesActivity extends AppCompatActivity {

    DatabaseReference databasePalace;
    ListView listView;
    List<Palace> palaceList;

    public void initViews(){
        palaceList= new ArrayList<>();
        listView = findViewById(R.id.listviewA);
        databasePalace = FirebaseDatabase.getInstance().getReference("palace");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_palaces);
        initViews();
            if (haveNetworkConnection()){

            }else {
                Toast.makeText(this,"Please Turn on Wifi or Mobile Data to see Palaces",Toast.LENGTH_LONG).show();
            }
    }

    @Override
    protected void onStart() {
        super.onStart();


        databasePalace.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                palaceList.clear();
                for (DataSnapshot palaceSnapshot : dataSnapshot.getChildren()){
                    Palace palace = palaceSnapshot.getValue(Palace.class);
                    palaceList.add(palace);
                }

                PalcesList adapter = new PalcesList(AllPalacesActivity.this,palaceList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
