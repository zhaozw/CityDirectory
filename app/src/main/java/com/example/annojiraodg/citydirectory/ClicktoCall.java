package com.example.annojiraodg.citydirectory;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ClicktoCall extends AppCompatActivity {
    private static final String TAG = "Mainactivity";
    public static final String MyPREFERENCES = "Sharedpre" ;
    public static final String MylocName = "MyLoc";
    public static final String PrevPress = "PrevPress";
    public static final String SC = "Sc";
    public static final String MC = "Mc";
    public static final String PA = "Pa";
    private String prevPressed;
    String location;
    SharedPreferences sharedpreferences;
    ArrayList<String> list = new ArrayList<String>();

    //String[] mobileNoArray = {"7829106205","213432432432"};
    ArrayList<String> phoneNoArrayList = new ArrayList<String>();
    //phoneNoArrayList.add("7829106205");
    String mainCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        prevPressed = sharedpreferences.getString(PrevPress,"");
        super.onCreate(savedInstanceState);

        if(prevPressed.equals("1")) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(PrevPress, "0");
            editor.commit();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        else{

            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }

        setContentView(R.layout.activity_clickto_calllist);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        location = sharedpreferences.getString(MylocName,"");
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(actionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar);
        TextView locView = (TextView)findViewById(R.id.set_locationname);
        int maxLength = 13;
        if(location.length() > 10) {
            location = location.substring(0, maxLength-1) + "...";
        }
        locView.setText(""+location);
        locView.setText(" "+location);

        actionBar.setDisplayShowHomeEnabled(true);
        //actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.ic_launcher);

        actionBar.setDisplayHomeAsUpEnabled(true);
        String[] nameArray = {"Anoop","prasanna","Annojirao","Abhijith","ashwath"};
        String[] mobileNoArray = {"7829106205","213432432432","213432432","2354524","426658768"};
        mainCategory = getIntent().getStringExtra("mainCategory");
        String [] phonenoArray = phoneNoArrayList.toArray(new String[phoneNoArrayList.size()]);

        ArrayAdapter adapter = new CustomClicktoCallAdapter(this,nameArray,mobileNoArray);

        ListView listView1 = (ListView) findViewById(R.id.listViewClicktoCall);
        listView1.setAdapter(adapter);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
           case R.id.set_location:
                setLocationRedirect();
                return true;
//            case R.id.about_us:
//                AboutUsView();
//                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void AboutUsView(){

    }
    private void setLocationRedirect(){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(PA, "ClicktoCall");
        editor.commit();
        Intent i = new Intent(ClicktoCall.this, MainActivity.class);
        //overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(PrevPress, "1");
        editor.commit();
        Intent i = new Intent(ClicktoCall.this, CategorySubcategory.class);
        i.putExtra("MainCategory",mainCategory);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
