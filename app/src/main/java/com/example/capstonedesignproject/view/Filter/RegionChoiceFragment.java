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

public class RegionChoiceFragment extends Fragment {
    static ArrayList<ProvinceData> provinceList = new ArrayList<>();
    static ArrayList<String> cityList = new ArrayList<>();

    public RegionChoiceFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_region_choice, container, false);

        Init();

        ListView provinceListView = v.findViewById(R.id.province_listView);
        final ProvinceAdapter provinceAdapter = new ProvinceAdapter(getContext(), provinceList);
        provinceListView.setAdapter(provinceAdapter);

        final ListView cityListView = v.findViewById(R.id.city_listView);

        // 클릭 리스너
        provinceListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                // TODO 선택한 시/도 의 세분류 리스트뷰 표시 (어댑터 변경)

                cityList = provinceList.get(position).getCityList();
                CityAdapter cityAdapter = new CityAdapter(getContext(), cityList);
                cityListView.setAdapter(cityAdapter);
            }
        });

        cityListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                // TODO 선택한 지역의 차박지 리스트 보여주는 액티비티로 이동
                // 선택한 지역명을 인텐트에 넣어 보내고, ListActivity에서 받아 해당 지역 데이터를 불러와 보여줌
                Intent intent = new Intent(getActivity(), ListActivity.class);
                intent.putExtra("Region", cityList.get(position));
                startActivity(intent);
            }
        });

        return v;
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
        cityList.add("강릉역/교동/옥계");
        cityList.add("영월/정선");
        cityList.add("속초/양양/고성");
        cityList.add("동해/삼척/태박");
        cityList.add("평창");
        cityList.add("홍청/횡성");
        cityList.add("화천/철원/인제/양구");
    }
}
