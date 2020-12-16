package com.example.capstonedesignproject.view.Test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.capstonedesignproject.R;
import com.example.capstonedesignproject.view.ETC.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * RecyclerView에서 차박지의 목록을 표시하기 위한 Adapter 클래스
 * 이 클래스로 RecyclerView의 아이템의 뷰를 생성하고, 뷰에 데이터를 넣는다
 */
public class ChabakjiAdapter_Favorite extends RecyclerView.Adapter<ChabakjiAdapter_Favorite.ChabakjiViewHolder> {
    private List<ChabakjiData> items = new ArrayList<>();

    public ChabakjiAdapter_Favorite() { }

    /**
     * 차박지 리스트에 데이터를 추가한 후 갱신한다
     *
     * @param items
     */
    public void setItemsAndRefresh(ChabakjiData items) {
        this.items.add(items);
        notifyDataSetChanged();
    }

    public ChabakjiData getItemAt(int position) {
        return items.get(position);
    }

    /**
     * RecyclerView의 아이템 뷰 생성과 뷰를 유지할 ViewHolder를 생성
     */
    @Override
    public ChabakjiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_cardview, parent, false);
        return new ChabakjiViewHolder(view);
    }

    /**
     * onCreateViewHolder로 만든 ViewHolder의 뷰에
     * setItemsAndRefresh(items)으로 설정된 데이터를 넣는다
     */
    @Override
    public void onBindViewHolder(final ChabakjiViewHolder holder, final int position) {
        final ChabakjiData item = getItemAt(position);
        holder.TV_chabakjiName.setText(item.getPlaceName());
        holder.TV_chabakjiRating.setText(String.valueOf(item.getAvg_point()));
        holder.TV_favoriteCount.setText(String.valueOf(item.getJjim()));
        holder.TV_chabakjiAddress.setText(item.getAddress());

        String imageURL = item.getFilePath();
        if(!item.getFilePath().startsWith("http://")){
            imageURL = HomeActivity.SERVER_URL + "/" + item.getFilePath();
        }

        Glide.with(holder.itemView.getContext())
                .load(imageURL)
                .fitCenter()
                .into(holder.BT_chabakjiImage);
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    static class ChabakjiViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.BT_chabakjiImage) ImageButton BT_chabakjiImage;
        @BindView(R.id.TV_chabakjiName) TextView TV_chabakjiName;
        @BindView(R.id.TV_chabakjiRating) TextView TV_chabakjiRating;
        @BindView(R.id.TV_favoriteCount) TextView TV_favoriteCount;
        @BindView(R.id.TV_chabakjiAddress) TextView TV_chabakjiAddress;
        ChabakjiViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
