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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
