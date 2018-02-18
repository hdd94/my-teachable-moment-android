package com.example.android.firebaseauthdemo;

import com.google.firebase.auth.FirebaseUser;

import java.util.Date;

/**
 * Created by duc on 17.02.18.
 */

public class TeachableMomentInformation {

    public String title;
    public String teachableMoment;
    public String place;
    public String date;
    public FirebaseUser user;
    public Date creationDate;



    public TeachableMomentInformation() {

    }

    public TeachableMomentInformation(String title, String teachableMoment, String place, String date, FirebaseUser user, Date creationDate) {
        this.title = title;
        this.teachableMoment = teachableMoment;
        this.place = place;
        this.date = date;
        this.user = user;
        this.creationDate = creationDate;
    }
}
