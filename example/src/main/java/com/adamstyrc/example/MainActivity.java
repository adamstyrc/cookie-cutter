package com.adamstyrc.example;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.adamstyrc.biscuit.BiscuitImageView;
import com.adamstyrc.biscuit.ImageUtils;
import com.adamstyrc.biscuit.Logger;

public class MainActivity extends AppCompatActivity {

    BiscuitImageView ivCrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivCrop = (BiscuitImageView) findViewById(R.id.ivBiscuit);

        ivCrop.setImageBitmap(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "Crop");
        menu.add(0, 1, 1, "Choose from gallery");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 0) {
            Bitmap bitmap = ivCrop.getCroppedBitmap();
            Bitmap circularBitmap = ImageUtils.getCircularBitmap(bitmap);
            ResultActivity.startResultActivity(this, circularBitmap);
        } else {
            choosePhotoFromGallery();
        }

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
                    ivCrop.setImageURI(imageUri);
                    Bitmap bitmap = ((BitmapDrawable) ivCrop.getDrawable()).getBitmap();
                    ivCrop.setImageBitmap(bitmap);
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
