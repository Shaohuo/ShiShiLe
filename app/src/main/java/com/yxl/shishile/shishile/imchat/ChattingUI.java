package com.yxl.shishile.shishile.imchat;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.yxl.shishile.shishile.R;
import com.yxl.shishile.shishile.api.ApiManager;
import com.yxl.shishile.shishile.api.ApiServer;
import com.yxl.shishile.shishile.api.FileRequestBody;
import com.yxl.shishile.shishile.api.RetrofitCallback;
import com.yxl.shishile.shishile.app.AppDataManager;
import com.yxl.shishile.shishile.event.XmppGrounpChatMessageEvent;
import com.yxl.shishile.shishile.model.CountDownModel;
import com.yxl.shishile.shishile.model.UploadResultModel;
import com.yxl.shishile.shishile.util.UriUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smackx.muc.MultiUserChat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by Jacen on 2017/10/19 18:02.
 * jacen@iswsc.com
 */

public class ChattingUI extends Activity implements OnItemClickListener, View.OnClickListener {

    private TextView mSendBtn;
    private EditText mConetnt;

    //    private ContactVo mContactVo;
    private String chatRoomId;
    private String chatRoomName;

    private ChatAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private MultiUserChat mMultiUserChat;
    private TextView mTitle;
    private ImageView mBack;
    private String chatRoomJid;
    private String mUsername;
    List<ChatMessageVo> mMsgList = new ArrayList<>();


    private CountdownView mCvCountdownView;
    private TextView mTvLotteryNum;
    private TextView mTvOpenPrize;
    int[] mTvDataIds = new int[]{R.id.six_num_01, R.id.six_num_02, R.id.six_num_03, R.id
            .six_num_04, R.id.six_num_05, R.id.six_num_06, R.id.six_num_07, R.id.six_num_08, R.id
            .six_num_09, R.id.six_num_10};
    private TextView mTvNextLotteryNum;
    private View mChatMoreContainer;
    private ImageView mBtnMoreItem;
    private LinearLayout mAddPic;
    private LinearLayout mAddCamera;

