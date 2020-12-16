package com.example.capstonedesignproject.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.capstonedesignproject.VO.ArticleVO;
import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.view.ETC.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    private List<ArticleVO> items = new ArrayList<>();

    public ArticleAdapter() { }

    public ArticleVO getItemAt(int position) { return items.get(position); }

    public void setItemsAndRefresh(ArticleVO items) {
        this.items.add(items);
        notifyDataSetChanged();
    }

    public void clear(){ this.items.clear(); }

    @NonNull
    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.board_view, parent, false);
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
        final ArticleVO item = getItemAt(position);

        String detail = item.getNickName() + "  " + item.getCreateTime();
        holder.TV_postTitle.setText(item.getTitle());
        holder.TV_postContent.setText(item.getContent());
        holder.TV_postDetail.setText(detail);

        String imageURL = item.getUrlPath();
        if(!item.getUrlPath().startsWith("http://")){
            imageURL = HomeActivity.SERVER_URL + "/" + item.getUrlPath();
        }

        Glide.with(holder.itemView.getContext())
                .load(imageURL)
                .fitCenter()
                .into(holder.IV_postPicture);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.TV_postTitle) TextView TV_postTitle;
        @BindView(R.id.TV_postDetail) TextView TV_postDetail;
        @BindView(R.id.TV_postContent) TextView TV_postContent;
        @BindView(R.id.IV_postPicture) ImageView IV_postPicture;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
