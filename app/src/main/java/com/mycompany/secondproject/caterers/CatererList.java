package com.mycompany.secondproject.caterers;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mycompany.secondproject.Palace;
import com.mycompany.secondproject.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CatererList extends ArrayAdapter<Caterer> {
    private Activity context;
    private List<Caterer> catererList;
    private ImageView imageView;
    public String url;

    public CatererList(Activity context , List<Caterer> catererList){
        super(context, R.layout.list_layout,catererList);
        this.context=context;
        this.catererList=catererList;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.caterer_list_layout,null,true);
        TextView txtName = listViewItem.findViewById(R.id.textViewName);
        TextView txtAddress = listViewItem.findViewById(R.id.textViewAddress);
        TextView txtphone = listViewItem.findViewById(R.id.textViewphone);
        TextView txtCity=listViewItem.findViewById(R.id.textViewCity);
        imageView = listViewItem.findViewById(R.id.imageView2);

        Caterer caterer12 = catererList.get(position);
        txtName.setText(caterer12.getName());
        txtAddress.setText(caterer12.getAddress());
        txtphone.setText(caterer12.getPhone());
        txtCity.setText(caterer12.getCity());

//        url = caterer12.getImageUrl();
//        Picasso.get().load(url).into(imageView);

        return listViewItem;
    }
}
