package com.example.android.firebaseauthdemo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by duc on 17.02.18.
 */

public class _TeachableMomentInformation implements Parcelable {

    public String id;
    public String title;
    public String teachableMoment;
    public String date;
    public String creationDate;
    public String userID;
    public String userNickname;

    public float averageRating;
    public int countRatings;
    public int counter;
    public boolean confirmed;

    public _TeachableMomentInformation() {

    }

    public _TeachableMomentInformation(String id, String title, String teachableMoment, String date, String creationDate, String userID, String userNickname) {
        this.id = id;
        this.title = title;
        this.teachableMoment = teachableMoment;
        this.date = date;
        this.creationDate = creationDate;
        this.userID = userID;
        this.userNickname = userNickname;
    }

    protected _TeachableMomentInformation(Parcel in) {
        id = in.readString();
        title = in.readString();
        teachableMoment = in.readString();
        date = in.readString();
        creationDate = in.readString();
        userID = in.readString();
        averageRating = in.readFloat();
        countRatings = in.readInt();
        counter = in.readInt();
        confirmed = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(teachableMoment);
        dest.writeString(date);
        dest.writeString(creationDate);
        dest.writeString(userID);
        dest.writeFloat(averageRating);
        dest.writeInt(countRatings);
        dest.writeInt(counter);
        dest.writeByte((byte) (confirmed ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<_TeachableMomentInformation> CREATOR = new Creator<_TeachableMomentInformation>() {
        @Override
        public _TeachableMomentInformation createFromParcel(Parcel in) {
            return new _TeachableMomentInformation(in);
        }

        @Override
        public _TeachableMomentInformation[] newArray(int size) {
            return new _TeachableMomentInformation[size];
        }
    };

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

    public int getCountRatings() {
        return countRatings;
    }

    public void setCountRatings(int countRatings) {
        this.countRatings = countRatings;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
}