package com.example.todolistfragment;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.Timer;
import java.util.TimerTask;

import maes.tech.intentanim.CustomIntent;


public class SplashActivity extends AppCompatActivity {
    private Button start_btn;
    private TextView splash_tv;
    private LinearLayout splash_linearLayout;
    private ImageView splash_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        viewInit();
        setAnimations();
        startPagerActivity();
    }
private void viewInit(){
    splash_tv=findViewById(R.id.splash_tv);
    start_btn=findViewById(R.id.start_btn);
    splash_img=findViewById(R.id.splash_img);
    splash_linearLayout=findViewById(R.id.splash_lin_lay);
}
private void setAnimations(){
    AnimationDrawable animationDrawable=(AnimationDrawable) splash_linearLayout.getBackground();
    animationDrawable.setEnterFadeDuration(10);
    animationDrawable.setExitFadeDuration(4000);
    animationDrawable.start();
    YoYo.with(Techniques.BounceIn).playOn(splash_img);
    YoYo.with(Techniques.RotateInUpRight).playOn(splash_tv);
    YoYo.with(Techniques.RotateInUpRight).playOn(start_btn);
    }
private void startPagerActivity(){
    start_btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            YoYo.with(Techniques.Tada).playOn(splash_tv);
            YoYo.with(Techniques.Tada).playOn(splash_img);
            Timer time = new Timer();
            TimerTask task =new TimerTask() {
                @Override
                public void run() {
                    Intent startIntent=new Intent(SplashActivity.this, PagerActivity.class);
                    startActivity(startIntent);
                    CustomIntent.customType(SplashActivity.this,"fadein-to-fadeout");
                    finish();
                }
            };
            time.schedule(task,900);

        }
    });
}
}
