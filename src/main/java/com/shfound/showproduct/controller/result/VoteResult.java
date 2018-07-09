package com.shfound.showproduct.controller.result;

import com.shfound.showproduct.model.ProductInfoModel;

import java.util.List;

public class VoteResult {
    private String prodName;
    private List<String> userMobiles;
    private List<Double> userLimits;
    private double amount;

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public List<String> getUserMobiles() {
        return userMobiles;
    }

    public void setUserMobiles(List<String> userMobiles) {
        this.userMobiles = userMobiles;
    }

    public List<Double> getUserLimits() {
        return userLimits;
    }

    public void setUserLimits(List<Double> userLimits) {
        this.userLimits = userLimits;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
