package com.example.nthigplxa1.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nthigplxa1.R
import com.example.nthigplxa1.adapter.ListExamAdapter
import com.example.nthigplxa1.adapter.ListExamItemTouchHelper
import com.example.nthigplxa1.adapter.ListTrafficTypeAdapter
import kotlinx.android.synthetic.main.activity_list_exam.*
import kotlinx.android.synthetic.main.activity_traffic_signs.*

class TrafficSignsActivity : AppCompatActivity(), View.OnClickListener,
    ListTrafficTypeAdapter.ItemListener {

    private var mListTypeAdapter: ListTrafficTypeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_traffic_signs)
        btn_backListTrafficSigns.setOnClickListener(this)
        initTypeTrafficRecycleView()
    }

    private fun initTypeTrafficRecycleView() {
        mListTypeAdapter = ListTrafficTypeAdapter(this, this)
        rv_listTrafficType.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_listTrafficType.adapter = mListTypeAdapter
        val arrType = arrayListOf("BIỂN BÁO CẤM", "BIỂN HIỆU LỆNH", "BIỂN CHỈ DẪN", "BIỂN BÁO NGUY HIỂM VÀ CẢNH CÁO", "BIỂN PHỤ")
        mListTypeAdapter?.setList(arrType)
    }

    override fun onClick(v: View?) {
        when(v) {
            btn_backListTrafficSigns -> {
                this.finish()
            }
        }
    }

    override fun onItemClick(position: Int) {

    }
}