    private final String IMAGE_TYPE = "image/*";
    public static final int IMAGE_REQUEST_CODE = 0x102;
    public static final int CAMERA_REQUEST_CODE = 0x103;
    private static File mCameraOutFile;
    private boolean isCanScrollToBottom = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.ui_chatting);
        initView();
        initData();
        setListener();
        loadLotteryCountDown();
        loadChatRoom();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mTitle = findViewById(R.id.app_title);
        mConetnt = findViewById(R.id.content);
        mBack = findViewById(R.id.app_back);
        mSendBtn = (TextView) findViewById(R.id.send);
        mChatMoreContainer = findViewById(R.id.chat_more_container);
        mAddCamera = findViewById(R.id.ll_add_camera);
        mAddPic = findViewById(R.id.ll_add_pic);
        mBtnMoreItem = findViewById(R.id.btn_more_item);
        ImageView mIvAddCamera = findViewById(R.id.iv_add_camera);
        ImageView mIvAddPic = findViewById(R.id.iv_add_pic);

        mCvCountdownView = findViewById(R.id.countdownView);
        mTvOpenPrize = findViewById(R.id.tvOpenPrize);
        mTvLotteryNum = findViewById(R.id.tvLotteryNum);
        mTvNextLotteryNum = findViewById(R.id.tvNextLotteryNum);

        for (int i = 0; i < mTvDataIds.length; i++) {
            TextView mTvData = findViewById(mTvDataIds[i]);
            mTvData.setVisibility(View.INVISIBLE);
        }
    }

    private void loadChatRoom() {
        ChatMessageDataBase.getInstance().deleteChatMessageByChatJid(chatRoomJid);
        mMultiUserChat = XmppUtils.getInstance().joinChatRoom("" + chatRoomId,
                mUsername);

        if (mMultiUserChat == null) {
            Toast.makeText(ChattingUI.this, "进入聊天室失败", Toast.LENGTH_SHORT).show();
        } else {
            JacenDialogUtils.showDialog(ChattingUI.this, "载入中...");
            mRecyclerView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IMAGE_REQUEST_CODE) {
                try {
                    Uri uri = data.getData();
                    String absolutePath = UriUtil.getRealPathFromUri(ChattingUI.this, uri);
                    compressAndSendPhoto(absolutePath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (requestCode == CAMERA_REQUEST_CODE) {
                if (mCameraOutFile != null) {
                    String absolutePath = mCameraOutFile.getAbsolutePath();
                    compressAndSendPhoto(absolutePath);
                }
            }
        }
    }

    private void compressAndSendPhoto(String absolutePath) {
        Luban.with(this)
                .ignoreBy(100)                                  // 忽略不压缩图片的大小
                .load(absolutePath)
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        Log.e("ChattingUI", "" + file.getAbsolutePath());
                        RequestBody requestFile = RequestBody.create(MediaType.parse
                                ("image/jpeg"), file);
                        RetrofitCallback retrofitCallback = new
                                RetrofitCallback<UploadResultModel>() {

                                    @Override
                                    public void onFailure(Call<UploadResultModel> call, Throwable
                                            t) {
                                        Log.e("ChattingUI", "" + t.getMessage());
                                    }

                                    @Override
                                    public void onSuccess(Call<UploadResultModel> call,
                                                          Response<UploadResultModel> response) {
                                        if (response.isSuccessful() && response.body() != null) {
                                            //允许消息滚动到底部
                                            isCanScrollToBottom = true;
                                            UploadResultModel.DataBean data = response.body()
                                                    .getData();
                                            Log.e("ChattingUI", "" + data.getUrl());
                                            XmppUtils.getInstance().sendChatRoomImage(chatRoomId,
                                                    "Image:" + ApiManager.BASE_URL + data.getUrl());
                                        }
                                    }

                                    @Override
                                    public void onLoading(long total, long progress) {
                                        Log.e("ChattingUI", progress + "/" + total);
                                    }
                                };
                        FileRequestBody requestBody = new FileRequestBody(requestFile,
                                retrofitCallback);

                        MultipartBody.Part body = MultipartBody.Part.createFormData("file",
                                file.getName(), requestBody);
                        RequestBody description = RequestBody.create(MediaType.parse
                                ("multipart/form-data"), "This is a description");
                        Call<UploadResultModel> call = ApiManager.getInstance().create(ApiServer
                                .class).uploadFile(description, body);
                        call.enqueue(retrofitCallback);

                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                    }
                }).launch();    //启动压缩
    }

    public void loadLotteryCountDown() {
        int lotteryId = getIntent().getIntExtra("lotteryId", -1);
        Call<CountDownModel> call = ApiManager.getInstance().create(ApiServer.class)
                .getLotteryCountDown(lotteryId);
        call.enqueue(new Callback<CountDownModel>() {
            @Override
            public void onResponse(Call<CountDownModel> call, Response<CountDownModel> response) {
                if (response.isSuccessful() && response.body() != null && response.body().data !=
                        null) {
                    CountDownModel.CountDown countDownData = response.body().data;
                    if (countDownData.countdown >= 0) {
                        mCvCountdownView.setVisibility(View.VISIBLE);
                        mTvOpenPrize.setVisibility(View.INVISIBLE);
                        mCvCountdownView.start(countDownData.countdown * 1000);
                        if (countDownData.number != null) {
                            try {
                                long number = Long.valueOf("" + countDownData.number);
                                mTvLotteryNum.setText("第" + number + "期");
                                mTvNextLotteryNum.setText("距第" + (number + 1) + "期开奖");
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                            String[] split = countDownData.data.split("\\,|\\+");
                            for (int i = 0; i < mTvDataIds.length; i++) {
                                if (isFinishing()) {
                                    return;
                                }
                                TextView mTvData = findViewById(mTvDataIds[i]);
                                if (i < split.length) {
                                    mTvData.setText("" + split[i]);
                                    mTvData.setVisibility(View.VISIBLE);
                                } else {
                                    mTvData.setVisibility(View.GONE);
                                }
                            }
                        }

                    } else {
                        mCvCountdownView.setVisibility(View.INVISIBLE);
                        mTvOpenPrize.setVisibility(View.VISIBLE);
                        loadLotteryCountDown();
                    }
                }
            }

            @Override
            public void onFailure(Call<CountDownModel> call, Throwable t) {

            }
        });
    }

    public void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
    }

    /**
     * 启动相机
     */
    public String startCamera() {
        // 指定相机拍摄照片保存地址
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent();
            // 指定开启系统相机的Action
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            File outDir = Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            if (!outDir.exists()) {
                outDir.mkdirs();
            }
            mCameraOutFile = new File(outDir, System.currentTimeMillis() + ".jpg");
            // 把文件地址转换成Uri格式
            Uri uri = Uri.fromFile(mCameraOutFile);
            // 设置系统相机拍摄照片完成后图片文件的存放地址
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            // 此值在最低质量最小文件尺寸时是0，在最高质量最大文件尺寸时是１
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
            return mCameraOutFile.getAbsolutePath();
        } else {
            Toast.makeText(ChattingUI.this, "请确认已经插入SD卡",
                    Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //退出后台时又有新消息接收，则切回时消息栏自动滑到底部
        if (mMsgList.size() > mAdapter.getItemCount()) {
            mRecyclerView.smoothScrollToPosition(mMsgList.size());
        }
    }

    private void setListener() {
        mSendBtn.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int
                    oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (mAdapter.getItemCount() > 0) {
                    if (isCanScrollToBottom || bottom != oldBottom) {
                        mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount());
                    }
                }
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                Log.e("ChattingUI", "" + newState + " smoothScrollToPosition " + isSlideToBottom
//                        (mRecyclerView));
                if (isSlideToBottom(mRecyclerView)) {
                    if (JacenDialogUtils.isShowing()) {
                        JacenDialogUtils.dismissDialog();
                        mRecyclerView.setVisibility(View.VISIBLE);
                    }
                }

                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    if (mChatMoreContainer.isShown()) {
                        mChatMoreContainer.setVisibility(View.GONE);
                    }
                }
            }
        });
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //触摸时不允许消息滚动到底部
                isCanScrollToBottom = false;
                return false;
            }
        });

        mBtnMoreItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mChatMoreContainer.isShown()) {
                    mChatMoreContainer.setVisibility(View.GONE);
                } else {
                    mChatMoreContainer.setVisibility(View.VISIBLE);
                }
            }
        });
        mCvCountdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                mCvCountdownView.restart();
                loadLotteryCountDown();
            }
        });
        mAddCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCamera();
            }
        });
        mAddPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAlbum();
            }
        });
        mConetnt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    mSendBtn.setEnabled(false);
                    mSendBtn.setVisibility(View.INVISIBLE);
                    mBtnMoreItem.setVisibility(View.VISIBLE);
                } else {
                    mSendBtn.setEnabled(true);
                    mSendBtn.setVisibility(View.VISIBLE);
                    mBtnMoreItem.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void initData() {
        chatRoomName = getIntent().getStringExtra("chatRoomName");
        chatRoomId = getIntent().getStringExtra("chatRoomId");
        chatRoomJid = chatRoomId + "@conference." + XmppUtils.SERVICENAME;
        mTitle.setText(chatRoomName);
        mUsername = AppDataManager.getInstance().getUser().getUsername();
        mAdapter = new ChatAdapter(this, mMsgList, this);
        mAdapter.setRecyclerView(mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send://发送信息
                //允许消息滚动到底部
                isCanScrollToBottom = true;
                String content = mConetnt.getText().toString().trim();
                XmppUtils.getInstance().sendChatRoomMessage(chatRoomId, content);
                mConetnt.setText("");
                break;
            case R.id.app_back:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        leaveChatroom();
        EventBus.getDefault().unregister(this);
    }

    private void leaveChatroom() {
        if (mMultiUserChat != null) {
            try {
                mMultiUserChat.leave();
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onXmppGrounpChatMessageEvent(XmppGrounpChatMessageEvent event) {
        if (event != null && event.getChatMessageVo() != null) {
            ChatMessageVo vo = event.getChatMessageVo();
            if (vo.getContent() == null) {
                List<ChatMessageVo> msgList = ChatMessageDataBase.getInstance()
                        .getChatMessageListByChatJid(chatRoomJid);
                if (msgList.size() > 0) {
                    mMsgList.clear();
                    mMsgList.addAll(msgList);
                    mAdapter.notifyDataSetChanged();
                } else {
                    if (JacenDialogUtils.isShowing()) {
                        JacenDialogUtils.dismissDialog();
                        mRecyclerView.setVisibility(View.VISIBLE);
                    }
                }
            }

            if (vo.isDelay() == 0) {
                mAdapter.addChatMessage(vo);
            }
        }
    }

}
