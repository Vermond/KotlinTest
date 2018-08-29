package com.example.vermond.kotlintest.old

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.vermond.kotlintest.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        test()
    }

    fun test(){
        //change text

        var tv: TextView =  findViewById(R.id.main_text)
        tv.setText(R.string.app_name)

        var btn:Button = findViewById(R.id.main_button)
        btn.setOnClickListener {
            val deviceInfo:Intent = Intent(this as Context, DeviceInfoActivity::class.java)
            startActivity(deviceInfo)
        }

        var btn2:Button = findViewById(R.id.main_button2)
        btn2.setOnClickListener {
            val deviceInfo:Intent = Intent(this as Context, SodaRocket::class.java)
            startActivity(deviceInfo)
        }
    }
}
