package com.example.android.firebaseauthdemo;

/**
 * Created by Duc on 22.10.17.
 */

public class _UserInformation {

    public String nickname;
    public String forename;
    public String surname;
    public String email;
    public String creationDate;

    public _UserInformation() {

    }

    public _UserInformation(String nickname, String forename, String surname, String email, String creationDate) {
        this.nickname = nickname;
        this.forename = forename;
        this.surname = surname;
        this.email = email;
        this.creationDate = creationDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
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
