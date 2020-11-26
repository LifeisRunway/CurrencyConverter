package com.example.currencyconverter.mvp.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "rates")
public class Rates {

    @PrimaryKey(autoGenerate = true)
    private int ratesId;

    @SerializedName("RUB")
    @Expose
    private float rub;

    @SerializedName("USD")
    @Expose
    private float usd;

    @SerializedName("EUR")
    @Expose
    private float eur;

    @SerializedName("GBP")
    @Expose
    private float gbp;

    @SerializedName("CHF")
    @Expose
    private float chf;

    @SerializedName("CNY")
    @Expose
    private float cny;

    public int getRatesId() {
        return ratesId;
    }

    public void setRatesId(int ratesId) {
        this.ratesId = ratesId;
    }

    public float getRub() {
        return rub;
    }

    public void setRub(float rub) {
        this.rub = rub;
    }

    public float getUsd() {
        return usd;
    }

    public void setUsd(float usd) {
        this.usd = usd;
    }

    public float getEur() {
        return eur;
    }

    public void setEur(float eur) {
        this.eur = eur;
    }

    public float getGbp() {
        return gbp;
    }

    public void setGbp(float gbp) {
        this.gbp = gbp;
    }

    public float getChf() {
        return chf;
    }

    public void setChf(float chf) {
        this.chf = chf;
    }

    public float getCny() {
        return cny;
    }

    public void setCny(float cny) {
        this.cny = cny;
    }

}
