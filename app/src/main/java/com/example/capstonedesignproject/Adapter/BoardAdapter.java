package com.example.capstonedesignproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.capstonedesignproject.Data.ArticleData;
import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.view.ETC.HomeActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BoardAdapter extends BaseAdapter {
    @BindView(R.id.TV_postTitle) TextView TV_postTitle;
    @BindView(R.id.TV_postDetail) TextView TV_postDetail;
    @BindView(R.id.TV_postContent) TextView TV_postContent;
    @BindView(R.id.IV_postPicture) ImageView IV_postPicture;

    private LayoutInflater mLayoutInflater;
    private ArrayList<ArticleData> postList;

    public BoardAdapter(Context context, ArrayList<ArticleData> data) {
        postList = data;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return postList.size();
    }

    @Override
    public ArticleData getItem(int position) {
        return postList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.board_view, null);
        ButterKnife.bind(this, view);

        ArticleData articleData = postList.get(position);
        String detail = articleData.getMemberId() + "  " + articleData.getCreateTime();
        TV_postTitle.setText(articleData.getTitle());
        TV_postContent.setText(articleData.getContent()); // TODO 최대글자수 제한해야 함
        TV_postDetail.setText(detail);

        Glide.with(parent.getContext())
                .load(HomeActivity.SERVER_URL + articleData.getFilePath())
                .fitCenter()
                .into(IV_postPicture);

        return view;
    }
}
