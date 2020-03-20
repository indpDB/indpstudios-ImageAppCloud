package com.indptechnologies.happybirthdayphotoframes;

/**
 * Created by SINarayanReddy on 01-11-2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DemoData {

    @SerializedName("data")
    @Expose
    private Demo[] data ;
    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("img_path")
    @Expose
    private String imgPath;

    public Demo[] getData() {
        return data;
    }

    public void setData(Demo[] data) {
        this.data = data;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

}