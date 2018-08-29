package com.example.vermond.kotlintest.com.example.vermond.kotlintest.soda

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.example.vermond.kotlintest.R

class SodaRocketShop : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)


        findViewById<Button>(R.id.shop_button_back).setOnClickListener {
            finish()
        }
    }
}