package com.example.capstonedesignproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.capstonedesignproject.Data.CityData;
import com.example.capstonedesignproject.R;

import java.util.ArrayList;

public class CityAdapter extends BaseAdapter {
    LayoutInflater mLayoutInflater = null;
    ArrayList<CityData> cityList;
    Context mContext = null;

    public CityAdapter(Context context, ArrayList<CityData> data) {
        mContext = context;
        cityList = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return cityList.size();
    }

    @Override
    public CityData getItem(int position) {
        return cityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.province_view, null);
        TextView cityName = view.findViewById(R.id.provinceName);
        cityName.setText(cityList.get(position).getCity());
        cityName.setHeight(120);
        return view;
    }

}
