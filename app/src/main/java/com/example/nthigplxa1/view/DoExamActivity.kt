package com.example.nthigplxa1.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.nthigplxa1.adapter.ListQuestionAdapter
import com.example.nthigplxa1.R
import com.example.nthigplxa1.db.AppDatabase
import com.example.nthigplxa1.model.Answer
import com.example.nthigplxa1.model.ExamWithQuestion
import com.example.nthigplxa1.model.Question
import kotlinx.android.synthetic.main.activity_do_exam.*
import kotlinx.android.synthetic.main.dialog_confirm_delete.*

class DoExamActivity : AppCompatActivity(), View.OnClickListener {

    private var mListQuestionAdapter: ListQuestionAdapter? = null
    private lateinit var mDialog: MaterialDialog
    private var countDownTimer: CountDownTimer? = null
    private var mArraylistEWQ: ArrayList<ExamWithQuestion> = ArrayList()
    private var mArrayListQues: ArrayList<Question> = ArrayList()
    private var mArrayListAns: ArrayList<Answer> = ArrayList()
    val arr = ArrayList<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_do_exam)
        initRecycleView()
        initCountDownTimer()
        btn_submit.setOnClickListener(this)
        val intent = intent
        val bundle = intent.extras
        if (bundle != null) {
            for (i in 0..24) {
                mArraylistEWQ.add(bundle.getSerializable("CAU_${i}") as ExamWithQuestion)
            }
        }

        val appDatabase = AppDatabase.getDatabase(this)
        Thread {
            mArraylistEWQ.forEach {
                mArrayListQues.add(appDatabase.appDao().readDataWithIDQuestion(it.mQuesId))
                mArrayListAns.addAll(appDatabase.appDao().readDataWithIDQues(it.mQuesId))
            }
            runOnUiThread {
                initRecycleView()
            }
        }.start()
    }

    private fun initRecycleView() {
        mListQuestionAdapter = ListQuestionAdapter(this)
        rv_doExam.layoutManager = LinearLayoutManager(this)
        rv_doExam.adapter = mListQuestionAdapter
        mListQuestionAdapter?.setList(mArrayListQues, mArrayListAns, mArraylistEWQ)
    }

    private fun changeTimeFromIntToString(time: Long): String {
        val min = time / 60000
        val sec = (time / 1000) % 60
        val secString = if (sec < 10) "0$sec" else "$sec"
        val minString = if (min < 10) "0$min:" else "$min:"
        return minString + secString
    }

    private fun initCountDownTimer() {
        countDownTimer = object : CountDownTimer(19 * 60000L, 1000L) {
            override fun onFinish() {

            }

            override fun onTick(p0: Long) {
                tv_countDownTime.text =
                    changeTimeFromIntToString(p0)
            }

        }
        countDownTimer?.start()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_submit -> {
                mDialog = MaterialDialog(this)
                    .noAutoDismiss()
                    .customView(R.layout.dialog_confirm_delete)
                mDialog.window?.setDimAmount(0F)
                mDialog.setCancelable(false)
                mDialog.tv_TitleOfCustomDialogConfirm.text = "Bạn có chắc chắn muốn nộp bài?"
                mDialog.btn_AcceptDiaLogConFirm.setOnClickListener() {
                    mDialog.dismiss()

                    var mDialog = MaterialDialog(this)
                        .noAutoDismiss()
                        .customView(R.layout.dialog_confirm_delete)
                    mDialog.window?.setDimAmount(0F)
                    mDialog.setCancelable(false)
                    mDialog.tv_TitleOfCustomDialogConfirm.text =
                        "Số điểm của bạn là ${mListQuestionAdapter?.getMark()}"
                    mDialog.btn_AcceptDiaLogConFirm.setOnClickListener {
                        cl_doExam.alpha = 1F
                        mDialog.dismiss()
                    }
                    mDialog.btn_CancelDialogConfirm.setOnClickListener {
                        cl_doExam.alpha = 1F
                        mDialog.dismiss()
                    }
                    mDialog.show()
                }
                mDialog.btn_CancelDialogConfirm.setOnClickListener {
                    cl_doExam.alpha = 1F
                    mDialog.dismiss()
                }
                cl_doExam.alpha = 0.2F
                mDialog.show()
            }
        }
    }
}