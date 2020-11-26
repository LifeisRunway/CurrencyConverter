package com.example.currencyconverter.di.modules;

import com.example.currencyconverter.app.CurrencyConverterApi;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = {RetrofitModule.class})
public class ApiModules {

    @Provides
    @Singleton
    public CurrencyConverterApi provideAuthApi(Retrofit retrofit) {return retrofit.create(CurrencyConverterApi.class);}

}
