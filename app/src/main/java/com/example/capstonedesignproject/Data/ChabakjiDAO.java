package com.example.capstonedesignproject.Data;

import android.graphics.Bitmap;

import java.io.Serializable;

public class ChabakjiDAO implements Serializable {
    private String place_name; // 장소 명
    private String address; // 주소
    private String utility; // 편의시설
    private String notify; // 주의사항
    private String introduce; // 한 줄 소개
    private String filePath; // 사진 파일 경로
    private Bitmap imageFile; // 사진 파일
    private int jjim;  // 찜
    private double latitude; // 위도
    private double longitude; // 경도

    public ChabakjiDAO(String place_name, String address, String utility, String notify, String introduce, String filePath, int jjim, double latitude, double longitude) {
        this.place_name = place_name;
        this.address = address;
        this.utility = utility;
        this.notify = notify;
        this.introduce = introduce;
        this.filePath = filePath;
        this.jjim = jjim;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public ChabakjiDAO(){}

    public String getPlace_name() { return place_name; }

    public void setPlace_name(String place_name) { this.place_name = place_name; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getUtility() { return utility; }

    public void setUtility(String utility) { this.utility = utility; }

    public String getNotify() { return notify; }

    public void setNotify(String notify) { this.notify = notify; }

    public String getIntroduce() { return introduce; }

    public void setIntroduce(String introduce) { this.introduce = introduce; }

    public String getFilePath() { return filePath; }

    public void setFilePath(String filePath) { this.filePath = filePath; }

    public Bitmap getImageFile() { return imageFile; }

    public void setImageFile(Bitmap imageFile) { this.imageFile = imageFile; }

    public int getJjim() { return jjim; }

    public void setJjim(int jjim) { this.jjim = jjim; }

    public double getLatitude() { return latitude; }

    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }

    public void setLongitude(double longitude) { this.longitude = longitude; }
}
