package com.nathan.myapps.fragment.ablum;

import com.nathan.myapps.R;
import com.nathan.myapps.activity.ablum.AblumListFragment;
import com.nathan.myapps.fragment.BaseFragment;
import com.nathan.myapps.fragment.FragmentController;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class AblumFragment extends BaseFragment implements OnClickListener {

    private TextView tvTitle1, tvTitle2, tvTitle3, tvTitle4, tvTitle5, tvTitle6;
    private Context mContext;
    private FragmentController mFragmentController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        return inflater.inflate(R.layout.ablum_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        findViews(view);
        init();
        addListeners();
        super.onViewCreated(view, bundle);
    }

    public void init() {
        tvTitle1.setSelected(true);
        mFragmentController = new FragmentController(getChildFragmentManager(), R.id.content);
        selector(0);
    }

    public void findViews(View view) {
        tvTitle1 = (TextView) view.findViewById(R.id.tab_title_1);
        tvTitle2 = (TextView) view.findViewById(R.id.tab_title_2);
        tvTitle3 = (TextView) view.findViewById(R.id.tab_title_3);
        tvTitle4 = (TextView) view.findViewById(R.id.tab_title_4);
        tvTitle5 = (TextView) view.findViewById(R.id.tab_title_5);
        tvTitle6 = (TextView) view.findViewById(R.id.tab_title_6);
    }

    @Override
    public void addListeners() {
        tvTitle1.setOnClickListener(this);
        tvTitle2.setOnClickListener(this);
        tvTitle3.setOnClickListener(this);
        tvTitle4.setOnClickListener(this);
        tvTitle5.setOnClickListener(this);
        tvTitle6.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        initTitle();
        switch (v.getId()) {
        case R.id.tab_title_1:
            v.setSelected(true);
            selector(0);
            break;
        case R.id.tab_title_2:
            v.setSelected(true);
            selector(1);
            break;
        case R.id.tab_title_3:
            v.setSelected(true);
            selector(2);
            break;
        case R.id.tab_title_4:
            v.setSelected(true);
            selector(3);
            break;
        case R.id.tab_title_5:
            v.setSelected(true);
            selector(4);
            break;
        case R.id.tab_title_6:
            v.setSelected(true);
            selector(5);
            break;
        }
    }

    private void selector(int count) {
        String tag = getResources().getStringArray(R.array.pic_type)[count];
        Bundle bundle = new Bundle();
        bundle.putString("type", tag);
        mFragmentController.add(AblumListFragment.class, tag, bundle);
    }

    private void initTitle() {
        tvTitle1.setSelected(false);
        tvTitle2.setSelected(false);
        tvTitle3.setSelected(false);
        tvTitle4.setSelected(false);
        tvTitle5.setSelected(false);
        tvTitle6.setSelected(false);
    }

}
