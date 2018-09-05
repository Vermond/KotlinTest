package com.example.vermond.kotlintest.com.example.vermond.kotlintest.soda

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.Image
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.animation.*
import android.widget.ImageView
import android.widget.TextView
import com.example.vermond.kotlintest.R
import java.util.*

class SodaRocketGame : AppCompatActivity(), SensorEventListener {

    lateinit var sensorManager:SensorManager
    lateinit var sensor:Sensor

    val standardSub:Float = 20f //흔듬 감지를 위한 최소값

    var totalScore:Double = 0.0
    var gameTime : Long = 2500

    var timerHandler:Handler = Handler()
    var timerRunnable:Runnable = Runnable {
        //결과 화면으로 점수 및 기타 정보 전달, 화면 변경
        val intent:Intent = Intent(this as Context, SodaRocketResult::class.java)
        intent.putExtra("score", totalScore)
        intent.putExtra("bonusPointRate", bonusPointRate)
        startActivityForResult(intent, Values.ACTIVITY_CHILD)
    }

    lateinit var image : ImageView
    lateinit var animSet : AnimationSet
    var animHandler = Handler()
    lateinit var animRunnable : Runnable

    //흔들기 애니메이션 재생 시간 (ms)
    //작을수록 애니메이션 빨라짐
    var totalAnimationTime : Long = 500
    //흔들기 애니메이션 흔들어짐 정도
    var maxShakePower = 30;
    //흔들기 애니메이션 캔 확대 축소 정도
    var maxScalePower : Float = 1.05f;

    var bonusTime:Long = 0
    var bonusPower:Float = 0f
    var bonusPointRate:Float = 1f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)

        // 초기화
        image = findViewById<ImageView>(R.id.game_imageView_soda)
        animSet = AnimationSet(true)
        animSet.setInterpolator(AccelerateInterpolator())

        //데이터 받아서 계산하기
        bonusTime = intent.extras.getLong("bonusTime")
        bonusPower = intent.extras.getFloat("bonusPower")
        bonusPointRate = intent.extras.getFloat("bonusPointRate")
        gameTime += bonusTime

        animRunnable = Runnable {
            //set image animation
            animSet.animations.clear()

            var rnd = Random()
            var rx = rnd.nextFloat() * maxShakePower - maxShakePower / 2
            var ry = rnd.nextFloat() * maxShakePower - maxShakePower / 2

            var animTime = totalAnimationTime / 2

            var anim: Animation = TranslateAnimation(Animation.ABSOLUTE, -rx, Animation.ABSOLUTE, rx, Animation.ABSOLUTE, -ry, Animation.ABSOLUTE, ry)
            anim.duration = animTime
            animSet.addAnimation(anim)

            var anim2: Animation = TranslateAnimation(Animation.ABSOLUTE, rx, Animation.ABSOLUTE, -rx, Animation.ABSOLUTE, ry, Animation.ABSOLUTE, -ry)
            anim2.duration = animTime
            anim2.startOffset = animTime
            animSet.addAnimation(anim2)

            var scale = 1 / maxScalePower

            var anim3 = ScaleAnimation(1f, maxScalePower, 1f, maxScalePower, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
            anim3.duration = animTime
            animSet.addAnimation(anim3)

            var anim4 = ScaleAnimation(1f, scale, 1f, scale, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
            anim4.duration = animTime
            anim4.startOffset = animTime
            animSet.addAnimation(anim4)

            image.animation = animSet
            image.startAnimation(animSet)
            animHandler.postDelayed(animRunnable, totalAnimationTime)
        }

        timerHandler.postDelayed(timerRunnable, gameTime)
        animHandler.postDelayed(animRunnable, totalAnimationTime)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this, sensor)
        animHandler.removeCallbacks(animRunnable)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME)
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
                val power: Double = Math.sqrt((x * x + y * y + z * z).toDouble()) + bonusPower

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

                    //점수 상승에 따른 애니메이션 조절
                    when {
                        totalScore < 1000 -> {
                            totalAnimationTime = 500
                            maxShakePower = 30
                            maxScalePower = 1.05f
                        }
                        totalScore >= 1000 && totalScore < 2000 -> {
                            totalAnimationTime = 450
                            maxShakePower = 40
                            maxScalePower = 1.05f
                        }
                        totalScore >= 2000 && totalScore < 3000 -> {
                            totalAnimationTime = 400
                            maxShakePower = 50
                            maxScalePower = 1.1f
                        }
                        totalScore >= 3000 && totalScore < 4000 -> {
                            totalAnimationTime = 350
                            maxShakePower = 60
                            maxScalePower = 1.1f
                        }
                        totalScore >= 4000 -> {
                            totalAnimationTime = 300
                            maxShakePower = 70
                            maxScalePower = 1.15f
                        }
                    }
                }
            }
        }
    }
}