package com.nathan.myapps.adapter ;

import java.util.ArrayList ;
import java.util.List ;

import uk.co.senab.photoview.PhotoView ;

import com.nathan.myapps.MyApplication ;
import com.nathan.myapps.R ;
import com.nathan.myapps.bean.ablum.PicItem ;
import com.nathan.myapps.widget.WaterFallNetworkImageView ;

import android.content.Context ;
import android.os.Parcelable ;
import android.support.v4.view.PagerAdapter ;
import android.support.v4.view.ViewPager ;
import android.view.LayoutInflater ;
import android.view.View ;
import android.view.ViewGroup ;
import android.widget.TextView ;

public class ViewPagerAblumAdapter extends PagerAdapter {
        
        private List < PicItem > list ;
        private LayoutInflater inflater ;
        
        public ViewPagerAblumAdapter ( Context context , List < PicItem > list ) {
                this.list = list ;
                inflater = LayoutInflater.from ( context ) ;
        }
        
        @ Override
        public void destroyItem ( ViewGroup container , int position , Object object ) {
                ( ( ViewPager ) container ).removeView ( ( View ) object ) ;
        }
        
        @ Override
        public void finishUpdate ( View container ) {
        }
        
        @ Override
        public int getCount ( ) {
                return list != null ? list.size ( ) : 0 ;
        }
        
        @ Override
        public Object instantiateItem ( ViewGroup view , int position ) {
                final PicItem pic = list.get ( position ) ;
                
                // 自定义的view
                View userLayout = inflater.inflate ( R.layout.ablum_detail_item , view , false ) ;
                PhotoView wtImageView = ( PhotoView ) userLayout
                                .findViewById ( R.id.imageView1 ) ;
                
                wtImageView.setImageUrl ( pic.picture_big_url , MyApplication.getInstance ( ).mImageLoader ) ;
                
                wtImageView.setTag ( pic ) ;
                
                ( ( ViewPager ) view ).addView ( userLayout , 0 ) ;
                return userLayout ;
        }
        
        @ Override
        public boolean isViewFromObject ( View view , Object object ) {
                return view.equals ( object ) ;
        }
        
        @ Override
        public void restoreState ( Parcelable state , ClassLoader loader ) {
        }
        
        @ Override
        public Parcelable saveState ( ) {
                return null ;
        }
        
        @ Override
        public void startUpdate ( View container ) {
        }
}
