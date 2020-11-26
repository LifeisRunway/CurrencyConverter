package com.example.currencyconverter.mvp;

import com.example.currencyconverter.app.CurrencyConverterApi;
import com.example.currencyconverter.mvp.models.Currency;
import io.reactivex.Observable;

public class CurrencyConverterService {

    private CurrencyConverterApi myCurrencyConverterApi;

    public CurrencyConverterService (CurrencyConverterApi  myApi) {this.myCurrencyConverterApi = myApi;}

    public Observable<Currency> getCurrency(String val) {
        return myCurrencyConverterApi.getCurrency(val);
    }

}
