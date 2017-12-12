package com.yxl.shishile.shishile.fragment;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yxl.shishile.shishile.R;
import com.yxl.shishile.shishile.widgets.KeyRadioGroupV2;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BeijingPkshiFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BeijingPkshiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BeijingPkshiFragment extends Fragment implements View.OnClickListener {

    private KeyRadioGroupV2 mKeyRadioGroupV2;
    private PopupWindow mpopupWindow;
    private LinearLayout linner;
    private LinearLayout champion_liner;
    private LinearLayout second_liner;
    private LinearLayout third_liner;
    private LinearLayout fourth_liner;
    private LinearLayout fifth_liner;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public BeijingPkshiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BeijingPkshiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BeijingPkshiFragment newInstance(String param1, String param2) {
        BeijingPkshiFragment fragment = new BeijingPkshiFragment();
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
    }

    private void initView(View view) {
        champion_liner = view.findViewById(R.id.champion_liner);
        second_liner = view.findViewById(R.id.second_liner);
        third_liner = view.findViewById(R.id.third_liner);
        fourth_liner = view.findViewById(R.id.fourth_liner);
        fifth_liner = view.findViewById(R.id.fifth_liner);
        linner = view.findViewById(R.id.title_liner);
        linner.setOnClickListener(this);
        mKeyRadioGroupV2 = (KeyRadioGroupV2) view.findViewById(R.id.krg_main_2);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_beijing_pkshi, container, false);
        initView(view);
        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_liner:
                showPopupWindow();
                break;
            case R.id.champion:
                mpopupWindow.dismiss();
                champion_liner.setVisibility(View.VISIBLE);
                second_liner.setVisibility(View.INVISIBLE);
                third_liner.setVisibility(View.INVISIBLE);
                fourth_liner.setVisibility(View.INVISIBLE);
                fifth_liner.setVisibility(View.INVISIBLE);
                break;
            case R.id.second_place:
                mpopupWindow.dismiss();
                champion_liner.setVisibility(View.VISIBLE);
                second_liner.setVisibility(View.VISIBLE);
                third_liner.setVisibility(View.INVISIBLE);
                fourth_liner.setVisibility(View.INVISIBLE);
                fifth_liner.setVisibility(View.INVISIBLE);
                break;
            case R.id.third_place:
                mpopupWindow.dismiss();
                champion_liner.setVisibility(View.VISIBLE);
                second_liner.setVisibility(View.VISIBLE);
                third_liner.setVisibility(View.VISIBLE);
                fourth_liner.setVisibility(View.INVISIBLE);
                fifth_liner.setVisibility(View.INVISIBLE);
                break;
            case R.id.fourth:
                mpopupWindow.dismiss();
                champion_liner.setVisibility(View.VISIBLE);
                second_liner.setVisibility(View.VISIBLE);
                third_liner.setVisibility(View.VISIBLE);
                fourth_liner.setVisibility(View.VISIBLE);
                fifth_liner.setVisibility(View.INVISIBLE);
                break;
            case R.id.fifth:
                mpopupWindow.dismiss();
                champion_liner.setVisibility(View.VISIBLE);
                second_liner.setVisibility(View.VISIBLE);
                third_liner.setVisibility(View.VISIBLE);
                fourth_liner.setVisibility(View.VISIBLE);
                fifth_liner.setVisibility(View.VISIBLE);
                break;
        }

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void showPopupWindow() {
        View contentView = getActivity().getLayoutInflater().inflate(R.layout.pop_layout, null);
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

//这里就可自定义在上方和下方了 ，这种方式是为了确定在某个位置，某个控件的左边，右边，上边，下边都可以
        mpopupWindow = new PopupWindow(contentView, 0, 0);
        mpopupWindow.setBackgroundDrawable(new BitmapDrawable());//注意这里如果不设置，下面的setOutsideTouchable(true);允许点击外部消失会失效
        mpopupWindow.setOutsideTouchable(true);   //设置外部点击关闭ppw窗口
        mpopupWindow.setFocusable(true);
// 获得目标控件位置
        mpopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mpopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mpopupWindow.showAsDropDown(linner);
        WindowManager windowManager = getActivity().getWindowManager();
        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        //当弹出Popupwindow时，背景变半透明
        params.alpha = 0.7f;
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getActivity().getWindow().setAttributes(params);
        //设置Popupwindow关闭监听，当Popupwindow关闭，背景恢复1f
        mpopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
                params.alpha = 1f;
                getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                getActivity().getWindow().setAttributes(params);
            }
        });
        mpopupWindow.setAnimationStyle(R.style.AnimationFade);
        mpopupWindow.update();

        TextView champion = contentView.findViewById(R.id.champion);
        TextView second_place = contentView.findViewById(R.id.second_place);
        TextView third_place = contentView.findViewById(R.id.third_place);
        TextView fourth = contentView.findViewById(R.id.fourth);
        TextView fifth = contentView.findViewById(R.id.fifth);
        champion.setOnClickListener(this);
        second_place.setOnClickListener(this);
        third_place.setOnClickListener(this);
        fourth.setOnClickListener(this);
        fifth.setOnClickListener(this);

    }
}
