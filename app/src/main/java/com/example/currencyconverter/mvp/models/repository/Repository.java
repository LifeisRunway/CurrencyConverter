package com.example.currencyconverter.mvp.models.repository;

import android.content.SharedPreferences;

import com.example.currencyconverter.app.CurrencyConverterApp;
import com.example.currencyconverter.di.database.CurrencyDao;
import com.example.currencyconverter.di.modules.SharedPreferenceModule;
import com.example.currencyconverter.mvp.CurrencyConverterService;
import com.example.currencyconverter.mvp.models.Currency;

import javax.inject.Inject;
import javax.inject.Singleton;
import io.reactivex.Observable;
import io.reactivex.Single;

@Singleton
public class Repository implements CurrencyRepository{

    @Inject
    CurrencyConverterService myService;

    @Inject
    CurrencyDao myCurrencyDao;

    @Inject
    SharedPreferences mySP;

    @Inject
    SharedPreferences.Editor mSPEditor;

    public Repository () {
        CurrencyConverterApp.getAppComponent().inject(this);
    }

    @Override
    public Observable<Currency> loadCurrencyApi(String baseCurrency) {
        return myService.getCurrency(baseCurrency);
    }

    @Override
    public Single<Currency> getCurrencyDB(String baseCurrency) {
        return myCurrencyDao.getCurrency(baseCurrency);
    }

    @Override
    public void saveCurrencyDB(Currency currency) {
        myCurrencyDao.insertOrUpdateCurrency(currency);
    }

    @Override
    public void saveCurrencySP(String currency, String value) {
        mSPEditor.putString(currency, value).apply();
    }

    @Override
    public String getCurrencySP(String currency) {
        return mySP.getString(currency, "");
    }
}
