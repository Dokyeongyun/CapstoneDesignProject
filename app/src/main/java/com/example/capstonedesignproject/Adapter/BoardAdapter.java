package com.example.capstonedesignproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.capstonedesignproject.Data.ArticleData;
import com.example.capstonedesignproject.R;

import java.util.ArrayList;

public class BoardAdapter extends BaseAdapter {
    LayoutInflater mLayoutInflater = null;
    ArrayList<ArticleData> postList;
    Context mContext = null;

    public BoardAdapter(Context context, ArrayList<ArticleData> data) {
        mContext = context;
        postList = data;
        mLayoutInflater = LayoutInflater.from(mContext);
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

        ArticleData post = postList.get(position);

        String detail = post.getMemberId() + "  " + post.getCreateTime();

        postTitle.setText(post.getTitle());
        postContent.setText(post.getContent()); // TODO 최대글자수 제한해야 함
        postDetail.setText(detail);
        postPicture.setImageBitmap(post.getImage());

        return view;
    }
}
