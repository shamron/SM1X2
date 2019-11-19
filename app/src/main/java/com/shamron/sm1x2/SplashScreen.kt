package com.shamron.sm1x2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.google.android.material.textview.MaterialTextView

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val textView = findViewById<MaterialTextView>(R.id.txt_app_name)

        val splash = AnimationUtils.loadAnimation(this, R.anim.splash)

        textView.startAnimation(splash)


        /*Timer*/
        val timer = object : Thread()
        {
            override fun run() {
                super.run()
                try
                {
                    sleep(2000)
                }catch (e: InterruptedException)
                {
                    e.stackTrace
                }finally {
                    startActivity(Intent(this@SplashScreen, MainActivity::class.java))
                    finish()
                }
            }
        }

        timer.start()
    }
}
