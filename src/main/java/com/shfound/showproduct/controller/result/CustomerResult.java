package com.shfound.showproduct.controller.result;

import com.shfound.showproduct.model.UserModel;

import java.util.List;

public class CustomerResult {
    private String wxId;
    private String invitationCode;
    private List<UserModel> firstLevel;
    private List<UserModel> secondLevel;

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public List<UserModel> getFirstLevel() {
        return firstLevel;
    }

    public void setFirstLevel(List<UserModel> firstLevel) {
        this.firstLevel = firstLevel;
    }

    public List<UserModel> getSecondLevel() {
        return secondLevel;
    }

    public void setSecondLevel(List<UserModel> secondLevel) {
        this.secondLevel = secondLevel;
    }
}
