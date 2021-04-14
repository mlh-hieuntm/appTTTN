package com.example.nthigplxa1.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nthigplxa1.R
import com.example.nthigplxa1.adapter.ItemHomeListener
import com.example.nthigplxa1.adapter.ListHomeAdapter
import com.example.nthigplxa1.model.Home
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*

class HomeActivity : AppCompatActivity(), ItemHomeListener {
    private var mHomeAdapter: ListHomeAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val arrayListHome = ArrayList<Home>()
        arrayListHome.add(Home(R.drawable.ic_test, "Danh sách đề thi"))
        arrayListHome.add(Home(R.drawable.ic_traffic_lights, "Mẹo thi kết quả cao"))
        arrayListHome.add(Home(R.drawable.ic_traffic_lights, "Danh sách các loại biển báo"))
        arrayListHome.add(Home(R.drawable.ic_traffic_lights, "Xử lý các lỗi vi phạm"))
        arrayListHome.add(Home(R.drawable.ic_traffic_lights, "Liên hệ với chúng tôi"))
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
            3 -> {

            }
            4 -> {

            }
            5 -> {

            }

        }
    }

}