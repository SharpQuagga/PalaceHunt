package com.mycompany.secondproject;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import java.util.List;

public class SerachPalaceList extends ArrayAdapter<Palace> {

    private Activity context;
    private List<Palace> palacelist;
    private ImageView imageView;
    public String url;
    public String BookedDates;
    public String PalaceCity;
    TextView textView;

    int pos;
    Palace palace;

    public SerachPalaceList(Activity context, List<Palace> palacelist) {
        super(context, R.layout.list_layout, palacelist);
        this.context = context;
        this.palacelist = palacelist;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        Search:
        {
            Intent intent = ((Activity) context).getIntent();
            String UserDate1 = intent.getStringExtra("InputDate");
            String UserCity = intent.getStringExtra("UserCity");
            // Log.e("InputDare",UserDate1);

            Palace palace12 = palacelist.get(position);
            BookedDates = palace12.getBooked();
            PalaceCity = palace12.getCity();
            Log.e("Data", "Dates " + BookedDates);


            if (BookedDates.contains(UserDate1) || !PalaceCity.contains(UserCity)) {
//            textView = listViewItem.findViewById(R.id.textView3);
//            textView.setText("NOT AVAILABLE");
//            txtAddress.setText("N/A");
//            txtphone.setText("N/A");
//            Log.e("Match","NOT SHOWING");
                listViewItem = inflater.inflate(R.layout.empty_layout, null, true);
                break Search;

            } else {
                listViewItem = inflater.inflate(R.layout.list_layout, null, true);
                TextView txtName = listViewItem.findViewById(R.id.textViewName);
                TextView txtAddress = listViewItem.findViewById(R.id.textViewAddress);
                TextView txtphone = listViewItem.findViewById(R.id.textViewphone);
                TextView txtCity = listViewItem.findViewById(R.id.textViewCity);
                imageView = listViewItem.findViewById(R.id.imageView2);

                txtName.setText(palace12.getName());
                txtAddress.setText(palace12.getAddress());
                txtphone.setText(palace12.getPhone());
                txtCity.setText(palace12.getCity());
                url = palace12.getImageUrl();
                Picasso.get().load(url).into(imageView);
            }
        }

            return listViewItem;
        }

    }