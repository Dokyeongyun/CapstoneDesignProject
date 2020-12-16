package com.example.capstonedesignproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.capstonedesignproject.VO.ProvinceData;
import com.example.capstonedesignproject.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProvinceAdapter extends BaseAdapter {
    @BindView(R.id.provinceName) TextView provinceName;
    private LayoutInflater mLayoutInflater;
    private ArrayList<ProvinceData> provinceList;

    public ProvinceAdapter(Context context, ArrayList<ProvinceData> data) {
        provinceList = data;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return provinceList.size();
    }

    @Override
    public ProvinceData getItem(int position) {
        return provinceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.province_view, null);
        ButterKnife.bind(this, view);
        provinceName.setText(provinceList.get(position).getProvince());
        provinceName.setHeight(120);
        return view;
    }
}
