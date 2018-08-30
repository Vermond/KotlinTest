package com.example.vermond.kotlintest.com.example.vermond.kotlintest.soda

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.vermond.kotlintest.R

class SodaRocketReady : AppCompatActivity() {

    var curItemIndex = 0;
    var MaxItemIndex = Values.ItemNameList.size + 1
    var curSodaIndex = 0;
    var MaxSodaIndex = Values.SodaNameList.size

    lateinit var sodaImage : ImageView;
    lateinit var sodaText : TextView;
    lateinit var itemImage : ImageView;
    lateinit var itemText : TextView;
    var sodaHave: Int = 1
    var itemHave: Int = 0
    var itemEnable = false
    var sodaEnable = false

    lateinit var startButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ready)

        val i:Intent = Intent(this as Context, SodaRocketInit::class.java)
        startActivity(i)

        //init
        sodaImage = findViewById(R.id.ready_image_soda)
        sodaText = findViewById(R.id.ready_text_soda)
        itemImage = findViewById(R.id.ready_image_item)
        itemText = findViewById(R.id.ready_text_item)
        sodaHave = getSharedPreferences(Values.PrefName, Context.MODE_PRIVATE).getInt(Values.PrefSodaHave, 1)
        itemHave = getSharedPreferences(Values.PrefName, Context.MODE_PRIVATE).getInt(Values.PrefItemHave, 0)
        startButton = findViewById<Button>(R.id.ready_button_start)

        updateSodaSelect()
        updateItemSelect()

        findViewById<Button>(R.id.ready_button_shop).setOnClickListener {
            val intent:Intent = Intent(this as Context, SodaRocketShop::class.java)
            startActivity(intent)
        }

        startButton.setOnClickListener {
            var intent:Intent = Intent(this as Context, SodaRocketGame::class.java)

            var bonusTime = Values.SodaStatList[curSodaIndex].time
            var bonusPower = Values.SodaStatList[curSodaIndex].power
            var bonusPointRate = Values.SodaStatList[curSodaIndex].point

            if (curItemIndex != 0) {
                bonusTime += Values.ItemStatList[curItemIndex - 1].time
                bonusPower += Values.ItemStatList[curItemIndex - 1].power
                bonusPointRate *= Values.ItemStatList[curItemIndex - 1].point
            }

            intent.putExtra("bonusTime", bonusTime)
            intent.putExtra("bonusPower", bonusPower)
            intent.putExtra("bonusPointRate", bonusPointRate)

            startActivity(intent)
        }

        findViewById<Button>(R.id.ready_button_rank).setOnClickListener {
            var intent:Intent = Intent(this as Context, SodaRocketRank::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.ready_button_soda_left).setOnClickListener{
            if (curSodaIndex > 0) curSodaIndex--
            else curSodaIndex = MaxSodaIndex - 1
            updateSodaSelect()
        }

        findViewById<Button>(R.id.ready_button_soda_right).setOnClickListener{
            if (curSodaIndex < MaxSodaIndex - 1) curSodaIndex++
            else curSodaIndex = 0
            updateSodaSelect()
        }

        findViewById<Button>(R.id.ready_button_sub_left).setOnClickListener{
            if (curItemIndex > 0) curItemIndex--
            else curItemIndex = MaxItemIndex - 1
            updateItemSelect()
        }

        findViewById<Button>(R.id.ready_button_sub_right).setOnClickListener{
            if (curItemIndex < MaxItemIndex - 1) curItemIndex++
            else curItemIndex = 0
            updateItemSelect()
        }
    }

    fun bitPow2(i: Int): Int {
        if (i == 0) return 1
        else return 2 shl (i - 1)
    }

    fun updateSodaSelect() {
        sodaImage.setImageResource(Values.SodaImageList[curSodaIndex])

        if (sodaHave.and(bitPow2(curSodaIndex)) != 0) {
            sodaText.visibility = View.INVISIBLE
            sodaEnable = true
        } else {
            sodaText.visibility = View.VISIBLE
            sodaEnable = false
        }
        updateStartButton()
    }

    fun updateItemSelect() {
        if (curItemIndex == 0) {
            itemImage.setImageDrawable(null)
            itemText.setText("None")
            itemEnable = true
        } else {
            itemImage.setImageResource(Values.ItemIconList[curItemIndex - 1])

            if (itemHave.and(bitPow2(curItemIndex - 1)) != 0) {
                itemText.setText(Values.ItemNameList[curItemIndex - 1])
                itemEnable = true
            } else {
                itemText.setText("사용불가능")
                itemEnable = false
            }
        }
        updateStartButton()
    }

    fun updateStartButton()
    {
        startButton.isEnabled = itemEnable && sodaEnable
    }

}