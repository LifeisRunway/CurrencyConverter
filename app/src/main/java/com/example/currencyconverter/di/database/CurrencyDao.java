package com.example.currencyconverter.di.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import com.example.currencyconverter.mvp.models.Currency;
import io.reactivex.Single;

@Dao
public interface CurrencyDao {

    @Query("SELECT * FROM currency WHERE base = :base")
    Single<Currency> getCurrency (String base);

    @Transaction
    default void insertOrUpdateCurrency (Currency currency) {
        Long temp = insertCurrency(currency);
        if (temp == -1L) updateCurrency(currency);
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insertCurrency (Currency currency);

    @Update
    void updateCurrency (Currency currency);

}
