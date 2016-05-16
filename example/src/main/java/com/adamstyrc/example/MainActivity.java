package com.adamstyrc.example;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.adamstyrc.cookiecutter.CookieCutterImageView;
import com.adamstyrc.cookiecutter.CookieCutterShape;
import com.adamstyrc.cookiecutter.ImageUtils;
import com.adamstyrc.cookiecutter.Logger;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    CookieCutterImageView ivCrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivCrop = (CookieCutterImageView) findViewById(R.id.ivCookieCutter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "Crop");
        menu.add(0, 1, 1, "Choose from gallery");
        menu.add(0, 2, 1, "Shape circle");
        menu.add(0, 3, 1, "Shape hole");
        menu.add(0, 4, 1, "Shape square");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                Bitmap bitmap = ivCrop.getCroppedBitmap();
                Bitmap circularBitmap = ImageUtils.getCircularBitmap(bitmap);
                ResultActivity.startResultActivity(this, circularBitmap);
                break;
            case 1:
                choosePhotoFromGallery();
                break;
            case 2:
                ivCrop.getParams().setShape(CookieCutterShape.CIRCLE);
                break;
            case 3:
                ivCrop.getParams().setShape(CookieCutterShape.HOLE);
                break;
            case 4:
                ivCrop.getParams().setShape(CookieCutterShape.SQUARE);
                break;

        }
        ivCrop.invalidate();

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case 1:
                try {
                    Uri imageUri = data.getData();
//                    ivCrop.setImageURI(imageUri);

                    Point screenSize = ImageUtils.getScreenSize(this);
                    Bitmap scaledBitmap = ImageUtils.decodeUriToScaledBitmap(this, imageUri, screenSize.x, screenSize.y);
                    ivCrop.setImageBitmap(scaledBitmap);

                } catch (Exception e) {
                    Logger.log(e.getMessage());
                }
                break;

        }
    }

    private void choosePhotoFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }
}
