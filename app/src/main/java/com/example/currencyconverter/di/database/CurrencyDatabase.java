package com.example.currencyconverter.di.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.currencyconverter.mvp.models.Currency;
import com.example.currencyconverter.mvp.models.Rates;

@Database(entities = {Currency.class, Rates.class}, version = 1, exportSchema = false)
public abstract class CurrencyDatabase extends RoomDatabase {
    public abstract CurrencyDao currencyDao();
    public static final String DATABASE_NAME = "currency_converter_database.db";
}
