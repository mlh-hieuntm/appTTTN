package com.example.nthigplxa1.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nthigplxa1.R
import com.example.nthigplxa1.adapter.ListTrafficAdapter
import com.example.nthigplxa1.adapter.ListTrafficTypeAdapter
import com.example.nthigplxa1.model.TrafficBoard
import kotlinx.android.synthetic.main.activity_traffic_signs.*

class TrafficSignsActivity : AppCompatActivity(), View.OnClickListener,
    ListTrafficTypeAdapter.ItemListener {

    private var mListTypeAdapter: ListTrafficTypeAdapter? = null
    private var mListAdapter: ListTrafficAdapter? = null
    private var mArrayListTraffic: ArrayList<TrafficBoard>? = null
    private val arrImg0 = ArrayList<Int>()
    private val arrName0 = ArrayList<String>()
    private val arrContent0 = ArrayList<String>()
    private val arrImg1 = ArrayList<Int>()
    private val arrName1 = ArrayList<String>()
    private val arrContent1 = ArrayList<String>()
    private val arrImg2 = ArrayList<Int>()
    private val arrName2 = ArrayList<String>()
    private val arrContent2 = ArrayList<String>()
    private val arrImg3 = ArrayList<Int>()
    private val arrName3 = ArrayList<String>()
    private val arrContent3 = ArrayList<String>()
    private val arrImg4 = ArrayList<Int>()
    private val arrName4 = ArrayList<String>()
    private val arrContent4 = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_traffic_signs)
        btn_backListTrafficSigns.setOnClickListener(this)
        mArrayListTraffic = ArrayList()
        initData0()
        initTypeTrafficRecycleView()
        initTrafficRecycleView()
    }

    private fun initData0() {
        arrImg0.clear()
        arrName0.clear()
        arrContent0.clear()
        for (i in 1..10) {
            if (i < 10) {
                if (i == 3 || i == 6) {
                    arrImg0.add(getIdentifierDrawable("img_p10${i}a"))
                    arrImg0.add(getIdentifierDrawable("img_p10${i}b"))
                    arrImg0.add(getIdentifierDrawable("img_p10${i}c"))
                } else {
                    arrImg0.add(getIdentifierDrawable("img_p10${i}"))
                }
            } else {
                arrImg0.add(getIdentifierDrawable("img_p110a"))
                arrImg0.add(getIdentifierDrawable("img_p110b"))
            }
        }
        for (i in 1..15) {
            arrName0.add(resources.getString(getIdentifierString("name${i}")))
            arrContent0.add(resources.getString(getIdentifierString("content${i}")))
        }
        mArrayListTraffic?.clear()
        for (i in 0..14) {
            mArrayListTraffic?.add(TrafficBoard(arrImg0[i], arrName0[i], arrContent0[i]))
        }
    }

    private fun initData1() {
        arrImg1.clear()
        arrName1.clear()
        arrContent1.clear()
        for (i in 97..105) {
            if (i == 103) {
                continue
            }
            arrImg1.add(getIdentifierDrawable("img_301${i.toChar()}"))
        }
        arrImg1.add(getIdentifierDrawable("img_302a"))
        arrImg1.add(getIdentifierDrawable("img_302b"))
        for (i in 3..9) {
            if (i == 8) {
                arrImg1.add(getIdentifierDrawable("img_308a"))
                arrImg1.add(getIdentifierDrawable("img_308b"))
            } else {
                arrImg1.add(getIdentifierDrawable("img_30${i}"))
            }
        }
        for (i in 16..33) {
            arrName1.add(resources.getString(getIdentifierString("name${i}")))
            arrContent1.add(resources.getString(getIdentifierString("content${i}")))
        }
        mArrayListTraffic?.clear()
        for (i in 0..arrImg1.lastIndex) {
            mArrayListTraffic?.add(TrafficBoard(arrImg1[i], arrName1[i], arrContent1[i]))
        }
    }

    private fun initData2() {
        arrImg2.clear()
        arrName2.clear()
        arrContent2.clear()
        arrImg2.add(getIdentifierDrawable("img401"))
        arrImg2.add(getIdentifierDrawable("img402"))
        for (i in 3..5) {
            arrImg2.add(getIdentifierDrawable("img40${i}a"))
            arrImg2.add(getIdentifierDrawable("img40${i}b"))
        }
        arrImg2.add(getIdentifierDrawable("img405c"))
        arrImg2.add(getIdentifierDrawable("img406"))
        for (i in 7..14) {
            if (i == 7 || i == 13 || i == 14) {
                if (i == 7) {
                    arrImg2.add(getIdentifierDrawable("img40${i}a"))
                    arrImg2.add(getIdentifierDrawable("img40${i}b"))
                    arrImg2.add(getIdentifierDrawable("img40${i}c"))
                } else {
                    arrImg2.add(getIdentifierDrawable("img4${i}a"))
                    arrImg2.add(getIdentifierDrawable("img4${i}b"))
                    arrImg2.add(getIdentifierDrawable("img4${i}c"))
                }
            } else {
                if (i < 10) {
                    arrImg2.add(getIdentifierDrawable("img40${i}"))
                } else {
                    arrImg2.add(getIdentifierDrawable("img4${i}"))
                }
            }
        }
        for (i in 34..59) {
            arrName2.add(resources.getString(getIdentifierString("name${i}")))
            arrContent2.add(resources.getString(getIdentifierString("content${i}")))
        }
        mArrayListTraffic?.clear()
        for (i in 0..arrImg2.lastIndex) {
            mArrayListTraffic?.add(TrafficBoard(arrImg2[i], arrName2[i], arrContent2[i]))
        }
    }

    private fun initData3() {
        arrImg3.clear()
        arrName3.clear()
        arrContent3.clear()
        for (i in 201..203) {
            arrImg3.add(getIdentifierDrawable("img${i}a"))
            arrImg3.add(getIdentifierDrawable("img${i}b"))
        }
        arrImg3.add(getIdentifierDrawable("img203c"))
        arrImg3.add(getIdentifierDrawable("img204"))
        for (i in 97..101) {
            arrImg3.add(getIdentifierDrawable("img205${i.toChar()}"))
        }
        arrImg3.add(getIdentifierDrawable("img206"))
        for (i in 97..107) {
            if (i == 106) {
                continue
            }
            arrImg3.add(getIdentifierDrawable("img207${i.toChar()}"))
        }
        for (i in 208..220) {
            if (i == 211) {
                arrImg3.add(getIdentifierDrawable("img211a"))
                arrImg3.add(getIdentifierDrawable("img211b"))
            } else {
                arrImg3.add(getIdentifierDrawable("img${i}"))
            }
        }
        for (i in 60..97) {
            arrName3.add(resources.getString(getIdentifierString("name${i}")))
            arrContent3.add(resources.getString(getIdentifierString("content${i}")))
        }
        mArrayListTraffic?.clear()
        for (i in 0..arrImg3.lastIndex) {
            mArrayListTraffic?.add(TrafficBoard(arrImg3[i], arrName3[i], arrContent3[i]))
        }
    }

    private fun initData4() {
        arrImg4.clear()
        arrName4.clear()
        arrContent4.clear()
        arrImg4.add(getIdentifierDrawable("img501"))
        arrImg4.add(getIdentifierDrawable("img502"))
        for (i in 97..102) {
            arrImg4.add(getIdentifierDrawable("img503${i.toChar()}"))
        }
        for (i in 504..509) {
            when (i) {
                507, 504 -> {
                    arrImg4.add(getIdentifierDrawable("img${i}"))
                }
                else -> {
                    arrImg4.add(getIdentifierDrawable("img${i}a"))
                    arrImg4.add(getIdentifierDrawable("img${i}b"))
                }
            }
            if (i == 505) {
                arrImg4.add(getIdentifierDrawable("img${i}c"))
            }
        }
        for (i in 98..116) {
            arrName4.add(resources.getString(getIdentifierString("name${i}")))
            arrContent4.add(resources.getString(getIdentifierString("content${i}")))
        }
        mArrayListTraffic?.clear()
        for (i in 0..arrImg4.lastIndex) {
            mArrayListTraffic?.add(TrafficBoard(arrImg4[i], arrName4[i], arrContent4[i]))
        }

    }
    private fun initTypeTrafficRecycleView() {
        mListTypeAdapter = ListTrafficTypeAdapter(this, this)
        rv_listTrafficType.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_listTrafficType.adapter = mListTypeAdapter
        val arrType = arrayListOf(
            "BIỂN BÁO CẤM",
            "BIỂN HIỆU LỆNH",
            "BIỂN CHỈ DẪN",
            "BIỂN BÁO NGUY HIỂM VÀ CẢNH BÁO",
            "BIỂN PHỤ"
        )
        mListTypeAdapter?.setList(arrType)
    }

    private fun initTrafficRecycleView() {
        mListAdapter = ListTrafficAdapter(this)
        rv_listTraffic.layoutManager =
            LinearLayoutManager(this)
        rv_listTraffic.adapter = mListAdapter
        mListAdapter?.setList(mArrayListTraffic!!)
    }

    private fun getIdentifierDrawable(id: String) = resources.getIdentifier(id, "drawable", this.packageName)

    private fun getIdentifierString(id: String) = resources.getIdentifier(id, "string", this.packageName)

    override fun onClick(v: View?) {
        when (v) {
            btn_backListTrafficSigns -> {
                this.finish()
            }
        }
    }

    override fun onItemClick(position: Int) {
        mListTypeAdapter?.setIndexSelected(position)
        when(position) {
            0 -> initData0()
            1 -> initData1()
            2 -> initData2()
            3 -> initData3()
            4 -> initData4()
        }
        rv_listTrafficType.smoothScrollToPosition(position)
        mListAdapter?.setList(mArrayListTraffic!!)
    }


}