package com.shfound.showproduct.controller.result;

import com.shfound.showproduct.model.ProductInfoModel;
import com.shfound.showproduct.model.VoteModel;

import java.util.List;

public class VoteResult {

    List<VoteModel> voteModels;
    private int amount;

    public List<VoteModel> getVoteModels() {
        return voteModels;
    }

    public void setVoteModels(List<VoteModel> voteModels) {
        this.voteModels = voteModels;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
