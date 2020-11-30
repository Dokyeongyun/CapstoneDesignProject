package com.example.capstonedesignproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.capstonedesignproject.Data.CommentVO;
import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.view.ETC.HomeActivity;
import com.example.capstonedesignproject.view.Test.ChabakjiData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private List<CommentVO> items = new ArrayList<>();

    public CommentAdapter() { }

    private CommentVO getItemAt(int position) { return items.get(position); }

    public void setItemsAndRefresh(CommentVO items) {
        this.items.add(items);
        notifyDataSetChanged();
    }

    public void clear(){
        this.items.clear();
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        if (items == null) { return 0; }
        return items.size();
    }

    /**
     * RecyclerView에 item이 셋팅될 때 + 스크롤될 때 호출
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CommentVO item = getItemAt(position);

        holder.TV_nickName.setText(item.getNickName());
        holder.TV_comment.setText(item.getContent());
        holder.TV_commentTime.setText(item.getCreateTime());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.TV_nickName) TextView TV_nickName;
        @BindView(R.id.TV_comment) TextView TV_comment;
        @BindView(R.id.TV_commentTime) TextView TV_commentTime;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
