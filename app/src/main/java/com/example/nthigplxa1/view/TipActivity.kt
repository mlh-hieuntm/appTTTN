package com.example.nthigplxa1.view

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import com.example.nthigplxa1.R
import kotlinx.android.synthetic.main.activity_tip.*

class TipActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tip)
        btn_backTip.setOnClickListener(this)
        tvVideo.setOnClickListener(this)
        tvThongTinCacMeo.setOnClickListener(this)
        tvhuongdanchitiet.setOnClickListener(this)
        tvthithu.setOnClickListener(this)
        initVideo()
        startState()
    }

    private fun initVideo() {
        vv_1.setVideoPath("android.resource://" + packageName + "/" + R.raw.video)
        val mediaController = MediaController(this)
        mediaController.setAnchorView(vv_1)
        vv_1.setMediaController(mediaController)

        vv_2.setVideoPath("android.resource://" + packageName + "/" + R.raw.video2)
        val mediaController2 = MediaController(this)
        mediaController2.setAnchorView(vv_2)
        vv_2.setMediaController(mediaController2)
    }

    private fun startState() {
        selectVideo.visibility = View.GONE
        vv_1.visibility = View.GONE
        vv_2.visibility = View.GONE
        sv_text.visibility = View.VISIBLE
        tvThongTinCacMeo.setBackgroundResource(R.drawable.background_custom_four_corner_gray)
        tvVideo.setBackgroundColor(Color.parseColor("#F2F2F2"))
    }

    override fun onClick(v: View?) {
        when (v) {
            btn_backTip -> {
                this.finish()
            }
            tvVideo -> {
                selectVideo.visibility = View.VISIBLE
                sv_text.visibility = View.GONE
                vv_1.visibility = View.VISIBLE
                tvVideo.setBackgroundResource(R.drawable.background_custom_four_corner_gray)
                tvThongTinCacMeo.setBackgroundColor(Color.parseColor("#F2F2F2"))
            }
            tvThongTinCacMeo -> {
                startState()
            }
            tvhuongdanchitiet -> {
                vv_1.visibility = View.VISIBLE
                vv_2.visibility = View.GONE
                tvhuongdanchitiet.setBackgroundResource(R.drawable.background_custom_four_corner_gray)
                tvthithu.setBackgroundColor(Color.parseColor("#F2F2F2"))
            }
            tvthithu -> {
                vv_1.visibility = View.GONE
                vv_2.visibility = View.VISIBLE
                tvthithu.setBackgroundResource(R.drawable.background_custom_four_corner_gray)
                tvhuongdanchitiet.setBackgroundColor(Color.parseColor("#F2F2F2"))
            }
        }
    }
}