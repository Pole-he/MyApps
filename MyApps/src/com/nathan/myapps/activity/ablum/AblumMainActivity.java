package com.nathan.myapps.activity.ablum;

import com.nathan.myapps.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class AblumMainActivity extends ActionBarActivity implements OnClickListener {

    private TextView tvTitle1, tvTitle2, tvTitle3, tvTitle4, tvTitle5, tvTitle6;
    /**
     * 用于对Fragment进行管理
     */
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ablum_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(
                getResources().getDrawable(R.drawable.actionbar_ablum));
        findViewById();
        init();
        setListener();

    }

    private void setListener() {
        tvTitle1.setOnClickListener(this);
        tvTitle2.setOnClickListener(this);
        tvTitle3.setOnClickListener(this);
        tvTitle4.setOnClickListener(this);
        tvTitle5.setOnClickListener(this);
        tvTitle6.setOnClickListener(this);
    }

    private void init() {
        fragmentManager = this.getSupportFragmentManager();
        tvTitle1.setSelected(true);
        getTitle(0);
    }

    private void findViewById() {
        tvTitle1 = (TextView) findViewById(R.id.tab_title_1);
        tvTitle2 = (TextView) findViewById(R.id.tab_title_2);
        tvTitle3 = (TextView) findViewById(R.id.tab_title_3);
        tvTitle4 = (TextView) findViewById(R.id.tab_title_4);
        tvTitle5 = (TextView) findViewById(R.id.tab_title_5);
        tvTitle6 = (TextView) findViewById(R.id.tab_title_6);
    }

    public boolean onOptionsItemSelected(MenuItem paramMenuItem) {

        switch (paramMenuItem.getItemId()) {
        case android.R.id.home:
            finish();
            break;
        }
        return super.onOptionsItemSelected(paramMenuItem);

    }

    @Override
    public void onClick(View v) {
        initTitle();
        switch (v.getId()) {
        case R.id.tab_title_1:
            v.setSelected(true);
            getTitle(0);
            break;
        case R.id.tab_title_2:
            v.setSelected(true);
            getTitle(1);
            break;
        case R.id.tab_title_3:
            v.setSelected(true);
            getTitle(2);
            break;
        case R.id.tab_title_4:
            v.setSelected(true);
            getTitle(3);
            break;
        case R.id.tab_title_5:
            v.setSelected(true);
            getTitle(4);
            break;
        case R.id.tab_title_6:
            v.setSelected(true);
            getTitle(5);
            break;
        }
    }

    private void initTitle() {
        tvTitle1.setSelected(false);
        tvTitle2.setSelected(false);
        tvTitle3.setSelected(false);
        tvTitle4.setSelected(false);
        tvTitle5.setSelected(false);
        tvTitle6.setSelected(false);
    }

    private void getTitle(int count) {
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        getData(transaction, count);

    }

    private void getData(FragmentTransaction transaction, int count) {

        Fragment fragment = fragmentManager.findFragmentByTag(getResources().getStringArray(
                R.array.pic_type)[count]);

        if (fragment != null) {
            transaction.show(fragment);
        }
        else {
            transaction.add(R.id.content, AblumListFragment.newInstance(getResources()
                    .getStringArray(R.array.pic_type)[count]),
                    getResources().getStringArray(R.array.pic_type)[count]);
        }
        transaction.commit();
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     * 
     * @param transaction
     *            用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {

        String[] picType = getResources().getStringArray(R.array.pic_type);
        for (int i = 0; i < picType.length; i++) {
            Fragment fragment = fragmentManager.findFragmentByTag(picType[i]);
            if (fragment != null) {
                transaction.hide(fragment);
            }
        }
    }
}
