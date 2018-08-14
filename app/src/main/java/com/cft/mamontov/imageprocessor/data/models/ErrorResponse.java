package com.cft.mamontov.imageprocessor.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ErrorResponse implements Parcelable {

    @SerializedName("code")
    private int mCode;

    @SerializedName("status")
    private int mStatus;

    @SerializedName("message")
    private String mMessage;

    @SerializedName("name")
    private String mName;

    public void setCode(int code) {
        mCode = code;
    }

    public void setStatus(int status) {
        mStatus = status;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public void setName(String name) {
        mName = name;
    }

    public ErrorResponse() {
    }

    public int getCode() {
        return mCode;
    }

    public int getStatus() {
        return mStatus;
    }

    public String getMessage() {
        return mMessage;
    }

    public String getName() {
        return mName;
    }

    public ErrorResponse(String message) {
        mMessage = message;
    }

    public ErrorResponse(int code, int status, String message, String name) {
        this.mCode = code;
        this.mStatus = status;
        this.mMessage = message;
        this.mName = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mCode);
        dest.writeInt(this.mStatus);
        dest.writeString(this.mMessage);
        dest.writeString(this.mName);
    }

    protected ErrorResponse(Parcel in) {
        this.mCode = in.readInt();
        this.mStatus = in.readInt();
        this.mMessage = in.readString();
        this.mName = in.readString();
    }

    public static final Creator<ErrorResponse> CREATOR = new Creator<ErrorResponse>() {
        @Override
        public ErrorResponse createFromParcel(Parcel source) {
            return new ErrorResponse(source);
        }

        @Override
        public ErrorResponse[] newArray(int size) {
            return new ErrorResponse[size];
        }
    };

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "mCode=" + mCode +
                ", mStatus=" + mStatus +
                ", mMessage='" + mMessage + '\'' +
                ", mName='" + mName + '\'' +
                '}';
    }
}
