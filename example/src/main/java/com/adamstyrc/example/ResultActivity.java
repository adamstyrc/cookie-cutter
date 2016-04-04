package com.adamstyrc.example;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ResultActivity extends AppCompatActivity {

    private static Bitmap bitmap;

    public static void startResultActivity(Context context, Bitmap bitmap) {
        Intent intent = new Intent(context, ResultActivity.class);
        ResultActivity.bitmap = bitmap;
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

//        bitmap = (Bitmap) getIntent().getExtras().get("bitmap");
        ImageView ivCropped = (ImageView) findViewById(R.id.ivCropped);
        ivCropped.setImageBitmap(bitmap);
    }
}
