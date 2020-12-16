package com.example.capstonedesignproject.view.Test;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ChabakjiData implements Serializable {
    private int placeId;
    private String placeName;
    private String address;
    private String phone_number;
    private String introduce;
    private String filePath;
    private int jjim;
    private double latitude;
    private double longitude;
    private double avg_point;
    private Map<String, Integer> utilityCount;

    public int getPlaceId() { return placeId; }
    public String getPlaceName() { return placeName; }
    public String getAddress() { return address; }
    public String getPhone_number() { return phone_number; }
    public String getIntroduce () { return introduce; }
    public String getFilePath () { return filePath; }
    public int getJjim() { return jjim; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public double getAvg_point() { return avg_point; }
    public Map<String, Integer> getUtilityCount() { return utilityCount; }
}
