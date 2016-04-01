package com.adamstyrc.zinger;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.adamstyrc.zingerlib.ZingerImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ZingerImageView zingerImageView = (ZingerImageView) findViewById(R.id.ivZinger);

//        Uri uri = Uri.parse("content://com.android.providers.media.documents/document/image%3A2832");
    }
}
