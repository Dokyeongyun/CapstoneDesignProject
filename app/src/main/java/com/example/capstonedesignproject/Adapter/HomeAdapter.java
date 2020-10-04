package com.example.capstonedesignproject.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstonedesignproject.R;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private ArrayList<HomeData> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageButton mImageButton;
        public TextView text1;
        public TextView text2;
        public TextView text3;
        public TextView text4;

        public ViewHolder(View view) {
            super(view);
            mImageButton = view.findViewById(R.id.imageButton);
            text1 = view.findViewById(R.id.name);
            text2 = view.findViewById(R.id.address);
            text3 = view.findViewById(R.id.convi);
            text4 = view.findViewById(R.id.character);
        }
    }

    public HomeAdapter (ArrayList<HomeData> myDataset) { mDataset = myDataset; }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // ViewHolder 에 넣어줄 view 정의 후 inflate
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    /**
     * RecyclerView에 item이 셋팅될 때 + 스크롤될 때 호출
     * */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.text1.setText(mDataset.get(position).text1);
        holder.text2.setText(mDataset.get(position).text2);
        holder.text3.setText(mDataset.get(position).text3);
        holder.text4.setText(mDataset.get(position).text4);
        holder.mImageButton.setImageResource(mDataset.get(position).img);
    }

    @Override
    public int getItemCount() { return mDataset.size(); }


    public static class HomeData{
        public String text1;
        public String text2;
        public String text3;
        public String text4;
        public int img;

        public HomeData(String text1, String text2, String text3, String text4, int img){
            this.text1 = text1;
            this.text2 = text2;
            this.text3 = text3;
            this.text4 = text4;
            this.img = img;
        }
    }


}
