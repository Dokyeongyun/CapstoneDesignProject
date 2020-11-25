package com.example.capstonedesignproject.view.Test;

import java.io.Serializable;
import java.util.List;

public class ChabakjiData implements Serializable {
    private String id;
    private String place_name;
    private String address;
    private String introduce;
    private String filePath;
    private String jjim;
    private String latitude;
    private String longitude;
    private String phoneNumber;
    private List<Utils> utils;

    public String getId() { return id; }
    public String getPlace_name () { return place_name; }
    public String getAddress() { return address; }
    public String getIntroduce () { return introduce; }
    public String getFilePath () { return filePath; }
    public String getJjim() { return jjim; }
    public String getLatitude() { return latitude; }
    public String getLongitude() { return longitude; }
    public String getPhoneNumber() { return phoneNumber; }
    public List<Utils> getUtils() { return utils; }
}
