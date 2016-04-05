package com.adamstyrc.biscuit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

/**
 * Created by adamstyrc on 05/04/16.
 */
public class CiachoActivity extends Activity {

    private static Bitmap bitmap;

    private BiscuitImageView ivCrop;

    public static void startCiachoActivity(Context context, Bitmap bitmap) {
        Intent intent = new Intent(context, CiachoActivity.class);
        CiachoActivity.bitmap = bitmap;
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ciacho);
        ivCrop = (BiscuitImageView) findViewById(R.id.ivCrop);
        ivCrop.setImageBitmap(bitmap);
    }
}
