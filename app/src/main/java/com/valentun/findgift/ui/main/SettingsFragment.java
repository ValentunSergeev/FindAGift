package com.valentun.findgift.ui.main;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.valentun.findgift.R;
import com.valentun.findgift.models.ExchangeRates;
import com.valentun.findgift.network.ExchangeRatesClient;
import com.valentun.findgift.network.RetrofitClientFactory;
import com.valentun.findgift.persistence.CurrenciesManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.valentun.findgift.Constants.PREFERENCES.DEFAULT_PRICE_TYPE;
import static com.valentun.findgift.Constants.PREFERENCES.PRICE_TYPE_KEY;

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceChangeListener {

    private String previousValue;
    private SharedPreferences preferences;
    private View container;
    private Activity parent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);

        parent = getActivity();
        preferences = getPreferenceScreen().getSharedPreferences();

        findPreference(PRICE_TYPE_KEY).setOnPreferenceChangeListener(this);
        preferences.registerOnSharedPreferenceChangeListener(this);

        container = parent.findViewById(R.id.main_fragment_container);

        parent.setTitle(getString(R.string.title_settings));

        String initialCurrency = preferences.getString(PRICE_TYPE_KEY, DEFAULT_PRICE_TYPE);
        getPreferenceScreen().getPreference(0).setSummary(initialCurrency);

        setRetainInstance(true);
    }

    @Override
    public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences, String key) {
        if (key.equals(PRICE_TYPE_KEY)) {
            String priceType = sharedPreferences.getString(key, DEFAULT_PRICE_TYPE);
            ExchangeRatesClient client = RetrofitClientFactory.getExchangeRatesClient();
            client.getExchangeRates(priceType).enqueue(new Callback<ExchangeRates>() {
                @Override
                public void onResponse(Call<ExchangeRates> call, Response<ExchangeRates> response) {
                    CurrenciesManager.setPreferredCurrencyRates(response.body());
                    Snackbar.make(container, R.string.currency_changed, Toast.LENGTH_SHORT).setDuration(500)
                            .show();
                }

                @Override
                public void onFailure(Call<ExchangeRates> call, Throwable t) {
                    showErrorDialog();
                    sharedPreferences.edit().putString(PRICE_TYPE_KEY, previousValue).apply();
                }
            });
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        previousValue = preferences.getString(PRICE_TYPE_KEY, DEFAULT_PRICE_TYPE);
        preference.setSummary((String) o);
        return true;
    }

    private void showErrorDialog(){
        new AlertDialog.Builder(parent)
                .setCancelable(false)
                .setTitle(R.string.internet_error_title)
                .setMessage(R.string.internet_error_message)
                .setPositiveButton("Ok", null)
                .show();
    }
}
