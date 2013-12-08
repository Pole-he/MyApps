package com.nathan.myapps.activity.ablum ;

import java.util.ArrayList ;
import java.util.List ;

import com.android.volley.Response ;
import com.android.volley.VolleyError ;
import com.android.volley.Response.Listener ;
import com.handmark.pulltorefresh.library.PullToRefreshBase ;
import com.handmark.pulltorefresh.library.PullToRefreshStaggeredGridView ;
import com.nathan.myapps.R ;
import com.nathan.myapps.adapter.WaterFallAdapter ;
import com.nathan.myapps.adapter.WaterFallAdapter ;
import com.nathan.myapps.bean.ablum.PicItem ;
import com.nathan.myapps.bean.ablum.PicListJson ;

import com.nathan.myapps.request.HttpVolleyRequest ;

import com.nathan.myapps.utils.DataHandler ;
import com.nathan.myapps.utils.Logger ;

import com.nathan.myapps.widget.LoadingView ;
import com.nathan.myapps.widget.gridview.StaggeredGridView ;

import android.graphics.Typeface ;
import android.os.Bundle ;
import android.support.v7.app.ActionBarActivity ;
import android.view.MenuItem ;
import android.view.View ;
import android.view.animation.Animation ;
import android.view.animation.AnimationUtils ;
import android.widget.Button ;
import android.widget.FrameLayout ;
import android.widget.ListView ;
import android.widget.TextView ;

public class AblumListActivity extends ActionBarActivity {
        
        private List < PicItem > mPicList = new ArrayList < PicItem > ( ) ;
        private WaterFallAdapter adapter ;
        private int mCurrentPage = 0 ;
        private StaggeredGridView gridView ;
        
        private PullToRefreshStaggeredGridView ptrstgv ;
        
        private LoadingView mLoadingView ;
        
        @ Override
        protected void onCreate ( Bundle savedInstanceState ) {
                // TODO Auto-generated method stub
                super.onCreate ( savedInstanceState ) ;
                setContentView ( R.layout.ablum_list ) ;
                getSupportActionBar ( ).setDisplayHomeAsUpEnabled ( true ) ;
                getSupportActionBar ( ).setDisplayShowTitleEnabled ( false ) ;
                getSupportActionBar ( ).setBackgroundDrawable (
                                getResources ( ).getDrawable ( R.drawable.bbuton_success ) ) ;
                findViewById ( ) ;
                init ( ) ;
                setListerner ( ) ;
                showLoadingView ( );
                getData ( 30 , mCurrentPage ) ;
        }
        
        private void showLoadingView ( ) {
                if ( mLoadingView == null ) {
                        mLoadingView = new LoadingView ( this , 1) ;
                }
                if ( ! mLoadingView.isShowing ( ) ) {
                        mLoadingView.show ( ) ;
                }
        }
        
        private void dimissLoadingView()
        {
                if(mLoadingView.isShowing ( ) )
                {
                        mLoadingView.dismiss();
                }
        }
        
        private void getData ( int tag , int start ) {
                HttpVolleyRequest < PicListJson > request = new HttpVolleyRequest < PicListJson > ( this ) ;
                request.HttpVolleyRequestGet ( DataHandler.instance ( ).getAblum ( tag , start ) , PicListJson.class ,
                                PicItem.class , createMyReqSuccessListener ( ) , createMyReqErrorListener ( ) ) ;
        }
        
        private void setListerner ( ) {
                
        }
        
        private void init ( ) {
                
                adapter = new WaterFallAdapter ( this , mPicList ) ;
                
                ptrstgv.setAdapter ( adapter ) ;
                ptrstgv.setMode ( PullToRefreshBase.Mode.PULL_FROM_START ) ;
                ptrstgv.getRefreshableView ( ).setHeaderView ( new Button ( this ) ) ;
                ptrstgv.setOnRefreshListener ( new PullToRefreshBase.OnRefreshListener < StaggeredGridView > ( ) {
                        
                        @ Override
                        public void onRefresh ( PullToRefreshBase < StaggeredGridView > refreshView ) {
                                mCurrentPage = 0 ;
                                getData ( 30 , mCurrentPage ) ;
                        }
                } ) ;
                
                ptrstgv.setOnLoadmoreListener ( new StaggeredGridView.OnLoadmoreListener ( ) {
                        
                        @ Override
                        public void onLoadmore ( ) {
                                mCurrentPage = mCurrentPage + 20 ;
                                getData ( 30 , mCurrentPage ) ;
                                showLoadingView ( );
                        }
                } ) ;
        }
        
        private void findViewById ( ) {
                ptrstgv = ( PullToRefreshStaggeredGridView ) findViewById ( R.id.ptrstgv ) ;
        }
        
        @ SuppressWarnings ( "rawtypes" )
        private Listener < PicListJson > createMyReqSuccessListener ( ) {
                return new Listener < PicListJson > ( ) {
                        
                        @ SuppressWarnings ( "unchecked" )
                        @ Override
                        public void onResponse ( PicListJson response ) {
                                dimissLoadingView ( );
                                if ( mCurrentPage == 0 ) {
                                        mPicList.clear ( ) ;
                                        ptrstgv.onRefreshComplete ( ) ;
                                }
                                mPicList.addAll ( ( List < PicItem > ) response.data.pictures ) ;
                                adapter.notifyDataSetChanged ( ) ;
                        }
                } ;
        }
        
        private Response.ErrorListener createMyReqErrorListener ( ) {
                return new Response.ErrorListener ( ) {
                        
                        @ Override
                        public void onErrorResponse ( VolleyError error ) {
                                ptrstgv.onRefreshComplete ( ) ;
                                dimissLoadingView ( );
                        }
                } ;
        }
        
        public boolean onOptionsItemSelected ( MenuItem paramMenuItem ) {
                
                switch ( paramMenuItem.getItemId ( ) ) {
                case android.R.id.home :
                        finish ( ) ;
                        break ;
                }
                return super.onOptionsItemSelected ( paramMenuItem ) ;
                
        }
}
