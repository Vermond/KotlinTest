package com.example.vermond.kotlintest.old

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.os.Vibrator
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.vermond.kotlintest.R
import java.util.*

/**
 * Created by Vermond on 2017-11-06.
 */
class SodaRocket : AppCompatActivity(), SensorEventListener {

    lateinit var sensorManager:SensorManager
    lateinit var sensor:Sensor

    var startHandler:Handler = Handler()
    var startRunnable:Runnable = Runnable {
        //랜덤한 방향으로 약간 움직인다
        oriParam = canImage.layoutParams

        var r = Random()
        when(r.nextInt(3)){
            0 -> {
            }
            1 -> {}
            2 -> {}
            else -> {}
        }

        //진동을 울린다
        val vib:Vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vib.vibrate(50)
        //vib.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE)) //api 26 이상만 가능

        //다음 핸들러 등록
        endHandler.postDelayed(endRunnable, 200)
    }
    var endHandler:Handler = Handler()
    var endRunnable:Runnable = Runnable {
        //원래 위치로 캔을 되돌린다.
        canImage.layoutParams = oriParam
    }

    lateinit var canImage:ImageView
    lateinit var oriParam:ViewGroup.LayoutParams

    var prevX:Float = 0.0f
    var prevY:Float = 0.0f
    var prevZ:Float = 0.0f
    val standardSub:Float = 20f //흔듬 감지를 위한 최소값

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.soda_main)

        canImage = findViewById(R.id.soda_can_image)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)

    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this, sensor)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME) //SENSOR_DELAY_NORMAL)
    }

    //get sensor value and print
    fun temp(str:String)
    {
        var tv:TextView = findViewById<TextView>(R.id.soda_main_power_text)
        tv.setText(str)
    }

    public override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        println("onAccuracyChanged")
    }

    public override fun onSensorChanged(event: SensorEvent?) {
        //Log.v("test", "[ 한글 테스트 ]")

        if (event != null) {
            if (event.sensor.type == Sensor.TYPE_LINEAR_ACCELERATION) {

                //버그인지 -1을 해줘야 제대로 된 인덱스를 가져옴
                //안하면 인덱스 벗어나버리는 오류 발생함
                val x:Float = event.values[SensorManager.AXIS_X-1]
                val y:Float = event.values[SensorManager.AXIS_Y-1]
                val z:Float = event.values[SensorManager.AXIS_Z-1]
                val power:Double = Math.sqrt((x*x + y*y + z*z).toDouble())

                val sub:Float = Math.abs(Math.abs(x-prevX) + Math.abs(y-prevY) + Math.abs(z-prevZ))

                //save previous value
                prevX = x
                prevY = y
                prevZ = z

              //  var str:String = String.format("t:%.2f, sub:%.2f", power, sub)
                //temp(str)
                //println(str)

                if (power > standardSub) {
                    //흔들때 진동 추가
                    startHandler.post(startRunnable)
                    var str:String = String.format("t:%.2f, sub:%.2f", power, sub)
                    println(str)
                }
            }
        }
    }
}