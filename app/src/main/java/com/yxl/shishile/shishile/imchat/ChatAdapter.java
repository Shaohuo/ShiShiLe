package com.yxl.shishile.shishile.imchat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.yxl.shishile.shishile.R;
import com.yxl.shishile.shishile.activity.DragPhotoActivity;
import com.yxl.shishile.shishile.app.Constant;
import com.yxl.shishile.shishile.util.DisplayUtil;

import java.io.IOException;
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
    private MediaPlayer mMediaPlayer;


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
        if (mRecyclerView != null) {
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);

                }
            });
        }
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


    private void bindChatHolderContent(final ChatHolder holder, final ChatMessageVo vo) {
        if (vo.getChatType() == ChatType.text) {
            holder.mContent.setVisibility(View.VISIBLE);
            holder.mImage.setVisibility(View.GONE);
            holder.mAudio.setVisibility(View.GONE);
            holder.mContent.setText(vo.getContent());
        } else if (vo.getChatType() == ChatType.image) {
            holder.mContent.setVisibility(View.GONE);
            holder.mImage.setVisibility(View.VISIBLE);
            holder.mAudio.setVisibility(View.GONE);
            final String imgUrl = vo.getContent().replace("Image://", "");
            holder.mImage.setImageDrawable(null);
            holder.mImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startPhotoActivity(context, holder.mImage, imgUrl);
                }
            });
            loadImage(holder, imgUrl);
        } else if (vo.getChatType() == ChatType.audio) {
            holder.mContent.setVisibility(View.GONE);
            holder.mImage.setVisibility(View.GONE);
            holder.mAudio.setVisibility(View.VISIBLE);
            holder.mAudio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playVoice(holder.mAudio, vo);
                }
            });

        }
        holder.mTime.setText(JacenUtils.parseChatTimer(vo.getSendTime()));
        holder.mTime.setVisibility(vo.isShowTime() ? View.VISIBLE : View.GONE);
        holder.mUserName.setText(vo.getSender());
    }

    /**
     * 播放语音信息
     *
     * @param iv
     * @param message
     */
    private void playVoice(final ImageView iv, final ChatMessageVo message) {
        if (message.isMe()) {
            iv.setImageResource(R.drawable.anim_chat_voice_right);
        } else {
            iv.setImageResource(R.drawable.anim_chat_voice_left);
        }
        final AnimationDrawable animationDrawable = (AnimationDrawable) iv.getDrawable();
        iv.post(new Runnable() {
            @Override
            public void run() {

                animationDrawable.start();
            }
        });
        if (mMediaPlayer == null || !mMediaPlayer.isPlaying()) {//点击播放，再次点击停止播放
            mMediaPlayer = new MediaPlayer();
            // 开始播放录音
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mMediaPlayer.start();
                }

            });
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    animationDrawable.stop();
                    // 恢复语音消息图标背景
                    if (message.isMe()) {
                        iv.setImageResource(R.drawable.gxu);
                    } else {
                        iv.setImageResource(R.drawable.gxx);
                    }
                }
            });
            try {
                mMediaPlayer.reset();
                String audioUrl = message.getContent().replace("Audio://", "");
                mMediaPlayer.setDataSource(audioUrl);
                mMediaPlayer.prepareAsync();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            stopPlayVoice(iv, message);
        }
    }

    private void stopPlayVoice(ImageView iv, ChatMessageVo message) {
        Drawable drawable = iv.getDrawable();
        if (drawable instanceof AnimationDrawable) {
            ((AnimationDrawable) drawable).stop();
        }
        // 恢复语音消息图标背景
        if (message.isMe()) {
            iv.setImageResource(R.drawable.gxu);
        } else {
            iv.setImageResource(R.drawable.gxx);
        }
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    private void loadImage(final ChatHolder holder, final String imgUrl) {

        Glide.with(context).load(imgUrl).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap>
                    glideAnimation) {
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
        });

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
        ImageView mAudio;
        OnItemClickListener l;

        ChatHolder(final View view, OnItemClickListener l) {
            super(view);
            view.setOnClickListener(this);
            this.l = l;
            mContent = (TextView) view.findViewById(R.id.content);
            mAvatar = (ImageView) view.findViewById(R.id.avatar);
            mTime = (TextView) view.findViewById(R.id.time);
            mUserName = (TextView) view.findViewById(R.id.username);
            mImage = (ImageView) view.findViewById(R.id.image);
            mAudio = (ImageView) view.findViewById(R.id.audio);
        }

        @Override
        public void onClick(View v) {
            l.onItemClick(v, getLayoutPosition());
        }
    }

    public void startPhotoActivity(Context context, ImageView imageView, String imgUrl) {
        Intent intent = new Intent(context, DragPhotoActivity.class);
        int location[] = new int[2];

        imageView.getLocationOnScreen(location);
        intent.putExtra("left", location[0]);
        intent.putExtra("top", location[1]);
        intent.putExtra("height", imageView.getHeight());
        intent.putExtra("width", imageView.getWidth());
        ArrayList<String> photoList = new ArrayList<>();
        photoList.add(imgUrl);
        intent.putStringArrayListExtra(Constant.PHOTO_LIST, photoList);
        context.startActivity(intent);
        if (context instanceof ChattingUI) {
            ((ChattingUI) context).setCanAutoScrollToBottom(false);
            ((ChattingUI) context).overridePendingTransition(0, 0);

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
