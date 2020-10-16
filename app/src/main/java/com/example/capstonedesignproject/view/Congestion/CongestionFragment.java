package com.example.capstonedesignproject.view.Congestion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.capstonedesignproject.R;

import net.daum.mf.map.api.MapView;


public class CongestionFragment extends Fragment {
    public CongestionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_congestion, container, false);

        MapView mapView = new MapView(getContext());

        ViewGroup mapViewContainer = v.findViewById(R.id.mapView);
        mapViewContainer.addView(mapView);

        return v;
    }
}
