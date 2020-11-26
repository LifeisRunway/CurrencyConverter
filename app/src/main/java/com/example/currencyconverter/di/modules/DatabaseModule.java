package com.example.currencyconverter.di.modules;

import android.content.Context;
import androidx.room.Room;
import com.example.currencyconverter.di.database.CurrencyDao;
import com.example.currencyconverter.di.database.CurrencyDatabase;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Provides
    @Singleton
    public CurrencyDatabase provideOfflineDB (Context context) {
        return Room.databaseBuilder(context, CurrencyDatabase.class, CurrencyDatabase.DATABASE_NAME).allowMainThreadQueries().build();
    }

    @Provides
    @Singleton
    public CurrencyDao provideArticleDAO (CurrencyDatabase offlineDB) {
        return offlineDB.currencyDao();
    }

}
