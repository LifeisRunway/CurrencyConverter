package com.example.currencyconverter.ui;

import androidx.appcompat.app.AlertDialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.currencyconverter.R;
import com.example.currencyconverter.mvp.models.Currency;
import com.example.currencyconverter.mvp.presenters.RepositoryPresenter;
import com.example.currencyconverter.mvp.views.MainView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import butterknife.Unbinder;
import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class MainActivity extends MvpAppCompatActivity implements MainView, AdapterView.OnItemSelectedListener {

    @InjectPresenter
    RepositoryPresenter repositoryPresenter;

    @BindView(R.id.editTextCurrency)
    EditText editTextCurrency;
    @BindView(R.id.spinnerCurrency)
    Spinner spinnerCurrency;
    @BindView(R.id.textViewResult)
    TextView textViewResult;
    @BindView(R.id.spinnerResult)
    Spinner spinnerResult;

    @OnTextChanged(value = R.id.editTextCurrency, callback = OnTextChanged.Callback.TEXT_CHANGED)
    public void onEditTextCurrencyAfterTextChanged(CharSequence s) {
        convertValue(s);
    }

    private Unbinder unbinder;
    private AlertDialog mErrorDialog;
    private static final String CURRENCY_FIRST = "currencyFirst";
    private static final String CURRENCY_SECOND = "currencySecond";

    @ProvidePresenter
    RepositoryPresenter providePresenter() {
        return new RepositoryPresenter();
    }

    private Currency mCurrency;
    private List<String> currencyList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        currencyList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.currencyList)));
        adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, currencyList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCurrency.setAdapter(adapter);
        spinnerCurrency.setOnItemSelectedListener(this);
        spinnerResult.setAdapter(adapter);
        spinnerResult.setOnItemSelectedListener(this);
        restoreSpinnerItemState(CURRENCY_FIRST, spinnerCurrency);
        restoreSpinnerItemState(CURRENCY_SECOND, spinnerResult);
        repositoryPresenter.loadData(spinnerCurrency.getSelectedItem().toString(), isConnected());
    }

    private boolean isConnected () {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NetworkCapabilities capabilities = (cm != null) ? cm.getNetworkCapabilities(cm.getActiveNetwork()) : null;
            return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI));
        } else {
            NetworkInfo nInfo = (cm != null) ? cm.getActiveNetworkInfo() : null;
            return nInfo != null && nInfo.isConnected();
        }
    }

    public void restoreSpinnerItemState(String currency, Spinner spinner) {
        String curRate = repositoryPresenter.getCurrencyState(currency);
        if(!curRate.equals("")) {
            int spinnerPos = adapter.getPosition(curRate);
            spinner.setSelection(spinnerPos);
        }
    }

    public void saveSpinnerItemState() {
        repositoryPresenter.saveCurrencyState(CURRENCY_FIRST, spinnerCurrency.getSelectedItem().toString());
        repositoryPresenter.saveCurrencyState(CURRENCY_SECOND, spinnerResult.getSelectedItem().toString());
    }

    @SuppressLint("SetTextI18n")
    void convertValue (CharSequence sequence) {
        String ss = String.valueOf(sequence);
        if(mCurrency != null) {
            String s = spinnerResult.getSelectedItem().toString();
            float f = 0;
            switch (s) {
                case "RUB":
                    f = mCurrency.getRates().getRub();
                    break;
                case "EUR":
                    f = mCurrency.getRates().getEur();
                    break;
                case "USD":
                    f = mCurrency.getRates().getUsd();
                    break;
                case "CHF":
                    f = mCurrency.getRates().getChf();
                    break;
                case "GBP":
                    f = mCurrency.getRates().getGbp();
                    break;
                case "CNY":
                    f = mCurrency.getRates().getCny();
                    break;
                default:
                    break;
            }
            if(!ss.equals("")) {
                float result = Float.parseFloat(ss);
                if(result != 0) {
                    float finish = f * result;
                    textViewResult.setText(String.valueOf(finish));
                }
            } else {textViewResult.setText(getResources().getString(R.string.zero_point));}
        } else {
            textViewResult.setText(R.string.no_data);
        }
    }

    @Override
    public void setValue (Currency currency) {
        mCurrency = currency;
    }

    @Override
    public void showError(String message) {
        mErrorDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage(message)
                .setOnCancelListener(dialog -> hideError())
                .show();
    }

    public void hideError() {
        if (mErrorDialog != null && mErrorDialog.isShowing()) {
            mErrorDialog.hide();
        }
    }

    @Override
    public void showSnackbar(String date, boolean isLoadedNet) {
        String s;
        if(!date.equals("")) {
            s = "Currency date - " + date + "!";
        } else {
            s = "No have data! Check internet connection";
        }

        if(isLoadedNet) {
            s = "Updated. " + s;
        } else {
            s = "Local Data. " + s;
        }
        Snackbar.make(textViewResult, s, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        saveSpinnerItemState();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.spinnerCurrency:
                repositoryPresenter.loadData(parent.getSelectedItem().toString(), isConnected());
                convertValue(editTextCurrency.getText());
                break;
            case R.id.spinnerResult:
                convertValue(editTextCurrency.getText());
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }
}