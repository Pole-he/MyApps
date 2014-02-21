package com.nathan.myapps.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.nathan.myapps.R;

public class FragmentController {

    private FragmentActivity mFragmentActivity;
    /**
     * 用于对Fragment进行管理
     */
    private FragmentManager fragmentManager;
    private List<Fragment> listFragment = new ArrayList<Fragment>();
    private Fragment currentFragment;
    private final static String TAG = "fragment_";
    private int resource;

    public FragmentController(FragmentManager fragmentManager , int resource) {
        this.resource = resource;
        this.fragmentManager = fragmentManager;
    }

    public void add(Class<? extends Fragment> clazz, String tag , Bundle bundle) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (currentFragment != null && currentFragment.isVisible()) {
            transaction.hide(currentFragment);
        }
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
//        transaction.setCustomAnimations(android.R.anim.slide_in_left, 0, 0,
//                android.R.anim.slide_out_right);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
//        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        if (fragment != null) {
            transaction.show(fragment);
        }
        else {
            try {
                fragment = clazz.newInstance();
                transaction.add(resource, fragment, tag);
                if(bundle!=null)
                {
                    fragment.setArguments(bundle);
                }
            }
            catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        currentFragment = fragment;
        transaction.commit();
    }

}
