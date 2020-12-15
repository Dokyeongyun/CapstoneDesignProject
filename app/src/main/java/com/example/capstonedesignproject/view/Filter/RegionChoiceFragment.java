package com.example.capstonedesignproject.view.Filter;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.capstonedesignproject.Adapter.CityAdapter;
import com.example.capstonedesignproject.Adapter.ProvinceAdapter;
import com.example.capstonedesignproject.Data.ProvinceData;
import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.view.ChabakJi.ListActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class RegionChoiceFragment extends Fragment {
    @BindView(R.id.province_listView) ListView provinceListView;
    @BindView(R.id.city_listView) ListView cityListView;

    private static ArrayList<ProvinceData> provinceList = new ArrayList<>();
    private static ArrayList<String> cityList = new ArrayList<>();

    public RegionChoiceFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_region_choice, container, false);
        ButterKnife.bind(this, v);
        Init();
        ProvinceAdapter provinceAdapter = new ProvinceAdapter(getContext(), provinceList);
        provinceListView.setAdapter(provinceAdapter);

        return v;
    }
    @OnItemClick(R.id.province_listView) void ClickProvince(AdapterView<?> parent, View v1, int position, long id){
        cityList = provinceList.get(position).getCityList();
        CityAdapter cityAdapter = new CityAdapter(getContext(), cityList);
        cityListView.setAdapter(cityAdapter);
    }
    @OnItemClick(R.id.city_listView) void ClickCity(AdapterView<?> parent, View v1, int position, long id){
        Intent intent = new Intent(getActivity(), ListActivity.class);
        intent.putExtra("Region", cityList.get(position));
        intent.putExtra("Type", "Region");
        startActivity(intent);
    }

    // 샘플 데이터
    static void Init(){
        cityInit();
        provinceList = new ArrayList<>();
        provinceList.add(new ProvinceData("강원", cityList));
        provinceList.add(new ProvinceData("서울", new ArrayList<String>()));
        provinceList.add(new ProvinceData("경기", new ArrayList<String>()));
        provinceList.add(new ProvinceData("인천", new ArrayList<String>()));
        provinceList.add(new ProvinceData("제주", new ArrayList<String>()));
        provinceList.add(new ProvinceData("대전", new ArrayList<String>()));
        provinceList.add(new ProvinceData("충북", new ArrayList<String>()));
        provinceList.add(new ProvinceData("충남", new ArrayList<String>()));
        provinceList.add(new ProvinceData("부산", new ArrayList<String>()));
        provinceList.add(new ProvinceData("울산", new ArrayList<String>()));

    }

    // 샘플 데이터
    static void cityInit(){
        cityList = new ArrayList<>();
        cityList.add("춘천/강촌");
        cityList.add("원주");
        cityList.add("경포대/사천/주문진/정동진");
        cityList.add("강릉/교동/옥계");
        cityList.add("영월/정선");
        cityList.add("속초/양양/고성");
        cityList.add("동해/삼척/태박");
        cityList.add("평창");
        cityList.add("홍천/횡성");
        cityList.add("화천/철원/인제/양구");
    }
}
