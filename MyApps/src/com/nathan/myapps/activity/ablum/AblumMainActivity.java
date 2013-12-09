package com.nathan.myapps.activity.ablum;

import com.nathan.myapps.R;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

public class AblumMainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ablum_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(
                getResources().getDrawable(R.drawable.bbuton_success));

        findViewById();
        init();

    }

    private void init() {
        FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
        t.replace(R.id.content, new AblumListFragment(), "content");
        t.commit();
    }

    private void findViewById() {
        // TODO Auto-generated method stub

    }

    public boolean onOptionsItemSelected(MenuItem paramMenuItem) {

        switch (paramMenuItem.getItemId()) {
        case android.R.id.home:
            finish();
            break;
        }
        return super.onOptionsItemSelected(paramMenuItem);

    }
}
