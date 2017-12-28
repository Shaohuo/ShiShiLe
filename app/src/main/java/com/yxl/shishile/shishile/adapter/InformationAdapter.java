package com.yxl.shishile.shishile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yxl.shishile.shishile.R;
import com.yxl.shishile.shishile.model.InformationModel;
import com.yxl.shishile.shishile.widgets.MZModeBannerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/27 0027.
 */

public class InformationAdapter extends BaseAdapter {

    private List<InformationModel.DataBean> list=new ArrayList<>();
    private MZModeBannerFragment view;
    public  InformationAdapter(MZModeBannerFragment context , List<InformationModel.DataBean> list)
    {
        this.list=list;
        view=context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View inflate;
        if (convertView==null) {
            inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_information, null);

        }
        else
        {
            inflate=  convertView;
        }
        TextView title = inflate.findViewById(R.id.title);
        TextView info = inflate.findViewById(R.id.info);
        if (list != null) {
            title.setText(list.get(i).getTitle());
            info.setText(list.get(i).getContent());
        }else {
        }
        return inflate;
    }
}
