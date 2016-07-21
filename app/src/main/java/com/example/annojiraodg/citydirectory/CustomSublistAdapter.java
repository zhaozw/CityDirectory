package com.example.annojiraodg.citydirectory;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.system.StructPollfd;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * Created by annojirao.dg on 13/06/2016.
 */
public class CustomSublistAdapter extends ArrayAdapter<String> {
    private final String[] subcategories;
    private final Activity context;

    public CustomSublistAdapter(Activity context, String[] names) {
        super(context, R.layout.activity_sub_category,names);
        this.context = context;
        this.subcategories = names;
    }

    public View getView(int position,View view,ViewGroup parent) {

        LayoutInflater inflater= context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.activity_sub_category, null,true);

        TextView txtSubcategory = (TextView) rowView.findViewById(R.id.subCategoryTextView);
        TextView txtSubcategoryArrow = (TextView) rowView.findViewById(R.id.subCategoryArrowTextView);

        txtSubcategory.setText(subcategories[position]);
        txtSubcategoryArrow.setText(" > ");
        return rowView;

    };
}
