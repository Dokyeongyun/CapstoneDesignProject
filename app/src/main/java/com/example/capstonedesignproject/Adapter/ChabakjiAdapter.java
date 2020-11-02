package com.example.capstonedesignproject.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.capstonedesignproject.Data.ChabakjiData;
import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.view.ChabakJi.HomeFragment;
import com.example.capstonedesignproject.view.ChabakJi.ListActivity;
import com.example.capstonedesignproject.view.ETC.HomeActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChabakjiAdapter extends RecyclerView.Adapter<ChabakjiAdapter.ViewHolder> {
    private static ArrayList<ChabakjiData> mDataset;

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.BT_chabakjiImage) ImageButton BT_chabakjiImage;
        @BindView(R.id.TV_chabakjiName) TextView TV_chabakjiName;
        @BindView(R.id.TV_chabakjiAddr) TextView TV_chabakjiAddr;
        @BindView(R.id.TV_chabakjiUtil) TextView TV_chabakjiUtil;
        @BindView(R.id.TV_chabakjiIntro) TextView TV_chabakjiIntro;
        @BindView(R.id.TV_chabakjiRating) TextView TV_chabakjiRating;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public ChabakjiAdapter(ArrayList<ChabakjiData> myDataset) {
        mDataset = myDataset;
    }

    @NonNull
    @Override
    public ChabakjiAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // ViewHolder 에 넣어줄 view 정의 후 inflate
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new ViewHolder(v);
    }

    /**
     * RecyclerView에 item이 셋팅될 때 + 스크롤될 때 호출
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.TV_chabakjiName.setText(mDataset.get(position).chabakjiName);
        holder.TV_chabakjiAddr.setText(mDataset.get(position).chabakjiAddr);
        holder.TV_chabakjiUtil.setText(mDataset.get(position).chabakjiUtil);
        holder.TV_chabakjiIntro.setText(mDataset.get(position).chabakjiIntro);
        holder.TV_chabakjiRating.setText(mDataset.get(position).chabakjiRating);
        Glide.with(holder.itemView.getContext())
                .load(HomeActivity.SERVER_URL + "/" + mDataset.get(position).filePath)
                .fitCenter()
                .into(holder.BT_chabakjiImage);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
