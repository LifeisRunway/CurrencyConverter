package com.example.currencyconverter.app;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.ResponseBody;

public class CurrencyConverterError extends Throwable{

    public CurrencyConverterError (ResponseBody responseBody) {super(getMessage(responseBody));}

    public static String getMessage (ResponseBody responseBody) {
        try {
            return new JSONObject(responseBody.string()).optString("message");
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return "Unknown exception";
    }

}
