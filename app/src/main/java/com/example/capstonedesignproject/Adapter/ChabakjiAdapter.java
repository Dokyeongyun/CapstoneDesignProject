package com.example.capstonedesignproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.view.ETC.HomeActivity;
import com.example.capstonedesignproject.view.Test.ChabakjiData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChabakjiAdapter extends RecyclerView.Adapter<ChabakjiAdapter.ViewHolder> {
    private final Context context;
    private List<ChabakjiData> items = new ArrayList<>();

    public ChabakjiAdapter(Context context) {
        this.context = context;
    }

    public ChabakjiData getItemAt(int position) {
        return items.get(position);
    }

    public void setItemsAndRefresh(ChabakjiData items) {
        this.items.add(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChabakjiAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    /**
     * RecyclerView에 item이 셋팅될 때 + 스크롤될 때 호출
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ChabakjiData item = getItemAt(position);

        holder.TV_chabakjiName.setText(item.getPlace_name());
        holder.TV_chabakjiAddr.setText(item.getAddress());
        holder.TV_chabakjiIntro.setText(item.getIntroduce());
        holder.TV_chabakjiRating.setText("3.5");

        String imageURL = item.getFilePath();
        if(!item.getFilePath().startsWith("http://")){
            imageURL = HomeActivity.SERVER_URL + "/" + item.getFilePath();
        }

        Glide.with(holder.itemView.getContext())
                .load(imageURL)
                .fitCenter()
                .into(holder.BT_chabakjiImage);
    }

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
}
