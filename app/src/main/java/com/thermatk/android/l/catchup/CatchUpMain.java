package com.thermatk.android.l.catchup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class CatchUpMain extends Activity implements CallbackListener{

    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    private CallbackListener callbackActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catch_up_main);
        callbackActivity = this;

        final Button butTest2 = (Button)findViewById(R.id.button2);

        butTest2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                MyNesClient myNes = new MyNesClient(getApplicationContext(), callbackActivity);
                myNes.getCurrentCourseList();
            }
        });

        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));
        // Set the list's click listener
        //mDrawerList.setOnItemClickListener(new DrawerItemClickListener());


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.catch_up_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivityForResult(new Intent(this, SettingsActivity.class), 0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void successCallback(String cbMessage) {
       // Log.i("CatchUp", "MYNES HTML FROM REQUEST" + cbMessage);
        final TextView tvInfo = (TextView)findViewById(R.id.textView1);
        tvInfo.setText(cbMessage);
    }

    @Override
    public void failCallback(String cbMessage) {

    }
}
