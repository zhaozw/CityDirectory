package com.example.annojiraodg.citydirectory;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by annojirao.dg on 30/05/2016.
 */
public class MainActivity extends AppCompatActivity implements OnClickListener {
    private static final String TAG = "Mainactivity";
    public static final String MyPREFERENCES = "Sharedpre" ;
    public static final String MylocName = "MyLoc";
    public static final String PrevPress = "PrevPress";
    public static final String PA = "Pa";
    boolean doubleBackToExitPressedOnce = false;
    String[] locationArray;
    String prevAct;
    Intent i;
    String location;
    SharedPreferences sharedpreferences;
    private String prevPressed;
    private void populateLocationArea(){
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        location = sharedpreferences.getString(MylocName,"");
        TextView t = (TextView) findViewById(R.id.locationTextArea);
        EditText ed = (EditText)findViewById(R.id.locationTextArea);
        if(location != null) {
            t.setText(location);
            ed.setSelection(location.length());
        }
        else{
            t.setText(location);
        }

    }
     @Override
    protected void onCreate(Bundle savedInstanceState) {
         Log.d(TAG,"inside oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateLocationArea();
        final AutoCompleteTextView locationView = (AutoCompleteTextView) findViewById(R.id.locationTextArea);
        locationArray = getResources().getStringArray(R.array.location_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, locationArray);
        locationView.setAdapter(adapter);
        locationView.setThreshold(1);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

         prevPressed = sharedpreferences.getString(PrevPress,"");
        //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
         if(prevPressed.equals("1")) {
             SharedPreferences.Editor editor = sharedpreferences.edit();
             editor.putString(PrevPress, "0");
             editor.commit();
             overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
         }
         else{

             overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
         }
        Button submitButton = (Button) findViewById(R.id.nextButton);
         sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
         submitButton.setOnClickListener(new View.OnClickListener() {

             public void onClick(View v) {
                 // Perform action on click

                 TextView t = (TextView) findViewById(R.id.locationTextArea);
                 String typedAreaData = t.getText().toString();
                 if(typedAreaData == "null" ||typedAreaData.equals("")){
                     return;
                 }
                 SharedPreferences.Editor editor = sharedpreferences.edit();
                 editor.putString(MylocName, typedAreaData);
                 editor.commit();
                 prevAct = sharedpreferences.getString(PA,"");
                 Toast toast = Toast.makeText(getApplicationContext(), "prev actvity " + prevAct + " ", Toast.LENGTH_SHORT);
                 toast.show();
                 if(prevAct != null){
                     switch(prevAct){
                         case "CategorySubcategory":
                             i = new Intent(MainActivity.this, CategorySubcategory.class);
                             break;
                         case "ClicktoCall":
                             i = new Intent(MainActivity.this, ClicktoCall.class);
                             break;
                         case "MainCategory":
                             i = new Intent(MainActivity.this, MainCategory.class);
                             break;
                         case "CategorySuperSubcategory":
                             i = new Intent(MainActivity.this, CategorySuperSubcategory.class);
                             break;
                     }

                 }else{
                     i = new Intent(MainActivity.this, MainCategory.class);
                 }
                 //Intent i = new Intent(MainActivity.this, MainCategory.class);
                 i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                 i.putExtra("Area",typedAreaData);
                 startActivity(i);
             }
         });

         android.support.v7.app.ActionBar actionBar = getSupportActionBar();
         actionBar.setBackgroundDrawable(ContextCompat.getDrawable(this, R.color.band));
         //actionBar.setSubtitle("Your search location is "+location);
         //actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_home_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.contact_us:
                showContactusDialog();
                return true;
            case R.id.about_us:
                AboutUsView();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void AboutUsView(){

    }

    private void showContactusDialog(){

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        alertDialog.setTitle("Contact Us");
        alertDialog.setMessage("7829106205");

        alertDialog.show();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if ((Build.VERSION.SDK_INT) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(PrevPress, "1");
        editor.commit();
        Intent i = new Intent(MainActivity.this, HomeActivity.class);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
