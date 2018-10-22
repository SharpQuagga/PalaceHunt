package com.mycompany.secondproject.caterers;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
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

public class SearchCatererList extends ArrayAdapter<Caterer>{

    private Activity context;
    private List<Caterer> catererlist;
    public String BookedDates;
    public String CatererCity;


    public SearchCatererList(Activity context, List<Caterer> catererlist) {
        super(context, R.layout.caterer_list_layout, catererlist);
        this.context = context;
        this.catererlist = catererlist;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem;

        Search:
        {
            Intent intent = ((Activity) context).getIntent();
            String UserDate1 = intent.getStringExtra("InputDate");
            String UserCity = intent.getStringExtra("UserCity");
            // Log.e("InputDare",UserDate1);

            Caterer caterer12 = catererlist.get(position);
            BookedDates = caterer12.getBooked();
            CatererCity = caterer12.getCity();
            Log.e("Data", "Dates " + BookedDates);


            if (BookedDates.contains(UserDate1) || !CatererCity.contains(UserCity)) {

                listViewItem = inflater.inflate(R.layout.empty_layout, null, true);
                break Search;

            } else {
                listViewItem = inflater.inflate(R.layout.caterer_list_layout, null, true);
                TextView txtName = listViewItem.findViewById(R.id.textViewName);
                TextView txtAddress = listViewItem.findViewById(R.id.textViewAddress);
                TextView txtphone = listViewItem.findViewById(R.id.textViewphone);
                TextView txtCity = listViewItem.findViewById(R.id.textViewCity);
               // imageView = listViewItem.findViewById(R.id.imageView2);

                txtName.setText(caterer12.getName());
                txtAddress.setText(caterer12.getAddress());
                txtphone.setText(caterer12.getPhone());
                txtCity.setText(caterer12.getCity());
                //url = palace12.getImageUrl();
                //Picasso.get().load(url).into(imageView);
            }
        }

        return listViewItem;
    }

}
