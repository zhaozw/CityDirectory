package com.example.annojiraodg.citydirectory;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by annojirao.dg on 01/06/2016.
 */
public class HomeActivity extends AppCompatActivity implements OnClickListener {
    private Handler handler;
    private  Runnable delayRunnable;
    public static final String MyPREFERENCES = "Sharedpre" ;
    public static final String MylocName = "MyLoc";
    private String prevPressed;
    SharedPreferences sharedpreferences;
    private String location;
    public static final String PrevPress = "PrevPress";
    Intent i;
    private void populateLocationArea(){
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        location = sharedpreferences.getString(MylocName,"");
//        SharedPreferences.Editor editor = sharedpreferences.edit();
//        editor.putString(PrevPress, "0");
//        editor.commit();
        if(!location.isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), "Your search happens in " + location + " area", Toast.LENGTH_SHORT);
            //Toast.makeText(getApplicationContext(), "Your search happens in " + location + " area", Toast.LENGTH_SHORT).show();
            View view = toast.getView();
            view.setBackgroundColor(123456);
            toast.show();
        }
        //t.setText(location);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(ContextCompat.getDrawable(this, R.color.band));
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        location = sharedpreferences.getString(MylocName,"");
        prevPressed = sharedpreferences.getString(PrevPress,"");
        handler = new Handler();
        //populateLocationArea();
//        if( getIntent().getBooleanExtra("Exit me", false)){
//            getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            handler.removeCallbacks(delayRunnable);
//            finish();
//
//        }
        if(prevPressed.equals("1")) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(PrevPress, "0");
            editor.commit();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        else{

            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
        //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Runnable delayRunnable = new Runnable() {
            @Override
            public void run() {
                if(location != null && !location.isEmpty() && !location.equals("null")){
                     i = new Intent(getApplicationContext(), MainCategory.class);
                }
                else{
                     i = new Intent(getApplicationContext(), MainActivity.class);
                }
                //overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
                //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                if(!prevPressed.equals("1")){
                    startActivity(i);
                }

            }
        };
        handler.postDelayed(delayRunnable, 2000);

    }

    @Override
    public void onClick(View v) {
    // OVERRIDE YOUR METHOD HERE
    }
}
