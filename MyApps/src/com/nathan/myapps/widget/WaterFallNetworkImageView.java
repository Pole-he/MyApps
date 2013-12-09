package com.nathan.myapps.widget ;

import android.content.Context ;
import android.graphics.Bitmap ;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter ;
import android.graphics.drawable.BitmapDrawable ;
import android.graphics.drawable.ColorDrawable ;
import android.graphics.drawable.Drawable ;
import android.graphics.drawable.TransitionDrawable ;
import android.util.AttributeSet ;
import android.view.MotionEvent ;
import android.view.View.MeasureSpec ;

import com.android.volley.toolbox.NetworkImageView ;
import com.nathan.myapps.R ;

public class WaterFallNetworkImageView extends NetworkImageView {
        
        private static final int FADE_IN_TIME_MS = 200 ;
        public int mWidth = 0 ;
        public int mHeight = 0 ;
        private static final float Trans = - 25f ;
        
        private final static float [ ] BT_SELECTED = new float [ ] {
                        1 , 0 , 0 , 0 , Trans , 0 , 1 , 0 , 0 , Trans , 0 , 0 , 1 , 0 , Trans , 0 , 0 , 0 , 1 , 0
        } ;
        
        private final static float [ ] BT_NOT_SELECTED = new float [ ] {
                        1 , 0 , 0 , 0 , 0 , 0 , 1 , 0 , 0 , 0 , 0 , 0 , 1 , 0 , 0 , 0 , 0 , 0 , 1 , 0
        } ;
        
        private ColorMatrixColorFilter mPressFilter ;
        private ColorMatrixColorFilter mNormalFilter ;
        
        public WaterFallNetworkImageView ( Context context ) {
                super ( context ) ;
                init ( ) ;
        }
        
        public WaterFallNetworkImageView ( Context context , AttributeSet attrs ) {
                super ( context , attrs ) ;
                init ( ) ;
        }
        
        public WaterFallNetworkImageView ( Context context , AttributeSet attrs , int defStyle ) {
                super ( context , attrs , defStyle ) ;
                init ( ) ;
        }
        
        private int color[] = {
                        0xff339900 , 0xff339933 , 0xff339966 , 0xff339999 , 0xff33CC00 , 0xff33CC33 , 0xff33CC66 ,
                        0xff33CC99 , 0xff33FF00 , 0xff33FF33 , 0xff33FF66 , 0xff33FF99
        } ;
        
        private void init ( ) {
//                setDefaultImageResId(R.drawable.placeholder_thumb);
//                setErrorImageResId(R.drawable.placeholder_fail);
            setBackgroundColor(getResources().getColor(R.color.white_1));
        }
        
//        @ Override
//        public void setImageBitmap ( Bitmap bm ) {
//                // 透明
//                // TransitionDrawable td = new TransitionDrawable ( new Drawable
//                // [ ] {
//                // new ColorDrawable ( android.R.color.transparent ) ,
//                // new BitmapDrawable ( getContext ( ).getResources ( ) , bm )
//                // } ) ;
//                // TransitionDrawable td = new TransitionDrawable ( new Drawable
//                // [ ] {
//                // new ColorDrawable ( color[(int)(Math.random ( )*11)] ) ,
//                // new BitmapDrawable ( getContext ( ).getResources ( ) , bm )
//                // } ) ;
//                // setImageDrawable ( td ) ;
//                // td.startTransition ( FADE_IN_TIME_MS ) ;
//        }
        
        @ Override
        public boolean onTouchEvent ( MotionEvent event ) {
                switch ( event.getAction ( ) ) {
                case MotionEvent.ACTION_DOWN :
                        if ( getDrawable ( ) != null ) {
                                if ( mPressFilter == null ) {
                                        mPressFilter = new ColorMatrixColorFilter ( BT_SELECTED ) ;
                                }
                                getDrawable ( ).setColorFilter ( mPressFilter ) ;
                        }
                        break ;
                case MotionEvent.ACTION_CANCEL :
                case MotionEvent.ACTION_UP :
                        if ( getDrawable ( ) != null ) {
                                if ( mNormalFilter == null ) {
                                        mNormalFilter = new ColorMatrixColorFilter ( BT_NOT_SELECTED ) ;
                                }
                                getDrawable ( ).setColorFilter ( mNormalFilter ) ;
                        }
                        break ;
                default :
                        break ;
                }
                return super.onTouchEvent ( event ) ;
        }
        
        @ Override
        protected void onMeasure ( int widthMeasureSpec , int heightMeasureSpec ) {
                
                // int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
                // int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
                int width = MeasureSpec.getSize ( widthMeasureSpec ) ;
                int height = MeasureSpec.getSize ( heightMeasureSpec ) ;
                
                int heightC = width * mHeight / mWidth ;
                
                setMeasuredDimension ( width , heightC ) ;
        }
}