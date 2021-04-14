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
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.nthigplxa1.R
import com.example.nthigplxa1.adapter.ItemListener
import com.example.nthigplxa1.adapter.ListExamAdapter
import com.example.nthigplxa1.adapter.ListExamItemTouchHelper
import com.example.nthigplxa1.db.*
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
    private var appDatabase: AppDatabase? = null
    private var mArrayListAns: ArrayList<Answer> = ArrayList()
    private var mArrayListQues: ArrayList<Question> = ArrayList()
    private var mArrayListExam: ArrayList<Exam> = ArrayList()
    private var mArrayListExamWithQues: ArrayList<ExamWithQuestion> = ArrayList()
    private var mArrayListQuesLaw: ArrayList<Question> = ArrayList()
    private var mArrayListQuesNoticeBoard: ArrayList<Question> = ArrayList()
    private var mArrayListQuesSituations: ArrayList<Question> = ArrayList()
    private var mArrayListParalysisPoint: ArrayList<Question> = ArrayList()
    private var mAns1: Answer? = null
    private var mAns2: Answer? = null
    private var mAns3: Answer? = null
    private var mAns4: Answer? = null
    private var mQuestion: Question? = null
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
        appDatabase?.appDao()?.readAllDataAnswer()?.let {
            mArrayListAns = it as ArrayList<Answer>
        }
        appDatabase?.appDao()?.readAllDataQuestion()?.let {
            mArrayListQues = it as ArrayList<Question>
        }
        appDatabase?.appDao()?.readAllDataExam()?.let {
            mArrayListExam = it as ArrayList<Exam>
        }
        appDatabase?.appDao()?.readAllData()?.let {
            mArrayListExamWithQues = it as ArrayList<ExamWithQuestion>
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
        ans3 = Answer(3, "Được cơ quan có thẩm quyền cấp phép.", 1,true)
        question = Question(
            1,
            typeQuestionLaw,
            true,
            "Cuộc đua xe chỉ được thực hiện khi nào ?",
            -1,
            "Hành vi đua xe chưa được cơ quan có thẩm quyền cấp phép là sai quy định của pháp luật."
        )
        saveDb(ans1, ans2, ans3, null, question)
    }

    private fun saveDb(ans1: Answer?, ans2: Answer?, ans3: Answer?, ans4: Answer?, question: Question) {
        ans1?.let {
            appDatabase?.appDao()?.saveDataAnswer(it)
        }
        ans2?.let {
            appDatabase?.appDao()?.saveDataAnswer(it)
        }
        ans3?.let {
            appDatabase?.appDao()?.saveDataAnswer(it)
        }
        ans4?.let {
            appDatabase?.appDao()?.saveDataAnswer(it)
        }
        appDatabase?.appDao()?.saveDataQuestion(question)
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
        if (mArrayListExam.isEmpty()) {
            tv_Suggest.visibility = View.VISIBLE
            rv_listExam.visibility = View.INVISIBLE
        } else {
            mListExamAdapter?.setList(mArrayListExam)
            rv_listExam.visibility = View.VISIBLE
        }

        pg.visibility = View.GONE
        mArrayListQues.forEach {
            when (it.mTypeQuestion) {
                typeQuestionNoticeBoard -> mArrayListQuesNoticeBoard.add(it)
                typeQuestionSituations -> mArrayListQuesSituations.add(it)
                typeQuestionLaw -> mArrayListQuesLaw.add(it)
            }
            if (it.mIsParalysisPoint) {
                mArrayListParalysisPoint.add(it)
            }
        }
    }

    private fun initDataBase() {
        appDatabase = AppDatabase.getDatabase(this)
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
            if (direction != ItemTouchHelper.RIGHT) {
                mDialog = MaterialDialog(this)
                    .noAutoDismiss()
                    .customView(R.layout.dialog_confirm_delete)
                mDialog.window?.setDimAmount(0F)
                mDialog.setCancelable(false)
                mDialog.btn_AcceptDiaLogConFirm.setOnClickListener() {
                    Thread {
                        appDatabase?.appDao()?.deleteExam(mArrayListExam[position])
                        runOnUiThread {
                            mArrayListExam.removeAt(position)
                            mListExamAdapter?.setList(mArrayListExam)
                            mDialog.dismiss()
                            cl_list_exam_activity.alpha = 1F
                            Toast.makeText(this, "Xóa thành công!", Toast.LENGTH_SHORT).show()
                            if (mArrayListExam.isEmpty()) {
                                tv_Suggest.visibility = View.VISIBLE
                            }
                        }
                    }.start()

                }
                mDialog.btn_CancelDialogConfirm.setOnClickListener {
                    cl_list_exam_activity.alpha = 1F
                    mListExamAdapter?.notifyItemChanged(position)
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
                mDialog.tv_TitleOfCustomDialogConfirm.text = "Bạn có muốn quay lại?"
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
                    createExam()
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

    private fun createExam() {
        var arrParalysisPoint = ArrayList<Question>()
        for (i in 1..5) {
            var pos = (0 until this.mArrayListParalysisPoint.size).random()
            var ques = mArrayListParalysisPoint[pos]
            while (arrParalysisPoint.contains(ques)) {
                pos = (0 until this.mArrayListParalysisPoint.size).random()
                ques = mArrayListParalysisPoint[pos]
            }
            arrParalysisPoint.add(ques)
        }
        // random 15 ques law
        var arrLaw = ArrayList<Question>()
        for (i in 1..10) {
            var pos = (0 until this.mArrayListQuesLaw.size).random()
            var ques = mArrayListQuesLaw[pos]
            while (arrLaw.contains(ques) || arrParalysisPoint.contains(ques) || ques.mIsParalysisPoint) {
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
            var exam = Exam(mArrayListExam.size + 1, false, 19)
            appDatabase?.appDao()?.saveDataExam(exam)
            var id = (mArrayListExamWithQues.size + 1) * 10
            var eExamWithQuestion: ExamWithQuestion?
            arrParalysisPoint.forEach {
                eExamWithQuestion = ExamWithQuestion(id++, -1, it.mID, exam.mID)
                mArrayListExamWithQues.add(eExamWithQuestion!!)
                appDatabase?.appDao()
                    ?.saveDataEwQ(eExamWithQuestion!!)
            }
            arrLaw.forEach {
                eExamWithQuestion = ExamWithQuestion(id++, -1, it.mID, exam.mID)
                mArrayListExamWithQues.add(eExamWithQuestion!!)
                appDatabase?.appDao()
                    ?.saveDataEwQ(eExamWithQuestion!!)
            }
            arrNoticeBoard.forEach {
                eExamWithQuestion = ExamWithQuestion(id++, -1, it.mID, exam.mID)
                appDatabase?.appDao()
                    ?.saveDataEwQ(eExamWithQuestion!!)
                mArrayListExamWithQues.add(eExamWithQuestion!!)
            }
            arrSituations.forEach {
                eExamWithQuestion = ExamWithQuestion(id++, -1, it.mID, exam.mID)
                appDatabase?.appDao()
                    ?.saveDataEwQ(eExamWithQuestion!!)
                mArrayListExamWithQues.add(eExamWithQuestion!!)
            }
            runOnUiThread {
                mArrayListExam.add(exam)
                mListExamAdapter?.setList(mArrayListExam)
                if (mArrayListExam.size == 1) {
                    rv_listExam.visibility = View.VISIBLE
                    tv_Suggest.visibility = View.GONE
                }
                mDialog.dismiss()
                cl_list_exam_activity.alpha = 1F
                Toast.makeText(this, "Thêm thành công!", Toast.LENGTH_SHORT).show()
            }
        }.start()

    }

    override fun onItemClick(position: Int, mItem: Exam) {
        mDialog = MaterialDialog(this)
            .noAutoDismiss()
            .customView(R.layout.dialog_confirm_delete)
        mDialog.window?.setDimAmount(0F)
        mDialog.setCancelable(false)
        mDialog.tv_TitleOfCustomDialogConfirm.text = "Bạn có chắc chắn muốn vào thi ?"
        mDialog.btn_AcceptDiaLogConFirm.setOnClickListener() {
            val arrEWQ = ArrayList<ExamWithQuestion>()
            mArrayListExamWithQues.forEach {
                if (it.mExamId == mItem.mID) {
                    arrEWQ.add(it)
                }
            }
            val intent = Intent(this, DoExamActivity::class.java)
            val bundle = Bundle()
            for (i in 0..24) {
                bundle.putSerializable("CAU_${i}", arrEWQ[i])
            }
            intent.putExtras(bundle)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
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