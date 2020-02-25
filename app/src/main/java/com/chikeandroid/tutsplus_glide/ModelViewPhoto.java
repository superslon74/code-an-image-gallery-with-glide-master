package com.chikeandroid.tutsplus_glide;


/**
 * Created gennadij schamanskij on 24/02/2020.
 * e-mail:superslon74@gmail.com
 */

import android.os.Parcel;
import android.os.Parcelable;

public class ModelViewPhoto implements Parcelable {

    private String mUrl;
    private String mTitle;

    public ModelViewPhoto(String url, String title) {
        mUrl = url;
        mTitle = title;
    }

    protected ModelViewPhoto(Parcel in) {
        mUrl = in.readString();
        mTitle = in.readString();
    }

    public static final Creator<ModelViewPhoto> CREATOR = new Creator<ModelViewPhoto>() {
        @Override
        public ModelViewPhoto createFromParcel(Parcel in) {
            return new ModelViewPhoto(in);
        }

        @Override
        public ModelViewPhoto[] newArray(int size) {
            return new ModelViewPhoto[size];
        }
    };

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mUrl);
        parcel.writeString(mTitle);
    }
}