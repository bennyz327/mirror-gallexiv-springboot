package com.team.gallexiv.data.dto;

import lombok.Data;

@Data
public class PreRegisterUserinfo {

    private String userName;
    private String pWord;
    private String userEmail;

    public PreRegisterUserinfo() {
    }

    public PreRegisterUserinfo(String userName, String pWord, String userEmail) {
        this.userName = userName;
        this.pWord = pWord;
        this.userEmail = userEmail;
    }
}
