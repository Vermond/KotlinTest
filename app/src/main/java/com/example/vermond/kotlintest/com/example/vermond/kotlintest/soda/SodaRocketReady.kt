package com.example.vermond.kotlintest.com.example.vermond.kotlintest.soda

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.example.vermond.kotlintest.R

class SodaRocketReady : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ready)

        findViewById<Button>(R.id.ready_button_shop).setOnClickListener {
            val intent:Intent = Intent(this as Context, SodaRocketShop::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.ready_button_start).setOnClickListener {
            var intent:Intent = Intent(this as Context, SodaRocketGame::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.ready_button_rank).setOnClickListener {
            var intent:Intent = Intent(this as Context, SodaRocketRank::class.java)
            startActivity(intent)
        }
    }
}