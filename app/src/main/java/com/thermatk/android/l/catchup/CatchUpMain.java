package com.thermatk.android.l.catchup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.nio.charset.Charset;


public class CatchUpMain extends Activity implements CallbackListener{
    public static CallbackListener callbackActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catch_up_main);
        callbackActivity = this;
        final Button butTest = (Button)findViewById(R.id.button1);

        final Button butTest2 = (Button)findViewById(R.id.button2);

        butTest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                MyNesClient myNes = new MyNesClient(getApplicationContext(), callbackActivity);
                myNes.login();
            }
        });
        butTest2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                MyNesClient myNes = new MyNesClient(getApplicationContext(), callbackActivity);
                myNes.get("", null);
            }
        });

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
    public void successCallback(int statusCode, Header[] headers, byte[] responseBody) {

        String resp = new String(responseBody, Charset.forName("CP1251"));
        //Log.i("CatchUp", "MYNES HTML FROM REQUEST" + resp);
        Document doc = Jsoup.parse(resp);
        final TextView tvInfo = (TextView)findViewById(R.id.textView1);

        tvInfo.setText(doc.getElementById("mynesmenu").html());
    }
}
