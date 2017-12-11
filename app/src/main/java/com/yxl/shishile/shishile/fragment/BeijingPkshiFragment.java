package com.yxl.shishile.shishile.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yxl.shishile.shishile.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BeijingPkshiFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BeijingPkshiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BeijingPkshiFragment extends Fragment implements View.OnClickListener {

    private ImageView ball1;
    private ImageView ball2;
    private ImageView ball3;
    private ImageView ball4;
    private ImageView ball5;
    private ImageView ball6;
    private ImageView ball7;
    private ImageView ball8;
    private ImageView ball9;
    private ImageView ball10;
    private int a = 0;
    private int b = 0;
    private int c = 0;
    private int d = 0;
    private int e = 0;
    private int f = 0;
    private int g = 0;
    private int h = 0;
    private int i = 0;
    private int j = 0;

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
        ball1 = view.findViewById(R.id.ball1);
        ball2 = view.findViewById(R.id.ball2);
        ball3 = view.findViewById(R.id.ball3);
        ball4 = view.findViewById(R.id.ball4);
        ball5 = view.findViewById(R.id.ball5);
        ball6 = view.findViewById(R.id.ball6);
        ball7 = view.findViewById(R.id.ball7);
        ball8 = view.findViewById(R.id.ball8);
        ball9 = view.findViewById(R.id.ball9);
        ball10 = view.findViewById(R.id.ball10);
        ball1.setOnClickListener(this);
        ball2.setOnClickListener(this);
        ball3.setOnClickListener(this);
        ball4.setOnClickListener(this);
        ball5.setOnClickListener(this);
        ball6.setOnClickListener(this);
        ball7.setOnClickListener(this);
        ball8.setOnClickListener(this);
        ball9.setOnClickListener(this);
        ball10.setOnClickListener(this);

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
            case R.id.ball1:
                if (a == 0) {
                    ball1.setImageResource(R.mipmap.ball01);
                    a = 1;
                } else if (a == 1) {
                    ball1.setImageResource(R.mipmap.ball1);
                    a = 0;
                }
            case R.id.ball2:
                if (b == 0) {
                    ball2.setImageResource(R.mipmap.ball02);
                    b = 1;
                } else if (b == 1) {
                    ball2.setImageResource(R.mipmap.ball2);
                    b = 0;
                }
            case R.id.ball3:
                if (c == 0) {
                    ball3.setImageResource(R.mipmap.ball03);
                    c = 1;
                } else if (c == 1) {
                    ball3.setImageResource(R.mipmap.ball3);
                    c = 0;
                }
            case R.id.ball4:
                if (d == 0) {
                    ball4.setImageResource(R.mipmap.ball04);
                    d = 1;
                } else if (d == 1) {
                    ball4.setImageResource(R.mipmap.ball4);
                    d = 0;
                }
            case R.id.ball5:
                if (e == 0) {
                    ball5.setImageResource(R.mipmap.ball05);
                    e = 1;
                } else if (e == 1) {
                    ball5.setImageResource(R.mipmap.ball5);
                    e = 0;
                }
            case R.id.ball6:
                if (f == 0) {
                    ball6.setImageResource(R.mipmap.ball06);
                    f = 1;
                } else if (f == 1) {
                    ball6.setImageResource(R.mipmap.ball6);
                    f = 0;
                }
            case R.id.ball7:
                if (g == 0) {
                    ball7.setImageResource(R.mipmap.ball07);
                    g = 1;
                } else if (g == 1) {
                    ball7.setImageResource(R.mipmap.ball7);
                    g = 0;
                }
            case R.id.ball8:
                if (h == 0) {
                    ball8.setImageResource(R.mipmap.ball08);
                    h = 1;
                } else if (h == 1) {
                    ball8.setImageResource(R.mipmap.ball8);
                    h = 0;
                }
            case R.id.ball9:
                if (i == 0) {
                    ball9.setImageResource(R.mipmap.ball09);
                    i = 1;
                } else if (i == 1) {
                    ball9.setImageResource(R.mipmap.ball9);
                    i = 0;
                }
            case R.id.ball10:
                if (j == 0) {
                    ball10.setImageResource(R.mipmap.ball010);
                    j = 1;
                } else if (j == 1) {
                    ball10.setImageResource(R.mipmap.ball10);
                    j = 0;
                }

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
}
