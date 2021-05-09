package com.example.nthigplxa1.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nthigplxa1.R
import com.example.nthigplxa1.adapter.ItemHomeListener
import com.example.nthigplxa1.adapter.ListHomeAdapter
import com.example.nthigplxa1.model.Home
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_law.*
import java.util.ArrayList

class LawActivity : AppCompatActivity(), View.OnClickListener, ItemHomeListener {

    private var mHomeAdapter: ListHomeAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_law)
        btn_backLaw.setOnClickListener(this)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val arrayList = ArrayList<Home>()
        arrayList.add(Home(R.drawable.img_huongdan, "Hiệu lệnh, chỉ dẫn"))
        arrayList.add(Home(R.drawable.img_chuyenhuong, "Chuyển hướng, nhường đường"))
        arrayList.add(Home(R.drawable.img_dungdoxe, "Dừng xe, đỗ xe"))
        arrayList.add(Home(R.drawable.img_coi, "Thiết bị ưu tiên, còi"))
        arrayList.add(Home(R.drawable.img_khoangcach, "Tốc độ, khoảng cách an toàn"))
        arrayList.add(Home(R.drawable.img_vanchuyen, "Vận chuyển người, hàng hóa"))
        arrayList.add(Home(R.drawable.img_trangthietbi, "Trang thiết bị, phương tiện"))
        arrayList.add(Home(R.drawable.img_duongcam, "Đường cấm, đường một chiều"))
        arrayList.add(Home(R.drawable.img_nongdocon, "Nồng độ cồn, chất kích thích"))
        arrayList.add(Home(R.drawable.img_giaytoxe, "Giấy tờ xe"))
        mHomeAdapter = ListHomeAdapter(this, this)
        rvLaw.layoutManager = LinearLayoutManager(this)
        rvLaw.adapter = mHomeAdapter
        mHomeAdapter?.setList(arrayList)
    }

    override fun onClick(v: View?) {
        when(v) {
            btn_backLaw -> {
                this.finish()
            }
        }
    }

    override fun onItemClick(position: Int, mItem: Home) {
        val intent = Intent(this, DetailLawActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra("NAME", mItem.title)
        startActivity(intent)
    }
}