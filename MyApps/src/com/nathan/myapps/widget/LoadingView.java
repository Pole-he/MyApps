package com.nathan.myapps.widget ;

import com.nathan.myapps.R ;

import android.app.Dialog ;
import android.app.ProgressDialog ;
import android.content.Context ;
import android.graphics.Typeface ;
import android.view.LayoutInflater ;
import android.view.View ;
import android.view.animation.Animation ;
import android.view.animation.AnimationUtils ;
import android.widget.FrameLayout ;
import android.widget.TextView ;

public class LoadingView extends Dialog {
        
        private FrameLayout flLoading ;
        private TextView tvLoading ;
        private Context mContext ;
        private int bgStyle[] = {
                        R.drawable.loading_bg1 , R.drawable.loading_bg2 , R.drawable.loading_bg3 ,
                        R.drawable.loading_bg4
        } ;
        
        /**
         * type 1-Ablum 2-Music 3-AT! 4-?
         * 
         * @param context
         * @param type
         */
        @ SuppressWarnings ( "deprecation" )
        public LoadingView ( Context context , int type ) {
                super ( context , R.style.wait_screen ) ;
                mContext = context ;
                LayoutInflater mInflater = ( LayoutInflater ) context
                                .getSystemService ( Context.LAYOUT_INFLATER_SERVICE ) ;
                View view = mInflater.inflate ( R.layout.loading_view , null ) ;
                flLoading = ( FrameLayout ) view.findViewById ( R.id.loading_layout ) ;
                flLoading.setBackgroundDrawable ( context.getResources ( ).getDrawable (  bgStyle [ type-1]  ) )  ;
                tvLoading = ( TextView ) view.findViewById ( R.id.loading ) ;
                Typeface typeFace = Typeface.createFromAsset ( context.getAssets ( ) , "fonts/BelshawDonutRobot.ttf" ) ;
                // 应用字体
                tvLoading.setTypeface ( typeFace ) ;
                setCanceledOnTouchOutside(false);
                getWindow().setWindowAnimations(R.style.dialogWindowAnim);  
                setContentView ( view ) ;
        }
        
        @ Override
        public void show ( ) {
                // TODO Auto-generated method stub
                Animation loading = AnimationUtils.loadAnimation ( mContext , R.anim.rotate_loading ) ;
                tvLoading.startAnimation ( loading ) ;
                super.show ( ) ;
        }
        
        @ Override
        public void dismiss ( ) {
                tvLoading.clearAnimation ( );
                super.dismiss ( ) ;
        }

}
