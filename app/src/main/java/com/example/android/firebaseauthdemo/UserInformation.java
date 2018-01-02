package com.example.android.firebaseauthdemo;

/**
 * Created by Duc on 22.10.17.
 */

public class UserInformation {

    public String nickname;
    public String forename;
    public String surname;

    public UserInformation() {

    }

    public UserInformation(String nickname, String forename, String surname) {
        this.nickname = nickname;
        this.forename = forename;
        this.surname = surname;
    }
}
