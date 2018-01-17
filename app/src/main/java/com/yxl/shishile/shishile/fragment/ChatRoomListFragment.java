package com.yxl.shishile.shishile.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yxl.shishile.shishile.R;
import com.yxl.shishile.shishile.adapter.ChatRoomListAdapter;
import com.yxl.shishile.shishile.model.ChatRoomModel;
import com.yxl.shishile.shishile.widgets.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ChatRoomListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatRoomListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView mRecyclerView;
    private ChatRoomListAdapter mAdapter;
    public List<ChatRoomModel> mChatRoomList = new ArrayList<>();
    public String[] mNames = new String[]{"重庆时时彩", "新疆时时彩", "湖北快3", "江苏快3", "福彩3D",
            "排列3", "江西11选5", "广东11选5", "山东11选5"};
    public String[] mChatRoomIds = new String[]{"chongqing", "xinjiang",
            "hubei", "jiangsu", "fucai",
            "pailie", "jiangxi", "guangdong", "shandong"};
    public int[] mLotteryIds = new int[]{1, 7,  2, 8, 5, 6, 9, 4, 11};

    public ChatRoomListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatRoomListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatRoomListFragment newInstance(String param1, String param2) {
        ChatRoomListFragment fragment = new ChatRoomListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mChatRoomList.clear();
        for (int i = 0; i < mNames.length; i++) {
            ChatRoomModel chatRoomModel = new ChatRoomModel();
            chatRoomModel.chatRoomName = mNames[i];
            chatRoomModel.chatId = mChatRoomIds[i];
            chatRoomModel.lotteryId = mLotteryIds[i];
            mChatRoomList.add(chatRoomModel);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_room, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        //创建默认的线性LayoutManager
        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(),2);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        mRecyclerView.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.addItemDecoration(new RecycleViewDivider(getContext(), GridLayoutManager
//                .HORIZONTAL));
        mAdapter = new ChatRoomListAdapter(mChatRoomList);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
