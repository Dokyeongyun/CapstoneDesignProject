package com.example.capstonedesignproject.Data;

import java.util.ArrayList;

public class ProvinceData {
    public String province;
    public ArrayList<String> cityList;

    public ProvinceData(String province, ArrayList<String> cityList){
        this.province = province;
        this.cityList = cityList;
    }

    public String getProvince() {
        return province;
    }

    public ArrayList<String> getCityList() {
        return cityList;
    }
}
