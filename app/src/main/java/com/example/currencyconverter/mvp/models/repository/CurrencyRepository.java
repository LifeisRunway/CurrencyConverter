package com.example.currencyconverter.mvp.models.repository;

import com.example.currencyconverter.mvp.models.Currency;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface CurrencyRepository {
    Observable<Currency> loadCurrencyApi(String baseCurrency);
    Single<Currency> getCurrencyDB(String baseCurrency);
    void saveCurrencyDB(Currency currency);

    void saveCurrencySP(String currency, String value);
    String getCurrencySP(String currency);

}
