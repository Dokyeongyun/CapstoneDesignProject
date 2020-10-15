package com.example.capstonedesignproject.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.capstonedesignproject.Adapter.HomeAdapter;
import com.example.capstonedesignproject.MainActivity;
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

        // CardView 아이템 클릭 리스너
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                HomeAdapter.HomeData data = myDataset.get(position);
                Toast.makeText(getActivity(), data.text1+" "+data.text2, Toast.LENGTH_SHORT).show();
                // TODO CardView 아이템 클릭시 해당 차박지 상세정보 띄우기
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        // BT_regionChoice ClickListener
        Button regionChoice = v.findViewById(R.id.BT_regionChoice);
        regionChoice.setText("내주변");
        regionChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegionChoiceActivity.class);
                startActivity(intent);
            }
        });

        Button ImageTest = v.findViewById(R.id.button3);
        ImageTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HttpImageTest.class);
                startActivity(intent);
            }
        });


        return v;
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private HomeFragment.ClickListener clickListener;

        RecyclerTouchListener(Context context, final RecyclerView recyclerView, final HomeFragment.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }

}
