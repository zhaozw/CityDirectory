package com.example.annojiraodg.citydirectory;


import android.app.ActionBar;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.content.ContextCompat;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

public class MainCategory extends AppCompatActivity {
    private static final String TAG = "Mainactivity";
    public static final String MyPREFERENCES = "Sharedpre" ;
    public static final String MylocName = "MyLoc";
    public static final String PrevPress = "PrevPress";
    public static final String MC = "Mc";
    public static final String PA = "Pa";
    private String prevPressed;
    String location;
    SharedPreferences sharedpreferences;

    ListView list;
    ArrayList<String> mainCategoryArrayList = new ArrayList<String>();
    ArrayList<String> mainCategoryArrayImageList = new ArrayList<String>();
    String[] mainCategoryName ={};
    String[] imgNames={};
    EditText searchTxt;

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("maincategorytoimage.json");
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

    @Override
    public void onContextMenuClosed(Menu menu) {
    }

    public void searchItem(String category){
        initialise(category);
    }

    public void initialise(String cat){
        try {
            JSONObject jsonRootObject  = new JSONObject(loadJSONFromAsset());

            Iterator iterator = jsonRootObject.keys();
            mainCategoryArrayList.clear();
            mainCategoryArrayImageList.clear();
            if(cat.equals("noConstraint")) {
                while (iterator.hasNext()) {
                    String key = (String) iterator.next();
                    mainCategoryArrayList.add(key);

                    try {
                        Object value = jsonRootObject.get(key);
                        mainCategoryArrayImageList.add((String) value);

                    } catch (JSONException e) {
                        // Something went wrong!
                    }
                }
            }
            else{
                while (iterator.hasNext()) {
                    String key = (String) iterator.next();
                    if(key.contains(cat)){
                        mainCategoryArrayList.add(key);

                        try {
                            Object value = jsonRootObject.get(key);
                            mainCategoryArrayImageList.add((String) value);

                        } catch (JSONException e) {
                            // Something went wrong!
                        }

                    }

                }
            }

            Collections.sort(mainCategoryArrayList);
            mainCategoryName = mainCategoryArrayList.toArray(new String[mainCategoryArrayList.size()]);

            Collections.sort(mainCategoryArrayImageList);
            imgNames = mainCategoryArrayImageList.toArray(new String[mainCategoryArrayImageList.size()]);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        CustomMainListAdapter adapter=new CustomMainListAdapter(this, mainCategoryName, imgNames);
        list=(ListView)findViewById(R.id.mainCategoryList);

        list.setAdapter(adapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String Selecteditem= mainCategoryName[+position];
                //Toast.makeText(getApplicationContext(), Selecteditem, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainCategory.this, CategorySubcategory.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("MainCategory",Selecteditem);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(MC, Selecteditem);
                editor.commit();
                startActivity(i);
            }
        });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        location = sharedpreferences.getString(MylocName,"");
        prevPressed = sharedpreferences.getString(PrevPress,"");
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        //actionBar.setSubtitle(" "+location);
        actionBar.setDisplayOptions(actionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar);
        //actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.band)));
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
        setContentView(R.layout.activity_main_category);
        initialise("noConstraint");
        searchTxt = (EditText)findViewById(R.id.searchText);
        searchTxt.addTextChangedListener(new TextWatcher() {
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
        editor.putString(PA, "MainCategory");
        editor.commit();
        onBackPressed();
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
        editor.putString(PA, "MainCategory");
        editor.commit();
        Intent i = new Intent(MainCategory.this, MainActivity.class);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
