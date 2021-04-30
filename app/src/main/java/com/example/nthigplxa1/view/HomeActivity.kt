package com.example.nthigplxa1.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nthigplxa1.BuildConfig
import com.example.nthigplxa1.R
import com.example.nthigplxa1.adapter.ItemHomeListener
import com.example.nthigplxa1.adapter.ListHomeAdapter
import com.example.nthigplxa1.model.Home
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*

class HomeActivity : AppCompatActivity(), ItemHomeListener {
    companion object {
        const val EMAIL_SUPPORT_FEEDBACK = "hieuhaui12@gmail.com"
    }

    private var mHomeAdapter: ListHomeAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val arrayListHome = ArrayList<Home>()
        arrayListHome.add(Home(R.drawable.ic_test, "Danh sách đề thi"))
        arrayListHome.add(Home(R.drawable.ic_tips, "Mẹo thi kết quả cao"))
        arrayListHome.add(Home(R.drawable.ic_traffic_lights, "Danh sách các loại biển báo"))
        arrayListHome.add(Home(R.drawable.ic_error, "Xử lý các lỗi vi phạm"))
        arrayListHome.add(Home(R.drawable.ic_contact, "Liên hệ với chúng tôi"))
        mHomeAdapter = ListHomeAdapter(this, this)
        rv_listHome.layoutManager = LinearLayoutManager(this)
        rv_listHome.adapter = mHomeAdapter
        mHomeAdapter?.setList(arrayListHome)
    }

    override fun onItemClick(position: Int, mItem: Home) {
        when (position) {
            0 -> {
                val intent = Intent(this, ListExamActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            1 -> {

            }
            2 -> {
                val intent = Intent(this, TrafficSignsActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            3 -> {

            }
            4 -> {
                val myVersion: String = Build.VERSION.RELEASE
                val sdkVersion: String = BuildConfig.VERSION_NAME
                val emailIntent = Intent(Intent.ACTION_SEND)
                emailIntent.putExtra(
                    Intent.EXTRA_EMAIL,
                    arrayOf(EMAIL_SUPPORT_FEEDBACK)
                )
                emailIntent.putExtra(
                    Intent.EXTRA_SUBJECT,
                    "Liên hệ Ứng dụng ôn thi bằng lái xe máy - Android"
                )
                emailIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    "[Android: $myVersion, tên thiết bị" +
                            ": ${getDeviceNamePhone()}, phiên bản" +
                            ": $sdkVersion] \n\n\n"
                )
                emailIntent.type = "plain/text"
                startActivity(emailIntent)
            }

        }
    }

    private fun getDeviceNamePhone(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.startsWith(manufacturer)) {
            convertNameDeviceToUpperCase(model)
        } else convertNameDeviceToUpperCase(manufacturer) + " " + model
    }

    private fun convertNameDeviceToUpperCase(str: String): String {
        if (TextUtils.isEmpty(str)) {
            return str
        }
        val arr = str.toCharArray()
        var capitalizeNext = true
        var phrase = ""
        for (c in arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase += Character.toUpperCase(c)
                capitalizeNext = false
                continue
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true
            }
            phrase += c
        }
        return phrase
    }

}