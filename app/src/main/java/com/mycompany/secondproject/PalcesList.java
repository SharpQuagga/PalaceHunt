package com.mycompany.secondproject;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PalcesList extends ArrayAdapter<Palace> {
    private Activity context;
    private List<Palace> palacelist;
   private ImageView imageView;
   public String url;

   public PalcesList(Activity context , List<Palace> palacelist){
       super(context,R.layout.list_layout,palacelist);
       this.context=context;
       this.palacelist=palacelist;

   }


    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout,null,true);
        TextView txtName = listViewItem.findViewById(R.id.textViewName);
        TextView txtAddress = listViewItem.findViewById(R.id.textViewAddress);
        TextView txtphone = listViewItem.findViewById(R.id.textViewphone);
        TextView txtCity=listViewItem.findViewById(R.id.textViewCity);
        imageView = listViewItem.findViewById(R.id.imageView2);

        Palace palace12 = palacelist.get(position);
        txtName.setText(palace12.getName());
        txtAddress.setText(palace12.getAddress());
        txtphone.setText(palace12.getPhone());
        txtCity.setText(palace12.getCity());

        url = palace12.getImageUrl();
        Picasso.get().load(url).into(imageView);

        return listViewItem;
    }
}
