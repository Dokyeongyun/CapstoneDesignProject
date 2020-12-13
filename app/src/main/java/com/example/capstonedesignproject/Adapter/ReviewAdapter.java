package com.example.capstonedesignproject.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstonedesignproject.Data.CommentVO;
import com.example.capstonedesignproject.Data.ReviewVO;
import com.example.capstonedesignproject.R;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private List<ReviewVO> items = new ArrayList<>();

    public ReviewAdapter() { }

    public ReviewVO getItemAt(int position) { return items.get(position); }

    public void setItemsAndRefresh(ReviewVO items) {
        this.items.add(items);
        notifyDataSetChanged();
    }

    public void clear(){
        this.items.clear();
    }

    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_view, parent, false);
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
        final ReviewVO item = getItemAt(position);
        holder.TV_nickName.setText(item.getNickName());
        holder.TV_review.setText(item.getReview_content());

        String time = item.getEval_time().split(" ")[0];
        holder.TV_evalTime.setText(time);

        float rating = Float.parseFloat(item.getEvaluation_point());
        if(rating == 3){
            holder.IV_satisfaction.setImageResource(R.drawable.mood_green_24dp);
        } else if(rating < 3){
            holder.IV_satisfaction.setImageResource(R.drawable.mood_bad_yellow_24dp);
        }
        holder.RB_ratingBar_review.setRating(rating);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.IV_satisfaction) ImageView IV_satisfaction;
        @BindView(R.id.RB_ratingBar_review) SimpleRatingBar RB_ratingBar_review;
        @BindView(R.id.TV_nickName) TextView TV_nickName;
        @BindView(R.id.TV_evalTime) TextView TV_evalTime;
        @BindView(R.id.TV_review) TextView TV_review;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
