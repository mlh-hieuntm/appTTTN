package com.example.nthigplxa1.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.nthigplxa1.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    private var anim: Animation? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        anim = AnimationUtils.loadAnimation(applicationContext, R.anim.anim_alpha_out)
        anim?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                finish()
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }
        }
        )

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        anim?.let {
            cl_splash.startAnimation(it)
        }
    }
    override fun finish() {
        super.finish()
        this.startActivity(Intent(this, HomeActivity::class.java))
        this.overridePendingTransition(0, 0)
    }
}