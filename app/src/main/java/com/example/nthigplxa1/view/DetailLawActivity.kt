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
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_td1,
                        "Không giữ khoảng cách an toàn để xảy ra va chạm với xe chạy liền trước hoặc không giữ khoảng cách theo quy định của biển báo hiệu “Cự ly tối thiểu giữa hai xe”",
                        "Phạt tiền từ 100.000 đồng đến 200.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_td2,
                        "Điều khiển xe chạy dưới tốc độ tối thiểu trên những đoạn đường bộ có quy định tốc độ tối thiểu cho phép",
                        "Phạt tiền từ 100.000 đồng đến 200.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_td3,
                        "Điều khiển xe chạy quá tốc độ quy định từ 5 km/h đến dưới 10 km/h",
                        "Phạt tiền từ 200.000 đồng đến 300.000."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_td4,
                        "Điều khiển xe chạy tốc độ thấp mà không đi bên phải phần đường xe chạy gây cản trở giao thông",
                        "Phạt tiền từ 200.000 đồng đến 300.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_td5,
                        "Điều khiển xe chạy quá tốc độ quy định từ 10 km/h đến 20 km/h",
                        "Phạt tiền từ 600.000 đồng đến 1.000.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_td6,
                        "Điều khiển xe chạy quá tốc độ quy định trên 20 km/h",
                        "Phạt tiền từ 4.000.000 đồng đến 5.000.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_td7,
                        "Không chú ý quan sát, điều khiển xe chạy quá tốc độ quy định gây tai nạn giao thông",
                        "Phạt tiền từ 4.000.000 đồng đến 5.000.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_td8,
                        "Không đi đúng phần đường, làn đường, không giữ khoảng cách an toàn giữa hai xe theo quy định gây tai nạn giao thông hoặc đi vào đường có biển báo hiệu có nội dung cấm đi vào đối với loại phương tiện đang điều khiển, đi ngược chiều của đường một chiều, đi ngược chiều trên đường có biển “Cấm đi ngược chiều” gây tai nạn giao thông",
                        "Phạt tiền từ 4.000.000 đồng đến 5.000.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_td9,
                        "Điều khiển xe thành nhóm từ 2 xe trở lên chạy quá tốc độ quy định",
                        "Phạt tiền từ 6.000.000 đồng đến 8.000.000 đồng."
                    )
                )
            }
            "Vận chuyển người, hàng hóa" -> {
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_vc1,
                        "Chở người ngồi trên xe sử dụng ô (dù)",
                        "Phạt tiền từ 100.000 đồng đến 200.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_vc2,
                        "Không đội mũ bảo hiểm cho người đi mô tô, xe máy” hoặc đội mũ bảo hiểm cho người đi mô tô, xe máy” không cài quai đúng quy cách khi điều khiển xe tham gia giao thông trên đường bộ",
                        "Phạt tiền từ 200.000 đồng đến 300.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_vc3,
                        "Chở người ngồi trên xe không đội mũ bảo hiểm cho người đi mô tô, xe máy” hoặc đội mũ bảo hiểm cho người đi mô tô, xe máy không cài quai đúng quy cách",
                        "Phạt tiền từ 200.000 đồng đến 300.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_vc4,
                        "Chở theo 2 người trên xe", "Phạt tiền từ 200.000 đồng đến 300.000."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_vc5,
                        "Chở theo từ 3 người trở lên trên xe",
                        "Phạt tiền từ 400.000 đồng đến 600.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_vc6,
                        "Chở người đứng trên yên, giá đèo hàng hoặc ngồi trên tay lái",
                        "Phạt tiền từ 400.000 đồng đến 600.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_vc7,
                        "Xếp hàng hóa trên xe vượt quá giới hạn quy định",
                        "Phạt tiền từ 400.000 đồng đến 600.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_vc8,
                        "Chở hàng vượt trọng tải thiết kế được ghi trong Giấy đăng ký xe đối với loại xe có quy định về trọng tải thiết kế",
                        "Phạt tiền từ 400.000 đồng đến 600.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_vc9,
                        "Sử dụng ô (dù)", "Phạt tiền từ 100.000 đồng đến 200.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_vc10,
                        "Không đội mũ bảo hiểm cho người đi mô tô, xe máy” hoặc đội mũ bảo hiểm cho người đi mô tô, xe máy” không cài quai đúng quy cách khi tham gia giao thông trên đường bộ",
                        "Phạt tiền từ 200.000 đồng đến 300.000 đồng."
                    )
                )

            }
            "Trang thiết bị, phương tiện" -> {
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_tb1,
                        "Không sử dụng đèn chiếu sáng trong thời gian từ 19 giờ ngày hôm trước đến 5 giờ ngày hôm sau hoặc khi sương mù, thời tiết xấu hạn chế tầm nhìn",
                        "Phạt tiền từ 100.000 đồng đến 200.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_tb2,
                        "Sử dụng đèn chiếu xa khi tránh xe đi ngược chiều",
                        "Phạt tiền từ 100.000 đồng đến 200.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_tb3,
                        "Chạy trong hầm đường bộ không sử dụng đèn chiếu sáng gần",
                        "Phạt tiền từ 400.000 đồng đến 600.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_tb4,
                        "Điều khiển xe không có đèn soi biển số hoặc có nhưng không có tác dụng",
                        "Phạt tiền từ 100.000 đồng đến 200.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_tb5,
                        "Điều khiển xe không có đèn báo hãm hoặc có nhưng không có tác dụng",
                        "Phạt tiền từ 100.000 đồng đến 200.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_tb6,
                        "Điều khiển xe không có gương chiếu hậu bên trái người điều khiển hoặc có nhưng không có tác dụng",
                        "Phạt tiền từ 100.000 đồng đến 200.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_tb7,
                        "Điều khiển xe gắn biển số không đúng quy định",
                        "Phạt tiền từ 100.000 đồng đến 200.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_tb8,
                        "Điều khiển xe gắn biển số không rõ chữ, số",
                        "Phạt tiền từ 100.000 đồng đến 200.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_tb9,
                        "Điều khiển xe gắn biển số bị bẻ cong, bị che lấp, bị hỏng",
                        "Phạt tiền từ 100.000 đồng đến 200.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_tb10,
                        "Điều khiển xe gắn biển số sơn, dán thêm làm thay đổi chữ, số hoặc thay đổi màu sắc của chữ, số, nền biển",
                        "Phạt tiền từ 100.000 đồng đến 200.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_tb11,
                        "Điều khiển xe không có đèn tín hiệu hoặc có nhưng không có tác dụng",
                        "Phạt tiền từ 100.000 đồng đến 200.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_tb12,
                        "Điều khiển xe không có bộ phận giảm thanh, giảm khói hoặc có nhưng không bảo đảm quy chuẩn môi trường về khí thải, tiếng ồn",
                        "Phạt tiền từ 100.000 đồng đến 200.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_tb13,
                        "Điều khiển xe không có đèn chiếu sáng gần, xa hoặc có nhưng không có tác dụng, không đúng tiêu chuẩn thiết kế",
                        "Phạt tiền từ 100.000 đồng đến 200.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_tb14,
                        "Điều khiển xe không có hệ thống hãm hoặc có nhưng không có tác dụng, không bảo đảm tiêu chuẩn kỹ thuật",
                        "Phạt tiền từ 100.000 đồng đến 200.000 đồng."
                    )
                )
            }
            "Nồng độ cồn, chất kích thích" -> {
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_ndc1, "Điều khiển xe trên đường mà trong máu hoặc hơi thở có nồng độ cồn nhưng chưa vượt quá 50 miligam/100 mililít máu hoặc chưa vượt quá 0,25 miligam/1 lít khí thở", "Phạt tiền từ 2.000.000 đồng đến 3.000.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_ndc2, "Điều khiển xe trên đường mà trong máu hoặc hơi thở có nồng độ cồn vượt quá 50 miligam đến 80 miligam/ 100 mililít máu hoặc vượt quá 0,25 miligam đến 0,4 miligam/1 lít khí thở", "Phạt tiền từ 4.000.000 đồng đến 5.000.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_ndc3, "Điều khiển xe trên đường mà trong máu hoặc hơi thở có nồng độ cồn vượt quá 80 miligam/100 mililít máu hoặc vượt quá 0,4 miligam/1 lít khí thở", "Phạt tiền từ 6.000.000 đồng đến 8.000.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_ndc4, "Không chấp hành yêu cầu kiểm tra về nồng độ cồn của người thi hành công vụ", "Phạt tiền từ 6.000.000 đồng đến 8.000.000 đồng."
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_ndc5, "Điều khiển xe trên đường mà trong cơ thể có chất ma túy", "Phạt tiền từ 6.000.000 đồng đến 8.000.000 đồng"
                    )
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_ndc6, "Không chấp hành yêu cầu kiểm tra về chất ma túy của người thi hành công vụ", "Phạt tiền từ 6.000.000 đồng đến 8.000.000 đồng."
                    )
                )
            }
            "Giấy tờ xe" -> {
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_gtx1, "Có Giấy phép lái xe nhưng không phù hợp với loại xe đang điều khiển", "Phạt tiền từ 3.000.000 đồng đến 4.000.000 đồng.")
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_gtx2, "Không có giấy phép lái xe hoặc sử dụng Giấy phép lái xe không do cơ quan có thẩm quyền cấp, Giấy phép lái xe bị tẩy xóa", "Phạt tiền từ 3.000.000 đồng đến 4.000.000 đồng.")
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_gtx3, "Có Giấy phép lái xe quốc tế do các nước tham gia Công ước về Giao thông đường bộ năm 1968 cấp (trừ Giấy phép lái xe quốc tế do Việt Nam cấp) nhưng không mang theo Giấy phép lái xe quốc gia.", "Phạt tiền từ 3.000.000 đồng đến 4.000.000 đồng")
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_gtx4, "Sử dụng Giấy phép lái xe không hợp lệ (Giấy phép lái xe có số phôi ghi ở mặt sau không trùng với số phôi được cấp mới nhất trong hệ thống thông tin quản lý Giấy phép lái xe)", "Phạt tiền từ 3.000.000 đồng đến 4.000.000 đồng.")
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_gtx5, "Không mang theo Giấy phép lái xe", "Phạt tiền từ 100.000 đồng đến 200.000 đồng.")
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_gtx6, "Không có giấy phép lái xe hoặc sử dụng Giấy phép lái xe không do cơ quan có thẩm quyền cấp, Giấy phép lái xe bị tẩy xóa", "Phạt tiền từ 800.000 đồng đến 1.200.000 đồng.")
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_gtx7, "Có Giấy phép lái xe quốc tế do các nước tham gia Công ước về Giao thông đường bộ năm 1968 cấp (trừ Giấy phép lái xe quốc tế do Việt Nam cấp) nhưng không mang theo Giấy phép lái xe quốc gia", "Phạt tiền từ 800.000 đồng đến 1.200.000 đồng.")
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_gtx8, "Sử dụng Giấy phép lái xe không hợp lệ (Giấy phép lái xe có số phôi ghi ở mặt sau không trùng với số phôi được cấp mới nhất trong hệ thống thông tin quản lý Giấy phép lái xe)", "Phạt tiền từ 800.000 đồng đến 1.200.000 đồng.")
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_gtx9, "Tẩy xóa, sửa chữa hoặc giả mạo hồ sơ đăng ký xe", "Phạt tiền từ 800.000 đồng đến 2.000.000 đồng đối với cá nhân, từ 1.600.000 đồng đến 4.000.000 đồng đối với tổ chức.")
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_gtx10, "Khai báo không đúng sự thật hoặc sử dụng các giấy tờ, tài liệu giả để được cấp lại biển số, Giấy đăng ký xe", "Phạt tiền từ 800.000 đồng đến 2.000.000 đồng đối với cá nhân, từ 1.600.000 đồng đến 4.000.000 đồng đối với tổ chức.")
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_gtx11, "Không chấp hành việc thu hồi Giấy đăng ký xe, biển số xe theo quy định", " Phạt tiền từ 800.000 đồng đến 2.000.000 đồng đối với cá nhân, từ 1.600.000 đồng đến 4.000.000 đồng đối với tổ chức.")
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_gtx12, "Đưa phương tiện không có Giấy đăng ký xe tham gia giao thông hoặc có nhưng đã hết hạn sử dụng", "Phạt tiền từ 800.000 đồng đến 2.000.000 đồng đối với cá nhân, từ 1.600.000 đồng đến 4.000.000 đồng đối với tổ chức.")
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_gtx13, "Đưa phương tiện có Giấy đăng ký xe nhưng không do cơ quan có thẩm quyền cấp hoặc bị tẩy xóa tham gia giao thông", "Phạt tiền từ 800.000 đồng đến 2.000.000 đồng đối với cá nhân, từ 1.600.000 đồng đến 4.000.000 đồng đối với tổ chức.")
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_gtx14, "Đưa phương tiện có Giấy đăng ký xe nhưng không đúng với số khung số máy của xe tham gia giao thông", "Phạt tiền từ 800.000 đồng đến 2.000.000 đồng đối với cá nhân, từ 1.600.000 đồng đến 4.000.000 đồng đối với tổ chức.")
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_gtx15, "Giấy tờ của phương tiện không có bản dịch sang tiếng Anh hoặc tiếng Việt theo quy định", "Phạt tiền từ 500.000 đồng đến 1.000.000 đồng.")
                )
                mArrayListTraffic.add(
                    TrafficBoard(
                        R.drawable.img_gtx16, "Không có tờ khai phương tiện vận tải đường bộ tạm nhập, tái xuất theo quy đinh", "Phạt tiền từ 500.000 đồng đến 1.000.000 đồng.")
                )
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