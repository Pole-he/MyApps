package com.nathan.myapps.activity.ablum ;

import java.util.ArrayList ;
import java.util.List ;

import com.nathan.myapps.R ;
import com.nathan.myapps.adapter.ViewPagerAblumAdapter ;
import com.nathan.myapps.bean.ablum.PicItem ;

import android.app.Activity ;
import android.os.Bundle ;
import android.support.v4.view.ViewPager ;
import android.support.v7.app.ActionBar ;
import android.support.v7.app.ActionBarActivity ;
import android.view.Window ;

public class AblumDetailActivity extends ActionBarActivity {
        
        private ViewPager vp ;
        
        @ Override
        protected void onCreate ( Bundle savedInstanceState ) {
                // TODO Auto-generated method stub
                super.onCreate ( savedInstanceState ) ;
                requestWindowFeature ( Window.FEATURE_NO_TITLE );
               // getSupportActionBar ( ).setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP);
                
                List < PicItem > picList = ( List < PicItem > ) getIntent ( ).getSerializableExtra( "data" ) ;
                int intoPostion = getIntent ( ).getIntExtra ( "intoPosition" , 0 ) ;
                
                setContentView ( R.layout.ablum_detail_view ) ;
                vp = ( ViewPager ) findViewById ( R.id.pager ) ;
                
                ViewPagerAblumAdapter adapter = new ViewPagerAblumAdapter ( this , picList ) ;
                vp.setAdapter ( adapter ) ;
                vp.setCurrentItem ( intoPostion ) ;
                
        }
        
}
