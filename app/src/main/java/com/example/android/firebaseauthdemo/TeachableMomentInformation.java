//package com.example.android.firebaseauthdemo;
//
//import com.google.firebase.auth.FirebaseUser;
//
//import java.util.Date;
//
///**
// * Created by duc on 17.02.18.
// */
//
//public class TeachableMomentInformation {
//
//    public String id;
//    public String title;
//    public String teachableMoment;
//    public String place;
//    public String date;
//    public String userID;
//    public String creationDate;
//
//
//
//    public TeachableMomentInformation() {
//
//    }
//
//    public TeachableMomentInformation(String id, String title, String teachableMoment, String place, String date, String user, String creationDate) {
//        this.id = id;
//        this.title = title;
//        this.teachableMoment = teachableMoment;
//        this.place = place;
//        this.date = date;
//        this.userID = user;
//        this.creationDate = creationDate;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getTeachableMoment() {
//        return teachableMoment;
//    }
//
//    public void setTeachableMoment(String teachableMoment) {
//        this.teachableMoment = teachableMoment;
//    }
//
//    public String getPlace() {
//        return place;
//    }
//
//    public void setPlace(String place) {
//        this.place = place;
//    }
//
//    public String getDate() {
//        return date;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }
//
//    public String getUserID() {
//        return userID;
//    }
//
//    public void setUserID(String userID) {
//        this.userID = userID;
//    }
//
//    public String getCreationDate() {
//        return creationDate;
//    }
//
//    public void setCreationDate(String creationDate) {
//        this.creationDate = creationDate;
//    }
//}





package com.example.android.firebaseauthdemo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.auth.FirebaseUser;

import java.util.Date;

/**
 * Created by duc on 17.02.18.
 */

public class TeachableMomentInformation implements Parcelable {

    public String id;
    public String title;
    public String teachableMoment;
    public String place;
    public String date;
    public String userID;
    public String creationDate;



    public TeachableMomentInformation() {

    }

    public TeachableMomentInformation(String id, String title, String teachableMoment, String place, String date, String user, String creationDate) {
        this.id = id;
        this.title = title;
        this.teachableMoment = teachableMoment;
        this.place = place;
        this.date = date;
        this.userID = user;
        this.creationDate = creationDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTeachableMoment() {
        return teachableMoment;
    }

    public void setTeachableMoment(String teachableMoment) {
        this.teachableMoment = teachableMoment;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    protected TeachableMomentInformation(Parcel in) {
        id = in.readString();
        title = in.readString();
        teachableMoment = in.readString();
        place = in.readString();
        date = in.readString();
        userID = in.readString();
        creationDate = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(teachableMoment);
        dest.writeString(place);
        dest.writeString(date);
        dest.writeString(userID);
        dest.writeString(creationDate);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<TeachableMomentInformation> CREATOR = new Parcelable.Creator<TeachableMomentInformation>() {
        @Override
        public TeachableMomentInformation createFromParcel(Parcel in) {
            return new TeachableMomentInformation(in);
        }

        @Override
        public TeachableMomentInformation[] newArray(int size) {
            return new TeachableMomentInformation[size];
        }
    };
}