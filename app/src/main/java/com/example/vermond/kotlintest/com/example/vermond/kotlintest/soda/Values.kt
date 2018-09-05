package com.example.vermond.kotlintest.com.example.vermond.kotlintest.soda

import com.example.vermond.kotlintest.R

class Stat(_time:Long, _power:Float, _point:Float) {
    val time = _time
    val power = _power
    val point = _point
}

//게임용 자료 선언하는 곳
class Values {
    companion object {
        const val ACTIVITY_CHILD = 10

        const val PrefName = "Money"
        const val PrefMoneyName = "Money"
        const val PrefSodaHave = "SodaHave"
        const val PrefItemHave = "ItemHave"

        val SodaNameList = arrayOf("캔", "작은 페트", "큰 페트")
        val SodaImageList = arrayOf(R.drawable.bottle_2_960_720, R.drawable.pet_middle, R.drawable.pet_large)
        val SodaDescList = arrayOf(
                "기본으로 가지고 있는 소다 캔",
                "약간 양이 많아진 중간 사이즈 페트병",
                "여럿이서 먹기 좋은 큰 페트병")
        val SodaPrice = arrayOf(0, 500, 1000)
        val SodaStatList = arrayOf(
                Stat(0, 0f, 1f),
                Stat(0, 10f, 1f),
                Stat(0, 20f, 1f)
        )

        val ItemNameList = arrayOf("시간증가", "파워증가", "포인트증가")
        val ItemIconList = arrayOf(R.drawable.ui_icon_fastforward, R.drawable.ui_icon_invgloves, R.drawable.ui_icon_currencywon)
        val ItemDescList = arrayOf(
                "흔드는 시간이 약간 증가한다",
                "흔드는 파워가 약간 증가한다",
                "얻는 포인트가 약간 증가한다")
        val ItemPrice = arrayOf(750, 750, 1000)
        val ItemStatList = arrayOf(
                Stat(1000, 0f, 1f),
                Stat(0, 10f, 1f),
                Stat(0, 0f, 1.5f)
        )

    }
}