package com.liu.drawlayoutstudy.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liu.drawlayoutstudy.R;

/**
 * @author lkq
 * @date 创建时间：2017/4/24
 * @description
 */

public class SimpleFragment extends Fragment {
    private static final String KEY="title";
    private TextView tv;

    public static SimpleFragment getInstance(String mText){
        SimpleFragment simpleFragment=new SimpleFragment();
        Bundle bundle=new Bundle();
        bundle.putString(KEY,mText);
        simpleFragment.setArguments(bundle);
        return simpleFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_simple,container,false);
        tv= (TextView) view.findViewById(R.id.tv);
        String mText=getArguments().getString(KEY);
        tv.setText(mText);
        return view;
    }
}
