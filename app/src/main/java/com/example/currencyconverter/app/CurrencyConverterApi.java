package com.example.currencyconverter.app;

import com.example.currencyconverter.mvp.models.Currency;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CurrencyConverterApi {
    @GET("latest")
    Observable<Currency> getCurrency(@Query("base") String value);
}
