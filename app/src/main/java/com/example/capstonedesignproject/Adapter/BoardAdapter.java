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

import java.util.ArrayList;

public class BoardAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater = null;
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

        TextView postTitle = view.findViewById(R.id.TV_postTitle);
        TextView postDetail = view.findViewById(R.id.TV_postDetail);
        TextView postContent = view.findViewById(R.id.TV_postContent);
        ImageView postPicture = view.findViewById(R.id.IV_postPicture);

        ArticleData articleData = postList.get(position);
        String detail = articleData.getMemberId() + "  " + articleData.getCreateTime();
        postTitle.setText(articleData.getTitle());
        postContent.setText(articleData.getContent()); // TODO 최대글자수 제한해야 함
        postDetail.setText(detail);

        Glide.with(parent.getContext())
                .load("http://211.222.234.14:8080/"+articleData.getFilePath())
                .fitCenter()
                .into(postPicture);

        return view;
    }
}
