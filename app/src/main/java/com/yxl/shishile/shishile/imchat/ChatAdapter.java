package com.yxl.shishile.shishile.imchat;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.yxl.shishile.shishile.R;
import com.yxl.shishile.shishile.util.DisplayUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @email jacen@iswsc.com
 * Created by Jacen on 2017/9/3 2:12.
 */

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private OnItemClickListener l;
    private List<ChatMessageVo> mList;
    private LayoutInflater mInflater;

    //    private final int MESSAGE_ERROR = -1;
    private final int MESSAGE_LEFT_TEXT = 1;
    private final int MESSAGE_RIGHT_TEXT = 2;
    private RecyclerView mRecyclerView;


    public ChatAdapter(Context context, List<ChatMessageVo> mList, OnItemClickListener l) {
        this.context = context;
        this.mList = mList;
        this.l = l;
        mInflater = LayoutInflater.from(context);
    }

    public void updateList(ArrayList<ChatMessageVo> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void addChatMessage(ChatMessageVo vo) {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        mList.add(vo);
        notifyItemInserted(mList.size() - 1);
        notifyItemChanged(mList.size() - 1);
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }

    @Override
    public int getItemCount() {
        if (mList != null)
            return mList.size();
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessageVo vo = mList.get(position);
        if (vo.isMe()) {
            return MESSAGE_RIGHT_TEXT;
        } else {
            return MESSAGE_LEFT_TEXT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case MESSAGE_LEFT_TEXT:
                View left_text = mInflater.from(context).inflate(R.layout.item_chat_text_left,
                        parent, false);
                return new ChatHolder(left_text, l);
            case MESSAGE_RIGHT_TEXT:
                View right_text = mInflater.from(context).inflate(R.layout.item_chat_text_right,
                        parent, false);
                return new ChatHolder(right_text, l);
            default:
                View view = View.inflate(context, R.layout.item_chat_text_error, null);
                return new ChatHolder(view, l);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatMessageVo vo = mList.get(position);
        if (holder instanceof ChatHolder) {
            bindChatHolderContent((ChatHolder) holder, vo);
        } else {
            errorTextContent((ErrorHolder) holder, vo);
        }
    }


    private void bindChatHolderContent(ChatHolder holder, ChatMessageVo vo) {
        if (vo.getChatType() == ChatType.text) {
            holder.mContent.setVisibility(View.VISIBLE);
            holder.mImage.setVisibility(View.GONE);
        } else if (vo.getChatType() == ChatType.image) {
            holder.mContent.setVisibility(View.GONE);
            holder.mImage.setVisibility(View.VISIBLE);
            String imgUrl = vo.getContent().replace("Image:", "");
//        Glide.with(context).load(new File(imgUrl)).into(holder.mImage);
            holder.mImage.setImageDrawable(null);
            loadImage(holder, imgUrl);
        }
        holder.mTime.setText(JacenUtils.parseChatTimer(vo.getSendTime()));
        holder.mTime.setVisibility(vo.isShowTime() ? View.VISIBLE : View.GONE);
        holder.mContent.setText(vo.getContent());
        holder.mUserName.setText(vo.getSender());
    }

    private void loadImage(final ChatHolder holder, final String imgUrl) {
        if (isLocalImage(imgUrl)) {
            Glide.with(context).load(new File(imgUrl)).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap>
                        glideAnimation) {
                    setImageViewSizeAndBitmap(bitmap, holder);
                }
            });
        }else{
            Glide.with(context).load(imgUrl).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap>
                        glideAnimation) {
                    setImageViewSizeAndBitmap(bitmap, holder);
                }
            });
        }

    }

    private boolean isLocalImage(String imgUrl) {
        return new File(imgUrl).exists();
    }

    private void setImageViewSizeAndBitmap(Bitmap bitmap, ChatHolder holder) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        ViewGroup.LayoutParams layoutParams = holder.mImage.getLayoutParams();
        if (width > height) {
            layoutParams.width = DisplayUtil.dip2px(context, 150);
            layoutParams.height = (int) ((float) (layoutParams.width * height) / (float)
                    width);
        } else {
            layoutParams.height = DisplayUtil.dip2px(context, 150);
            layoutParams.width = (int) ((float) (layoutParams.height * width) / (float)
                    height);
        }
        holder.mImage.setLayoutParams(layoutParams);
        holder.mImage.setImageBitmap(bitmap);
    }

    private void errorTextContent(ErrorHolder holder, ChatMessageVo vo) {
        holder.mTime.setText(JacenUtils.parseChatTimer(vo.getSendTime()));
        holder.mTime.setVisibility(vo.isShowTime() ? View.VISIBLE : View.GONE);
        holder.mContent.setText(vo.getContent());
    }

    class ChatHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTime;
        TextView mContent;
        TextView mUserName;
        ImageView mAvatar;
        ImageView mImage;
        OnItemClickListener l;

        ChatHolder(View view, OnItemClickListener l) {
            super(view);
            view.setOnClickListener(this);
            this.l = l;
            mContent = (TextView) view.findViewById(R.id.content);
            mAvatar = (ImageView) view.findViewById(R.id.avatar);
            mTime = (TextView) view.findViewById(R.id.time);
            mUserName = (TextView) view.findViewById(R.id.username);
            mImage = (ImageView) view.findViewById(R.id.image);
        }

        @Override
        public void onClick(View v) {
            l.onItemClick(v, getLayoutPosition());
        }
    }

//    class LeftTextHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//        TextView mTime;
//        ImageView mAvatar;
//        TextView mContent;
//        TextView mUserName;
//        ImageView mImage;
//        OnItemClickListener l;
//
//        LeftTextHolder(View view, OnItemClickListener l) {
//            super(view);
//            view.setOnClickListener(this);
//            this.l = l;
//            mTime = (TextView) view.findViewById(R.id.time);
//            mAvatar = (ImageView) view.findViewById(R.id.avatar);
//            mContent = (TextView) view.findViewById(R.id.content);
//            mUserName = (TextView) view.findViewById(R.id.username);
//            mImage = (ImageView) view.findViewById(R.id.image);
//        }
//
//        @Override
//        public void onClick(View v) {
//            l.onItemClick(v, getLayoutPosition());
//        }
//    }

    class ErrorHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTime;
        TextView mContent;
        OnItemClickListener l;

        ErrorHolder(View view, OnItemClickListener l) {
            super(view);
            view.setOnClickListener(this);
            this.l = l;
            mTime = (TextView) view.findViewById(R.id.time);
            mContent = (TextView) view.findViewById(R.id.content);

        }

        @Override
        public void onClick(View v) {
            l.onItemClick(v, getLayoutPosition());
        }
    }

}
