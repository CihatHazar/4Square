package com.cihathazargmail.a4square;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;                                                            //Gerekli import lar yapiliyor
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_splash);

        final ImageView iv = (ImageView) findViewById(R.id.imageView);                              //Giris ekraninda animasyon efekti olusturmak icin gerekli bazi degerler tanimlaniyor
        final Animation an = AnimationUtils.loadAnimation(getBaseContext(),R.anim.rotate);          //R.anim.rotate.xml dosyasinda bulunan animasyon ayarlari, burada cekiliyor

        iv.startAnimation(an);                                                                      //Animasyon burada baslatiliyor
        an.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                finish();
                Intent i = new Intent(getBaseContext(),MainActivity.class);                         //R.anim.rotate.xml dosyasindaki ayar uzerina 2sn beklenip MainActivity cagrilip baslatiliyor
                startActivity(i);

            }
        });
    }

}