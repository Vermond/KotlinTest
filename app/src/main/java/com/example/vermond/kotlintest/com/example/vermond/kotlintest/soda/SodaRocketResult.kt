package com.example.vermond.kotlintest.com.example.vermond.kotlintest.soda

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.example.vermond.kotlintest.R
import android.util.Log
import android.widget.TextView

class SodaRocketResult : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // get score
        var score:Double = intent.extras.getDouble("score")
        findViewById<TextView>(R.id.result_text_score).setText(String.format("%.2f", score))

        findViewById<Button>(R.id.result_button_rank).setOnClickListener {
            var intent: Intent = Intent(this as Context, SodaRocketRank::class.java)
            intent.putExtra("score", score)
            startActivityForResult(intent, Values.ACTIVITY_CHILD)
        }

        findViewById<Button>(R.id.result_button_return).setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Values.ACTIVITY_CHILD)
        {
            if (resultCode == Activity.RESULT_OK) {
                setResult(Activity.RESULT_OK)
                this.finish()
            }
        }
    }
}