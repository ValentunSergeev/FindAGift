package com.valentun.findgift.ui.newgift;

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
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.valentun.findgift.Constants;
import com.valentun.findgift.Constants.GIFT_PARAMS;
import com.valentun.findgift.R;
import com.valentun.findgift.models.ExchangeRates;
import com.valentun.findgift.models.Gift;
import com.valentun.findgift.network.ExchangeRatesClient;
import com.valentun.findgift.network.RetrofitClientFactory;
import com.valentun.findgift.persistence.CurrenciesManager;
import com.valentun.findgift.ui.abstracts.ApiActivity;
import com.valentun.findgift.utils.BitmapUtils;
import com.valentun.findgift.utils.SearchUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import static android.widget.ImageView.ScaleType.CENTER_CROP;
import static com.valentun.findgift.Constants.PERMISSIONS;
import static com.valentun.findgift.utils.TextUtils.isEmpty;
import static com.valentun.findgift.utils.WidgetUtils.getIntFromEditText;
import static com.valentun.findgift.utils.WidgetUtils.getTextFromEditText;

public class NewGiftActivity extends ApiActivity {
    private static final int GALLERY_REQUEST = 1339;
    private static final int PERMISSIONS_REQUEST_CODE = 1;

    @BindView(R.id.new_image)
    ImageView newImage;
    @BindView(R.id.new_name)
    EditText newName;
    @BindView(R.id.new_description)
    EditText newDescription;
    @BindView(R.id.new_price)
    EditText newPrice;
    @BindView(R.id.new_min_age)
    EditText newMinAge;
    @BindView(R.id.new_max_age)
    EditText newMaxAge;

    @BindView(R.id.money_type)
    Spinner newPriceType;
    @BindView(R.id.new_event)
    Spinner newEventType;

    @BindView(R.id.new_gender)
    RadioGroup newGender;

    private Bitmap image;
    private int[] eventTypes;
    private String[] priceTypes;

    private String imageURL;
    private Gift gift;

    private ExchangeRatesClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_gift);

        ButterKnife.bind(this);

        client = RetrofitClientFactory.getExchangeRatesClient();

        priceTypes = getResources().getStringArray(R.array.money_types_keys);
        eventTypes = getResources().getIntArray(R.array.event_type_keys);

        newMinAge.setOnFocusChangeListener(new AgeFocusListener(GIFT_PARAMS.MIN_AGE_VALUE));
        newMaxAge.setOnFocusChangeListener(new AgeFocusListener(GIFT_PARAMS.MAX_AGE_VALUE));
    }

    @OnClick(R.id.new_image)
    public void setImageClicked(View v) {
        if (hasReadPermissions()) {
            makeGalleryRequest();
        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSIONS_REQUEST_CODE);
        }
    }

    @OnClick(R.id.new_gift_submit)
    public void createButtonClicked(View v) {
        if (image == null) {
            Snackbar.make(container, R.string.no_image_message, Snackbar.LENGTH_SHORT).show();
            return;
        }


        createRequestBodyObject();

        if (isEmpty(gift.getName(), gift.getPrice(), gift.getDescription())) {
            Snackbar.make(container, getString(R.string.empty_fields_message), Snackbar.LENGTH_SHORT).show();
            return;
        }

        showProgress(getString(R.string.create_gift_image_message));

        if (imageURL == null) {
            uploadImage();
        } else {
            if (CurrenciesManager.isEURRatePresent()) uploadGift();
            else getCurrency();
        }
    }

    private void createRequestBodyObject() {
        String name = getTextFromEditText(newName);
        String price = getTextFromEditText(newPrice);
        String description = getTextFromEditText(newDescription);
        String priceType = priceTypes[newPriceType.getSelectedItemPosition()];
        int eventType = eventTypes[newEventType.getSelectedItemPosition()];
        String gender = SearchUtils.getGenderFromButtonId(newGender.getCheckedRadioButtonId());
        int minAge = getIntFromEditText(newMinAge, GIFT_PARAMS.MIN_AGE_VALUE);
        int maxAge = getIntFromEditText(newMaxAge, GIFT_PARAMS.MAX_AGE_VALUE);

        gift = new Gift().setName(name)
                .setDescription(description)
                .setImageUrl(imageURL)
                .setPrice(price)
                .setPriceType(priceType)
                .setEventType(eventType)
                .setGender(gender)
                .setMaxAge(maxAge)
                .setMinAge(minAge);
    }

    private void uploadImage() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference images = storage.getReference()
                .child(Constants.Firebase.IMAGE_STORAGE_KEY);
        StorageReference imageRef = images.child(Gift.generateImageName());

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] data = stream.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri uri = taskSnapshot.getDownloadUrl();
                imageURL = uri.toString();
                gift.setImageUrl(imageURL);
                if (CurrenciesManager.isEURRatePresent()) uploadGift();
                else getCurrency();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                showSnackbarMessage(R.string.image_upload_error);
            }
        });

    }

    private void uploadGift() {
        showProgress(getString(R.string.create_gift_message));
        gift.setPrice(CurrenciesManager.covertPriceToEUR(gift));
        apiClient.createGift(gift).enqueue(new ApiCallback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent();
                    intent.setAction("ThisFixDataLost");
                    setResult(RESULT_OK, intent);

                    finish();
                } else {
                    progressDialog.dismiss();
                    showSnackbarMessage(getString(R.string.create_gift_error, response.errorBody()));
                }
            }
        });
    }

    private void getCurrency() {
        showProgress(getString(R.string.new_gift_currency));
        client.getExchangeRates(Constants.Convert.EUR).enqueue(new ApiCallback<ExchangeRates>() {
            @Override
            public void onResponse(Call<ExchangeRates> call, Response<ExchangeRates> response) {
                if (response.isSuccessful()) {
                    CurrenciesManager.setEURRates(response.body());
                    uploadGift();
                } else {
                    progressDialog.dismiss();
                    showDefaultErrorMessage();
                }
            }
        });
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
            newImage.setScaleType(CENTER_CROP);
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
            return BitmapUtils.cropImage(original);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean hasReadPermissions() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    private class AgeFocusListener implements OnFocusChangeListener {
        private int defaultValue;

        private AgeFocusListener(int value) {
            defaultValue = value;
        }

        @Override
        public void onFocusChange(View view, boolean isFocused) {
            EditText ageText = (EditText) view;
            String text = ageText.getText().toString();
            if (!isFocused && TextUtils.isEmpty(text))
                ageText.setText(String.valueOf(defaultValue));
        }
    }
}
