package com.example.currencyconverter.mvp.views;

import com.example.currencyconverter.mvp.models.Currency;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.StateStrategyType;


@StateStrategyType(AddToEndSingleStrategy.class)
public interface MainView extends MvpView {

    void setValue (Currency currency);

    void showError(String message);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showSnackbar(String date, boolean isConnected);

}
