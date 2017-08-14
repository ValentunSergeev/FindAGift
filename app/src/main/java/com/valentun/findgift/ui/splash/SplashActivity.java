package com.valentun.findgift.ui.splash;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.valentun.findgift.Constants;
import com.valentun.findgift.R;
import com.valentun.findgift.models.ExchangeRates;
import com.valentun.findgift.network.ExchangeRatesClient;
import com.valentun.findgift.network.RetrofitClientFactory;
import com.valentun.findgift.persistence.CurrenciesManager;
import com.valentun.findgift.ui.main.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String priceType = CurrenciesManager.getPreferredPriceType();
        ExchangeRatesClient client = RetrofitClientFactory.getExchangeRatesClient();



        client.getExchangeRates(priceType).enqueue(new Callback<ExchangeRates>() {
            @Override
            public void onResponse(Call<ExchangeRates> call, Response<ExchangeRates> response) {
                CurrenciesManager.setPreferredCurrencyRates(response.body());
                getEURRates();
            }

            @Override
            public void onFailure(Call<ExchangeRates> call, Throwable t) {
                showErrorDialog();
            }
        });
    }

    private void getEURRates() {
        ExchangeRatesClient client = RetrofitClientFactory.getExchangeRatesClient();
        client.getExchangeRates(Constants.Convert.EUR).enqueue(new Callback<ExchangeRates>() {
            @Override
            public void onResponse(Call<ExchangeRates> call, Response<ExchangeRates> response) {
                CurrenciesManager.setEURRates(response.body());
                startMainActivity();
            }

            @Override
            public void onFailure(Call<ExchangeRates> call, Throwable t) {
                showErrorDialog();
            }
        });
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showErrorDialog(){
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle(R.string.internet_error_title)
                .setMessage(R.string.internet_error_message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).show();
    }
}
