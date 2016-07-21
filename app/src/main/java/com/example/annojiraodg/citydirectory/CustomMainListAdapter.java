package com.example.annojiraodg.citydirectory;

/**
 * Created by annojirao.dg on 12/06/2016.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomMainListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final String[] imgid;

    public CustomMainListAdapter(Activity context, String[] itemname, String[] imgid) {
        super(context, R.layout.maincategory_sublist, itemname);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.itemname = itemname;
        this.imgid = imgid;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.maincategory_sublist, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        //TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        txtTitle.setText(itemname[position]);
        final String image = imgid[position];
        int id = context.getResources().getIdentifier(image, "drawable", context.getPackageName());
        imageView.setImageResource(id);
        return rowView;

    };
}