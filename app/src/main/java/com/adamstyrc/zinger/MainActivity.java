package com.adamstyrc.zinger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

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
        ImageView ivCropped = (ImageView) findViewById(R.id.ivCropped);
        ivCropped.setImageBitmap(ivZinger.getCroppedBitmap());

        return true;
    }
}
