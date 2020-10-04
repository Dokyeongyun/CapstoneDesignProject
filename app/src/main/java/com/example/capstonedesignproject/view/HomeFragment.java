package com.example.capstonedesignproject.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.capstonedesignproject.Adapter.HomeAdapter;
import com.example.capstonedesignproject.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<HomeAdapter.HomeData> myDataset;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerView = v.findViewById(R.id.home_recyclerView);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        myDataset = new ArrayList<>();
        mAdapter = new HomeAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

        /* 샘플 데이터 */
        myDataset.add(new HomeAdapter.HomeData("강릉 인근 해수욕장", "강릉시 강릉동 198-1", "개수시설, 편의점", "없음", R.drawable.search_24dp));
        myDataset.add(new HomeAdapter.HomeData("강릉 인근 해수욕장", "강릉시 강릉동 198-1", "개수시설, 편의점", "없음", R.drawable.search_24dp));
        myDataset.add(new HomeAdapter.HomeData("강릉 인근 해수욕장", "강릉시 강릉동 198-1", "개수시설, 편의점", "없음", R.drawable.search_24dp));

        return v;
    }
}
