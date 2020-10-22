package com.example.capstonedesignproject.Adapter;

import android.graphics.Bitmap;
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
        ImageButton mImageButton;
        TextView text1;
        TextView text2;
        TextView text3;
        TextView text4;

        ViewHolder(View view) {
            super(view);
            mImageButton = view.findViewById(R.id.imageButton);
            text1 = view.findViewById(R.id.name);
            text2 = view.findViewById(R.id.address);
            text3 = view.findViewById(R.id.convi);
            text4 = view.findViewById(R.id.character);
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

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    /**
     * RecyclerView에 item이 셋팅될 때 + 스크롤될 때 호출
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.text1.setText(mDataset.get(position).text1);
        holder.text2.setText(mDataset.get(position).text2);
        holder.text3.setText(mDataset.get(position).text3);
        holder.text4.setText(mDataset.get(position).text4);
        holder.mImageButton.setImageBitmap(mDataset.get(position).img);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
