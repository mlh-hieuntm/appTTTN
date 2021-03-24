package com.example.nthigplxa1.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.nthigplxa1.db.AnswerDatabase
import com.example.nthigplxa1.db.ExamDatabase
import com.example.nthigplxa1.db.QuestionDatabase
import com.example.nthigplxa1.adapter.ItemListener
import com.example.nthigplxa1.adapter.ListExamAdapter
import com.example.nthigplxa1.adapter.ListExamItemTouchHelper
import com.example.nthigplxa1.R
import com.example.nthigplxa1.model.Answer
import com.example.nthigplxa1.model.Question
import kotlinx.android.synthetic.main.activity_list_exam.*
import kotlinx.android.synthetic.main.dialog_confirm_delete.*

class ListExamActivity : AppCompatActivity(),
    ListExamItemTouchHelper.RecyclerItemTouchHelperListener, View.OnClickListener, ItemListener {
    private val FIRST_TIME_OPEN_APP = "FIRST_TIME_OPEN_APP"
    private val typeQuestionLaw = "KHAI_NIEM_VA_QUY_TAC"
    private val typeQuestionNoticeBoard = "BIEN_BAO_DUONG_BO"
    private val typeQuestionSituations = "SA_HINH"
    private var mListExamAdapter: ListExamAdapter? = null
    private lateinit var mDialog: MaterialDialog
    private var helper: ItemTouchHelper? = null
    private var sharedPreferences: SharedPreferences? = null
    private var examDatabase: ExamDatabase? = null
    private var questionDatabase: QuestionDatabase? = null
    private var answerDatabase: AnswerDatabase? = null
    val arr = ArrayList<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_exam)
        initRecycleView()
        initDataBase()
        btn_addExam.setOnClickListener(this)
        btn_backListExam.setOnClickListener(this)
        sharedPreferences = getSharedPreferences("openApp", MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        val isFirstOpenApp = sharedPreferences?.getBoolean(FIRST_TIME_OPEN_APP, false)

        if (!isFirstOpenApp!!) {
            Thread {
                insertDB()
            }.start()
            editor?.putBoolean(FIRST_TIME_OPEN_APP, true)
            editor?.commit()
        } else {

        }
    }

    private fun insertDB() {
        var ans1: Answer? = null
        var ans2: Answer? = null
        var ans3: Answer? = null
        var ans4: Answer? = null
        var question: Question? = null
        // cau 1
        ans1 = Answer(1, "Diễn ra trên đường phố không có người qua lại.", 1)
        ans2 = Answer(2, "Được người dân ủng hộ.", 1)
        ans3 = Answer(3, "Được cơ quan có thẩm quyền cấp phép.", 1)
        question = Question(
            1,
            typeQuestionLaw,
            true,
            3,
            "Cuộc đua xe chỉ được thực hiện khi nào ?",
            -1,
            "Hành vi đua xe chưa được cơ quan có thẩm quyền cấp phép là sai quy định của pháp luật."
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 2
        ans1 = Answer(4, "Bị nghiêm cấm.", 2)
        ans2 = Answer(5, "Được người dân ủng hộ.", 2)
        ans3 = Answer(6, "Được cơ quan có thẩm quyền cấp phép.", 2)
        question = Question(
            2,
            typeQuestionLaw,
            true,
            4,
            "Người điều khiển phương tiện giao thông đường bộ mà trong cơ thể có chất ma tuý có bị nghiêm cấm hay không ?",
            -1,
            "Ma túy là chất kích thích bị nghiêm cấm đối với người điều khiển phương tiện giao thông đường bộ."
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 3
        ans1 = Answer(7, "Chỉ bị nhắc nhở.", 3)
        ans2 = Answer(
            8,
            "Bị xử phạt hành chính hoặc có thể bị xử lý hình sự tùy theo mức độ vi phạm.",
            3
        )
        ans3 = Answer(9, "Không bị xử lý hình sự.", 3)
        question = Question(
            3,
            typeQuestionLaw,
            true,
            8,
            "Sử dụng rượu bia khi lái xe, nếu bị phát hiện thì bị xử lý như thế nào ?",
            -1,
            "Người điều khiển phương tiện giao thông đường bộ không được dùng rượu, bia khi lái xe."
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 4
        ans1 = Answer(10, "DNgười điều khiển: Xe ô tô, xe mô tô, xe đạp, xe gắn máy.", 4)
        ans2 = Answer(11, "Người ngồi phía sau người điều khiển xe cơ giới.", 4)
        ans3 = Answer(12, "Người đi bộ.", 4)
        ans4 = Answer(13, "Cả ý 1 và ý 2", 4)
        question = Question(
            4,
            typeQuestionLaw,
            true,
            10,
            "Theo Luật phòng chống tác hại của rượu, bia, đối tượng nào dưới đây bị cấm sử dụng rượu bia khi tham gia giao thông ?",
            -1,
            "Những người trực tiếp điều khiển ôtô, môtô, xe đạp và xe gắn máy bị cấm sử dụng rượu, bia."
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        answerDatabase?.answerDao()?.saveData(ans4!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 5
        ans1 = Answer(14, "Bị nghiêm cấm tuỳ từng trường hợp.", 5)
        ans2 = Answer(15, "Không bị nghiêm cấm.", 5)
        ans3 = Answer(16, "Bị nghiêm cấm.", 5)
        question = Question(
            5,
            typeQuestionLaw,
            true,
            16,
            "Hành vi điều khiển xe cơ giới chạy quá tốc độ quy định, giành đường, vượt ẩu có bị nghiêm cấm hay không ?",
            -1,
            "Nếu vi phạm những lỗi trên, người điều khiển phương tiện phải chịu trách nhiệm theo quy định của pháp luật."
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 6
        ans1 = Answer(17, "Được phép.", 6)
        ans2 = Answer(18, "Không được phép.", 6)
        ans3 = Answer(19, "Tùy từng trường hợp.", 6)
        question = Question(
            6,
            typeQuestionLaw,
            true,
            18,
            "Ở phần đường dành cho người đi bộ qua đường, trên cầu, đầu cầu, đường cao tốc, đường hẹp, đường dốc, tại nơi đường bộ giao nhau cùng mức với đường sắt có được quay đầu xe hay không ?",
            -1,
            "Hành vi quay đầu tại những nơi không được phép tăng nguy cơ xảy ra tai nạn giao thông."
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 7
        ans1 = Answer(20, "Được phép.", 7)
        ans2 = Answer(
            21,
            "ếu phương tiện được kéo, đẩy có khối lượng nhỏ hơn phương tiện của mình.",
            7
        )
        ans3 = Answer(22, "Tuỳ trường hợp.", 7)
        ans4 = Answer(23, "Không được phép.", 7)
        question = Question(
            7,
            typeQuestionLaw,
            true,
            23,
            "Người điều khiển xe mô tô hai bánh, ba bánh, xe gắn máy có được phép sử dụng xe để kéo hoặc đẩy các phương tiện khác khi tham gia giao thông không ?",
            -1,
            "Xe môtô hai bánh, ba bánh, xe gắn máy không được phép sử dụng xe để kéo hoặc đẩy các phương tiện khác ở bất kỳ trường hợp nào."
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        answerDatabase?.answerDao()?.saveData(ans4!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 8
        ans1 = Answer(24, "Được phép.", 8)
        ans2 = Answer(25, "Tuỳ trường hợp.", 8)
        ans3 = Answer(26, "Không được phép.", 8)
        question = Question(
            8,
            typeQuestionLaw,
            true,
            26,
            "Khi điều khiển xe mô tô hai bánh, xe mô tô ba bánh, xe gắn máy, những hành vi buông cả hai tay; sử dụng xe để kéo, đẩy xe khác, vật khác; sử dụng chân chống của xe quệt xuống đường khi xe đang chạy có được phép hay không ?",
            -1,
            "Người điều khiển phương tiện vi phạm những hành vi trên có thể bị xử lý theo quy định của pháp luật."
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 9
        ans1 = Answer(
            27,
            "Buông cả hai tay; sử dụng xe để kéo, đẩy xe khác, vật khác; sử dụng chân chống của xe quệt xuống đường khi xe đang chạy.",
            9
        )
        ans2 = Answer(
            28,
            "Buông một tay; sử dụng xe để chở người hoặc hàng hoá; để chân chạm xuống đất khi khởi hành",
            9
        )
        ans3 = Answer(
            29,
            "Đội mũ bảo hiểm; chạy xe đúng tốc độ quy định và chấp hành đúng quy tắc giao thông đường bộ.",
            9
        )
        ans4 = Answer(30, "Chở người ngồi sau dưới 16 tuổi", 9)
        question = Question(
            9,
            typeQuestionLaw,
            true,
            27,
            "Khi điều khiển xe mô tô hai bánh, xe mô tô ba bánh, xe gắn máy, những hành vi nào không được phép ?",
            -1,
            "Những hành vi trên không được phép thực hiện vì gây nguy hiểm cho người điều khiển phương tiện và những người xung quanh."
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        answerDatabase?.answerDao()?.saveData(ans4!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 10
        ans1 = Answer(31, "Được mang, vác tuỳ trường hợp cụ thể.", 10)
        ans2 = Answer(32, "Không được mang, vác.", 10)
        ans3 = Answer(33, "Được mang, vác nhưng phải đảm bảo an toàn.", 10)
        question = Question(
            10,
            typeQuestionLaw,
            true,
            32,
            "Người ngồi trên xe mô tô hai bánh, ba bánh, xe gắn máy khi tham gia giao thông có được mang, vác vật cồng kềnh hay không ?",
            -1,
            "Mang, vác vật cồng kềnh khi ngồi trên xe môtô hai bánh, ba bánh, xe gắn máy tiềm ẩn nhiều nguy cơ xảy ra tai nạn giao thông."
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 11
        ans1 = Answer(34, "Được phép.", 11)
        ans2 = Answer(35, "Được bám trong trường hợp phương tiện của mình bị hỏng.", 11)
        ans3 = Answer(36, "Được kéo, đẩy trong trường hợp phương tiện khác bị hỏng.", 11)
        ans4 = Answer(37, "Không được phép.", 11)
        question = Question(
            11,
            typeQuestionLaw,
            true,
            37,
            "Người ngồi trên xe mô tô hai bánh, xe mô tô ba bánh, xe gắn máy khi tham gia giao thông có được bám, kéo hoặc đẩy các phương tiện khác không ?",
            -1,
            "Hành vi bám, kéo hoặc đẩy các phương tiện khác khi ngồi trên xe môtô hai bánh, ba bánh, xe gắn máy là vi phạm pháp luật."
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        answerDatabase?.answerDao()?.saveData(ans4!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 12
        ans1 = Answer(38, "Được sử dụng.", 12)
        ans2 = Answer(39, "Chỉ người ngồi sau được sử dụng.", 12)
        ans3 = Answer(40, "Không được sử dụng.", 12)
        ans4 = Answer(41, "Được sử dụng nếu không có áo mưa.", 12)
        question = Question(
            12,
            typeQuestionLaw,
            true,
            40,
            "Người ngồi trên xe mô tô hai bánh, xe mô tô ba bánh, xe gắn máy khi tham gia giao thông có được sử dụng ô khi trời mưa hay không ?",
            -1,
            "Sử dụng ô (dù) khi ngồi trên xe môtô hai bánh, ba bánh, xe gắn máy dễ khiến phương tiện mất lái."
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        answerDatabase?.answerDao()?.saveData(ans4!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 13
        ans1 = Answer(42, "Chỉ được phép nếu cả hai đội mũ bảo hiểm.", 13)
        ans2 = Answer(43, "Không được phép.", 13)
        ans3 = Answer(44, "Chỉ được thực hiện trên đường thật vắng.", 13)
        ans4 = Answer(45, "Chỉ được phép khi người đi xe đạp đã quá mệt.", 13)
        question = Question(
            13,
            typeQuestionLaw,
            true,
            43,
            "Khi đang lên dốc người ngồi trên xe mô tô có được kéo theo người đang điều khiển xe đạp hay không ?",
            -1,
            "Nghiêm cấm xe môtô kéo theo người đang điều khiển xe đạp leo dốc."
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        answerDatabase?.answerDao()?.saveData(ans4!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 14
        ans1 = Answer(46, "Chỉ được kéo nếu đã nhìn thấy trạm xăng.", 14)
        ans2 = Answer(
            47,
            "Chỉ được thực hiện trên đường vắng phương tiện cùng tham gia giao thông.",
            14
        )
        ans3 = Answer(48, "Không được phép.", 14)
        question = Question(
            14,
            typeQuestionLaw,
            true,
            48,
            "Hành vi sử dụng xe mô tô để kéo, đẩy xe mô tô khác bị hết xăng đến trạm mua xăng có được phép hay không ?",
            -1,
            "Hành vi dùng xe môtô để kéo, đẩy xe môtô khác bị cấm."
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 15
        ans1 = Answer(49, "Không được vận chuyển.", 15)
        ans2 = Answer(50, "Chỉ được vận chuyển khi đã chằng buộc cẩn thận.", 15)
        ans3 = Answer(
            51,
            "Chỉ được vận chuyển vật cồng kềnh trên xe máy nếu khoảng cách về nhà ngắn hơn 2 km.",
            15
        )
        question = Question(
            15,
            typeQuestionLaw,
            true,
            51,
            "Hành vi vận chuyển đồ vật cồng kềnh bằng xe mô tô, xe gắn máy khi tham gia giao thông có được phép hay không ?",
            -1,
            "Quy định tại Việt Nam và nhiều quốc gia không cho phép vận chuyển đồ vật cồng kềnh bằng xe môtô, xe gắn máy."
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 16
        ans1 = Answer(52, "Khi tham gia giao thông đường bộ.", 16)
        ans2 = Answer(53, "Chỉ khi đi trên đường chuyên dùng; đường cao tốc.", 16)
        question = Question(
            16,
            typeQuestionLaw,
            true,
            53,
            "Người ngồi trên xe mô tô 2 bánh, xe gắn máy phải đội mũ bảo hiểm có cài quai đúng quy cách khi nào ?",
            -1,
            "Phạt tiền 200.000-300.000 đồng đối với người điều khiển, người ngồi trên xe không đội mũ bảo hiểm hoặc đội mũ bảo hiểm không cài quai đúng quy cách khi tham gia giao thông trên đường bộ."
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 17
        ans1 = Answer(54, "Được phép nhưng phải đảm bảo an toàn.", 17)
        ans2 = Answer(55, "Không được phép.", 17)
        ans3 = Answer(56, "Được phép tùy từng hoàn cảnh, điều kiện cụ thể.", 17)
        question = Question(
            17,
            typeQuestionLaw,
            true,
            55,
            "Người điều khiển xe mô tô hai bánh, xe gắn máy có được đi xe dàn hàng ngang; đi xe vào phần đường dành cho người đi bộ và phương tiện khác; sử dụng ô, điện thoại di động, thiết bị âm thanh (trừ thiết bị trợ thính) hay không ?",
            -1,
            "Hành vi dùng điện thoại di động và các thiết bị âm thanh khi điều khiển xe môtô bị phạt từ 600.000 đồng đến 1 triệu đồng."
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 18
        ans1 = Answer(57, "Giảm tốc độ, đi từ từ để vượt qua trước người đi bộ.", 18)
        ans2 = Answer(
            58,
            "Giảm tốc độ, có thể dừng lại nếu cần thiết trước vạch dừng xe để nhường đường cho người đi bộ qua đường.",
            18
        )
        ans3 = Answer(59, "Tăng tốc độ để vượt qua trước người đi bộ.", 18)
        question = Question(
            18,
            typeQuestionLaw,
            true,
            58,
            "Người lái xe phải xử lý như thế nào khi quan sát phía trước thấy người đi bộ đang sang đường tại nơi có vạch đường dành cho người đi bộ để đảm bảo an toàn ?",
            -1,
            "Phải giảm tốc độ, phải dừng lại trước vạch dừng chờ người đi bộ đi qua rồi mới tiếp tục di chuyển."
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 19
        ans1 = Answer(
            60,
            "Giữ tay ga ở mức độ phù hợp, sử dụng phanh trước và phanh sau để giảm tốc độ.",
            19
        )
        ans2 = Answer(
            61,
            "Nhả hết tay ga, tắt động cơ, sử dụng phanh trước và phanh sau để giảm tốc độ.",
            19
        )
        ans3 = Answer(
            62,
            "Sử dụng phanh trước để giảm tốc độ kết hợp với tắt chìa khóa điện của xe.",
            19
        )
        question = Question(
            19,
            typeQuestionLaw,
            true,
            60,
            "Khi điều khiển xe mô tô tay ga xuống đường dốc dài, độ dốc cao, người lái xe cần thực hiện các thao tác nào dưới đây để đảm bảo an toàn ?",
            -1,
            "Người lái xe phải giữ tay ga ở mức độ phù hợp, sử dụng phanh trước và phanh sau để giảm tốc độ là đáp án phù hợp nhất."
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 20
        ans1 = Answer(
            63,
            "Giảm tốc độ để đảm bảo an toàn với xe phía trước và sử dụng điện thoại để liên lạc.",
            20
        )
        ans2 = Answer(
            64,
            "Giảm tốc độ để dừng xe ở nơi cho phép dừng xe sau đó sử dụng điện thoại để liên lạc.",
            20
        )
        ans3 = Answer(
            65,
            "Tăng tốc độ để cách xa xe phía sau và sử dụng điện thoại để liên lạc.",
            20
        )
        question = Question(
            20,
            typeQuestionLaw,
            true,
            64,
            "Khi đang lái xe mô tô và ô tô, nếu có nhu cầu sử dụng điện thoại để nhắn tin hoặc gọi điện, người lái xe phải thực hiện như thế nào trong các tình huống nêu dưới đây ?",
            -1,
            "Tuyệt đối không được sử dụng điện thoại khi điều khiển phương tiện tham gia giao thông đường bộ"
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 21
        ans1 = Answer(
            66,
            "Gồm xe ô tô, máy kéo, xe mô tô hai bánh, xe mô tô ba bánh, xe gắn máy, xe cơ giới dùng cho người khuyết tật và xe máy chuyên dùng.",
            21
        )
        ans2 = Answer(
            67,
            "Gồm xe ô tô; máy kéo; rơ moóc hoặc sơ mi rơ moóc được kéo bởi xe ô tô, máy kéo; xe mô tô hai bánh; xe mô tô ba bánh, xe gắn máy (kể cả xe máy điện) và các loại xe tương tự.",
            21
        )
        question = Question(
            21,
            typeQuestionLaw,
            false,
            67,
            "Khái niệm “phương tiện giao thông cơ giới đường bộ” được hiểu thế nào là đúng?",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 22
        ans1 = Answer(
            68,
            "Gồm xe đạp (kể cả xe đạp máy, xe đạp điện), xe xích lô, xe lăn dùng cho người khuyết tật, xe súc vật kéo và các loại xe tương tự.",
            22
        )
        ans2 = Answer(
            69,
            "Gồm xe đạp (kể cả xe đạp máy, xe đạp điện), xe gắn máy, xe cơ giới dùng cho người khuyết tật và xe máy chuyên dùng.",
            22
        )
        ans3 = Answer(
            70,
            "Gồm xe ô tô, máy kéo, rơ moóc hoặc sơ mi rơ moóc được kéo bởi xe ô tô, máy kéo..",
            22
        )
        question = Question(
            22,
            typeQuestionLaw,
            false,
            68,
            "Khái niệm “phương tiện giao thông thô sơ đường bộ” được hiểu thế nào là đúng??",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 23
        ans1 = Answer(71, "Phương tiện giao thông cơ giới đường bộ.", 23)
        ans2 =
            Answer(72, "Phương tiện giao thông thô sơ đường bộ và xe máy chuyên dùng.", 23)
        ans3 = Answer(73, "Cả ý 1 và 2.", 23)
        question = Question(
            23,
            typeQuestionLaw,
            false,
            73,
            "“Phương tiện tham gia giao thông đường bộ” gồm những loại nào?",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 24
        ans1 = Answer(74, "Người điều khiển xe cơ giới, người điều khiển xe thô sơ.", 24)
        ans2 = Answer(
            75,
            "Người điều khiển xe máy chuyên dùng tham gia giao thông đường bộ.",
            24
        )
        ans3 = Answer(76, "Cả ý 1 và ý 2. ", 24)
        question = Question(
            24,
            typeQuestionLaw,
            false,
            76,
            "“Người điều khiển phương tiện tham gia giao thông đường bộ” gồm những đối tượng nào dưới đây?",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 25
        ans1 = Answer(
            77,
            "Là người điều khiển phương tiện tham gia giao thông tại nơi thi công, nơi ùn tắc giao thông, ở bến phà, tại cầu đường bộ đi chung với đường sắt.",
            25
        )
        ans2 = Answer(
            78,
            "Là cảnh sát giao thông, người được giao nhiệm vụ hướng dẫn giao thông tại nơi thi công, nơi ùn tắc giao thông, ở bến phà, tại cầu đường bộ đi chung với đường sắt.",
            25
        )
        ans3 = Answer(
            79,
            "Là người tham gia giao thông tại nơi thi công, nơi ùn tắt giao thông, ở bến phà, tại cầu đường bộ đi chung với đường sắt.",
            25
        )
        question = Question(
            25,
            typeQuestionLaw,
            false,
            78,
            "Khái niệm “người điều khiển giao thông” được hiểu như thế nào là đúng?",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 26
        ans1 = Answer(
            80,
            "Người điều khiển, người sử dụng phương tiện tham giao giao thông đường bộ.",
            26
        )
        ans2 = Answer(81, " Người điều khiển, dẫn dắt súc vật, người đi bộ trên đường.", 26)
        ans3 = Answer(82, " Cả ý 1 và ý 2.", 26)

        question = Question(
            26,
            typeQuestionLaw,
            false,
            82,
            " “Người tham gia giao thông đường bộ” gồm những đối tượng nào?",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 27
        ans1 = Answer(
            83,
            "Là trạng thái đứng yên của phương tiện giao thông có giới hạn trong một khoảng thời gian cần thiết đủ để cho người lên, xuống phương tiện đó, xếp dỡ hàng hóa hoặc thực hiện công việc khác.",
            27
        )
        ans2 = Answer(
            84,
            "Là trạng thái đứng yên của phương tiện giao thông không giới hạn thời gian.",
            27
        )
        question = Question(
            27,
            typeQuestionLaw,
            false,
            84,
            "Khái niệm “đỗ xe” được hiểu như thế nào là đúng?",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 28
        ans1 = Answer(
            85,
            "Là trạng thái đứng yên của phương tiện giao thông không giới hạn thời gian để cho người lên, xuống phương tiện, xếp dỡ hàng hóa hoặc thực hiện công việc khác.",
            28
        )
        ans2 = Answer(
            86,
            "Là trạng thái đứng yên tạm thời của phương tiện giao thông trong một khoảng thời gian cần thiết đủ để cho người lên, xuống phương tiện, xếp dỡ hàng hóa hoặc thực hiện công việc khác.",
            28
        )
        ans3 = Answer(
            87,
            "Là trạng thái đứng yên của phương tiện giao thông không giới hạn thời gian giữa 02 lần vận chuyển hàng hóa hoặc hành khách.",
            28
        )
        question = Question(
            28,
            typeQuestionLaw,
            false,
            86,
            " Trong các khái niệm dưới đây, khái niệm “dừng xe” được hiểu như thế nào là đúng?",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 29
        ans1 = Answer(88, " Bất cứ đèn nào miễn là mắt nhìn rõ phía trước.", 29)
        ans2 = Answer(89, " Chỉ bật đèn chiếu xa (đèn pha) khi không nhìn rõ đường.", 29)
        ans3 = Answer(
            90,
            " Đèn chiếu xa (đèn pha) khi đường vắng, đèn pha chiếu gần (đèn cốt) khi có xe đi ngược chiều.",
            29
        )
        ans4 = Answer(91, " Đèn chiếu gần (đèn cốt).", 29)
        question = Question(
            29,
            typeQuestionLaw,
            false,
            91,
            " Người lái xe sử dụng đèn như thế nào khi lái xe trong khu đô thị và đông dân cư vào ban đêm?",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        answerDatabase?.answerDao()?.saveData(ans4!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 30
        ans1 = Answer(92, "Từ 22 giờ đêm đến 5 giờ sáng.", 30)
        ans2 = Answer(93, "Từ 5 giờ sáng đến 22 giờ tối.", 30)
        ans3 = Answer(94, "Từ 23 giờ đêm đến 5 giờ sáng hôm sau.", 30)
        question = Question(
            30,
            typeQuestionLaw,
            false,
            93,
            " Khi lái xe trong khu đô thị và đông dân cư trừ các khu vực có biển cấm sử dụng còi, người lái xe được sử dụng còi như thế nào trong các trường hợp dưới đây?",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 31
        ans1 = Answer(95, "Vượt về phía bên phải để đi tiếp.", 31)
        ans2 = Answer(96, "Giảm tốc độ chờ xe container rẽ xong rồi tiếp tục đi.", 31)
        ans3 = Answer(97, "Vượt về phía bên trái để đi tiếp.", 31)
        question = Question(
            31,
            typeQuestionSituations,
            false,
            96,
            "Trong tình huống dưới đây, xe đầu kéo kéo rơ móoc (xe container) đang rẽ phải, xe con màu xanh và xe máy phía sau xe container đi như thế nào để đảm bảo an toàn?",
            R.drawable.cau31,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 32
        ans1 = Answer(
            98,
            "Quan sát nếu thấy không có tàu thì tăng tốc, cho xe vượt qua đường sắt.",
            32
        )
        ans2 = Answer(99, "Dừng lại trước rào chắn một khoảng cách an toàn.", 32)
        ans3 = Answer(
            100,
            "Ra tín hiệu, yêu cầu người gác chắn tàu kéo chậm Barie để xe bạn qua.",
            32
        )
        question = Question(
            32,
            typeQuestionSituations,
            false,
            99,
            "Xe của bạn đang di chuyển gần đến khu vực giao cắt với đường sắt, khi rào chắn đang dịch chuyển, bạn điều khiển xe như thế nào là đúng quy tắc giao thông?",
            R.drawable.cau32,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 33
        ans1 = Answer(
            101,
            "Xe con.",
            33
        )
        ans2 = Answer(102, "Xe mô tô.", 33)
        ans3 = Answer(
            103,
            "Cả 2 xe đều đúng.",
            33
        )
        question = Question(
            33,
            typeQuestionSituations,
            false,
            101,
            "Xe nào dừng đúng theo quy tắc giao thông?",
            R.drawable.cau33,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 34
        ans1 = Answer(
            104,
            "Tăng tốc độ, rẽ phải trước xe tải và xe đạp.",
            34
        )
        ans2 = Answer(105, "Giảm tốc độ, rẽ phải sau xe tải và xe đạp.", 34)
        ans3 = Answer(
            106,
            "Tăng tốc độ, rẽ phải trước xe đạp.",
            34
        )
        question = Question(
            34,
            typeQuestionSituations,
            false,
            105,
            "Bạn xử lý như thế nào trong trường hợp này?",
            R.drawable.cau34,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 35
        ans1 = Answer(
            107,
            "Xe của bạn, mô tô, xe con.",
            35
        )
        ans2 = Answer(108, "Xe con, xe của bạn, mô tô.", 35)
        ans3 = Answer(
            109,
            "Mô tô, xe con, xe của bạn.",
            35
        )
        question = Question(
            35,
            typeQuestionSituations,
            false,
            108,
            "Các xe đi theo thứ nào là đúng quy tắc giao thông đường bộ?",
            R.drawable.cau35,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 36
        ans1 = Answer(
            110,
            "Xe khách.",
            36
        )
        ans2 = Answer(111, "Mô tô.", 36)
        ans3 = Answer(
            112,
            "Xe con.",
            36
        )
        ans4 = Answer(
            113,
            "Xe con và mô tô.",
            36
        )
        question = Question(
            36,
            typeQuestionSituations,
            false,
            112,
            "Xe nào vi phạm quy tắc giao thông?",
            R.drawable.cau36,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        answerDatabase?.answerDao()?.saveData(ans4!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 37
        ans1 = Answer(
            114,
            "Xe con (B), mô tô (C).",
            37
        )
        ans2 = Answer(115, "Xe con (A), mô tô (C).", 37)
        ans3 = Answer(
            116,
            "Xe con (E), mô tô (D).",
            37
        )
        ans4 = Answer(
            117,
            "Tất cả các loại xe trên.",
            37
        )
        question = Question(
            37,
            typeQuestionSituations,
            false,
            116,
            "Trong hình dưới những xe nào vi phạm quy tắc giao thông?",
            R.drawable.cau37,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        answerDatabase?.answerDao()?.saveData(ans4!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 38
        ans1 = Answer(
            118,
            "Xe con ( E ), mô tô ( C ).",
            38
        )
        ans2 = Answer(119, "Xe tải ( A ), mô tô ( D ).", 38)
        ans3 = Answer(
            120,
            "Xe khách ( B ), mô tô ( C ).",
            38
        )
        ans4 = Answer(
            121,
            "Xe khách ( B ), mô tô ( D ).",
            38
        )
        question = Question(
            38,
            typeQuestionSituations,
            false,
            118,
            "Trong hình dưới, những xe nào vi phạm quy tắc giao thông?",
            R.drawable.cau38,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        answerDatabase?.answerDao()?.saveData(ans4!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 39
        ans1 = Answer(
            122,
            "Đúng.",
            39
        )
        ans2 = Answer(123, "Không đúng.", 39)
        question = Question(
            39,
            typeQuestionSituations,
            false,
            123,
            "Xe tải kéo mô tô ba bánh như hình này có đúng quy tắc giao thông không?",
            R.drawable.cau39,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 40
        ans1 = Answer(
            124,
            "Xe mô tô.",
            40
        )
        ans2 = Answer(125, "Xe ô tô con.", 40)
        ans3 = Answer(
            126,
            "Không xe nào vi phạm.",
            40
        )
        ans4 = Answer(127, "Cả hai xe.", 40)
        question = Question(
            40,
            typeQuestionSituations,
            false,
            127,
            "Theo tín hiệu đèn của xe cơ giới, xe nào vi phạm quy tắc giao thông?",
            R.drawable.cau40,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        answerDatabase?.answerDao()?.saveData(ans4!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 41
        ans1 = Answer(
            128,
            "Cho phép.",
            41
        )
        ans2 = Answer(129, "Không được vượt.", 41)
        question = Question(
            41,
            typeQuestionSituations,
            false,
            129,
            "Bạn có được phép vượt xe mô tô phía trước không?",
            R.drawable.cau41,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 42
        ans1 = Answer(
            130,
            "Xe mô tô.",
            42
        )
        ans2 = Answer(131, "Xe con.", 42)
        question = Question(
            42,
            typeQuestionSituations,
            false,
            131,
            "Trường hợp này xe nào được quyền đi trước?",
            R.drawable.cau42,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 43
        ans1 = Answer(
            132,
            "Cả ba hướng.",
            43
        )
        ans2 = Answer(133, "Hướng 1 và 2.", 43)
        ans3 = Answer(
            134,
            "Hướng 1 và 3.",
            43
        )
        ans4 = Answer(135, "Hướng 2 và 3.", 43)
        question = Question(
            43,
            typeQuestionSituations,
            false,
            134,
            "Theo hướng mũi tên, những hướng nào xe mô tô được phép đi?",
            R.drawable.cau43,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        answerDatabase?.answerDao()?.saveData(ans4!!)
        questionDatabase?.questionDao()?.saveData(question!!)
// cau 44
        ans1 = Answer(
            136,
            "Cả ba hướng.",
            44
        )
        ans2 = Answer(137, "Chỉ hướng 1 và 3", 44)
        ans3 = Answer(
            138,
            "Chỉ hướng 1.",
            44
        )
        question = Question(
            44,
            typeQuestionSituations,
            false,
            136,
            "Theo hướng mũi tên, những hướng nào xe gắn máy đi được?",
            R.drawable.cau44,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        questionDatabase?.questionDao()?.saveData(question!!)
// cau 45
        ans1 = Answer(
            139,
            "Xe khách, xe tải, mô tô, xe con.",
            45
        )
        ans2 = Answer(140, "Xe con, xe khách, xe tải, mô tô.", 45)
        ans3 = Answer(
            141,
            "Mô tô, xe tải, xe khách, xe con.",
            45
        )
        ans4 = Answer(142, "Mô tô, xe tải, xe con, xe khách.", 45)
        question = Question(
            45,
            typeQuestionSituations,
            false,
            141,
            "Thứ tự các xe đi như thế nào là đúng quy tắc giao thông?",
            R.drawable.cau45,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        answerDatabase?.answerDao()?.saveData(ans4!!)
        questionDatabase?.questionDao()?.saveData(question!!)
// cau 46
        ans1 = Answer(
            143,
            "Xe con (A), mô tô, xe con (B), xe đạp.",
            46
        )
        ans2 = Answer(144, "Xe con (B), xe đạp, mô tô, xe con (A).", 46)
        ans3 = Answer(
            145,
            "Xe con (A), xe con (B), mô tô + xe đạp.",
            46
        )
        ans4 = Answer(146, "Mô tô + xe đạp, xe con (A), xe con (B).", 46)
        question = Question(
            46,
            typeQuestionSituations,
            false,
            146,
            "Thứ tự các xe đi như thế nào là đúng quy tắc giao thông?",
            R.drawable.cau46,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        answerDatabase?.answerDao()?.saveData(ans4!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 47
        ans1 = Answer(
            143,
            "Xe con (A), mô tô, xe con (B), xe đạp.",
            47
        )
        ans2 = Answer(144, "Xe con (B), xe đạp, mô tô, xe con (A).", 47)
        ans3 = Answer(
            145,
            "Xe con (A), xe con (B), mô tô + xe đạp.",
            47
        )
        ans4 = Answer(146, "Mô tô + xe đạp, xe con (A), xe con (B).", 47)
        question = Question(
            47,
            typeQuestionSituations,
            false,
            146,
            "Thứ tự các xe đi như thế nào là đúng quy tắc giao thông?",
            R.drawable.cau47,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        answerDatabase?.answerDao()?.saveData(ans4!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 48
        ans1 = Answer(
            147,
            "Xe tải, xe con, mô tô.",
            48
        )
        ans2 = Answer(148, "Xe con, xe tải, mô tô.", 48)
        ans3 = Answer(
            149,
            "Mô tô, xe con, xe tải.",
            48
        )
        ans4 = Answer(150, "Xe con, mô tô, xe tải.", 48)
        question = Question(
            48,
            typeQuestionSituations,
            false,
            149,
            "Thứ tự các xe đi như thế nào là đúng quy tắc giao thông?",
            R.drawable.cau48,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        answerDatabase?.answerDao()?.saveData(ans4!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 49
        ans1 = Answer(
            151,
            "Chỉ mô tô.",
            49
        )
        ans2 = Answer(152, "Chỉ xe tải.", 49)
        ans3 = Answer(
            153,
            "Cả 3 xe.",
            49
        )
        ans4 = Answer(154, "Chỉ mô tô và xe tải.", 49)
        question = Question(
            49,
            typeQuestionSituations,
            false,
            153,
            "Xe nào đỗ vi phạm quy tắc giao thông?",
            R.drawable.cau49,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1!!)
        answerDatabase?.answerDao()?.saveData(ans2!!)
        answerDatabase?.answerDao()?.saveData(ans3!!)
        answerDatabase?.answerDao()?.saveData(ans4!!)
        questionDatabase?.questionDao()?.saveData(question!!)
        // cau 50
        ans1 = Answer(
            155,
            "Cả hai xe",
            50
        )
        ans2 = Answer(156, "Không xe nào vi phạm.", 50)
        ans3 = Answer(
            157,
            "Chỉ xe mô tô vi phạm.",
            50
        )
        ans4 = Answer(158, "Chỉ xe tải vi phạm.", 50)
        question = Question(
            50,
            typeQuestionSituations,
            false,
            155,
            "Xe nào đỗ vi phạm quy tắc giao thông?",
            R.drawable.cau50,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        answerDatabase?.answerDao()?.saveData(ans4)
        questionDatabase?.questionDao()?.saveData(question)
        // cau 51
        ans1 = Answer(
            159,
            "Xe tải.",
            51
        )
        ans2 = Answer(160, "Xe con và mô tô.", 51)
        ans3 = Answer(
            161,
            "Cả ba xe.",
            51
        )
        ans4 = Answer(162, "Xe con và xe tải.", 51)
        question = Question(
            51,
            typeQuestionSituations,
            false,
            159,
            "Trong trường hợp này xe nào đỗ vi phạm quy tắc giao thông?",
            R.drawable.cau51,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        answerDatabase?.answerDao()?.saveData(ans4)
        questionDatabase?.questionDao()?.saveData(question)

    }

    private fun initDataBase() {
        examDatabase = Room.databaseBuilder(this, ExamDatabase::class.java, "ExamDB")
            .fallbackToDestructiveMigration().build()
        answerDatabase = Room.databaseBuilder(this, AnswerDatabase::class.java, "AnswerDB")
            .fallbackToDestructiveMigration().build()
        questionDatabase = Room.databaseBuilder(this, QuestionDatabase::class.java, "QuestionDB")
            .fallbackToDestructiveMigration().build()
    }

    private fun initRecycleView() {
        mListExamAdapter = ListExamAdapter(this, this)
        rv_listExam.layoutManager = LinearLayoutManager(this)
        rv_listExam.adapter = mListExamAdapter
        val itemTouchHelperCallback: ItemTouchHelper.SimpleCallback = ListExamItemTouchHelper(
            0, ItemTouchHelper.LEFT, this
        )
        helper = ItemTouchHelper(itemTouchHelperCallback)
        helper?.attachToRecyclerView(rv_listExam)
        for (i in 0..20) {
            arr.add(i)
        }
        mListExamAdapter?.setList(arr)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int, position: Int) {
        if (viewHolder is ListExamAdapter.ViewHolder) {
            val mItem = viewHolder.mItem
            if (direction != ItemTouchHelper.RIGHT) {
                mDialog = MaterialDialog(this)
                    .noAutoDismiss()
                    .customView(R.layout.dialog_confirm_delete)
                mDialog.window?.setDimAmount(0F)
                mDialog.setCancelable(false)
                mDialog.btn_AcceptDiaLogConFirm.setOnClickListener() {
                    arr.removeAt(position)
                    mListExamAdapter?.setList(arr)
                    mDialog.dismiss()
                    cl_list_exam_activity.alpha = 1F
                    Toast.makeText(this, "Xóa thành công!", Toast.LENGTH_SHORT).show()
                }
                mDialog.btn_CancelDialogConfirm.setOnClickListener {
                    cl_list_exam_activity.alpha = 1F
                    mListExamAdapter?.setList(arr)
                    mDialog.dismiss()
                }
                cl_list_exam_activity.alpha = 0.2F
                mDialog.show()

            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_backListExam -> {
                mDialog = MaterialDialog(this)
                    .noAutoDismiss()
                    .customView(R.layout.dialog_confirm_delete)
                mDialog.window?.setDimAmount(0F)
                mDialog.setCancelable(false)
                mDialog.tv_TitleOfCustomDialogConfirm.text = "Bạn có muốn thoát?"
                mDialog.btn_AcceptDiaLogConFirm.setOnClickListener() {
                    this.finish()
                }
                mDialog.btn_CancelDialogConfirm.setOnClickListener {
                    cl_list_exam_activity.alpha = 1F
                    mDialog.dismiss()
                }
                cl_list_exam_activity.alpha = 0.2F
                mDialog.show()
            }
            R.id.btn_addExam -> {
                mDialog = MaterialDialog(this)
                    .noAutoDismiss()
                    .customView(R.layout.dialog_confirm_delete)
                mDialog.window?.setDimAmount(0F)
                mDialog.setCancelable(false)
                mDialog.tv_TitleOfCustomDialogConfirm.text = "Bạn có muốn tạo một đề thi mới ?"
                mDialog.btn_AcceptDiaLogConFirm.setOnClickListener() {
                    arr.add(arr.size)
                    mListExamAdapter?.setList(arr)
                    mDialog.dismiss()
                    cl_list_exam_activity.alpha = 1F
                    Toast.makeText(this, "Thêm thành công!", Toast.LENGTH_SHORT).show()
                }
                mDialog.btn_CancelDialogConfirm.setOnClickListener {
                    cl_list_exam_activity.alpha = 1F
                    mDialog.dismiss()
                }
                cl_list_exam_activity.alpha = 0.2F
                mDialog.show()
            }
        }
    }

    override fun onItemClick(position: Int, mItem: Int) {
        mDialog = MaterialDialog(this)
            .noAutoDismiss()
            .customView(R.layout.dialog_confirm_delete)
        mDialog.window?.setDimAmount(0F)
        mDialog.setCancelable(false)
        mDialog.tv_TitleOfCustomDialogConfirm.text = "Bạn có chắc chắn muốn vào thi ?"
        mDialog.btn_AcceptDiaLogConFirm.setOnClickListener() {
            val intent = Intent(this, DoExamActivity::class.java)
            startActivity(intent)
            mDialog.dismiss()
            cl_list_exam_activity.alpha = 1F
        }
        mDialog.btn_CancelDialogConfirm.setOnClickListener {
            cl_list_exam_activity.alpha = 1F
            mDialog.dismiss()
        }
        cl_list_exam_activity.alpha = 0.2F
        mDialog.show()

    }
}