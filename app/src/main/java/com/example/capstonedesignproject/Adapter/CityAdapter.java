package com.example.capstonedesignproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.capstonedesignproject.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CityAdapter extends BaseAdapter {
    @BindView(R.id.provinceName) TextView cityName;
    private LayoutInflater mLayoutInflater;
    private ArrayList<String> cityList;

    public CityAdapter(Context context, ArrayList<String> data) {
        cityList = data;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return cityList.size();
    }

    @Override
    public String getItem(int position) {
        return cityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.province_view, null);
        ButterKnife.bind(this, view);
        cityName.setText(cityList.get(position));
        cityName.setHeight(120);
        return view;
    }

}
