package com.adamstyrc.zinger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.adamstyrc.zingerlib.ZingerImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Crop");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        ZingerImageView ivZinger = (ZingerImageView) findViewById(R.id.ivZinger);
        ResultActivity.startResultActivity(this, ivZinger.getCroppedBitmap());

        return true;
    }
}
