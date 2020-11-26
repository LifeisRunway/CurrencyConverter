package com.example.currencyconverter.app;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import com.example.currencyconverter.di.AppComponent;
import com.example.currencyconverter.di.DaggerAppComponent;
import com.example.currencyconverter.di.modules.ContextModule;

public class CurrencyConverterApp extends Application {

    public static CurrencyConverterApp INSTANCE;
    private static AppComponent mAppComp;

    @Override
    public void onCreate () {
        super.onCreate();
        INSTANCE = this;
    }

    public static AppComponent getAppComponent() {
        if(mAppComp == null) {
            mAppComp = DaggerAppComponent.builder()
                    .contextModule(new ContextModule(INSTANCE))
                    .build();
        }
        return mAppComp;
    }

    @VisibleForTesting
    public static void setAppComponent(@NonNull AppComponent appComponent) {
        mAppComp = appComponent;
    }



}
