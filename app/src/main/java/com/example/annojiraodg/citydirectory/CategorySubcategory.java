package com.example.annojiraodg.citydirectory;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class CategorySubcategory extends AppCompatActivity {
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

    ArrayList<String> subCategoryArrayList = new ArrayList<String>();
    String[] subCategoriesArray ={};
    EditText searchSubCategorytext;
    String mainCategory;
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("subcategories.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void initialise(String cat){

        try {
            JSONObject jsonRootObject  = new JSONObject(loadJSONFromAsset());
            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            mainCategory = sharedpreferences.getString(MC,"");
            JSONArray jsonArray = jsonRootObject.optJSONArray(mainCategory);
            subCategoryArrayList.clear();
            JSONArray jsonCategory = (JSONArray)jsonArray;
            if (jsonCategory != null) {
                int len = jsonCategory.length();
                if(cat.equals("noConstraint")) {
                    for (int i = 0; i < len; i++) {
                        subCategoryArrayList.add(jsonCategory.get(i).toString());
                    }
                }
                else{
                    for (int i = 0; i < len; i++) {
                        String data = jsonCategory.get(i).toString();
                        if(data.contains(cat)) {
                            subCategoryArrayList.add(jsonCategory.get(i).toString());
                        }
                    }
                }
            }
        } catch (JSONException e) {
            //e.printStackTrace();
        }
        //Collections.sort(subCategoryArrayList);
        subCategoriesArray = subCategoryArrayList.toArray(new String[subCategoryArrayList.size()]);
        ArrayAdapter adapter = new CustomSublistAdapter(this,subCategoriesArray);
        //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        ListView listView = (ListView) findViewById(R.id.subCategoryList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                String Selecteditem= subCategoriesArray[+position];
                //Toast.makeText(getApplicationContext(),Selecteditem, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(CategorySubcategory.this, CategorySuperSubcategory.class);
                //Intent i = new Intent(CategorySubcategory.this, NotfoundResult.class);
                //i.putExtra("subCategory",Selecteditem);
                //i.putExtra("mainCategory",mainCategory);
                //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(SC, Selecteditem);
                editor.commit();
                startActivity(i);
            }
        });

    }

    private void searchItem(String s){
        initialise(s);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        location = sharedpreferences.getString(MylocName,"");
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
        setContentView(R.layout.activity_category_subcategory);
        Intent subCategotyIntent = getIntent();
        mainCategory = subCategotyIntent.getStringExtra("MainCategory");

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayOptions(actionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar);
        actionBar.setBackgroundDrawable(ContextCompat.getDrawable(this, R.color.band));
        TextView locView = (TextView)findViewById(R.id.set_locationname);
        int maxLength = 13;
        if(location.length() > 10) {
            location = location.substring(0, maxLength-1) + "...";
        }
        locView.setText(""+location);

        actionBar.setDisplayShowHomeEnabled(true);
        //actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.ic_launcher);


        initialise("noConstraint");
        searchSubCategorytext = (EditText)findViewById(R.id.searchSubCategoryText);
        searchSubCategorytext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    //reset listView
                    initialise("noConstraint");
                }
                else{
                    //perform search activity
                    searchItem(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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
        editor.putString(PA, "CategorySubcategory");
        editor.commit();
        Intent i = new Intent(CategorySubcategory.this, MainActivity.class);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
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
        editor.putString(PA, "CategorySubcategory");
        editor.commit();
        Intent i = new Intent(CategorySubcategory.this, MainCategory.class);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
