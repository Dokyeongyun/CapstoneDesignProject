package com.example.capstonedesignproject.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstonedesignproject.Data.ChabakjiData;
import com.example.capstonedesignproject.R;

import java.util.ArrayList;

public class ChabakjiAdapter extends RecyclerView.Adapter<ChabakjiAdapter.ViewHolder> {
    private static ArrayList<ChabakjiData> mDataset;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton BT_chabakjiImage;
        TextView TV_chabakjiName;
        TextView TV_chabakjiAddr;
        TextView TV_chabakjiUtil;
        TextView TV_chabakjiIntro;
        TextView TV_chabakjiRating;

        ViewHolder(View view) {
            super(view);
            BT_chabakjiImage = view.findViewById(R.id.BT_chabakjiImage);
            TV_chabakjiName = view.findViewById(R.id.TV_chabakjiName);
            TV_chabakjiAddr = view.findViewById(R.id.TV_chabakjiAddr);
            TV_chabakjiUtil = view.findViewById(R.id.TV_chabakjiUtil);
            TV_chabakjiIntro = view.findViewById(R.id.TV_chabakjiIntro);
            TV_chabakjiRating = view.findViewById(R.id.TV_chabakjiRating);
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
        holder.BT_chabakjiImage.setImageBitmap(mDataset.get(position).chabakjiImage);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
