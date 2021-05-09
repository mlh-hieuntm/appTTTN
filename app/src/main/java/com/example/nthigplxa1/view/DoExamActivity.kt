package com.example.nthigplxa1.view

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.nthigplxa1.R
import com.example.nthigplxa1.adapter.ListQuestionAdapter
import com.example.nthigplxa1.db.AppDatabase
import com.example.nthigplxa1.model.Answer
import com.example.nthigplxa1.model.Exam
import com.example.nthigplxa1.model.ExamWithQuestion
import com.example.nthigplxa1.model.Question
import kotlinx.android.synthetic.main.activity_do_exam.*
import kotlinx.android.synthetic.main.dialog_confirm_delete.*

class DoExamActivity : AppCompatActivity(), View.OnClickListener, ListQuestionAdapter.ItemListener {

    private var mListQuestionAdapter: ListQuestionAdapter? = null
    private lateinit var mDialog: MaterialDialog
    private var countDownTimer: CountDownTimer? = null
    private var mArraylistEWQ: ArrayList<ExamWithQuestion> = ArrayList()
    private var mArrayListQues: ArrayList<Question> = ArrayList()
    private var mArrayListAns: ArrayList<Answer> = ArrayList()
    private var mArrayListAnsCorrect: ArrayList<Answer> = ArrayList()
    private var mArrayAnsSelectedID: ArrayList<Int> = ArrayList()
    private var mExam = Exam()
    private var check = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_do_exam)
        initRecycleView()
        initCountDownTimer()
        btn_submit.setOnClickListener(this)
        img_back.setOnClickListener(this)
        val intent = intent
        val bundle = intent.extras
        if (bundle != null) {
            for (i in 0..24) {
                mArraylistEWQ.add(bundle.getSerializable("CAU_${i}") as ExamWithQuestion)
                mArrayAnsSelectedID.add(mArraylistEWQ[i].mIdAnsSelected)
            }
            mExam = bundle.getSerializable("EXAM") as Exam
        }

        if (mExam.mIsFinished) {
            tv_countDownTime.text = "00:00"
            var res = "Đáp án màu xanh lá cây là đáp án bạn chọn, đáp án màu đỏ là đáp án đúng. Nếu câu trả lời của bạn đúng thì sẽ không hiện đáp án màu đỏ, nếu câu đó bạn không chọn đáp án nào thì sẽ không hiển thị đáp án màu xanh!"
            val mDialog = MaterialDialog(this)
                .noAutoDismiss()
                .customView(R.layout.dialog_confirm_delete)
            mDialog.window?.setDimAmount(0F)
            mDialog.setCancelable(false)
            mDialog.tv_TitleOfCustomDialogConfirm.text = res
            countDownTimer?.cancel()
            mListQuestionAdapter?.showExplain()
            mDialog.btn_AcceptDiaLogConFirm.setOnClickListener {
                cl_doExam.alpha = 1F
                mDialog.dismiss()
            }
            mDialog.btn_CancelDialogConfirm.setOnClickListener {
                cl_doExam.alpha = 1F
                mDialog.dismiss()
            }
            mDialog.show()
        } else {
            countDownTimer?.start()
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
        mListQuestionAdapter = ListQuestionAdapter(this, this)
        rv_doExam.layoutManager = LinearLayoutManager(this)
        rv_doExam.adapter = mListQuestionAdapter
        mArrayListQues.forEach { it1 ->
            mArrayListAns.forEach { it2 ->
                if (it2.mQuestionID == it1.mID && it2.mIsCorrect) {
                    mArrayListAnsCorrect.add(it2)
                }
            }
        }
        mListQuestionAdapter?.setList(
            mArrayListQues,
            mArrayListAns,
            mArraylistEWQ,
            mArrayAnsSelectedID,
            mArrayListAnsCorrect,
            mExam
        )
        if (mExam.mIsFinished) {
            val res = getMark()
            if (check || res < 21) {
                btn_submit.text = "Trượt: $res/25 "
            } else {
                btn_submit.text = "Đỗ: $res/25"
            }
        }
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
                endExam()
            }

            override fun onTick(p0: Long) {
                tv_countDownTime.text =
                    changeTimeFromIntToString(p0)
            }
        }
    }

    private fun getMark(): Int {
        var mark = 0
        for (i in 0..24) {
            if (mArrayAnsSelectedID[i] == mArrayListAnsCorrect[i].mID) {
                mark++
            } else if (!mArrayListQues[i].mIsNotParalysisPoint) {
                check = true
            }
        }
        return mark
    }

    private fun endExam() {
        if (::mDialog.isInitialized && mDialog.isShowing) {
            mDialog.dismiss()
        }
        val mDialog = MaterialDialog(this)
            .noAutoDismiss()
            .customView(R.layout.dialog_confirm_delete)
        mDialog.window?.setDimAmount(0F)
        mDialog.setCancelable(false)
        val mark = getMark()
        var res = "Số điểm của bạn là ${mark}, bạn đã trượt!"
        if (mark >= 21) {
            res =
                "Số điểm của bạn là ${mark}, bạn đã đỗ!"
        } else {
            if (check) {
                res = "Bạn đã trả lời sai câu điểm liệt!"
            }
        }
        mDialog.tv_TitleOfCustomDialogConfirm.text =
            "$res Đáp án màu xanh lá cây là đáp án bạn chọn, đáp án màu đỏ là đáp án đúng. Nếu câu trả lời của bạn đúng thì sẽ không hiện đáp án màu đỏ, nếu câu đó bạn không chọn đáp án nào thì sẽ không hiển thị đáp án màu xanh!"
        countDownTimer?.cancel()
        mListQuestionAdapter?.showExplain()
        btn_submit.visibility = View.INVISIBLE
        mDialog.btn_AcceptDiaLogConFirm.setOnClickListener {
            cl_doExam.alpha = 1F
            mDialog.dismiss()
        }
        mDialog.btn_CancelDialogConfirm.setOnClickListener {
            cl_doExam.alpha = 1F
            mDialog.dismiss()
        }
        mDialog.show()
        mExam.mIsFinished = true
        for (i in 0..24) {
            mArraylistEWQ[i].mIdAnsSelected = mArrayAnsSelectedID[i]
        }
        Thread {
            AppDatabase.getDatabase(this).appDao().saveDataExam(mExam)
            for (i in 0..24) {
                AppDatabase.getDatabase(this).appDao().saveDataEwQ(mArraylistEWQ[i])
            }
        }.start()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_back -> {
                confirmBack()
            }
            R.id.btn_submit -> {
                mDialog = MaterialDialog(this)
                    .noAutoDismiss()
                    .customView(R.layout.dialog_confirm_delete)
                mDialog.window?.setDimAmount(0F)
                mDialog.setCancelable(false)
                mDialog.tv_TitleOfCustomDialogConfirm.text = "Bạn có chắc chắn muốn nộp bài?"
                mDialog.btn_AcceptDiaLogConFirm.setOnClickListener() {
                    endExam()
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

    private fun confirmBack() {
        mDialog = MaterialDialog(this)
            .noAutoDismiss()
            .customView(R.layout.dialog_confirm_delete)
        mDialog.window?.setDimAmount(0F)
        mDialog.setCancelable(false)
        mDialog.tv_TitleOfCustomDialogConfirm.text = "Bạn có chắc chắn muốn thoát?"
        mDialog.btn_AcceptDiaLogConFirm.setOnClickListener() {
            startActivity(Intent(this, ListExamActivity::class.java))
            this.finish()
        }
        mDialog.btn_CancelDialogConfirm.setOnClickListener {
            cl_doExam.alpha = 1F
            mDialog.dismiss()
        }
        cl_doExam.alpha = 0.2F
        mDialog.show()
    }

    override fun onBackPressed() {
        confirmBack()
    }

    override fun onItemClick(position: Int, mItem: Int, mIdAns: String) {
        if (mIdAns.isNotEmpty()) {
            mArrayAnsSelectedID[position] = mIdAns.toInt()
        }
        mListQuestionAdapter?.setArrayAnsSelected(position, mArrayAnsSelectedID)
    }
}