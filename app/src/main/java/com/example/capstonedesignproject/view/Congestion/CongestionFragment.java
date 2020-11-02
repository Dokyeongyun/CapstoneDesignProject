package com.example.capstonedesignproject.view.Congestion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.capstonedesignproject.R;

import net.daum.mf.map.api.MapView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CongestionFragment extends Fragment {
    @BindView(R.id.mapView) ViewGroup mapViewContainer;
    public CongestionFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_congestion, container, false);
        ButterKnife.bind(this, v);
        MapView mapView = new MapView(Objects.requireNonNull(getContext()));
        mapViewContainer.addView(mapView);
        return v;
    }
}
