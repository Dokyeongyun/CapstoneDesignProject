package com.example.capstonedesignproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstonedesignproject.Data.ProvinceData;
import com.example.capstonedesignproject.R;

import java.util.ArrayList;

public class ProvinceAdapter extends BaseAdapter {
    LayoutInflater mLayoutInflater = null;
    ArrayList<ProvinceData> provinceList;
    Context mContext = null;

    public ProvinceAdapter(Context context, ArrayList<ProvinceData> data) {
        mContext = context;
        provinceList = data;
        mLayoutInflater = LayoutInflater.from(mContext);
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
        TextView provinceName = view.findViewById(R.id.provinceName);
        provinceName.setText(provinceList.get(position).getProvince());
        provinceName.setHeight(150);
        return view;
    }
}
