package com.example.vermond.kotlintest.old

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.vermond.kotlintest.R

/**
 * Created by Vermond on 2017-11-05.
 */
class DeviceInfoActivity : AppCompatActivity() {

    var titleList:MutableList<Int> = mutableListOf()
    var descList:MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.device_info)

        addItem()
        temp()
    }

    /*
    available device info from Build.xxx
    https://developer.android.com/reference/android/os/Build.html

    version
    board
    bootloader
    brand
    device
    display
    fingerprint
    hardware name
    id
    manufacturer
    model
    product
    abis (3 types)

    radio version
    serial
    */

    fun addItem(){
        titleList.add(R.string.info_version)
        descList.add(Build.VERSION.RELEASE)

        titleList.add(R.string.info_board)
        descList.add(Build.BOARD)

        titleList.add(R.string.info_bootloader)
        descList.add(Build.BOOTLOADER)

        titleList.add(R.string.info_brand)
        descList.add(Build.BRAND)

        titleList.add(R.string.info_device)
        descList.add(Build.DEVICE)

        titleList.add(R.string.info_display)
        descList.add(Build.DISPLAY)

        titleList.add(R.string.info_fingerprint)
        descList.add(Build.FINGERPRINT)

        titleList.add(R.string.info_hardware_name)
        descList.add(Build.HARDWARE)

        titleList.add(R.string.info_id)
        descList.add(Build.ID)

        titleList.add(R.string.info_manufacturer)
        descList.add(Build.MANUFACTURER)

        titleList.add(R.string.info_model)
        descList.add(Build.MODEL)

        titleList.add(R.string.info_product)
        descList.add(Build.PRODUCT)

        //API 부족하고 ABIS에 32/64 비트 둘다 있어서 일단 생략함
        //titleList.add(R.string.info_abis)
        //descList.add(Build.SUPPORTED_ABIS.toString())

        titleList.add(R.string.info_radio_version)
        descList.add(Build.getRadioVersion())

        //권한 필요해서 생략함
        //titleList.add(R.string.info_serial)
        //descList.add(Build.getSerial())
    }

    fun temp(){
        val vi:LayoutInflater = applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var parentLayout:LinearLayout = findViewById<LinearLayout>(R.id.device_info_layout)

        var index = 0

        while (index < titleList.size){
            var layout:ConstraintLayout = vi.inflate(R.layout.device_info_item, null) as ConstraintLayout
            var item1:TextView = layout.findViewById(R.id.device_info_item_title_text)
            var item2:TextView = layout.findViewById(R.id.device_info_item_desc_text)

            item1.setText(titleList[index]);
            item2.setText(descList[index]);
            parentLayout.addView(layout, 0, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))

            ++index
        }
    }

}