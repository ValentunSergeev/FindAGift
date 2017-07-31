package com.valentun.findgift;

import android.Manifest;
import android.app.ProgressDialog;
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
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewGiftActivity extends AppCompatActivity {
    private static final int GALLERY_REQUEST = 1339;
    private static final int PERMISSIONS_REQUEST_CODE = 1;
    private static final String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};

    @BindView(R.id.new_image)
    ImageView newImage;
    @BindView(R.id.new_name)
    TextView newName;
    @BindView(R.id.new_price)
    TextView newPrice;
    @BindView(R.id.new_container)
    View container;

    private Bitmap image;
    private ProgressDialog progressDialog;
    private String name, price, imageURL;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_gift);

        ButterKnife.bind(this);

        database = FirebaseDatabase.getInstance().getReference();

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
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

        name = newName.getText().toString();
        price = newPrice.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(price)) {
            Snackbar.make(container, getString(R.string.empty_fields_message), Snackbar.LENGTH_SHORT).show();
            return;
        }

        showProgress(getString(R.string.create_gift_image_message));

        if (imageURL == null) {
            uploadImage();
        } else {
            uploadGift();
        }

        //TODO loadTask
    }

    private void uploadImage() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference images = storage.getReference()
                .child(Constants.Firebase.IMAGE_STORAGE_KEY);
        StorageReference imageRef = images.child(name + Constants.Firebase.IMAGE_FORMAT);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] data = stream.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            Uri uri = taskSnapshot.getDownloadUrl();
            showProgress(getString(R.string.create_gift_message));
            imageURL = uri.toString();
            uploadGift();
        }).addOnFailureListener(e -> {
            progressDialog.dismiss();
            Snackbar.make(container, R.string.image_upload_error, Snackbar.LENGTH_SHORT).show();
        });
    }

    private void uploadGift() {
        Gift gift = new Gift(name, price, imageURL);
        String id = Gift.generateId(gift);

        database.child(Constants.Firebase.TABLE_NAME)
                .child(id).setValue(gift).addOnSuccessListener(aVoid -> {
            progressDialog.dismiss();
            finish();
        })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Snackbar.make(container, R.string.gift_create_error, Snackbar.LENGTH_SHORT).show();
                });
    }

    private void showProgress(String string) {
        if (progressDialog.isShowing()) progressDialog.dismiss();
        progressDialog.setTitle(string);
        progressDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
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
            Bitmap original = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            return Utils.getImage(original, this, Constants.IMAGE_SIZE);
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
