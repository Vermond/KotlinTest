package com.example.vermond.kotlintest.com.example.vermond.kotlintest.soda

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import com.example.vermond.kotlintest.R

class SodaRocketGame : AppCompatActivity(), SensorEventListener {

    lateinit var sensorManager:SensorManager
    lateinit var sensor:Sensor

    var prevX:Float = 0.0f
    var prevY:Float = 0.0f
    var prevZ:Float = 0.0f
    val standardSub:Float = 20f //흔듬 감지를 위한 최소값

    var totalScore:Double = 0.0

    var timerHandler:Handler = Handler()
    var timerRunnable:Runnable = Runnable {
        val intent:Intent = Intent(this as Context, SodaRocketResult::class.java)
        intent.putExtra("score", totalScore)
        startActivityForResult(intent, Values.ACTIVITY_CHILD)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)

        timerHandler.postDelayed(timerRunnable, 2500)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this, sensor)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME) //SENSOR_DELAY_NORMAL)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Values.ACTIVITY_CHILD)
        {
            if (resultCode == Activity.RESULT_OK) {
                this.finish()
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        //내용없음
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            if (event.sensor.type == Sensor.TYPE_LINEAR_ACCELERATION) {
                //버그인지 -1을 해줘야 제대로 된 인덱스를 가져옴
                //안하면 인덱스 벗어나버리는 오류 발생함
                val x: Float = event.values[SensorManager.AXIS_X - 1]
                val y: Float = event.values[SensorManager.AXIS_Y - 1]
                val z: Float = event.values[SensorManager.AXIS_Z - 1]
                val power: Double = Math.sqrt((x * x + y * y + z * z).toDouble())

                val sub: Float = Math.abs(Math.abs(x - prevX) + Math.abs(y - prevY) + Math.abs(z - prevZ))

                //save previous value
                prevX = x
                prevY = y
                prevZ = z

                if (power > standardSub) {
                    //흔들때 진동 추가
                    val vib: Vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        //api 26 이상만 가능
                        vib.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
                    } else {
                        vib.vibrate(50)
                    }

                    //점수 추가
                    totalScore += power;

                    findViewById<TextView>(R.id.game_text_score).setText(String.format("%.2f", totalScore))
                }
            }
        }
    }
}