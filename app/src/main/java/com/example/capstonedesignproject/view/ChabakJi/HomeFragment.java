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
import com.example.capstonedesignproject.view.Filter.FilterActivity;
import com.example.capstonedesignproject.view.Filter.RegionChoiceActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class HomeFragment extends Fragment {
    private static final int FILTER_REQUEST_CODE = 1;
    private static Context context;

    @BindView(R.id.BT_regionChoice) Button BT_regionChoice;
    @BindView(R.id.BT_setFilter) Button BT_setFilter;
    @BindView(R.id.home_recyclerView) RecyclerView mRecyclerView;

    private static RecyclerView.Adapter mAdapter;
    static ArrayList<ChabakjiData> myDataset;
    private RecyclerView.LayoutManager mLayoutManager;
    public static List<ChabakjiDAO> list;
    private static int page = 0;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, v);
        Init(v);

        // 차박지 리스트를 불러와 RecyclerView에 설정
        try { getChabakjiList(page); } catch (Exception e) { e.printStackTrace(); }
        setChabakjiList(list);

        // CardView 아이템 클릭 리스너
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("Chabakji", list.get(position));
                startActivity(intent);
            }
            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return v;
    }

    @OnClick(R.id.BT_regionChoice) void ClickRegionChoice(){
        Intent intent = new Intent(getActivity(), RegionChoiceActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.BT_setFilter) void ClickSetFilter(){
        Intent intent = new Intent(getActivity(), FilterActivity.class);
        startActivityForResult(intent, FILTER_REQUEST_CODE);
    }

    // 초기 설정
    private void Init(View v){
        context = getActivity();
        mLayoutManager = new LinearLayoutManager(getContext());
        myDataset = new ArrayList<>();
        mAdapter = new ChabakjiAdapter(myDataset);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        BT_regionChoice.setText("내주변");
    }

    // 최초 차박지리스트 불러오기
    private void getChabakjiList(int pageNum) throws ExecutionException, InterruptedException {
        list = new ChabakjiInfoTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "get.do", pageNum).get();
    }

    // RecyclerView 에 넣을 List 에 차박지 정보 삽입
    static void setChabakjiList(List<ChabakjiDAO> addList){
        if (addList == null) {
            Toast.makeText(context, "차박지 데이터를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
        } else {
            list = new ArrayList<>();
            for (int i = 0; i < addList.size(); i++) {
                ChabakjiDAO temp = addList.get(i);
                list.add(temp);
                myDataset.add(new ChabakjiData(temp.getPlace_name(), temp.getAddress(), temp.getUtility(), temp.getIntroduce(), "3.5", temp.getFilePath()));
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == FILTER_REQUEST_CODE){
            if(resultCode == 1){
                if(data!=null){
                    boolean[] filterArr = data.getBooleanArrayExtra("FilterArr");
                    StringBuilder sb = new StringBuilder();
                    for (boolean b : filterArr) {
                        sb.append(b).append(" ");
                    }
                    Toast.makeText(getActivity(), sb, Toast.LENGTH_SHORT).show();
                }
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
