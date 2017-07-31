package com.valentun.findgift;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewGiftActivity extends AppCompatActivity {
    @BindView(R.id.new_image)
    ImageView newImage;
    @BindView(R.id.new_name)
    TextView newName;
    @BindView(R.id.new_price)
    TextView newPrice;
    @BindView(R.id.new_container)
    View container;

    private static final int GALLERY_REQUEST = 1339;
    private static final int PERMISSIONS_REQUEST_CODE = 1;
    private static final String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
    private Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_gift);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.new_image)
    public void setImageClicked(View v) {
        if (hasReadPermissons()) {
            makeGalleryRequest();
        } else {
            ActivityCompat.requestPermissions(this, permissions, PERMISSIONS_REQUEST_CODE);
        }
    }

    @OnClick(R.id.create_button)
    public void createButtonClicked(View v) {
        if (image == null) {
            Snackbar.make(container, R.string.no_image_message, Snackbar.LENGTH_SHORT).show();
            return;
        }

        //TODO loadTask
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeGalleryRequest();
            } else {
                Snackbar.make(container, R.string.no_permission_message, Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            image = getImage(uri);
            newImage.setImageBitmap(image);
        }
    }

    private void makeGalleryRequest() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/**");
        startActivityForResult(intent, GALLERY_REQUEST);
    }

    private Bitmap getImage(Uri uri) {
        try {
            return MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean hasReadPermissons() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }
}
