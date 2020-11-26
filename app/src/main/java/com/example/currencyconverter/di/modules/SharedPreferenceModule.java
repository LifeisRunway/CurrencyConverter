package com.example.currencyconverter.di.modules;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SharedPreferenceModule {

    @Provides
    @Singleton
    SharedPreferences provideSharedPreference (Context context) {
        SharedPreferences sp = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        boolean hasVisited = sp.getBoolean("hasVisited", false);
        if (!hasVisited) {
            sp.edit().putBoolean("hasVisited", true).putString("currencyFirst", "").putString("currencySecond", "").apply(); }
        return sp;
    }

    @Provides
    @Singleton
    SharedPreferences.Editor provideSharedPreferenceEditor (SharedPreferences sharedPreferences) {
        return sharedPreferences.edit();
    }

}
