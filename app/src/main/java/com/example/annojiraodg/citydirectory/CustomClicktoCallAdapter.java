package com.example.annojiraodg.citydirectory;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
/**
 * Created by annojirao.dg on 08/06/2016.
 */
public class CustomClicktoCallAdapter extends ArrayAdapter<String> {
    private final String[] name;
    private final String[] phoneNumber;
    final String Tag = "customAdapter";
    public CustomClicktoCallAdapter(Context context, String[] names, String[] phoneNumber) {
        super(context, R.layout.activity_clickto_callblock,names);
        this.name = names;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int row = position;
        LayoutInflater inflator =  LayoutInflater.from(getContext());
        View customView = inflator.inflate(R.layout.activity_clickto_callblock,parent,false);

        String name = getItem(position);
        TextView nameText = (TextView) customView.findViewById(R.id.nameTextView);
        TextView phoneText = (TextView) customView.findViewById(R.id.phoneNoTextView);
        Button clicktoCall = (Button) customView.findViewById(R.id.clicktoCallButton);
        //clicktoCall.setHint(phoneNumber[position]);

        nameText.setText(name);
        phoneText.setText(phoneNumber[position]);
        clicktoCall.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                //DO SOMETHING! {RUN SOME FUNCTION ... DO CHECKS... ETC}
                Log.i(Tag,phoneNumber[row]);
                Intent in=new Intent(Intent.ACTION_CALL, Uri.parse(phoneNumber[row]));
                try{
                    getContext().startActivity(in);

                    //startActivity(in);
                }

                catch (android.content.ActivityNotFoundException ex){

                }
            }
        });
        //return super.getView(position, convertView, parent);
        return customView;
    }
}
