package com.example.currencyconverter.di;

import com.example.currencyconverter.di.modules.ContextModule;
import com.example.currencyconverter.di.modules.CurrencyConverterModule;
import com.example.currencyconverter.di.modules.DatabaseModule;
import com.example.currencyconverter.di.modules.SharedPreferenceModule;
import com.example.currencyconverter.mvp.models.repository.Repository;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {ContextModule.class, CurrencyConverterModule.class, DatabaseModule.class, SharedPreferenceModule.class})
public interface AppComponent {

    void inject(Repository repository);
}
