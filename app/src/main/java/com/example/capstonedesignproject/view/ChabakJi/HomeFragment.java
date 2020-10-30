package com.example.capstonedesignproject.view.ChabakJi;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
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

import com.example.capstonedesignproject.Adapter.ChabakjiAdapter;
import com.example.capstonedesignproject.Data.ChabakjiDAO;
import com.example.capstonedesignproject.Data.ChabakjiData;
import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.Server.ChabakjiInfoTask;
import com.example.capstonedesignproject.view.Board.HttpImageTest;
import com.example.capstonedesignproject.view.Filter.FilterActivity;
import com.example.capstonedesignproject.view.Filter.RegionChoiceActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    static final int FILTER_REQUEST_CODE = 1;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ChabakjiData> myDataset;
    List<ChabakjiDAO> list;
    static int page = 0;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        mRecyclerView = v.findViewById(R.id.home_recyclerView);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        myDataset = new ArrayList<>();
        mAdapter = new ChabakjiAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

        // 차박지 리스트를 불러온다.
        list = new ArrayList<>();
        try {
            list = new ChabakjiInfoTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, page).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (list == null) {
            Toast.makeText(getActivity(), "차박지 데이터를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < list.size(); i++) {
                ChabakjiDAO temp = list.get(i);
                myDataset.add(new ChabakjiData(temp.getPlace_name(), temp.getAddress(), temp.getUtility(), temp.getIntroduce(), "3.5", temp.getFilePath()));
            }
        }

        // CardView 아이템 클릭 리스너
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                // TODO CardView 아이템 클릭시 해당 차박지 상세정보 띄우기
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("Chabakji", list.get(position));
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

        Button filter = v.findViewById(R.id.BT_setFilter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FilterActivity.class);
                startActivityForResult(intent, FILTER_REQUEST_CODE);
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == FILTER_REQUEST_CODE){
            if(resultCode == 1){
                boolean[] filterArr = data.getBooleanArrayExtra("FilterArr");
                StringBuilder sb = new StringBuilder();
                for(int i=0; i<filterArr.length; i++){
                    sb.append(filterArr[i]+" ");
                }
                Toast.makeText(getActivity(), sb, Toast.LENGTH_SHORT).show();

                // TODO 선택된 조건을 이용하여 검색 수행 및 결과 띄우기
            }
        }
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
