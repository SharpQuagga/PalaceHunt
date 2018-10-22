package com.mycompany.secondproject.caterers;

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
import com.mycompany.secondproject.AllPalacesActivity;
import com.mycompany.secondproject.Palace;
import com.mycompany.secondproject.PalcesList;
import com.mycompany.secondproject.R;

import java.util.ArrayList;
import java.util.List;

public class AllCaterersActivity extends AppCompatActivity {

    DatabaseReference databaseCaterers;
    ListView listView;
    List<Caterer> CatererList;

    public void initViews(){
        CatererList = new ArrayList<>();
        listView = findViewById(R.id.listviewC);
        databaseCaterers = FirebaseDatabase.getInstance().getReference("Caterers");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_caterers);
        initViews();
        if (haveNetworkConnection()){

        }else {
            Toast.makeText(this,"Please Turn on Wifi or Mobile Data to see Palaces",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();


        databaseCaterers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CatererList.clear();
                for (DataSnapshot catererSnapshot : dataSnapshot.getChildren()){
                   Caterer caterer = catererSnapshot.getValue(Caterer.class);
                    CatererList.add(caterer);
                }

                    CatererList adapter = new CatererList(AllCaterersActivity.this,CatererList);
                    listView.setAdapter(adapter);
               // PalcesList adapter = new PalcesList(AllCaterersActivity.this,CatererList);
              //  listView.setAdapter(adapter);
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
