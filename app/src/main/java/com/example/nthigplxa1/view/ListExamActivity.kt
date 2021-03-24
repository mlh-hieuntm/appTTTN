package com.example.nthigplxa1.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
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
import com.example.nthigplxa1.db.ExamWithQuestionDatabase
import com.example.nthigplxa1.model.Answer
import com.example.nthigplxa1.model.Exam
import com.example.nthigplxa1.model.ExamWithQuestion
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
    private var examWithQuestionDatabase: ExamWithQuestionDatabase? = null
    private var mArrayListAns: ArrayList<Answer> = ArrayList()
    private var mArrayListQues: ArrayList<Question> = ArrayList()
    private var mArrayListExam: ArrayList<Exam> = ArrayList()
    private var mArrayListExamWithQues: ArrayList<ExamWithQuestion> = ArrayList()
    private var mArrayListQuesLaw: ArrayList<Question> = ArrayList()
    private var mArrayListQuesNoticeBoard: ArrayList<Question> = ArrayList()
    private var mArrayListQuesSituations: ArrayList<Question> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_exam)
        initRecycleView()
        initDataBase()
        registrationViewListener()
        checkDbInApp()
    }

    private fun registrationViewListener() {
        btn_addExam.setOnClickListener(this)
        btn_backListExam.setOnClickListener(this)
    }

    private fun getAllDB() {
        answerDatabase?.answerDao()?.readAllData()?.let {
            mArrayListAns = it as ArrayList<Answer>
        }
        questionDatabase?.questionDao()?.readAllData()?.let {
            mArrayListQues = it as ArrayList<Question>
        }
        examDatabase?.examDao()?.readAllData()?.let {
            mArrayListExam = it as ArrayList<Exam>
        }
        examDatabase?.examDao()?.readAllData()?.let {
            mArrayListExam = it as ArrayList<Exam>
        }
//        examWithQuestionDatabase?.examWithQuestionDao()?.readAllData()?.let {
//            mArrayListExamWithQues = it as ArrayList<ExamWithQuestion>
//        }
    }

    private fun checkDbInApp() {
        sharedPreferences = getSharedPreferences("openApp", MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        val isFirstOpenApp = sharedPreferences?.getBoolean(FIRST_TIME_OPEN_APP, false)
        if (!isFirstOpenApp!!) {
            Thread {
                insertDB()
                getAllDB()
                runOnUiThread {
                    setUpFirstState()
                }
            }.start()
            editor?.putBoolean(FIRST_TIME_OPEN_APP, true)
            editor?.commit()
        } else {
            Thread {
                getAllDB()
                runOnUiThread {
                    setUpFirstState()
                }
            }.start()
        }
    }

    private fun setUpFirstState() {
        mListExamAdapter?.setList(mArrayListExam)
        rv_listExam.visibility = View.VISIBLE
        cl_progressbar.visibility = View.GONE
        mArrayListQues.forEach {
            when (it.mTypeQuestion) {
                typeQuestionNoticeBoard -> mArrayListQuesNoticeBoard.add(it)
                typeQuestionSituations -> mArrayListQuesSituations.add(it)
                typeQuestionLaw -> mArrayListQuesLaw.add(it)
            }
        }
    }

    private fun insertDB() {
        var ans1: Answer?
        var ans2: Answer?
        var ans3: Answer?
        var ans4: Answer?
        var question: Question?
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
            147,
            "Xe con (A), mô tô, xe con (B), xe đạp.",
            47
        )
        ans2 = Answer(148, "Xe con (B), xe đạp, mô tô, xe con (A).", 47)
        ans3 = Answer(
            149,
            "Xe con (A), xe con (B), mô tô + xe đạp.",
            47
        )
        ans4 = Answer(150, "Mô tô + xe đạp, xe con (A), xe con (B).", 47)
        question = Question(
            47,
            typeQuestionSituations,
            false,
            150,
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
            151,
            "Xe tải, xe con, mô tô.",
            48
        )
        ans2 = Answer(152, "Xe con, xe tải, mô tô.", 48)
        ans3 = Answer(
            153,
            "Mô tô, xe con, xe tải.",
            48
        )
        ans4 = Answer(154, "Xe con, mô tô, xe tải.", 48)
        question = Question(
            48,
            typeQuestionSituations,
            false,
            153,
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
            155,
            "Chỉ mô tô.",
            49
        )
        ans2 = Answer(156, "Chỉ xe tải.", 49)
        ans3 = Answer(
            157,
            "Cả 3 xe.",
            49
        )
        ans4 = Answer(158, "Chỉ mô tô và xe tải.", 49)
        question = Question(
            49,
            typeQuestionSituations,
            false,
            157,
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
            159,
            "Cả hai xe",
            50
        )
        ans2 = Answer(160, "Không xe nào vi phạm.", 50)
        ans3 = Answer(
            161,
            "Chỉ xe mô tô vi phạm.",
            50
        )
        ans4 = Answer(162, "Chỉ xe tải vi phạm.", 50)
        question = Question(
            50,
            typeQuestionSituations,
            false,
            159,
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
            163,
            "Xe tải.",
            51
        )
        ans2 = Answer(164, "Xe con và mô tô.", 51)
        ans3 = Answer(
            165,
            "Cả ba xe.",
            51
        )
        ans4 = Answer(166, "Xe con và xe tải.", 51)
        question = Question(
            51,
            typeQuestionSituations,
            false,
            163,
            "Trong trường hợp này xe nào đỗ vi phạm quy tắc giao thông?",
            R.drawable.cau51,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        answerDatabase?.answerDao()?.saveData(ans4)
        questionDatabase?.questionDao()?.saveData(question)

        //cau 52
        ans1 = Answer(229, "Phải đảm bảo phụ tùng do đúng nhà sản xuất đó cung cấp.", 52)
        ans2 = Answer(230, "Phải được chấp thuận của cơ quan có thẩm quyền.", 52)
        ans3 =
            Answer(231, "Phải là xe đăng ký và hoạt động tại các khu vực có địa hình phức tạp.", 52)
        question = Question(
            52,
            typeQuestionLaw,
            false,
            230,
            " Trong trường hợp đặc biệt, để được lắp đặt, sử dụng còi, đèn không đúng với thiết kế của nhà sản xuất đối với từng loại xe cơ giới bạn phải đảm bảo yêu cầu nào dưới đây?",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        questionDatabase?.questionDao()?.saveData(question)
        //cau 53
        ans1 = Answer(232, "Không được vượt.", 53)
        ans2 = Answer(233, "Được vượt khi đang đi trên cầu.", 53)
        ans3 = Answer(
            234,
            "Được phép vượt khi qua nơi giao nhau có ít phương tiện cùng tham gia giao thông.",
            53
        )
        ans4 = Answer(235, "Được vượt khi đảm bảo an toàn.", 53)
        question = Question(
            53,
            typeQuestionLaw,
            false,
            235,
            " Bạn đang lái xe phía trước có một xe cảnh sát giao thông không phát tín hiệu ưu tiên bạn có được phép vượt hay không?",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        answerDatabase?.answerDao()?.saveData(ans4)
        questionDatabase?.questionDao()?.saveData(question)

        // 54
        ans1 = Answer(236, "Xe mô tô 2 bánh có dung tích xi-lanh từ 50 cm3 trở lên.", 54)
        ans2 = Answer(237, " Xe gắn máy có dung tích xi-lanh dưới 50cm3.", 54)
        ans3 = Answer(238, " Xe ô tô tải dưới 3.5 tấn; xe chở người đến 9 chỗ ngồi.", 54)
        ans4 = Answer(239, " Tất cả các ý nêu trên.", 54)

        question = Question(
            54,
            typeQuestionLaw,
            false,
            237,
            " Người đủ 16 tuổi được điều khiển các loại xe nào dưới đây?",
            -1,
            ""
        )

        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        answerDatabase?.answerDao()?.saveData(ans4)
        questionDatabase?.questionDao()?.saveData(question)
        //55
        ans1 = Answer(240, "16 Tuổi.", 55)
        ans2 = Answer(241, "18 Tuổi.", 55)
        ans3 = Answer(242, "17 Tuổi.", 55)
        question = Question(
            55,
            typeQuestionLaw,
            false,
            241,
            " Người đủ bao nhiêu tuổi trở lên thì được điều khiển xe mô tô hai bánh, xe mô tô ba bánh có dung tích xi lanh từ 50 cm3 trở lên và các loại xe có kết cấu tương tự; xe ô tô tải, máy kéo có trọng tải dưới 3500kg; xe ô tô chở người đến 9 chỗ ngồi?",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        questionDatabase?.questionDao()?.saveData(question)
        //56
        ans1 = Answer(243, "Xe mô tô có dung tích xi-lanh 125 cm3.", 56)
        ans2 = Answer(244, "Xe mô tô có dung tích xi-lanh từ 175 cm3 trở lên.", 56)
        ans3 = Answer(245, "Xe mô tô có dung tích xi-lanh 100 cm3.", 56)
        question = Question(
            56,
            typeQuestionLaw,
            false,
            244,
            "Người có giấy phép lái xe mô tô hạng A1 không được phép điều khiển loại xe nào dưới đây?",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        questionDatabase?.questionDao()?.saveData(question)
        //57
        ans1 = Answer(246, "Xe mô tô hai bánh có dung tích xi-lanh từ 50 cm3 đến dưới 175 cm3.", 57)
        ans2 = Answer(247, "Xe mô tô ba bánh dùng cho người khuyết tật.", 57)
        ans3 = Answer(248, "Cả ý 1 và ý 2.", 57)
        question = Question(
            57,
            typeQuestionLaw,
            false,
            248,
            " Người có giấy phép lái xe mô tô hạng A1 được phép điều khiển các loại xe nào dưới đây? ",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        questionDatabase?.questionDao()?.saveData(question)
        //58
        ans1 = Answer(249, "Người tham gia giao thông ở các hướng phải dừng lại..", 58)
        ans2 = Answer(
            250,
            "Người tham gia giao thông ở các hướng được đi theo chiều gậy chỉ của cảnh sát giao thông.",
            58
        )
        ans3 = Answer(
            251,
            "Người tham gia giao thông ở phía trước và phía sau người điều khiển được đi tất cả các hướng, người tham gia giao thông ở phía bên phải và phía bên trái người điều khiển phải dừng lại.",
            58
        )
        ans4 = Answer(
            252,
            "Người tham gia giao thông ở phía trước và phía sau người điều khiển phải dừng lại; người tham gia giao thông ở phía bên phải và bên trái người điều khiển được đi tất cả các hướng.",
            58
        )
        question = Question(
            58,
            typeQuestionSituations,
            false,
            252,
            " Khi gặp hiệu lệnh như dưới đây của cảnh sát giao thông thì người tham gia giao thông phải đi như thế nào là đúng quy tắc giao thông? ",
            R.drawable.cau58,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        answerDatabase?.answerDao()?.saveData(ans4)
        questionDatabase?.questionDao()?.saveData(question)
        //59
        ans1 = Answer(
            253,
            "Người tham gia giao thông ở hướng đối diện cảnh sát giao thông được đi, các hướng khác cần phải dừng lại.",
            59
        )
        ans2 = Answer(
            254,
            "Người tham gia giao thông được rẽ phải theo chiều mũi tên màu xanh ở bục cảnh sát giao thông.",
            59
        )
        ans3 = Answer(
            255,
            "Người tham gia giao thông ở các hướng đều phải dừng lại trừ các xe đã ở trong khu vực giao nhau.",
            59
        )
        ans4 = Answer(
            256,
            "Người ở hướng đối diện cảnh sát giao thông phải dừng lại, các hướng khác được đi trong đó có bạn.",
            59
        )
        question = Question(
            59,
            typeQuestionSituations,
            false,
            255,
            "Khi gặp hiệu lệnh như dưới đây của cảnh sát giao thông thì người tham gia giao thông phải đi như thế nào là đúng quy tắc giao thông?",
            R.drawable.cau59,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        answerDatabase?.answerDao()?.saveData(ans4)
        questionDatabase?.questionDao()?.saveData(question)
        //60
        ans1 = Answer(257, "Đỏ – Vàng – Xanh.", 60)
        ans2 = Answer(258, "Cam – Vàng – Xanh.", 60)
        ans3 = Answer(259, "Vàng – Xanh dương – Xanh lá.", 60)
        ans4 = Answer(260, "Đỏ – Cam – Xanh.", 60)
        question = Question(
            60,
            typeQuestionLaw,
            false,
            257,
            " Theo luật giao thông đường bộ, tín hiệu đèn giao thông gồm 3 màu nào dưới đây? ",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        answerDatabase?.answerDao()?.saveData(ans4)
        questionDatabase?.questionDao()?.saveData(question)
        //61
        ans1 = Answer(261, "Biển báo nguy hiểm.", 61)
        ans2 = Answer(262, "Biển báo cấm.", 61)
        ans3 = Answer(263, "Biển báo hiệu lệnh.", 61)
        ans4 = Answer(264, "Biển báo chỉ dẫn.", 61)
        question = Question(
            61,
            typeQuestionNoticeBoard,
            false,
            262,
            " Biển báo hiệu có dạng hình tròn, viền đỏ, nền trắng, trên nền có hình vẽ hoặc chữ số, chữ viết màu đen là loại biển gì dưới đây?",
            R.drawable.cau61,
            ""
        )

        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        answerDatabase?.answerDao()?.saveData(ans4)
        questionDatabase?.questionDao()?.saveData(question)
        //62
        ans1 = Answer(265, " Biển báo nguy hiểm.", 62)
        ans2 = Answer(266, "Biển báo cấm.", 62)
        ans3 = Answer(267, "Biển báo hiệu lệnh.", 62)
        ans4 = Answer(268, "Biển báo chỉ dẫn.", 62)
        question = Question(
            62,
            typeQuestionNoticeBoard,
            false,
            265,
            " Biển báo hiệu có dạng hình tam giác đều, viền đỏ, nền màu vàng, trên có hình vẽ màu đen là loại biển gì dưới đây?",
            R.drawable.cau62,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        answerDatabase?.answerDao()?.saveData(ans4)
        questionDatabase?.questionDao()?.saveData(question)
        //63
        ans1 = Answer(269, "Biển báo nguy hiểm.", 63)
        ans2 = Answer(270, "Biển báo cấm.", 63)
        ans3 = Answer(271, "Biển báo hiệu lệnh phải thi hành.", 63)
        ans4 = Answer(272, "Biển báo chỉ dẫn.", 63)
        question = Question(
            63,
            typeQuestionNoticeBoard,
            false,
            271,
            " Biển báo hiệu hình tròn có nền xanh lam có hình vẽ màu trắng là loại gì dưới đây?",
            R.drawable.cau63,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        answerDatabase?.answerDao()?.saveData(ans4)
        questionDatabase?.questionDao()?.saveData(question)
        //64
        ans1 = Answer(273, "Biển báo nguy hiểm.", 64)
        ans2 = Answer(274, "Biển báo cấm.", 64)
        ans3 = Answer(275, "Biển báo hiệu lệnh phải thi hành.", 64)
        ans4 = Answer(276, "Biển báo chỉ dẫn.", 64)
        question = Question(
            64,
            typeQuestionNoticeBoard,
            false,
            276,
            " Biển báo hiệu hình chữ nhật hoặc hình vuông hoặc hình mũi tên nền xanh lam là loại biển gì dưới đây?",
            R.drawable.cau64,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        answerDatabase?.answerDao()?.saveData(ans4)
        questionDatabase?.questionDao()?.saveData(question)
        //65
        ans1 = Answer(277, "Biển báo hiệu cố định.", 65)
        ans2 = Answer(278, "Báo hiệu tạm thời.", 65)
        question = Question(
            65,
            typeQuestionLaw,
            false,
            278,
            "Tại nơi có biển báo hiệu cố định lại có báo hiệu tạm thời thì người tham gia giao thông phải chấp hành hiệu lệnh của báo hiệu nào? ",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        questionDatabase?.questionDao()?.saveData(question)
        //66
        ans1 = Answer(279, "02 Năm.", 66)
        ans2 = Answer(280, "03 Năm.", 66)
        ans3 = Answer(281, "04 Năm.", 66)
        ans4 = Answer(282, "05 Năm.", 66)
        question = Question(
            66,
            typeQuestionLaw,
            false,
            281,
            " Khi sử dụng giấy phép lái xe đã khai báo mất để điều khiển phương tiện cơ giới đường bộ, ngoài việc bị thu hồi giấy phép lái xe, chịu trách nhiệm trước pháp luật, người lái xe không được cấp giấy phép lái xe trong thời gian bao nhiêu năm?",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        answerDatabase?.answerDao()?.saveData(ans4)
        questionDatabase?.questionDao()?.saveData(question)
        //67
        ans1 = Answer(
            283,
            "Cho xe đi trên bất kỳ làn đường nào hoặc giữa 02 làn đường nếu không có xe phía trước; khi cần thiết phải chuyển làn đường, người lái xe phải quan sát xe phía trước để đảm bảo an toàn.",
            67
        )
        ans2 = Answer(
            284,
            "Phải cho xe đi trong một làn đường và chỉ được chuyển làn đường ở những nơi cho phép; khi chuyển làn phải có tín hiệu báo trước và phải bảo đảm an toàn.",
            67
        )
        ans3 = Answer(
            285,
            "Phải cho xe đi trong một làn đường và chỉ được chuyển làn đường ở những nơi cho phép; khi chuyển làn phải có tín hiệu báo trước và phải bảo đảm an toàn.",
            67
        )

        question = Question(
            67,
            typeQuestionLaw,
            false,
            284,
            "Trên đường có nhiều làn đường cho xe đi cùng chiều được phân biệt bằng vạch kẻ phân làn đường, người điều khiển phương tiện phải cho xe đi như thế nào?",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        questionDatabase?.questionDao()?.saveData(question)
        //68
        ans1 = Answer(
            28311,
            "Cho xe đi trên bất kỳ làn đường nào hoặc giữa 02 làn đường nếu không có xe phía trước; khi cần thiết phải chuyển làn đường, người lái xe phải quan sát xe phía trước để đảm bảo an toàn.",
            68
        )
        ans2 = Answer(
            28411,
            "Phải cho xe đi trong một làn đường và chỉ được chuyển làn đường ở những nơi cho phép; khi chuyển làn phải có tín hiệu báo trước và phải bảo đảm an toàn.",
            68
        )
        ans3 = Answer(
            28511,
            "Phải cho xe đi trong một làn đường và chỉ được chuyển làn đường ở những nơi cho phép; khi chuyển làn phải có tín hiệu báo trước và phải bảo đảm an toàn.",
            68
        )

        question = Question(
            68,
            typeQuestionLaw,
            false,
            28411,
            "Trên đường có nhiều làn đường cho xe đi cùng chiều được phân biệt bằng vạch kẻ phân làn đường, người điều khiển phương tiện phải cho xe đi như thế nào?",
            -1,
            ""
        )

        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        questionDatabase?.questionDao()?.saveData(question)
        //69
        ans1 = Answer(
            286,
            " Xe thô sơ phải đi trên làn đường bên trái ngoài cùng, xe cơ giới, xe máy chuyên dùng đi trên làn đường bên phải.",
            69
        )
        ans2 = Answer(
            287,
            " Xe thô sơ phải đi trên làn đường bên phải trong cùng; xe cơ giới, xe máy chuyên dùng đi trên làn đường bên trái.",
            69
        )
        ans3 = Answer(
            288,
            " Xe thô sơ đi trên làn đường phù hợp không gây cản trở giao thông, xe cơ giới, xe máy chuyên dùng đi trên làn đường bên phải.",
            69
        )
        question = Question(
            69,
            typeQuestionLaw,
            false,
            287,
            " Trên đường một chiều có vạch kẻ phân làn đường xe thô sơ và xe cơ giới phải đi như thế nào là đúng quy tắc giao thông?",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        questionDatabase?.questionDao()?.saveData(question)
        //70
        ans1 = Answer(289, "Phải báo hiệu bằng đèn hoặc còi.", 70)
        ans2 = Answer(290, "Chỉ được báo hiệu bằng còi.", 70)
        ans3 = Answer(291, "Phải báo hiệu bằng cả còi và đèn.", 70)
        ans4 = Answer(292, "Chỉ được báo hiệu bằng đèn.", 70)
        question = Question(
            70,
            typeQuestionLaw,
            false,
            292,
            " Bạn đang lái xe trong khu vực đô thị từ 22 giờ đến 5 giờ sáng hôm sau và cần vượt một xe khác, bạn cần báo hiệu như thế nào để đảm bảo an toàn giao thông?",
            -1,
            ""
        )

        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        answerDatabase?.answerDao()?.saveData(ans4)
        questionDatabase?.questionDao()?.saveData(question)
        //71
        ans1 = Answer(
            293,
            "Tăng tốc độ và ra hiệu cho xe sau vượt, không được gây trở ngại cho xe sau vượt.",
            71
        )
        ans2 = Answer(
            294,
            "Người điều khiển phương tiện phía trước phải giảm tốc độ, đi sát về bên phải của phần đường xe chạy, cho đến khi xe sau đã vượt qua, không được gây trở ngại cho xe sau vượt.",
            71
        )
        ans3 = Answer(
            295,
            "Cho xe tránh về bên trái mình và ra hiệu cho xe sau vượt; nếu có chướng ngại vật phía trước hoặc thiếu điều kiện an toàn chưa cho vượt được phải ra hiệu cho xe sau biết; cấm gây trở ngại cho xe xin vượt.",
            71
        )
        question = Question(
            71,
            typeQuestionLaw,
            false,
            294,
            " Khi điều khiển xe chạy trên đường biết có xe sau xin vượt nếu đủ điều kiện an toàn người lái xe phải làm gì?",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        questionDatabase?.questionDao()?.saveData(question)
        //72
        ans1 = Answer(296, "Quan sát gương, ra tín hiệu, quan sát an toàn và chuyển hướng.", 72)
        ans2 = Answer(
            297,
            "Quan sát gương, giảm tốc độ, ra tín hiệu chuyển hướng, quan sát an toàn và chuyển hướng.",
            72
        )
        ans3 = Answer(298, " Quan sát gương, tăng tốc độ, ra tín hiệu và chuyển hướng.", 72)
        question = Question(
            72,
            typeQuestionLaw,
            false,
            297,
            " Khi muốn chuyển hướng, người lái xe phải thực hiện như thế nào để đảm bảo an toàn giao thông? ",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        questionDatabase?.questionDao()?.saveData(question)
        //73
        ans1 = Answer(
            299,
            " Nơi đường hẹp chỉ đủ cho một xe chạy và có chỗ tránh xe thì xe nào ở gần chỗ tránh hơn phải vào vị trí tránh, nhường đường cho xe kia đi.",
            73
        )
        ans2 = Answer(
            300,
            " Xe xuống dốc phải nhường đường cho xe đang lên dốc; xe nào có chướng ngại vật phía trước phải nhường đường cho xe không có chướng ngại vật đi trước.",
            73
        )
        ans3 = Answer(
            301,
            " Xe lên dốc phải nhường đường cho xe xuống dốc; xe nào không có chướng ngại vật phía trước phải nhường đường cho xe có chướng ngại vật đi trước.",
            73
        )
        ans4 = Answer(302, " Cả ý 1 và ý 2.", 73)
        question = Question(
            73,
            typeQuestionLaw,
            false,
            302,
            " Khi tránh xe đi ngược chiều, các xe phải nhường đường như thế nào là đúng quy tắc giao thông?",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        answerDatabase?.answerDao()?.saveData(ans4)
        questionDatabase?.questionDao()?.saveData(question)
        //74
        ans1 = Answer(304, "Tiếp tục đi và xe lên dốc phải nhường đường cho mình.", 74)
        ans2 = Answer(305, "Nhường đường cho xe lên dốc.", 74)
        ans3 = Answer(306, "Chỉ nhường đường khi xe lên dốc nháy đèn.", 74)
        question = Question(
            74,
            typeQuestionLaw,
            false,
            305,
            " Bạn đang lái xe trên đường hẹp, xuống dốc và gặp một xe đang đi lên dốc, bạn cần làm gì?",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        questionDatabase?.questionDao()?.saveData(question)
        //75
        ans1 = Answer(307, "Nhường đường cho xe đi ở bên phải mình tới.", 75)
        ans2 = Answer(308, "Nhường đường cho xe đi ở bên trái mình tới.", 75)
        ans3 = Answer(
            309,
            "Nhường đường cho xe đi trên đường ưu tiên hoặc đường chính từ bất kỳ hướng nào tới.",
            75
        )
        question = Question(
            75,
            typeQuestionLaw,
            false,
            309,
            " Tại nơi đường giao nhau, người lái xe đang đi trên đường không ưu tiên phải nhường đường như thế nào là đúng quy tắc giao thông?",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        questionDatabase?.questionDao()?.saveData(question)
        //76
        ans1 = Answer(310, " Phải nhường đường cho xe đi đến từ bên phải.", 76)
        ans2 = Answer(311, " Xe báo hiệu xin đường trước xe đó được đi trước.", 76)
        ans3 = Answer(312, " Phải nhường đường cho xe đi đến từ bên trái.", 76)
        question = Question(
            76,
            typeQuestionLaw,
            false,
            310,
            " Tại nơi đường giao nhau không có báo hiệu đi theo vòng xuyến, người điều khiển phương tiện phải nhường đường như thế nào là đúng quy tắc giao thông?",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        questionDatabase?.questionDao()?.saveData(question)
        //77
        ans1 = Answer(313, "Chở người bệnh đi cấp cứu; trẻ em dưới 14 tuổi.", 77)
        ans2 = Answer(314, "Áp giải người có hành vi vi phạm pháp luật.", 77)
        ans3 = Answer(315, "Cả ý 1 và ý 2.", 77)
        question = Question(
            77,
            typeQuestionLaw,
            false,
            315,
            "Người điều khiển xe mô tô hai bánh, xe gắn máy được phép chở tối đa 2 người trong những trường hợp nào?",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        questionDatabase?.questionDao()?.saveData(question)
        //78
        ans1 = Answer(
            316,
            " Đi vào phần đường dành cho người đi bộ và phương tiện khác; sử dụng ô, điện thoại di động, thiết bị âm thanh (trừ thiết bị trợ thính), đi xe dàn hàng ngang.",
            78
        )
        ans2 = Answer(
            317,
            " Chở 02 người; trong đó, có người bệnh đi cấp cứu hoặc trẻ em dưới 14 tuổi hoặc áp giải người có hành vi vi phạm pháp luật.",
            78
        )
        ans3 = Answer(
            318,
            " Điều khiển phương tiện tham gia giao thông trên đường tỉnh lộ hoặc quốc lộ.",
            78
        )
        question = Question(
            78,
            typeQuestionLaw,
            false,
            316,
            " Người điều khiển xe mô tô hai bánh, xe gắn máy không được thực hiện những hành vi nào dưới dây?",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        questionDatabase?.questionDao()?.saveData(question)
        //79
        ans1 = Answer(
            319,
            "Khi có báo hiệu cảnh báo nguy hiểm hoặc có chướng ngại vật trên đường; khi chuyển hướng xe chạy hoặc tầm nhìn bị hạn chế; khi qua nơi đường giao nhau, nơi đường bộ giao nhau với đường sắt; đường vòng; đường có địa hình quanh co, đèo dốc.",
            79
        )
        ans2 = Answer(
            320,
            "Khi qua cầu, cống hẹp; khi lên gần đỉnh dốc, khi xuống dốc, khi qua trường học, khu đông dân cư, khu vực đang thi công trên đường bộ; hiện trường xảy ra tai nạn giao thông.",
            79
        )
        ans3 = Answer(321, "Khi điều khiển xe vượt xe khác trên đường quốc lộ, đường cao tốc.", 79)
        ans4 = Answer(322, "Cả ý 1 và ý 2.", 79)
        question = Question(
            79,
            typeQuestionLaw,
            false,
            322,
            " Người lái xe phải giảm tốc độ thấp hơn tốc độ tối đa cho phép (có thể dừng lại một cách an toàn) trong trường hợp nào dưới đây?",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        answerDatabase?.answerDao()?.saveData(ans4)
        questionDatabase?.questionDao()?.saveData(question)
        //80
        ans1 = Answer(323, "Khi cho xe chạy thẳng.", 80)
        ans2 = Answer(324, "Trước khi thay đổi làn đường.", 80)
        ans3 = Answer(325, "Sau khi thay đổi làn đường.", 80)
        question = Question(
            80,
            typeQuestionLaw,
            false,
            324,
            " Khi điều khiển xe cơ giới, người lái xe phải bật đèn tín hiệu báo rẽ trong các trường hợp nào sau đây?",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        questionDatabase?.questionDao()?.saveData(question)
        //81
        ans1 = Answer(326, "Xe bị vượt bất ngờ tăng tốc độ và cố tình không nhường đường.", 81)
        ans2 = Answer(327, "Xe bị vượt giảm tốc độ và nhường đường.", 81)
        ans3 = Answer(328, "Phát hiện có xe đi ngược chiều.", 81)
        ans4 = Answer(329, "Cả ý 1 và ý 2.", 81)
        question = Question(
            81,
            typeQuestionLaw,
            false,
            329,
            " Trên đoạn đường hai chiều không có giải phân cách giữa, người lái xe không được vượt xe khác trong các trường hợp nào dưới đây?",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        answerDatabase?.answerDao()?.saveData(ans4)
        questionDatabase?.questionDao()?.saveData(question)
        //82
        ans1 = Answer(
            330,
            "Nhường đường cho người đi bộ đang đi trên phần đường dành cho người đi bộ sang đường; nhường đường cho xe đi trên đường ưu tiên, đường chính từ bất kì hướng nào tới; nhường đường cho xe ưu tiên, xe đi từ bên phải đến.",
            82
        )
        ans2 = Answer(
            331,
            "Nhường đường cho người đi bộ đang đứng chờ đi qua phần đường dành cho người đi bộ sang đường; nhường đường cho xe đi trên đường ngược chiều, đường nhánh từ bất kỳ hướng nào tới; nhường đường cho xe đi từ bên trái đến.",
            82
        )
        ans3 = Answer(332, "Không phải nhường đường.", 82)
        question = Question(
            82,
            typeQuestionLaw,
            false,
            330,
            "Tại ngã ba hoặc ngã tư không có đảm bảo an toàn, người lái xe phải nhường đường như thế nào là đúng trong các trường hợp dưới đây?",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        questionDatabase?.questionDao()?.saveData(question)
        //83
        ans1 = Answer(
            333,
            " Nếu đủ điều khiện an toàn, người lái xe phải giảm tốc độ, đi sát về bên phải của phần đường xe chạy cho đến khi xe sau đã vượt qua, không được gây trở ngại đối với xe xin vượt.",
            83
        )
        ans2 = Answer(
            334,
            " Lái xe vào lề đường bên trái và giảm tốc độ để xe phía sau vượt qua, không được gây trở ngại đối với xe xin vượt.",
            83
        )
        ans3 = Answer(
            335,
            " Nếu đủ điều kiện an toàn, người lái xe phải tăng tốc độ, đi sát về bên phải của phần đường xe chạy cho đến khi xe sau đã vượt qua.",
            83
        )
        question = Question(
            83,
            typeQuestionLaw,
            false,
            333,
            " Người lái xe mô tô xử lý như thế nào khi cho xe mô tô phía sau vượt? ",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        questionDatabase?.questionDao()?.saveData(question)
        //84
        ans1 = Answer(
            336,
            "Phải đội mũ bảo hiểm đạt chuẩn, có cài quai đúng quy cách, mặc quần áo gọn gàng; không sử dụng ô, điện thoại di động, thiết bị âm thanh (trừ thiết bị trợ thính).",
            84
        )
        ans2 = Answer(
            337,
            "Phải đội mũ bảo hiểm khi trời mưa gió hoặc trời quá nắng; có thể sử dụng ô, điện thoại di động thiết bị âm thanh nhưng đảm bảo an toàn.",
            84
        )
        ans3 = Answer(
            338,
            "Phải đội mũ bảo hiểm khi cảm thấy mất an toàn giao thông hoặc khi chuẩn bị di chuyển quãng đường xa.",
            84
        )
        question = Question(
            84,
            typeQuestionLaw,
            false,
            336,
            "Trong các trường hợp dưới đây, để đảo bảo an toàn khi tham gia giao thông, người lái xe mô tô cần thực hiện như thế nào?",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        questionDatabase?.questionDao()?.saveData(question)
        //85
        ans1 = Answer(
            339,
            "Là đoạn đường nằm trong khu công nghiệp có đông người và phương tiện tham gia giao thông và được xác định cụ thể bằng biển chỉ dẫn địa giới.",
            85
        )
        ans2 = Answer(
            340,
            "Là đoạn đường bộ nằm trong khu vực nội thành phố, nội thị xã, nội thị trấn và những đoạn đường có đông dân cư sinh sống sát dọc theo đường, có các hoạt động có thể ảnh hưởng đến an toàn giao thông; được xác định bằng biển báo hiệu là đường khu đông dân cư.",
            85
        )
        ans3 = Answer(
            341,
            "Là đoạn đường nằm ngoài khu vực nội thành phố, nội thị xã có đông người và phương tiện tham gia giao thông và được xác định cụ thể bằng biển chỉ dẫn địa giới.",
            85
        )
        question = Question(
            85,
            typeQuestionLaw,
            false,
            340,
            "Đường bộ trong khu vực đông dân cư gồm những đoạn đường nào dưới đây? ",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        questionDatabase?.questionDao()?.saveData(question)
        //86
        ans1 = Answer(342, "50 km/h.", 86)
        ans2 = Answer(343, "40 km/h.", 86)
        ans3 = Answer(344, "60 km/h.", 86)
        question = Question(
            86,
            typeQuestionLaw,
            false,
            343,
            " Tốc độ tối đa cho phép đối với xe máy chuyên dùng, xe gắn máy (kể cả xe máy điện) và các loại xe tương tự trên đường bộ (trừ đường cao tốc) không được vượt quá bao nhiêu km/h?",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        questionDatabase?.questionDao()?.saveData(question)
        //87
        ans1 = Answer(345, "60 km/h.", 87)
        ans2 = Answer(346, "50 km/h.", 87)
        ans3 = Answer(347, "40 km/h.", 87)
        question = Question(
            87,
            typeQuestionLaw,
            false,
            345,
            " Trên đường bộ (trừ đường cao tốc) trong khu vực đông dân cư, đường đôi có dải phân cách giữa, xe mô tô hai bánh, ô tô chở người đến 30 chỗ tham gia giao thông với tốc độ tối đa cho phép là bao nhiêu?",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        questionDatabase?.questionDao()?.saveData(question)
        //88
        ans1 = Answer(348, "60 km/h.", 88)
        ans2 = Answer(349, "50 km/h.", 88)
        ans3 = Answer(350, "40 km/h.", 88)
        question = Question(
            88,
            typeQuestionLaw,
            false,
            349,
            " Trên đường bộ (trừ đường cao tốc) trong khu vực đông dân cư, đường hai chiều không có dải phân cách giữa, xe mô tô hai bánh, ô tô chở người đến 30 chỗ tham gia giao thông với tốc độ tối đa cho phép là bao nhiêu?",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        questionDatabase?.questionDao()?.saveData(question)
        //89
        ans1 = Answer(351, "Ô tô con, ô tô tải, ô tô chở người trên 30 chỗ.", 89)
        ans2 = Answer(352, "Xe gắn máy, xe máy chuyên dung.", 89)
        ans3 = Answer(353, "Cả ý 1 và ý 2.", 89)
        question = Question(
            89,
            typeQuestionLaw,
            false,
            351,
            " Trên đường bộ (trừ đường cao tốc) trong khu vực đông dân cư, đường hai chiều hoặc đường một chiều có một làn xe cơ giới, loại xe nào dưới đây được tham gia giao thông với tốc độ tối đa cho phép là 50 km/h?",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        questionDatabase?.questionDao()?.saveData(question)
        //90
        ans1 = Answer(
            354,
            "Chủ động giữ khoảng cách an toàn phù hợp với xe chạy liền trước xe của mình.",
            90
        )
        ans2 = Answer(
            355,
            "Đảm bảo khoảng cách an toàn theo mật độ phương tiện, tình hình giao thông thực tế.",
            90
        )
        ans3 = Answer(356, "Cả ý 1 và ý 2.", 90)
        question = Question(
            90,
            typeQuestionLaw,
            false,
            356,
            " Khi điều khiển xe chạy với tốc độ dưới 60km/h, để đảm bảo khoảng cách an toàn giữa hai xe, người lái xe phải điều khiển xe như thế nào?",
            -1,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        answerDatabase?.answerDao()?.saveData(ans3)
        questionDatabase?.questionDao()?.saveData(question)
        //91
        ans1 = Answer(
            3541,
            "Biển 1.",
            91
        )
        ans2 = Answer(
            3552,
            "Biển 2.",
            91
        )
        question = Question(
            91,
            typeQuestionNoticeBoard,
            false,
            3541,
            "Biển báo nào báo hiệu bắt đầu đoạn đường vào phạm vi khu dân cư, các phương tiện tham gia giao thông phải tuân thủ các quy định đi đường được áp dụng ở khu đông dân cư?",
            R.drawable.cau91,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
        questionDatabase?.questionDao()?.saveData(question)
        //92

        ans1 = Answer(
            35411,
            "Biển 1.",
            92
        )
        ans2 = Answer(
            35522,
            "Biển 2.",
            92
        )
        question = Question(
            92,
            typeQuestionNoticeBoard,
            false,
            35411,
            "Biển nào báo hiệu”Hướng đi thẳng phải theo” ?",
            R.drawable.cau92,
            ""
        )
        answerDatabase?.answerDao()?.saveData(ans1)
        answerDatabase?.answerDao()?.saveData(ans2)
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
                    mArrayListExam.removeAt(position)
                    mListExamAdapter?.setList(mArrayListExam)
                    mDialog.dismiss()
                    cl_list_exam_activity.alpha = 1F
                    Toast.makeText(this, "Xóa thành công!", Toast.LENGTH_SHORT).show()
                }
                mDialog.btn_CancelDialogConfirm.setOnClickListener {
                    cl_list_exam_activity.alpha = 1F
                    mListExamAdapter?.setList(mArrayListExam)
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

                    // random 15 ques law
                    var arrLaw = ArrayList<Question>()
                    for (i in 1..15) {
                        var pos = (0 until this.mArrayListQuesLaw.size).random()
                        var ques = mArrayListQuesLaw[pos]
                        while (arrLaw.contains(ques)) {
                            pos = (0 until this.mArrayListQuesLaw.size).random()
                            ques = mArrayListQuesLaw[pos]
                        }
                        arrLaw.add(ques)
                    }
                    // random 5 ques NoticeBoard
                    var arrNoticeBoard = ArrayList<Question>()
                    for (i in 1..5) {
                        var pos = (0 until this.mArrayListQuesNoticeBoard.size).random()
                        var ques = mArrayListQuesNoticeBoard[pos]
                        while (arrNoticeBoard.contains(ques)) {
                            pos = (0 until this.mArrayListQuesNoticeBoard.size).random()
                            ques = mArrayListQuesNoticeBoard[pos]
                        }
                        arrNoticeBoard.add(ques)
                    }
                    // random 5 ques law
                    var arrSituations = ArrayList<Question>()
                    for (i in 1..5) {
                        var pos = (0 until this.mArrayListQuesSituations.size).random()
                        var ques = mArrayListQuesSituations[pos]
                        while (arrSituations.contains(ques)) {
                            pos = (0 until this.mArrayListQuesSituations.size).random()
                            ques = mArrayListQuesSituations[pos]
                        }
                        arrSituations.add(ques)
                    }
                    Thread {
                        var exam = Exam(mArrayListExam.size, false, 19)
                        examDatabase?.examDao()?.saveData(exam)
                        arrLaw.forEach {
                            examWithQuestionDatabase?.examWithQuestionDao()
                                ?.saveData(ExamWithQuestion(-1, it.mID, exam.mID))
                        }
                        arrNoticeBoard.forEach {
                            examWithQuestionDatabase?.examWithQuestionDao()
                                ?.saveData(ExamWithQuestion(-1, it.mID, exam.mID))
                        }
                        arrSituations.forEach {
                            examWithQuestionDatabase?.examWithQuestionDao()
                                ?.saveData(ExamWithQuestion(-1, it.mID, exam.mID))
                        }
                        runOnUiThread {
                            mArrayListExam.add(exam)
                            mListExamAdapter?.setList(mArrayListExam)
                            mDialog.dismiss()
                            cl_list_exam_activity.alpha = 1F
                            Toast.makeText(this, "Thêm thành công!", Toast.LENGTH_SHORT).show()
                        }
                    }.start()

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

    override fun onItemClick(position: Int, mItem: Exam) {
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