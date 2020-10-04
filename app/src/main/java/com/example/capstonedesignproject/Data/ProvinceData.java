package com.example.capstonedesignproject.Data;

import java.util.ArrayList;

public class ProvinceData {
    public String province;
    public ArrayList<CityData> cityList;

    public ProvinceData(String province, ArrayList<CityData> cityList){
        this.province = province;
        this.cityList = cityList;
    }

    public String getProvince() {
        return province;
    }

    public ArrayList<CityData> getCityList() {
        return cityList;
    }
}
