package com.example.nthigplxa1.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nthigplxa1.R
import com.example.nthigplxa1.adapter.ListHomeAdapter
import com.example.nthigplxa1.adapter.ListLawDetailAdapter
import com.example.nthigplxa1.adapter.ListTrafficAdapter
import com.example.nthigplxa1.model.Home
import com.example.nthigplxa1.model.TrafficBoard
import kotlinx.android.synthetic.main.activity_detail_law.*
import kotlinx.android.synthetic.main.activity_law.*
import kotlinx.android.synthetic.main.activity_traffic_signs.*
import java.util.ArrayList

class DetailLawActivity : AppCompatActivity(), View.OnClickListener {
    var mListAdapter: ListTrafficAdapter? = null
    private var mArrayListTraffic = ArrayList<TrafficBoard>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_law)
        initRecyclerView()
        btn_backDLaw.setOnClickListener(this)
        val name = intent.getStringExtra("NAME")
        tv_titleOfListDLaw.text = name.toString()
    }
    private fun initRecyclerView() {
        mListAdapter = ListTrafficAdapter(this)
        rvDLaw.layoutManager =
            LinearLayoutManager(this)
        rvDLaw.adapter = mListAdapter
        mArrayListTraffic.add(TrafficBoard(R.drawable.img1, "Đi vào khu vực cấm, đường có biển báo hiệu có nội dung cấm đi vào đối với loại phương tiện đang điều khiển.", "Phạt tiền từ 400.000 đồng đến 600.000 đồng."))
        mArrayListTraffic.add(TrafficBoard(R.drawable.img2, "Đi ngược chiều của đường một chiều, đi ngược chiều trên đường có biển “Cấm đi ngược chiều”", "Phạt tiền từ 1.000.000 đồng đến 2.000.000 đồng."))
        mListAdapter?.setList(mArrayListTraffic)
    }
    override fun onClick(v: View?) {
        when(v) {
            btn_backDLaw -> {
                this.finish()
            }
        }
    }
}