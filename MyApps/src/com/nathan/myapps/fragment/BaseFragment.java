package com.nathan.myapps.fragment;

import android.support.v4.app.Fragment;
import android.view.View;

public abstract class BaseFragment extends Fragment {

    public abstract void addListeners();
    public abstract void init();
    public abstract void findViews(View paramView);
    
}
