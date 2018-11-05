package com.mycompany.secondproject.caterers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mycompany.secondproject.MapsURLActivity;
import com.mycompany.secondproject.Palace;
import com.mycompany.secondproject.R;
import com.mycompany.secondproject.SearchedPalacsActivity;
import com.mycompany.secondproject.SerachPalaceList;

import java.util.ArrayList;
import java.util.List;

public class SearchedCaterersActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    DatabaseReference databasePalace;
    ListView listView;
    List<Caterer> SearchcatererList;   int pos;
    Caterer caterer;   String UCity;
    public void initViews(){
        SearchcatererList= new ArrayList<>();
        listView = findViewById(R.id.listviewSearchC);
        databasePalace = FirebaseDatabase.getInstance().getReference("Caterers");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_caterers);
        initViews();
        if (haveNetworkConnection()){

        }else {
            Toast.makeText(this,"Please Turn on Wifi or Mobile Data to see Palaces",Toast.LENGTH_LONG).show();
        }
        ProgressDialog();

        Intent rcv = getIntent();
        UCity = rcv.getStringExtra("UserCity");
    }

    @Override
    protected void onStart() {
        super.onStart();


        databasePalace.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SearchcatererList.clear();
                for (DataSnapshot catererSnapshot : dataSnapshot.getChildren()){
                   Caterer caterer1 = catererSnapshot.getValue(Caterer.class);
                    SearchcatererList.add(caterer1);
                }

                    SearchCatererList adapter = new SearchCatererList(SearchedCaterersActivity.this,SearchcatererList);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(SearchedCaterersActivity.this);
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

    public void ProgressDialog(){
        final ProgressDialog dialog = ProgressDialog.show(this, "", "Please Wait...",
                true);
        dialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                dialog.dismiss();
            }
        }, 3000);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        pos = i;
        caterer = SearchcatererList.get(i);

        Toast.makeText(this,"You Selected: "+caterer.name,Toast.LENGTH_LONG).show();

        showOptions();
    }

    private void showOptions() {
        String[] items = {"View Location","Call","Share via","Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which){
                    case 0:
                        Intent intent = new Intent(SearchedCaterersActivity.this,MapsURLActivity.class);
                        intent.putExtra("City",UCity);
                        startActivity(intent);
                        break;

                    case 1:
                        String number = caterer.getPhone();
                        Intent intent21 = new Intent(Intent.ACTION_DIAL);
                        intent21.setData(Uri.parse("tel:" + number));
                        startActivity(intent21);
                        break;

                    case 2:
                        Intent i=new Intent(android.content.Intent.ACTION_SEND);
                        i.setType("text/plain");
                        i.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject test");
                        i.putExtra(android.content.Intent.EXTRA_TEXT, "extra text that you want to put");
                        startActivity(Intent.createChooser(i,"Share via"));
                        break;

                    case 3:

                        break;
                }

            }
        });
        builder.create().show();
    }
}
