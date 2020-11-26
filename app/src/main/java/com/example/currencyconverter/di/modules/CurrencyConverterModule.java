package com.example.currencyconverter.di.modules;

import com.example.currencyconverter.app.CurrencyConverterApi;
import com.example.currencyconverter.mvp.CurrencyConverterService;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module(includes = {ApiModules.class})
public class CurrencyConverterModule {

    @Provides
    @Singleton
    public CurrencyConverterService provideCurrencyConverterService (CurrencyConverterApi authApi) {return new CurrencyConverterService(authApi);}

}
