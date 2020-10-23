package com.example.capstonedesignproject.view.ChabakJi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
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
import com.example.capstonedesignproject.Server.FileDownloadTask;
import com.example.capstonedesignproject.view.Board.HttpImageTest;
import com.example.capstonedesignproject.view.Filter.RegionChoiceActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ChabakjiData> myDataset;
    List<ChabakjiDAO> list;

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
        // TODO 불러온 차박지를 캐시에 저장해놓고, 동일한 데이털는 불러오지 않도록 해야 함 / 스크롤을 내리다가 재요청하여 10개씩 가져옴
        list = new ArrayList<>();
        try {
            list = new ChabakjiInfoTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 차박지 사진 filePath 에 접근하여 파일을 Bitmap 으로 가져오기
        final Bitmap[] imageArr = new Bitmap[list.size()];
        for(int i=0; i<imageArr.length; i++){
            String filePath = list.get(i).getFilePath();
            try{
                imageArr[i] = new FileDownloadTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,filePath).get();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if (list == null) {
            Toast.makeText(getActivity(), "차박지 데이터를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < list.size(); i++) {
                ChabakjiDAO temp = list.get(i);
                myDataset.add(new ChabakjiData(temp.getPlace_name(), temp.getAddress(), temp.getUtility(), temp.getIntroduce(), imageArr[i]));
            }
        }
        /*

         */
        /* 샘플 데이터 *//*

        myDataset.add(new HomeAdapter.HomeData("강릉 인근 해수욕장", "강릉시 강릉동 198-1", "개수시설, 편의점", "없음", R.drawable.search_24dp));
        myDataset.add(new HomeAdapter.HomeData("강릉 인근 해수욕장", "강릉시 강릉동 198-1", "개수시설, 편의점", "없음", R.drawable.search_24dp));
        myDataset.add(new HomeAdapter.HomeData("강릉 인근 해수욕장", "강릉시 강릉동 198-1", "개수시설, 편의점", "없음", R.drawable.search_24dp));
*/

        // CardView 아이템 클릭 리스너
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ChabakjiData data = myDataset.get(position);
                Toast.makeText(getActivity(), data.text1 + " " + data.text2, Toast.LENGTH_SHORT).show();
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
