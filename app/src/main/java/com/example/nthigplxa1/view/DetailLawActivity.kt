package com.example.nthigplxa1.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nthigplxa1.R
import com.example.nthigplxa1.adapter.ListTrafficAdapter
import com.example.nthigplxa1.model.TrafficBoard
import kotlinx.android.synthetic.main.activity_detail_law.*
import java.util.*

class DetailLawActivity : AppCompatActivity(), View.OnClickListener {
    var mListAdapter: ListTrafficAdapter? = null
    private var mArrayListTraffic = ArrayList<TrafficBoard>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_law)
        btn_backDLaw.setOnClickListener(this)
        val name = intent.getStringExtra("NAME")
        tv_titleOfListDLaw.text = name.toString()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mListAdapter = ListTrafficAdapter(this)
        rvDLaw.layoutManager =
            LinearLayoutManager(this)
        rvDLaw.adapter = mListAdapter
        when (tv_titleOfListDLaw.text.toString()) {
            "Đường cấm, đường một chiều" -> {
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img1,
                        "Đi vào khu vực cấm, đường có biển báo hiệu có nội dung cấm đi vào đối với loại phương tiện đang điều khiển.",
                        "Phạt tiền từ 400.000 đồng đến 600.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img2,
                        "Đi ngược chiều của đường một chiều, đi ngược chiều trên đường có biển “Cấm đi ngược chiều”",
                        "Phạt tiền từ 1.000.000 đồng đến 2.000.000 đồng."
                    )
                )
            }
            "Hiệu lệnh, chỉ dẫn" -> {
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_hl1,
                        "Không chấp hành hiệu lệnh, chỉ dẫn của biển báo hiệu, vạch kẻ",
                        "Phạt tiền từ 100.000 đồng đến 200.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_hl2,
                        "Không chấp hành hiệu lệnh của đèn tín hiệu giao thông",
                        "Phạt tiền từ 600.000 đồng đến 1.000.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_hl3,
                        "Không chấp hành hiệu lệnh, hướng dẫn của người điều khiển giao thông hoặc người kiểm soát giao thông",
                        "Phạt tiền từ 600.000 đồng đến 1.000.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_hl4,
                        "Không chấp hành hiệu lệnh, chỉ dẫn của biển báo hiệu, vạch kẻ đường khi đi qua đường ngang, cầu chung",
                        "Phạt tiền từ 200.000 đồng đến 300.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_hl5,
                        "Vượt đường ngang, cầu chung khi đèn đỏ đã bật sáng",
                        "Phạt tiền từ 600.000 đồng đến 1.000.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_hl6,
                        "Không chấp hành hiệu lệnh, chỉ dẫn của nhân viên gác đường ngang, cầu chung khi đi qua đường ngang, cầu chung",
                        "Phạt tiền từ 600.000 đồng đến 1.000.000 đồng."
                    )
                )
            }
            "Chuyển hướng, nhường đường" -> {
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_ch1,
                        "Không có báo hiệu xin vượt trước khi vượt",
                        "Phạt tiền từ 100.000 đồng đến 200.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_ch2,
                        "Chuyển hướng không nhường quyền đi trước cho người đi bộ, xe lăn của người khuyết tật qua đường tại nơi có vạch kẻ đường dành cho người đi bộ",
                        "Phạt tiền từ 100.000 đồng đến 200.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_ch3,
                        "Chuyển hướng không nhường quyền đi trước cho xe thô sơ đang đi trên phần đường dành cho xe thô sơ",
                        "Phạt tiền từ 100.000 đồng đến 200.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_ch4,
                        "Chuyển hướng không nhường đường cho các xe đi ngược chiều",
                        "Phạt tiền từ 100.000 đồng đến 200.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_ch5,
                        "Chuyển hướng không nhường đường cho người đi bộ, xe lăn của người khuyết tật đang qua đường tại nơi không có vạch kẻ đường cho người đi bộ",
                        "Phạt tiền từ 100.000 đồng đến 200.000 đồng"
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_ch6,
                        "Chuyển hướng không nhường đường cho người đi bộ, xe lăn của người khuyết tật đang qua đường tại nơi không có vạch kẻ đường cho người đi bộ",
                        "Phạt tiền từ 100.000 đồng đến 200.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_ch7,
                        "Lùi xe mô tô ba bánh không quan sát hoặc không có tín hiệu báo trước",
                        "Phạt tiền từ 100.000 đồng đến 200.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_ch8,
                        "Không tuân thủ các quy định về nhường đường tại nơi đường giao nhau",
                        "Phạt tiền từ 100.000 đồng đến 200.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_ch9,
                        "Chuyển làn đường không đúng nơi được phép hoặc không có tín hiệu báo trước",
                        "Phạt tiền từ 100.000 đồng đến 200.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_ch10,
                        "Tránh xe không đúng quy định",
                        "Phạt tiền từ 100.000 đồng đến 200.000 đồng."
                    )
                )
            }
            "Dừng xe, đỗ xe" -> {
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_dx1,
                        "Dừng xe, đỗ xe trên phần đường xe chạy ở đoạn đường ngoài đô thị nơi có lề đường",
                        "Phạt tiền từ 200.000 đồng đến 300.000."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_dx2,
                        "Dừng xe, đỗ xe ở lòng đường đô thị gây cản trở giao thông",
                        "Phạt tiền từ 200.000 đồng đến 300.000."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_dx3,
                        "Tụ tập từ 3 xe trở lên ở lòng đường, trong hầm đường bộ",
                        "Phạt tiền từ 200.000 đồng đến 300.000."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_dx4,
                        "Đỗ, để xe ở lòng đường đô thị, hè phố trái quy định của pháp luật",
                        "Phạt tiền từ 200.000 đồng đến 300.000."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_dx5,
                        "Dừng xe, đỗ xe trên đường xe điện, điểm dừng đón trả khách của xe buýt, nơi đường bộ giao nhau, trên phần đường dành cho người đi bộ qua đường",
                        "Phạt tiền từ 200.000 đồng đến 300.000."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_dx6,
                        "Đỗ xe tại nơi có biển “Cấm đỗ xe” hoặc biển “Cấm dừng xe và đỗ xe”",
                        "Phạt tiền từ 200.000 đồng đến 300.000."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_dx7,
                        "Không tuân thủ các quy định về dừng xe, đỗ xe tại nơi đường bộ giao nhau cùng mức với đường sắt",
                        "Phạt tiền từ 200.000 đồng đến 300.000."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_dx8,
                        "Dừng xe, đỗ xe trong phạm vi an toàn của đường sắt",
                        "Phạt tiền từ 200.000 đồng đến 300.000."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_dx9,
                        "Dừng xe, đỗ xe trên cầu",
                        "Phạt tiền từ 400.000 đồng đến 600.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_dx10,
                        "Dừng xe, đỗ xe trong hầm đường bộ không đúng nơi quy định",
                        "Phạt tiền từ 600.000 đồng đến 1.000.000 đồng."
                    )
                )
            }
            "Thiết bị ưu tiên, còi" -> {
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_coi1,
                        "Bấm còi trong thời gian từ 22 giờ ngày hôm trước đến 5 giờ ngày hôm sau, sử dụng đèn chiếu xa trong đô thị, khu đông dân cư",
                        "Phạt tiền từ 100.000 đồng đến 200.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_coi2,
                        "Xe được quyền ưu tiên lắp đặt, sử dụng thiết bị phát tín hiệu ưu tiên không đúng quy định hoặc sử dụng thiết bị phát tín hiệu ưu tiên mà không có Giấy phép của cơ quan có thẩm quyền cấp hoặc có Giấy phép của cơ quan có thẩm quyền cấp nhưng không còn giá trị sử dụng theo quy định",
                        "Phạt tiền từ 100.000 đồng đến 200.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_coi3,
                        "Xe không được quyền ưu tiên lắp đặt, sử dụng thiết bị phát tín hiệu của xe được quyền ưu tiên.",
                        "Phạt tiền từ 200.000 đồng đến 300.000."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_coi4,
                        "Bấm còi, rú ga (nẹt pô) liên tục trong đô thị, khu đông dân cư",
                        "Phạt tiền từ 400.000 đồng đến 600.000 đông."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_coi5,
                        "Điều khiển xe không có còi hoặc có nhưng không có tác dụng",
                        "Phạt tiền từ 100.000 đồng đến 200.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_coi6,
                        "Sử dụng còi không đúng quy chuẩn kỹ thuật cho từng loại xe",
                        "Phạt tiền từ 100.000 đồng đến 200.000 đồng."
                    )
                )
            }
            "Tốc độ, khoảng cách an toàn" -> {

            }
            "Vận chuyển người, hàng hóa" -> {

            }
            "Trang thiết bị, phương tiện" -> {

            }
            "Nồng độ cồn, chất kích thích" -> {

            }
            "Giấy tờ xe" -> {

            }
        }
        mListAdapter?.setList(mArrayListTraffic)
    }

    override fun onClick(v: View?) {
        when (v) {
            btn_backDLaw -> {
                this.finish()
            }
        }
    }
}