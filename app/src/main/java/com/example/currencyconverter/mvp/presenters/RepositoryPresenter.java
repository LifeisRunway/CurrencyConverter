package com.example.currencyconverter.mvp.presenters;

import com.example.currencyconverter.mvp.models.Currency;
import com.example.currencyconverter.mvp.models.repository.Repository;
import com.example.currencyconverter.mvp.views.MainView;
import javax.inject.Singleton;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;

@Singleton
@InjectViewState
public class RepositoryPresenter extends BasePresenter<MainView> {

    private Disposable disposable;
    private final Repository mRepository;

    public RepositoryPresenter() {
        mRepository = new Repository();
    }

    public void loadData (String value, boolean isConnected) {

        if(isConnected) {
            Observable<Currency> observable;
            observable = mRepository.loadCurrencyApi(value);
            disposable = observable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((Currency c) -> {
                        mRepository.saveCurrencyDB(c);
                        getViewState().setValue(c);
                        getViewState().showSnackbar(c.getDate(), isConnected);
                    }, error -> getViewState().showError(error.toString()));
        } else {

            Single<Currency> observable = mRepository.getCurrencyDB(value);
            disposable = observable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((Currency c) -> {
                        getViewState().setValue(c);
                        getViewState().showSnackbar(c.getDate(), isConnected);
                    }, error -> {
                        if(error instanceof androidx.room.EmptyResultSetException) {
                            getViewState().showSnackbar("", isConnected);
                            getViewState().setValue(null);
                        } else {getViewState().showError(error.toString());}
                    });
        }
        if(disposable != null) {unsubscribeOnDestroy(disposable);}
    }

    public void saveCurrencyState(String currency, String value) {
        mRepository.saveCurrencySP(currency, value);
    }

    public String getCurrencyState(String currency) {
        return mRepository.getCurrencySP(currency);
    }

}
