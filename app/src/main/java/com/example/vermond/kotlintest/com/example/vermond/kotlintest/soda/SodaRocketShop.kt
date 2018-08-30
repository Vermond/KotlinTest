package com.example.vermond.kotlintest.com.example.vermond.kotlintest.soda

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.vermond.kotlintest.R
import kotlinx.android.synthetic.main.abc_activity_chooser_view.view.*

class SodaRocketShop : AppCompatActivity() {

    enum class ItemType {
        Soda,
        Item
    }

    var sodaHave: Int = 1
    var itemHave: Int = 0
    var curIndex: Int = 0
    var currentType = ItemType.Soda
    var maxItem = 3;
    var curPoint = 0f

    lateinit var itemImage: ImageView
    lateinit var itemText: TextView
    lateinit var pointText: TextView
    lateinit var buyBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        //초기화 먼저 하자
        itemImage = findViewById<ImageView>(R.id.shop_image_goods)
        itemText = findViewById<TextView>(R.id.shop_text_desc)
        pointText = findViewById<TextView>(R.id.shop_text_point)
        buyBtn = findViewById<Button>(R.id.shop_button_buy)

        curPoint = getSharedPreferences(Values.PrefName, Context.MODE_PRIVATE).getFloat(Values.PrefMoneyName, 0f)
        sodaHave = getSharedPreferences(Values.PrefName, Context.MODE_PRIVATE).getInt(Values.PrefSodaHave, 1)
        itemHave = getSharedPreferences(Values.PrefName, Context.MODE_PRIVATE).getInt(Values.PrefItemHave, 0)

        pointText.setText(String.format("Point : %.0f", curPoint))
        changeItemInfo()

        findViewById<Button>(R.id.shop_button_back).setOnClickListener { finish() }
        findViewById<Button>(R.id.shop_button_case1).setOnClickListener {
            if (currentType == ItemType.Soda) return@setOnClickListener

            currentType = ItemType.Soda
            maxItem = Values.SodaImageList.size
            curIndex = 0
            changeItemInfo()
        }
        findViewById<Button>(R.id.shop_button_case2).setOnClickListener {
            if (currentType == ItemType.Item) return@setOnClickListener

            currentType = ItemType.Item
            maxItem = Values.ItemIconList.size
            curIndex = 0
            changeItemInfo()
        }
        findViewById<Button>(R.id.shop_button_left).setOnClickListener {
            if (curIndex > 0) curIndex--
            else curIndex = maxItem - 1
            changeItemInfo()
        }
        findViewById<Button>(R.id.shop_button_right).setOnClickListener {
            if (curIndex < maxItem - 1) curIndex++
            else curIndex = 0
            changeItemInfo()
        }
        buyBtn.setOnClickListener {
            tryBuying()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        //save current buying result
        //getSharedPreferences(Values.PrefName, Context.MODE_PRIVATE).edit().putInt(Values.PrefSodaHave, sodaHave).apply()
    }

    fun bitPow2(i: Int): Int {
        if (i == 0) return 1
        else return 2 shl (i - 1)
    }

    fun changeItemInfo() {
        when (currentType) {
            ItemType.Item -> {
                itemImage.setImageResource(Values.ItemIconList[curIndex])
                itemText.setText(Values.ItemDescList[curIndex])

                Log.i("tag", String.format("%d, %d (%d) : %d", itemHave, curIndex, bitPow2(curIndex), itemHave.and(bitPow2(curIndex))))

                if (itemHave.and(bitPow2(curIndex)) != 0) {
                    buyBtn.isEnabled = false
                    buyBtn.setText("보유중")
                } else {
                    buyBtn.isEnabled = true
                    buyBtn.setText(String.format("Buy\n(%d)", Values.ItemPrice[curIndex]))
                }
            }
            ItemType.Soda -> {
                itemImage.setImageResource(Values.SodaImageList[curIndex])
                itemText.setText(Values.SodaDescList[curIndex])

                //Log.i("tag", String.format("%d, %d (%d) : %d", sodaHave, curIndex, bitPow2(curIndex), sodaHave.and(bitPow2(curIndex))))
                if (sodaHave.and(bitPow2(curIndex)) != 0) {
                    buyBtn.isEnabled = false
                    buyBtn.setText("보유중")
                } else {
                    buyBtn.isEnabled = true
                    buyBtn.setText(String.format("Buy\n(%d)", Values.SodaPrice[curIndex]))
                }
            }
        }
    }

    fun tryBuying() {
        when (currentType) {
            ItemType.Item -> {
                if (curPoint >= Values.ItemPrice[curIndex]) {
                    curPoint -= Values.ItemPrice[curIndex]
                    itemHave += bitPow2(curIndex)

                    pointText.setText(String.format("Point : %.0f", curPoint))
                    getSharedPreferences(Values.PrefName, Context.MODE_PRIVATE).edit().putFloat(Values.PrefMoneyName, curPoint).apply()
                    getSharedPreferences(Values.PrefName, Context.MODE_PRIVATE).edit().putInt(Values.PrefItemHave, itemHave).apply()

                    changeItemInfo()
                } else {
                    Toast.makeText(this as Context, "포인트가 모자랍니다", Toast.LENGTH_SHORT).show()
                }
            }
            ItemType.Soda -> {
                if (curPoint >= Values.SodaPrice[curIndex]) {
                    curPoint -= Values.SodaPrice[curIndex]
                    sodaHave += bitPow2(curIndex)

                    pointText.setText(String.format("Point : %.0f", curPoint))
                    getSharedPreferences(Values.PrefName, Context.MODE_PRIVATE).edit().putFloat(Values.PrefMoneyName, curPoint).apply()
                    getSharedPreferences(Values.PrefName, Context.MODE_PRIVATE).edit().putInt(Values.PrefSodaHave, sodaHave).apply()

                    changeItemInfo()
                } else {
                    Toast.makeText(this as Context, "포인트가 모자랍니다", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}