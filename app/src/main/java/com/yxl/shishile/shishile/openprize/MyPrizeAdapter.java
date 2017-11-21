package com.yxl.shishile.shishile.openprize;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yxl.shishile.shishile.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public class MyPrizeAdapter extends RecyclerView.Adapter<MyPrizeAdapter.ViewHolder>{

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_prize,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.mTextView.setText(""+position);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ViewHolder(View view){
            super(view);
            mTextView = (TextView) view.findViewById(R.id.textView);
        }
    }
}
