package com.example.vermond.kotlintest.com.example.vermond.kotlintest.soda

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.widget.TextView
import com.example.vermond.kotlintest.R

class SodaRocketInit : AppCompatActivity() {

    var animHandler = Handler()
    lateinit var animRunnable : Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init)

        //깜빡이는 애니메이션 설정
        var animSet : AnimationSet = AnimationSet(true)
        animSet.setInterpolator(AccelerateInterpolator())

        var anim1 = AlphaAnimation(1f, 0.1f)
        anim1.duration = 1000
        animSet.addAnimation(anim1)

        var anim2 = AlphaAnimation(1f, 10f)
        anim2.duration = 1000
        anim2.startOffset = 1000
        animSet.addAnimation(anim2)

        var alertText : TextView = findViewById(R.id.init_text_alert)
        alertText.animation = animSet

        animRunnable = Runnable {
            //AlphaAnimation
            alertText.startAnimation(animSet)

            animHandler.postDelayed(animRunnable, 2000)
        }

        animHandler.post(animRunnable)
    }


    override fun onBackPressed() {
        //동작하지 않게 한다
    }

    override fun onUserInteraction() {
        finish()
    }
}