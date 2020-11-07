package com.example.capstonedesignproject.view.ETC;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstonedesignproject.Adapter.SmallChabakjiAdapter;
import com.example.capstonedesignproject.Data.ChabakjiDAO;
import com.example.capstonedesignproject.Data.ChabakjiData;
import com.example.capstonedesignproject.Listener.ClickListener;
import com.example.capstonedesignproject.Listener.RecyclerTouchListener;
import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.Server.ChabakjiInfoTask;
import com.example.capstonedesignproject.view.Board.BoardFragment;
import com.example.capstonedesignproject.view.Board.SuggestActivity;
import com.example.capstonedesignproject.view.ChabakJi.DetailActivity;
import com.example.capstonedesignproject.view.Filter.RegionChoiceFragment;
import com.example.capstonedesignproject.view.Filter.SearchActivity;
import com.example.capstonedesignproject.view.Login.LoginActivity;
import com.example.capstonedesignproject.view.MyPage.ManageMyProfile;
import com.example.capstonedesignproject.view.MyPage.MyPageFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainFragment extends Fragment {
    private static Context context;

    @BindView(R.id.RV_main) RecyclerView RV_main;
    @BindView(R.id.TV_searchUsingRegion) TextView TV_searchUsingRegion;
    @BindView(R.id.TV_searchUsingKeyword) TextView TV_searchUsingKeyword;
    @BindView(R.id.TV_searchBoard) TextView TV_searchBoard;
    @BindView(R.id.TV_goToBoard) TextView TV_goToBoard;
    @BindView(R.id.TV_goToSuggest) TextView TV_goToSuggest;
    @BindView(R.id.TV_goToJJIM) TextView TV_goToJJIM;
    @BindView(R.id.TV_goToMypage) TextView TV_goToMypage;
    @BindView(R.id.TV_goToQnA) TextView TV_goToQnA;
    @BindView(R.id.TV_goToLogout) TextView TV_goToLogout;

    private static RecyclerView.Adapter mAdapter;
    private static ArrayList<ChabakjiData> myDataset;
    private RecyclerView.LayoutManager mLayoutManager;
    public static List<ChabakjiDAO> list;
    private static int page = 0;

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, v);
        Init();

        // 차박지 리스트를 불러와 RecyclerView에 설정
        try { getChabakjiList(page); } catch (Exception e) { e.printStackTrace(); }
        setChabakjiList(list);

        // CardView 아이템 클릭 리스너
        RV_main.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), RV_main, new ClickListener() {
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
    // 초기 설정
    private void Init(){
        context = getActivity();
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        myDataset = new ArrayList<>();
        mAdapter = new SmallChabakjiAdapter(myDataset);
        RV_main.setHasFixedSize(true);
        RV_main.setLayoutManager(mLayoutManager);
        RV_main.setAdapter(mAdapter);
    }

    // 최초 차박지리스트 불러오기
    private void getChabakjiList(int pageNum) throws ExecutionException, InterruptedException {
        list = new ChabakjiInfoTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "get.do", pageNum).get();
    }

    // RecyclerView 에 넣을 List 에 차박지 정보 삽입
    private static void setChabakjiList(List<ChabakjiDAO> addList){
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

    @OnClick(R.id.TV_searchUsingRegion) void searchUsingRegion(){
        ((HomeActivity) Objects.requireNonNull(getActivity())).replaceFragment(new RegionChoiceFragment(), "지역선택");
    }
    @OnClick(R.id.TV_searchUsingKeyword) void searchUsingKeyword(){
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        intent.putExtra("FromMain", "Keyword");
        startActivity(intent);
    }
    @OnClick(R.id.TV_searchBoard) void searchBoard(){
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        intent.putExtra("FromMain", "Board");
        startActivity(intent);
    }
    @OnClick(R.id.TV_goToBoard) void goToBoard(){
        ((HomeActivity) Objects.requireNonNull(getActivity())).replaceFragment(new BoardFragment(), "게시판");
    }
    @OnClick(R.id.TV_goToSuggest) void goToSuggest(){
        Intent intent = new Intent(getActivity(), SuggestActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.TV_goToJJIM) void goToJJIM(){
        // TODO 현재 로그인 회원의 즐겨찾는 차박지 리스트 화면으로 이동
    }
    @OnClick(R.id.TV_goToMypage) void goToMypage(){
        ((HomeActivity) Objects.requireNonNull(getActivity())).replaceFragment(new MyPageFragment(), "마이페이지");
    }
    @OnClick(R.id.TV_goToQnA) void goToQnA(){
        // TODO QnA 페이지로 이동
    }
    @OnClick(R.id.TV_goToLogout) void goToLogout(){
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
