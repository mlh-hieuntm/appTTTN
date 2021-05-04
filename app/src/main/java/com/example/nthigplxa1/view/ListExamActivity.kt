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
    private val COUNT_EXAM = "COUNT_EXAM"
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
        ans1 = Answer(1, getString(R.string.ans_1_1), 1, true)
        ans2 = Answer(2, getString(R.string.ans_1_2), 1)
        ans3 = Answer(3, getString(R.string.ans_1_3), 1)
        question = Question(1, typeQuestionLaw, false, getString(R.string.ques_1), -1, getString(R.string.explain_1))
        saveDb(ans1, ans2, ans3, null, question)
        //cau2
        ans1 = Answer(5, getString(R.string.ans_2_1), 2)
        ans2 = Answer(6, getString(R.string.ans_2_2), 2)
        ans3 = Answer(7, getString(R.string.ans_2_3), 2, mIsCorrect = true)
        question = Question(2, typeQuestionLaw, false, getString(R.string.ques_2), -1, getString(R.string.explain_2))
        saveDb(ans1, ans2, ans3, null, question)
        //cau3
        ans1 = Answer(9, getString(R.string.ans_3_1), 3, true)
        ans2 = Answer(10, getString(R.string.ans_3_2), 3)
        ans3 = Answer(11, getString(R.string.ans_3_3), 3)
        ans4 = Answer(12, getString(R.string.ans_3_4), 3)
        question = Question(3, typeQuestionLaw, true, getString(R.string.ques_3), -1, getString(R.string.explain_3))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau4
        ans1 = Answer(13, getString(R.string.ans_4_1), 4)
        ans2 = Answer(14, getString(R.string.ans_4_2), 4, true)
        ans3 = Answer(15, getString(R.string.ans_4_3), 4)
        question = Question(4, typeQuestionLaw, false, getString(R.string.ques_4), -1, getString(R.string.explain_4))
        saveDb(ans1, ans2, ans3, null, question)
        //cau5
        ans1 = Answer(17, getString(R.string.ans_5_1), 5)
        ans2 = Answer(18, getString(R.string.ans_5_2), 5)
        ans3 = Answer(19, getString(R.string.ans_5_3), 5)
        ans4 = Answer(20, getString(R.string.ans_5_4), 5, true)
        question = Question(5, typeQuestionLaw, false, getString(R.string.ques_5), -1, getString(R.string.explain_5))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau6
        ans1 = Answer(21, getString(R.string.ans_6_1), 6)
        ans2 = Answer(22, getString(R.string.ans_6_2), 6)
        ans3 = Answer(23, getString(R.string.ans_6_3), 6, true)
        question = Question(6, typeQuestionLaw, false, getString(R.string.ques_6), -1, getString(R.string.explain_6))
        saveDb(ans1, ans2, ans3, null, question)
        //cau7
        ans1 = Answer(25, getString(R.string.ans_7_1), 7)
        ans2 = Answer(26, getString(R.string.ans_7_2), 7, true)
        ans3 = Answer(27, getString(R.string.ans_7_3), 7)
        ans4 = Answer(28, getString(R.string.ans_7_4), 7)
        question = Question(7, typeQuestionLaw, false, getString(R.string.ques_7), -1, getString(R.string.explain_7))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau8
        ans1 = Answer(29, getString(R.string.ans_8_1), 8)
        ans2 = Answer(30, getString(R.string.ans_8_2), 8)
        ans3 = Answer(31, getString(R.string.ans_8_3), 8)
        ans4 = Answer(32, getString(R.string.ans_8_4), 8, true)
        question = Question(8, typeQuestionLaw, false, getString(R.string.ques_8), -1, getString(R.string.explain_8))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau9
        ans1 = Answer(33, getString(R.string.ans_9_1), 9)
        ans2 = Answer(34, getString(R.string.ans_9_2), 9)
        ans3 = Answer(35, getString(R.string.ans_9_3), 9, true)
        ans4 = Answer(36, getString(R.string.ans_9_4), 9)
        question = Question(9, typeQuestionLaw, false, getString(R.string.ques_9), -1, getString(R.string.explain_9))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau10
        ans1 = Answer(37, getString(R.string.ans_10_1), 10)
        ans2 = Answer(38, getString(R.string.ans_10_2), 10, true)
        ans3 = Answer(39, getString(R.string.ans_10_3), 10)
        question = Question(10, typeQuestionLaw, false, getString(R.string.ques_10), -1, getString(R.string.explain_10))
        saveDb(ans1, ans2, ans3, null, question)
        //cau11
        ans1 = Answer(41, getString(R.string.ans_11_1), 11, true)
        ans2 = Answer(42, getString(R.string.ans_11_2), 11)
        ans3 = Answer(43, getString(R.string.ans_11_3), 11)
        question = Question(11, typeQuestionLaw, false, getString(R.string.ques_11), -1, getString(R.string.explain_11))
        saveDb(ans1, ans2, ans3, null, question)
        //cau12
        ans1 = Answer(45, getString(R.string.ans_12_1), 12)
        ans2 = Answer(46, getString(R.string.ans_12_2), 12)
        ans3 = Answer(47, getString(R.string.ans_12_3), 12, true)
        question = Question(12, typeQuestionLaw, false, getString(R.string.ques_12), -1, getString(R.string.explain_12))
        saveDb(ans1, ans2, ans3, null, question)
        //cau13
        ans1 = Answer(49, getString(R.string.ans_13_1), 13)
        ans2 = Answer(50, getString(R.string.ans_13_2), 13, true)
        ans3 = Answer(51, getString(R.string.ans_13_3), 13)
        ans4 = Answer(52, getString(R.string.ans_13_4), 13)
        question = Question(13, typeQuestionLaw, false, getString(R.string.ques_13), -1, getString(R.string.explain_13))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau14
        ans1 = Answer(53, getString(R.string.ans_14_1), 14, true)
        ans2 = Answer(54, getString(R.string.ans_14_2), 14)
        ans3 = Answer(55, getString(R.string.ans_14_3), 14)
        ans4 = Answer(56, getString(R.string.ans_14_4), 14)
        question = Question(14, typeQuestionLaw, false, getString(R.string.ques_14), -1, getString(R.string.explain_14))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau15
        ans1 = Answer(57, getString(R.string.ans_15_1), 15)
        ans2 = Answer(58, getString(R.string.ans_15_2), 15, true)
        ans3 = Answer(59, getString(R.string.ans_15_3), 15)
        question = Question(15, typeQuestionLaw, true, getString(R.string.ques_15), -1, getString(R.string.explain_15))
        saveDb(ans1, ans2, ans3, null, question)
        //cau16
        ans1 = Answer(61, getString(R.string.ans_16_1), 16)
        ans2 = Answer(62, getString(R.string.ans_16_2), 16, true)
        ans3 = Answer(63, getString(R.string.ans_16_3), 16)
        question = Question(16, typeQuestionLaw, true, getString(R.string.ques_16), -1, getString(R.string.explain_16))
        saveDb(ans1, ans2, ans3, null, question)
        //cau17
        ans1 = Answer(65, getString(R.string.ans_17_1), 17)
        ans2 = Answer(66, getString(R.string.ans_17_2), 17)
        ans3 = Answer(67, getString(R.string.ans_17_3), 17)
        ans4 = Answer(68, getString(R.string.ans_17_4), 17, true)
        question = Question(17, typeQuestionLaw, true, getString(R.string.ques_17), -1, getString(R.string.explain_17))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau18
        ans1 = Answer(69, getString(R.string.ans_18_1), 18, true)
        ans2 = Answer(70, getString(R.string.ans_18_2), 18)
        ans3 = Answer(71, getString(R.string.ans_18_3), 18)
        question = Question(18, typeQuestionLaw, true, getString(R.string.ques_18), -1, getString(R.string.explain_18))
        saveDb(ans1, ans2, ans3, null, question)
        //cau19
        ans1 = Answer(73, getString(R.string.ans_19_1), 19)
        ans2 = Answer(74, getString(R.string.ans_19_2), 19)
        ans3 = Answer(75, getString(R.string.ans_19_3), 19, true)
        question = Question(19, typeQuestionLaw, true, getString(R.string.ques_19), -1, getString(R.string.explain_19))
        saveDb(ans1, ans2, ans3, null, question)
        //cau20
        ans1 = Answer(77, getString(R.string.ans_20_1), 20, true)
        ans2 = Answer(78, getString(R.string.ans_20_2), 20)
        ans3 = Answer(79, getString(R.string.ans_20_3), 20)
        question = Question(20, typeQuestionLaw, true, getString(R.string.ques_20), -1, getString(R.string.explain_20))
        saveDb(ans1, ans2, ans3, null, question)
        //cau21
        ans1 = Answer(81, getString(R.string.ans_21_1), 21)
        ans2 = Answer(82, getString(R.string.ans_21_2), 21, true)
        question = Question(21, typeQuestionLaw, true, getString(R.string.ques_21), -1, getString(R.string.explain_21))
        saveDb(ans1, ans2, null, null, question)
        //cau22
        ans1 = Answer(85, getString(R.string.ans_22_1), 22, true)
        ans2 = Answer(86, getString(R.string.ans_22_2), 22)
        ans3 = Answer(87, getString(R.string.ans_22_3), 22)
        question = Question(22, typeQuestionLaw, true, getString(R.string.ques_22), -1, getString(R.string.explain_22))
        saveDb(ans1, ans2, ans3, null, question)
        //cau23
        ans1 = Answer(89, getString(R.string.ans_23_1), 23)
        ans2 = Answer(90, getString(R.string.ans_23_2), 23)
        ans3 = Answer(91, getString(R.string.ans_23_3), 23, true)
        question = Question(23, typeQuestionLaw, true, getString(R.string.ques_23), -1, getString(R.string.explain_23))
        saveDb(ans1, ans2, ans3, null, question)
        //cau24
        ans1 = Answer(93, getString(R.string.ans_24_1), 24)
        ans2 = Answer(94, getString(R.string.ans_24_2), 24)
        ans3 = Answer(95, getString(R.string.ans_24_3), 24, true)
        question = Question(24, typeQuestionLaw, true, getString(R.string.ques_24), -1, getString(R.string.explain_24))
        saveDb(ans1, ans2, ans3, null, question)
        //cau25
        ans1 = Answer(97, getString(R.string.ans_25_1), 25)
        ans2 = Answer(98, getString(R.string.ans_25_2), 25, true)
        ans3 = Answer(99, getString(R.string.ans_25_3), 25)
        question = Question(25, typeQuestionLaw, true, getString(R.string.ques_25), -1, getString(R.string.explain_25))
        saveDb(ans1, ans2, ans3, null, question)
        //cau26
        ans1 = Answer(101, getString(R.string.ans_26_1), 26)
        ans2 = Answer(102, getString(R.string.ans_26_2), 26)
        ans3 = Answer(103, getString(R.string.ans_26_3), 26, true)
        question = Question(26, typeQuestionLaw, true, getString(R.string.ques_26), -1, getString(R.string.explain_26))
        saveDb(ans1, ans2, ans3, null, question)
        //cau27
        ans1 = Answer(105, getString(R.string.ans_27_1), 27)
        ans2 = Answer(106, getString(R.string.ans_27_2), 27, true)
        question = Question(27, typeQuestionLaw, true, getString(R.string.ques_27), -1, getString(R.string.explain_27))
        saveDb(ans1, ans2, null, null, question)
        //cau28
        ans1 = Answer(109, getString(R.string.ans_28_1), 28)
        ans2 = Answer(110, getString(R.string.ans_28_2), 28, true)
        ans3 = Answer(111, getString(R.string.ans_28_3), 28)
        question = Question(28, typeQuestionLaw, true, getString(R.string.ques_28), -1, getString(R.string.explain_28))
        saveDb(ans1, ans2, ans3, null, question)
        //cau29
        ans1 = Answer(113, getString(R.string.ans_29_1), 29)
        ans2 = Answer(114, getString(R.string.ans_29_2), 29)
        ans3 = Answer(115, getString(R.string.ans_29_3), 29, true)
        question = Question(29, typeQuestionLaw, false, getString(R.string.ques_29), -1, getString(R.string.explain_29))
        saveDb(ans1, ans2, ans3, null, question)
        //cau30
        ans1 = Answer(117, getString(R.string.ans_30_1), 30)
        ans2 = Answer(118, getString(R.string.ans_30_2), 30, true)
        ans3 = Answer(119, getString(R.string.ans_30_3), 30)
        question = Question(30, typeQuestionLaw, false, getString(R.string.ques_30), -1, getString(R.string.explain_30))
        saveDb(ans1, ans2, ans3, null, question)
        //cau31
        ans1 = Answer(121, getString(R.string.ans_31_1), 31)
        ans2 = Answer(122, getString(R.string.ans_31_2), 31)
        ans3 = Answer(123, getString(R.string.ans_31_3), 31)
        ans4 = Answer(124, getString(R.string.ans_31_4), 31, true)
        question = Question(31, typeQuestionLaw, true, getString(R.string.ques_31), -1, getString(R.string.explain_31))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau32
        ans1 = Answer(125, getString(R.string.ans_32_1), 32)
        ans2 = Answer(126, getString(R.string.ans_32_2), 32, true)
        ans3 = Answer(127, getString(R.string.ans_32_3), 32)
        question = Question(32, typeQuestionLaw, true, getString(R.string.ques_32), -1, getString(R.string.explain_32))
        saveDb(ans1, ans2, ans3, null, question)
        //cau33
        ans1 = Answer(129, getString(R.string.ans_33_1), 33)
        ans2 = Answer(130, getString(R.string.ans_33_2), 33, true)
        ans3 = Answer(131, getString(R.string.ans_33_3), 33)
        question = Question(33, typeQuestionLaw, true, getString(R.string.ques_33), -1, getString(R.string.explain_33))
        saveDb(ans1, ans2, ans3, null, question)
        //cau34
        ans1 = Answer(133, getString(R.string.ans_34_1), 34)
        ans2 = Answer(134, getString(R.string.ans_34_2), 34)
        ans3 = Answer(135, getString(R.string.ans_34_3), 34)
        ans4 = Answer(136, getString(R.string.ans_34_4), 34, true)
        question = Question(34, typeQuestionLaw, true, getString(R.string.ques_34), -1, getString(R.string.explain_34))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau35
        ans1 = Answer(137, getString(R.string.ans_35_1), 35, true)
        ans2 = Answer(138, getString(R.string.ans_35_2), 35)
        ans3 = Answer(139, getString(R.string.ans_35_3), 35)
        ans4 = Answer(140, getString(R.string.ans_35_4), 35)
        question = Question(35, typeQuestionLaw, false, getString(R.string.ques_35), -1, getString(R.string.explain_35))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau36
        ans1 = Answer(141, getString(R.string.ans_36_1), 36)
        ans2 = Answer(142, getString(R.string.ans_36_2), 36, true)
        ans3 = Answer(143, getString(R.string.ans_36_3), 36)
        ans4 = Answer(144, getString(R.string.ans_36_4), 36)
        question = Question(36, typeQuestionLaw, true, getString(R.string.ques_36), -1, getString(R.string.explain_36))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau37
        ans1 = Answer(145, getString(R.string.ans_37_1), 37)
        ans2 = Answer(146, getString(R.string.ans_37_2), 37, true)
        ans3 = Answer(147, getString(R.string.ans_37_3), 37)
        question = Question(37, typeQuestionLaw, true, getString(R.string.ques_37), -1, getString(R.string.explain_37))
        saveDb(ans1, ans2, ans3, null, question)
        //cau38
        ans1 = Answer(149, getString(R.string.ans_38_1), 38)
        ans2 = Answer(150, getString(R.string.ans_38_2), 38, true)
        ans3 = Answer(151, getString(R.string.ans_38_3), 38)
        question = Question(38, typeQuestionLaw, true, getString(R.string.ques_38), -1, getString(R.string.explain_38))
        saveDb(ans1, ans2, ans3, null, question)
        //cau39
        ans1 = Answer(153, getString(R.string.ans_39_1), 39)
        ans2 = Answer(154, getString(R.string.ans_39_2), 39)
        ans3 = Answer(155, getString(R.string.ans_39_3), 39, true)
        question = Question(39, typeQuestionLaw, true, getString(R.string.ques_39), -1, getString(R.string.explain_39))
        saveDb(ans1, ans2, ans3, null, question)
        //cau40
        ans1 = Answer(157, getString(R.string.ans_40_1), 40)
        ans2 = Answer(158, getString(R.string.ans_40_2), 40)
        ans3 = Answer(159, getString(R.string.ans_40_3), 40)
        ans4 = Answer(160, getString(R.string.ans_40_4), 40, true)
        question = Question(40, typeQuestionLaw, true, getString(R.string.ques_40), -1, getString(R.string.explain_40))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau41
        ans1 = Answer(161, getString(R.string.ans_41_1), 41)
        ans2 = Answer(162, getString(R.string.ans_41_2), 41)
        ans3 = Answer(163, getString(R.string.ans_41_3), 41, true)
        ans4 = Answer(164, getString(R.string.ans_41_4), 41)
        question = Question(41, typeQuestionLaw, true, getString(R.string.ques_41), -1, getString(R.string.explain_41))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau42
        ans1 = Answer(165, getString(R.string.ans_42_1), 42, true)
        ans2 = Answer(166, getString(R.string.ans_42_2), 42)
        ans3 = Answer(167, getString(R.string.ans_42_3), 42)
        ans4 = Answer(168, getString(R.string.ans_42_4), 42)
        question = Question(42, typeQuestionLaw, true, getString(R.string.ques_42), -1, getString(R.string.explain_42))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau43
        ans1 = Answer(169, getString(R.string.ans_43_1), 43)
        ans2 = Answer(170, getString(R.string.ans_43_2), 43, true)
        ans3 = Answer(171, getString(R.string.ans_43_3), 43)
        ans4 = Answer(172, getString(R.string.ans_43_4), 43)
        question = Question(43, typeQuestionLaw, true, getString(R.string.ques_43), -1, getString(R.string.explain_43))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau44
        ans1 = Answer(173, getString(R.string.ans_44_1), 44, true)
        ans2 = Answer(174, getString(R.string.ans_44_2), 44)
        ans3 = Answer(175, getString(R.string.ans_44_3), 44)
        ans4 = Answer(176, getString(R.string.ans_44_4), 44)
        question = Question(44, typeQuestionLaw, true, getString(R.string.ques_44), -1, getString(R.string.explain_44))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau45
        ans1 = Answer(177, getString(R.string.ans_45_1), 45)
        ans2 = Answer(178, getString(R.string.ans_45_2), 45)
        ans3 = Answer(179, getString(R.string.ans_45_3), 45, true)
        ans4 = Answer(180, getString(R.string.ans_45_4), 45)
        question = Question(45, typeQuestionLaw, true, getString(R.string.ques_45), -1, getString(R.string.explain_45))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau46
        ans1 = Answer(181, getString(R.string.ans_46_1), 46)
        ans2 = Answer(182, getString(R.string.ans_46_2), 46)
        ans3 = Answer(183, getString(R.string.ans_46_3), 46)
        ans4 = Answer(184, getString(R.string.ans_46_4), 46, true)
        question = Question(46, typeQuestionLaw, true, getString(R.string.ques_46), -1, getString(R.string.explain_46))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau47
        ans1 = Answer(185, getString(R.string.ans_47_1), 47)
        ans2 = Answer(186, getString(R.string.ans_47_2), 47, true)
        question = Question(47, typeQuestionLaw, true, getString(R.string.ques_47), -1, getString(R.string.explain_47))
        saveDb(ans1, ans2, null, null, question)
        //cau48
        ans1 = Answer(189, getString(R.string.ans_48_1), 48)
        ans2 = Answer(190, getString(R.string.ans_48_2), 48)
        ans3 = Answer(191, getString(R.string.ans_48_3), 48, true)
        ans4 = Answer(192, getString(R.string.ans_48_4), 48)
        question = Question(48, typeQuestionLaw, true, getString(R.string.ques_48), -1, getString(R.string.explain_48))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau49
        ans1 = Answer(193, getString(R.string.ans_49_1), 49)
        ans2 = Answer(194, getString(R.string.ans_49_2), 49, true)
        ans3 = Answer(195, getString(R.string.ans_49_3), 49)
        question = Question(49, typeQuestionLaw, true, getString(R.string.ques_49), -1, getString(R.string.explain_49))
        saveDb(ans1, ans2, ans3, null, question)
        //cau50
        ans1 = Answer(197, getString(R.string.ans_50_1), 50)
        ans2 = Answer(198, getString(R.string.ans_50_2), 50, true)
        ans3 = Answer(199, getString(R.string.ans_50_3), 50)
        question = Question(50, typeQuestionLaw, true, getString(R.string.ques_50), -1, getString(R.string.explain_50))
        saveDb(ans1, ans2, ans3, null, question)
        //cau51
        ans1 = Answer(201, getString(R.string.ans_51_1), 51)
        ans2 = Answer(202, getString(R.string.ans_51_2), 51)
        ans3 = Answer(203, getString(R.string.ans_51_3), 51)
        ans4 = Answer(204, getString(R.string.ans_51_4), 51, true)
        question = Question(51, typeQuestionLaw, true, getString(R.string.ques_51), -1, getString(R.string.explain_51))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau52
        ans1 = Answer(205, getString(R.string.ans_52_1), 52)
        ans2 = Answer(206, getString(R.string.ans_52_2), 52, true)
        ans3 = Answer(207, getString(R.string.ans_52_3), 52)
        question = Question(52, typeQuestionLaw, true, getString(R.string.ques_52), -1, getString(R.string.explain_52))
        saveDb(ans1, ans2, ans3, null, question)
        //cau53
        ans1 = Answer(209, getString(R.string.ans_53_1), 53)
        ans2 = Answer(210, getString(R.string.ans_53_2), 53, true)
        ans3 = Answer(211, getString(R.string.ans_53_3), 53)
        question = Question(53, typeQuestionLaw, true, getString(R.string.ques_53), -1, getString(R.string.explain_53))
        saveDb(ans1, ans2, ans3, null, question)
        //cau54
        ans1 = Answer(213, getString(R.string.ans_54_1), 54)
        ans2 = Answer(214, getString(R.string.ans_54_2), 54)
        ans3 = Answer(215, getString(R.string.ans_54_3), 54)
        ans4 = Answer(216, getString(R.string.ans_54_4), 54, true)
        question = Question(54, typeQuestionLaw, true, getString(R.string.ques_54), -1, getString(R.string.explain_54))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau55
        ans1 = Answer(217, getString(R.string.ans_55_1), 55)
        ans2 = Answer(218, getString(R.string.ans_55_2), 55, true)
        ans3 = Answer(219, getString(R.string.ans_55_3), 55)
        question = Question(55, typeQuestionLaw, true, getString(R.string.ques_55), -1, getString(R.string.explain_55))
        saveDb(ans1, ans2, ans3, null, question)
        //cau56
        ans1 = Answer(221, getString(R.string.ans_56_1), 56)
        ans2 = Answer(222, getString(R.string.ans_56_2), 56)
        ans3 = Answer(223, getString(R.string.ans_56_3), 56, true)
        question = Question(56, typeQuestionLaw, true, getString(R.string.ques_56), -1, getString(R.string.explain_56))
        saveDb(ans1, ans2, ans3, null, question)
        //cau57
        ans1 = Answer(225, getString(R.string.ans_57_1), 57, true)
        ans2 = Answer(226, getString(R.string.ans_57_2), 57)
        ans3 = Answer(227, getString(R.string.ans_57_3), 57)
        question = Question(57, typeQuestionLaw, true, getString(R.string.ques_57), -1, getString(R.string.explain_57))
        saveDb(ans1, ans2, ans3, null, question)
        //cau58
        ans1 = Answer(229, getString(R.string.ans_58_1), 58, true)
        ans2 = Answer(230, getString(R.string.ans_58_2), 58)
        ans3 = Answer(231, getString(R.string.ans_58_3), 58)
        question = Question(58, typeQuestionLaw, false, getString(R.string.ques_58), -1, getString(R.string.explain_58))
        saveDb(ans1, ans2, ans3, null, question)
        //cau59
        ans1 = Answer(233, getString(R.string.ans_59_1), 59)
        ans2 = Answer(234, getString(R.string.ans_59_2), 59)
        ans3 = Answer(235, getString(R.string.ans_59_3), 59, true)
        question = Question(59, typeQuestionLaw, true, getString(R.string.ques_59), -1, getString(R.string.explain_59))
        saveDb(ans1, ans2, ans3, null, question)
        //cau60
        ans1 = Answer(237, getString(R.string.ans_60_1), 60, true)
        ans2 = Answer(238, getString(R.string.ans_60_2), 60)
        ans3 = Answer(239, getString(R.string.ans_60_3), 60)
        question = Question(60, typeQuestionLaw, true, getString(R.string.ques_60), -1, getString(R.string.explain_60))
        saveDb(ans1, ans2, ans3, null, question)
        //cau61
        ans1 = Answer(241, getString(R.string.ans_61_1), 61)
        ans2 = Answer(242, getString(R.string.ans_61_2), 61)
        ans3 = Answer(243, getString(R.string.ans_61_3), 61)
        ans4 = Answer(244, getString(R.string.ans_61_4), 61, true)
        question = Question(61, typeQuestionLaw, true, getString(R.string.ques_61), -1, getString(R.string.explain_61))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau62
        ans1 = Answer(245, getString(R.string.ans_62_1), 62)
        ans2 = Answer(246, getString(R.string.ans_62_2), 62, true)
        ans3 = Answer(247, getString(R.string.ans_62_3), 62)
        question = Question(62, typeQuestionLaw, true, getString(R.string.ques_62), -1, getString(R.string.explain_62))
        saveDb(ans1, ans2, ans3, null, question)
        //cau63
        ans1 = Answer(249, getString(R.string.ans_63_1), 63)
        ans2 = Answer(250, getString(R.string.ans_63_2), 63)
        ans3 = Answer(251, getString(R.string.ans_63_3), 63)
        ans4 = Answer(252, getString(R.string.ans_63_4), 63, true)
        question = Question(63, typeQuestionLaw, true, getString(R.string.ques_63), -1, getString(R.string.explain_63))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau64
        ans1 = Answer(253, getString(R.string.ans_64_1), 64, true)
        ans2 = Answer(254, getString(R.string.ans_64_2), 64)
        ans3 = Answer(255, getString(R.string.ans_64_3), 64)
        question = Question(64, typeQuestionLaw, true, getString(R.string.ques_64), -1, getString(R.string.explain_64))
        saveDb(ans1, ans2, ans3, null, question)
        //cau65
        ans1 = Answer(257, getString(R.string.ans_65_1), 65, true)
        ans2 = Answer(258, getString(R.string.ans_65_2), 65)
        ans3 = Answer(259, getString(R.string.ans_65_3), 65)
        question = Question(65, typeQuestionLaw, true, getString(R.string.ques_65), -1, getString(R.string.explain_65))
        saveDb(ans1, ans2, ans3, null, question)
        //cau66
        ans1 = Answer(261, getString(R.string.ans_66_1), 66, true)
        ans2 = Answer(262, getString(R.string.ans_66_2), 66)
        ans3 = Answer(263, getString(R.string.ans_66_3), 66)
        question = Question(66, typeQuestionLaw, true, getString(R.string.ques_66), -1, getString(R.string.explain_66))
        saveDb(ans1, ans2, ans3, null, question)
        //cau67
        ans1 = Answer(265, getString(R.string.ans_67_1), 67)
        ans2 = Answer(266, getString(R.string.ans_67_2), 67, true)
        ans3 = Answer(267, getString(R.string.ans_67_3), 67)
        question = Question(67, typeQuestionLaw, true, getString(R.string.ques_67), -1, getString(R.string.explain_67))
        saveDb(ans1, ans2, ans3, null, question)
        //cau68
        ans1 = Answer(269, getString(R.string.ans_68_1), 68)
        ans2 = Answer(270, getString(R.string.ans_68_2), 68, true)
        ans3 = Answer(271, getString(R.string.ans_68_3), 68)
        question = Question(68, typeQuestionLaw, true, getString(R.string.ques_68), -1, getString(R.string.explain_68))
        saveDb(ans1, ans2, ans3, null, question)
        //cau69
        ans1 = Answer(273, getString(R.string.ans_69_1), 69, true)
        ans2 = Answer(274, getString(R.string.ans_69_2), 69)
        ans3 = Answer(275, getString(R.string.ans_69_3), 69)
        question = Question(69, typeQuestionLaw, true, getString(R.string.ques_69), -1, getString(R.string.explain_69))
        saveDb(ans1, ans2, ans3, null, question)
        //cau70
        ans1 = Answer(277, getString(R.string.ans_70_1), 70)
        ans2 = Answer(278, getString(R.string.ans_70_2), 70, true)
        ans3 = Answer(279, getString(R.string.ans_70_3), 70)
        question = Question(70, typeQuestionLaw, true, getString(R.string.ques_70), -1, getString(R.string.explain_70))
        saveDb(ans1, ans2, ans3, null, question)
        //cau71
        ans1 = Answer(281, getString(R.string.ans_71_1), 71, true)
        ans2 = Answer(282, getString(R.string.ans_71_2), 71)
        ans3 = Answer(283, getString(R.string.ans_71_3), 71)
        question = Question(71, typeQuestionLaw, true, getString(R.string.ques_71), -1, getString(R.string.explain_71))
        saveDb(ans1, ans2, ans3, null, question)
        //cau72
        ans1 = Answer(285, getString(R.string.ans_72_1), 72)
        ans2 = Answer(286, getString(R.string.ans_72_2), 72)
        ans3 = Answer(287, getString(R.string.ans_72_3), 72, true)
        question = Question(72, typeQuestionLaw, true, getString(R.string.ques_72), -1, getString(R.string.explain_72))
        saveDb(ans1, ans2, ans3, null, question)
        //cau73
        ans1 = Answer(289, getString(R.string.ans_73_1), 73, true)
        ans2 = Answer(290, getString(R.string.ans_73_2), 73)
        ans3 = Answer(291, getString(R.string.ans_73_3), 73)
        ans4 = Answer(292, getString(R.string.ans_73_4), 73)
        question = Question(73, typeQuestionLaw, true, getString(R.string.ques_73), -1, getString(R.string.explain_73))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau74
        ans1 = Answer(293, getString(R.string.ans_74_1), 74)
        ans2 = Answer(294, getString(R.string.ans_74_2), 74)
        ans3 = Answer(295, getString(R.string.ans_74_3), 74, true)
        ans4 = Answer(296, getString(R.string.ans_74_4), 74)
        question = Question(74, typeQuestionLaw, true, getString(R.string.ques_74), -1, getString(R.string.explain_74))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau75
        ans1 = Answer(297, getString(R.string.ans_75_1), 75)
        ans2 = Answer(298, getString(R.string.ans_75_2), 75)
        ans3 = Answer(299, getString(R.string.ans_75_3), 75, true)
        question = Question(75, typeQuestionLaw, true, getString(R.string.ques_75), -1, getString(R.string.explain_75))
        saveDb(ans1, ans2, ans3, null, question)
        //cau76
        ans1 = Answer(301, getString(R.string.ans_76_1), 76)
        ans2 = Answer(302, getString(R.string.ans_76_2), 76, true)
        ans3 = Answer(303, getString(R.string.ans_76_3), 76)
        question = Question(76, typeQuestionLaw, false, getString(R.string.ques_76), -1, getString(R.string.explain_76))
        saveDb(ans1, ans2, ans3, null, question)
        //cau77
        ans1 = Answer(305, getString(R.string.ans_77_1), 77, true)
        ans2 = Answer(306, getString(R.string.ans_77_2), 77)
        ans3 = Answer(307, getString(R.string.ans_77_3), 77)
        ans4 = Answer(308, getString(R.string.ans_77_4), 77)
        question = Question(77, typeQuestionLaw, true, getString(R.string.ques_77), -1, getString(R.string.explain_77))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau78
        ans1 = Answer(309, getString(R.string.ans_78_1), 78)
        ans2 = Answer(310, getString(R.string.ans_78_2), 78)
        ans3 = Answer(311, getString(R.string.ans_78_3), 78, true)
        question = Question(78, typeQuestionLaw, true, getString(R.string.ques_78), -1, getString(R.string.explain_78))
        saveDb(ans1, ans2, ans3, null, question)
        //cau79
        ans1 = Answer(313, getString(R.string.ans_79_1), 79)
        ans2 = Answer(314, getString(R.string.ans_79_2), 79)
        ans3 = Answer(315, getString(R.string.ans_79_3), 79)
        ans4 = Answer(316, getString(R.string.ans_79_4), 79, true)
        question = Question(79, typeQuestionLaw, true, getString(R.string.ques_79), -1, getString(R.string.explain_79))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau80
        ans1 = Answer(317, getString(R.string.ans_80_1), 80)
        ans2 = Answer(318, getString(R.string.ans_80_2), 80, true)
        ans3 = Answer(319, getString(R.string.ans_80_3), 80)
        question = Question(80, typeQuestionLaw, true, getString(R.string.ques_80), -1, getString(R.string.explain_80))
        saveDb(ans1, ans2, ans3, null, question)
        //cau81
        ans1 = Answer(321, getString(R.string.ans_81_1), 81)
        ans2 = Answer(322, getString(R.string.ans_81_2), 81)
        ans3 = Answer(323, getString(R.string.ans_81_3), 81, true)
        question = Question(81, typeQuestionLaw, true, getString(R.string.ques_81), -1, getString(R.string.explain_81))
        saveDb(ans1, ans2, ans3, null, question)
        //cau82
        ans1 = Answer(325, getString(R.string.ans_82_1), 82, true)
        ans2 = Answer(326, getString(R.string.ans_82_2), 82)
        ans3 = Answer(327, getString(R.string.ans_82_3), 82)
        question = Question(82, typeQuestionLaw, true, getString(R.string.ques_82), -1, getString(R.string.explain_82))
        saveDb(ans1, ans2, ans3, null, question)
        //cau83
        ans1 = Answer(329, getString(R.string.ans_83_1), 83, true)
        ans2 = Answer(330, getString(R.string.ans_83_2), 83)
        ans3 = Answer(331, getString(R.string.ans_83_3), 83)
        question = Question(83, typeQuestionLaw, true, getString(R.string.ques_83), -1, getString(R.string.explain_83))
        saveDb(ans1, ans2, ans3, null, question)
        //cau84
        ans1 = Answer(333, getString(R.string.ans_84_1), 84)
        ans2 = Answer(334, getString(R.string.ans_84_2), 84)
        ans3 = Answer(335, getString(R.string.ans_84_3), 84)
        ans4 = Answer(336, getString(R.string.ans_84_4), 84, true)
        question = Question(84, typeQuestionLaw, true, getString(R.string.ques_84), -1, getString(R.string.explain_84))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau85
        ans1 = Answer(337, getString(R.string.ans_85_1), 85, true)
        ans2 = Answer(338, getString(R.string.ans_85_2), 85)
        ans3 = Answer(339, getString(R.string.ans_85_3), 85)
        question = Question(85, typeQuestionLaw, true, getString(R.string.ques_85), -1, getString(R.string.explain_85))
        saveDb(ans1, ans2, ans3, null, question)
        //cau86
        ans1 = Answer(341, getString(R.string.ans_86_1), 86)
        ans2 = Answer(342, getString(R.string.ans_86_2), 86)
        ans3 = Answer(343, getString(R.string.ans_86_3), 86)
        ans4 = Answer(344, getString(R.string.ans_86_4), 86, true)
        question = Question(86, typeQuestionLaw, true, getString(R.string.ques_86), -1, getString(R.string.explain_86))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau87
        ans1 = Answer(345, getString(R.string.ans_87_1), 87, true)
        ans2 = Answer(346, getString(R.string.ans_87_2), 87)
        question = Question(87, typeQuestionLaw, true, getString(R.string.ques_87), -1, getString(R.string.explain_87))
        saveDb(ans1, ans2, null, null, question)
        //cau88
        ans1 = Answer(349, getString(R.string.ans_88_1), 88, true)
        ans2 = Answer(350, getString(R.string.ans_88_2), 88)
        question = Question(88, typeQuestionLaw, true, getString(R.string.ques_88), -1, getString(R.string.explain_88))
        saveDb(ans1, ans2, null, null, question)
        //cau89
        ans1 = Answer(353, getString(R.string.ans_89_1), 89)
        ans2 = Answer(354, getString(R.string.ans_89_2), 89)
        ans3 = Answer(355, getString(R.string.ans_89_3), 89, true)
        question = Question(89, typeQuestionLaw, true, getString(R.string.ques_89), -1, getString(R.string.explain_89))
        saveDb(ans1, ans2, ans3, null, question)
        //cau90
        ans1 = Answer(357, getString(R.string.ans_90_1), 90)
        ans2 = Answer(358, getString(R.string.ans_90_2), 90, true)
        ans3 = Answer(359, getString(R.string.ans_90_3), 90)
        question = Question(90, typeQuestionLaw, false, getString(R.string.ques_90), -1, getString(R.string.explain_90))
        saveDb(ans1, ans2, ans3, null, question)
        //cau91
        ans1 = Answer(361, getString(R.string.ans_91_1), 91)
        ans2 = Answer(362, getString(R.string.ans_91_2), 91)
        ans3 = Answer(363, getString(R.string.ans_91_3), 91, true)
        ans4 = Answer(364, getString(R.string.ans_91_4), 91)
        question = Question(91, typeQuestionLaw, true, getString(R.string.ques_91), -1, getString(R.string.explain_91))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau92
        ans1 = Answer(365, getString(R.string.ans_92_1), 92)
        ans2 = Answer(366, getString(R.string.ans_92_2), 92)
        ans3 = Answer(367, getString(R.string.ans_92_3), 92)
        ans4 = Answer(368, getString(R.string.ans_92_4), 92, true)
        question = Question(92, typeQuestionLaw, true, getString(R.string.ques_92), -1, getString(R.string.explain_92))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau93
        ans1 = Answer(369, getString(R.string.ans_93_1), 93)
        ans2 = Answer(370, getString(R.string.ans_93_2), 93)
        ans3 = Answer(371, getString(R.string.ans_93_3), 93)
        ans4 = Answer(372, getString(R.string.ans_93_4), 93, true)
        question = Question(93, typeQuestionLaw, true, getString(R.string.ques_93), -1, getString(R.string.explain_93))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau94
        ans1 = Answer(373, getString(R.string.ans_94_1), 94)
        ans2 = Answer(374, getString(R.string.ans_94_2), 94)
        ans3 = Answer(375, getString(R.string.ans_94_3), 94, true)
        question = Question(94, typeQuestionLaw, true, getString(R.string.ques_94), -1, getString(R.string.explain_94))
        saveDb(ans1, ans2, ans3, null, question)
        //cau95
        ans1 = Answer(377, getString(R.string.ans_95_1), 95)
        ans2 = Answer(378, getString(R.string.ans_95_2), 95)
        ans3 = Answer(379, getString(R.string.ans_95_3), 95, true)
        ans4 = Answer(380, getString(R.string.ans_95_4), 95)
        question = Question(95, typeQuestionLaw, true, getString(R.string.ques_95), -1, getString(R.string.explain_95))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau96
        ans1 = Answer(381, getString(R.string.ans_96_1), 96, true)
        ans2 = Answer(382, getString(R.string.ans_96_2), 96)
        ans3 = Answer(383, getString(R.string.ans_96_3), 96)
        question = Question(96, typeQuestionLaw, true, getString(R.string.ques_96), -1, getString(R.string.explain_96))
        saveDb(ans1, ans2, ans3, null, question)
        //cau97
        ans1 = Answer(385, getString(R.string.ans_97_1), 97, true)
        ans2 = Answer(386, getString(R.string.ans_97_2), 97)
        ans3 = Answer(387, getString(R.string.ans_97_3), 97)
        question = Question(97, typeQuestionLaw, false, getString(R.string.ques_97), -1, getString(R.string.explain_97))
        saveDb(ans1, ans2, ans3, null, question)
        //cau98
        ans1 = Answer(389, getString(R.string.ans_98_1), 98)
        ans2 = Answer(390, getString(R.string.ans_98_2), 98)
        ans3 = Answer(391, getString(R.string.ans_98_3), 98)
        ans4 = Answer(392, getString(R.string.ans_98_4), 98, true)
        question = Question(98, typeQuestionLaw, true, getString(R.string.ques_98), -1, getString(R.string.explain_98))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau99
        ans1 = Answer(393, getString(R.string.ans_99_1), 99, true)
        ans2 = Answer(394, getString(R.string.ans_99_2), 99)
        ans3 = Answer(395, getString(R.string.ans_99_3), 99)
        question = Question(99, typeQuestionLaw, true, getString(R.string.ques_99), -1, getString(R.string.explain_99))
        saveDb(ans1, ans2, ans3, null, question)
        //cau100
        ans1 = Answer(397, getString(R.string.ans_100_1), 100, true)
        ans2 = Answer(398, getString(R.string.ans_100_2), 100)
        ans3 = Answer(399, getString(R.string.ans_100_3), 100)
        question = Question(100, typeQuestionLaw, true, getString(R.string.ques_100), -1, getString(R.string.explain_100))
        saveDb(ans1, ans2, ans3, null, question)
        //cau101
        ans1 = Answer(401, getString(R.string.ans_101_1), 101, true)
        ans2 = Answer(402, getString(R.string.ans_101_2), 101)
        ans3 = Answer(403, getString(R.string.ans_101_3), 101)
        question = Question(101, typeQuestionNoticeBoard, true, getString(R.string.ques_101), R.drawable.img_101, getString(R.string.explain_101))
        saveDb(ans1, ans2, ans3, null, question)
        //cau102
        ans1 = Answer(405, getString(R.string.ans_102_1), 102, true)
        ans2 = Answer(406, getString(R.string.ans_102_2), 102)
        ans3 = Answer(407, getString(R.string.ans_102_3), 102)
        ans4 = Answer(408, getString(R.string.ans_102_4), 102)
        question = Question(102, typeQuestionNoticeBoard, true, getString(R.string.ques_102), R.drawable.img_102, getString(R.string.explain_102))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau103
        ans1 = Answer(409, getString(R.string.ans_103_1), 103)
        ans2 = Answer(410, getString(R.string.ans_103_2), 103, true)
        ans3 = Answer(411, getString(R.string.ans_103_3), 103)
        question = Question(103, typeQuestionNoticeBoard, true, getString(R.string.ques_103), R.drawable.img_103, getString(R.string.explain_103))
        saveDb(ans1, ans2, ans3, null, question)
        //cau104
        ans1 = Answer(413, getString(R.string.ans_104_1), 104)
        ans2 = Answer(414, getString(R.string.ans_104_2), 104, true)
        ans3 = Answer(415, getString(R.string.ans_104_3), 104)
        ans4 = Answer(416, getString(R.string.ans_104_4), 104)
        question = Question(104, typeQuestionNoticeBoard, true, getString(R.string.ques_104), R.drawable.img_104, getString(R.string.explain_104))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau105
        ans1 = Answer(417, getString(R.string.ans_105_1), 105)
        ans2 = Answer(418, getString(R.string.ans_105_2), 105)
        ans3 = Answer(419, getString(R.string.ans_105_3), 105)
        ans4 = Answer(420, getString(R.string.ans_105_4), 105, true)
        question = Question(105, typeQuestionNoticeBoard, true, getString(R.string.ques_105), R.drawable.img_105, getString(R.string.explain_105))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau106
        ans1 = Answer(421, getString(R.string.ans_106_1), 106)
        ans2 = Answer(422, getString(R.string.ans_106_2), 106)
        ans3 = Answer(423, getString(R.string.ans_106_3), 106, true)
        ans4 = Answer(424, getString(R.string.ans_106_4), 106)
        question = Question(106, typeQuestionNoticeBoard, true, getString(R.string.ques_106), R.drawable.img_106, getString(R.string.explain_106))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau107
        ans1 = Answer(425, getString(R.string.ans_107_1), 107, true)
        ans2 = Answer(426, getString(R.string.ans_107_2), 107)
        ans3 = Answer(427, getString(R.string.ans_107_3), 107)
        question = Question(107, typeQuestionNoticeBoard, true, getString(R.string.ques_107), R.drawable.img_107, getString(R.string.explain_107))
        saveDb(ans1, ans2, ans3, null, question)
        //cau108
        ans1 = Answer(429, getString(R.string.ans_108_1), 108)
        ans2 = Answer(430, getString(R.string.ans_108_2), 108, true)
        ans3 = Answer(431, getString(R.string.ans_108_3), 108)
        ans4 = Answer(432, getString(R.string.ans_108_4), 108)
        question = Question(108, typeQuestionNoticeBoard, true, getString(R.string.ques_108), R.drawable.img_108, getString(R.string.explain_108))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau109
        ans1 = Answer(433, getString(R.string.ans_109_1), 109)
        ans2 = Answer(434, getString(R.string.ans_109_2), 109)
        ans3 = Answer(435, getString(R.string.ans_109_3), 109, true)
        ans4 = Answer(436, getString(R.string.ans_109_4), 109)
        question = Question(109, typeQuestionNoticeBoard, true, getString(R.string.ques_109), R.drawable.img_109, getString(R.string.explain_109))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau110
        ans1 = Answer(437, getString(R.string.ans_110_1), 110, true)
        ans2 = Answer(438, getString(R.string.ans_110_2), 110)
        ans3 = Answer(439, getString(R.string.ans_110_3), 110)
        ans4 = Answer(440, getString(R.string.ans_110_4), 110)
        question = Question(110, typeQuestionNoticeBoard, true, getString(R.string.ques_110), R.drawable.img_110, getString(R.string.explain_110))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau111
        ans1 = Answer(441, getString(R.string.ans_111_1), 111, true)
        ans2 = Answer(442, getString(R.string.ans_111_2), 111)
        ans3 = Answer(443, getString(R.string.ans_111_3), 111)
        ans4 = Answer(444, getString(R.string.ans_111_4), 111)
        question = Question(111, typeQuestionNoticeBoard, true, getString(R.string.ques_111), R.drawable.img_111, getString(R.string.explain_111))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau112
        ans1 = Answer(445, getString(R.string.ans_112_1), 112)
        ans2 = Answer(446, getString(R.string.ans_112_2), 112)
        ans3 = Answer(447, getString(R.string.ans_112_3), 112, true)
        ans4 = Answer(448, getString(R.string.ans_112_4), 112)
        question = Question(112, typeQuestionNoticeBoard, true, getString(R.string.ques_112), R.drawable.img_112, getString(R.string.explain_112))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau113
        ans1 = Answer(449, getString(R.string.ans_113_1), 113)
        ans2 = Answer(450, getString(R.string.ans_113_2), 113, true)
        ans3 = Answer(451, getString(R.string.ans_113_3), 113)
        question = Question(113, typeQuestionNoticeBoard, true, getString(R.string.ques_113), R.drawable.img_113, getString(R.string.explain_113))
        saveDb(ans1, ans2, ans3, null, question)
        //cau114
        ans1 = Answer(453, getString(R.string.ans_114_1), 114)
        ans2 = Answer(454, getString(R.string.ans_114_2), 114)
        ans3 = Answer(455, getString(R.string.ans_114_3), 114, true)
        question = Question(114, typeQuestionNoticeBoard, true, getString(R.string.ques_114), R.drawable.img_114, getString(R.string.explain_114))
        saveDb(ans1, ans2, ans3, null, question)
        //cau115
        ans1 = Answer(457, getString(R.string.ans_115_1), 115, true)
        ans2 = Answer(458, getString(R.string.ans_115_2), 115)
        ans3 = Answer(459, getString(R.string.ans_115_3), 115)
        question = Question(115, typeQuestionNoticeBoard, true, getString(R.string.ques_115), R.drawable.img_115, getString(R.string.explain_115))
        saveDb(ans1, ans2, ans3, null, question)
        //cau116
        ans1 = Answer(461, getString(R.string.ans_116_1), 116)
        ans2 = Answer(462, getString(R.string.ans_116_2), 116, true)
        ans3 = Answer(463, getString(R.string.ans_116_3), 116)
        question = Question(116, typeQuestionNoticeBoard, true, getString(R.string.ques_116), R.drawable.img_116, getString(R.string.explain_116))
        saveDb(ans1, ans2, ans3, null, question)
        //cau117
        ans1 = Answer(465, getString(R.string.ans_117_1), 117)
        ans2 = Answer(466, getString(R.string.ans_117_2), 117, true)
        ans3 = Answer(467, getString(R.string.ans_117_3), 117)
        question = Question(117, typeQuestionNoticeBoard, true, getString(R.string.ques_117), R.drawable.img_117, getString(R.string.explain_117))
        saveDb(ans1, ans2, ans3, null, question)
        //cau118
        ans1 = Answer(469, getString(R.string.ans_118_1), 118)
        ans2 = Answer(470, getString(R.string.ans_118_2), 118, true)
        ans3 = Answer(471, getString(R.string.ans_118_3), 118)
        question = Question(118, typeQuestionNoticeBoard, true, getString(R.string.ques_118), R.drawable.img_118, getString(R.string.explain_118))
        saveDb(ans1, ans2, ans3, null, question)
        //cau119
        ans1 = Answer(473, getString(R.string.ans_119_1), 119)
        ans2 = Answer(474, getString(R.string.ans_119_2), 119, true)
        question = Question(119, typeQuestionNoticeBoard, true, getString(R.string.ques_119), R.drawable.img_119, getString(R.string.explain_119))
        saveDb(ans1, ans2, null, null, question)
        //cau120
        ans1 = Answer(477, getString(R.string.ans_120_1), 120)
        ans2 = Answer(478, getString(R.string.ans_120_2), 120, true)
        ans3 = Answer(479, getString(R.string.ans_120_3), 120)
        question = Question(120, typeQuestionNoticeBoard, true, getString(R.string.ques_120), R.drawable.img_120, getString(R.string.explain_120))
        saveDb(ans1, ans2, ans3, null, question)
        //cau121
        ans1 = Answer(481, getString(R.string.ans_121_1), 121)
        ans2 = Answer(482, getString(R.string.ans_121_2), 121, true)
        question = Question(121, typeQuestionNoticeBoard, true, getString(R.string.ques_121), R.drawable.img_121, getString(R.string.explain_121))
        saveDb(ans1, ans2, null, null, question)
        //cau122
        ans1 = Answer(485, getString(R.string.ans_122_1), 122)
        ans2 = Answer(486, getString(R.string.ans_122_2), 122)
        ans3 = Answer(487, getString(R.string.ans_122_3), 122, true)
        question = Question(122, typeQuestionNoticeBoard, true, getString(R.string.ques_122), R.drawable.img_122, getString(R.string.explain_122))
        saveDb(ans1, ans2, ans3, null, question)
        //cau123
        ans1 = Answer(489, getString(R.string.ans_123_1), 123, true)
        ans2 = Answer(490, getString(R.string.ans_123_2), 123)
        ans3 = Answer(491, getString(R.string.ans_123_3), 123)
        question = Question(123, typeQuestionNoticeBoard, true, getString(R.string.ques_123), R.drawable.img_123, getString(R.string.explain_123))
        saveDb(ans1, ans2, ans3, null, question)
        //cau124
        ans1 = Answer(493, getString(R.string.ans_124_1), 124)
        ans2 = Answer(494, getString(R.string.ans_124_2), 124)
        ans3 = Answer(495, getString(R.string.ans_124_3), 124, true)
        ans4 = Answer(496, getString(R.string.ans_124_4), 124)
        question = Question(124, typeQuestionNoticeBoard, true, getString(R.string.ques_124), R.drawable.img_124, getString(R.string.explain_124))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau125
        ans1 = Answer(497, getString(R.string.ans_125_1), 125)
        ans2 = Answer(498, getString(R.string.ans_125_2), 125, true)
        ans3 = Answer(499, getString(R.string.ans_125_3), 125)
        ans4 = Answer(500, getString(R.string.ans_125_4), 125)
        question = Question(125, typeQuestionNoticeBoard, true, getString(R.string.ques_125), R.drawable.img_125, getString(R.string.explain_125))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau126
        ans1 = Answer(501, getString(R.string.ans_126_1), 126, true)
        ans2 = Answer(502, getString(R.string.ans_126_2), 126)
        ans3 = Answer(503, getString(R.string.ans_126_3), 126)
        question = Question(126, typeQuestionNoticeBoard, true, getString(R.string.ques_126), R.drawable.img_126, getString(R.string.explain_126))
        saveDb(ans1, ans2, ans3, null, question)
        //cau127
        ans1 = Answer(505, getString(R.string.ans_127_1), 127)
        ans2 = Answer(506, getString(R.string.ans_127_2), 127, true)
        ans3 = Answer(507, getString(R.string.ans_127_3), 127)
        question = Question(127, typeQuestionNoticeBoard, true, getString(R.string.ques_127), R.drawable.img_127, getString(R.string.explain_127))
        saveDb(ans1, ans2, ans3, null, question)
        //cau128
        ans1 = Answer(509, getString(R.string.ans_128_1), 128, true)
        ans2 = Answer(510, getString(R.string.ans_128_2), 128)
        ans3 = Answer(511, getString(R.string.ans_128_3), 128)
        ans4 = Answer(512, getString(R.string.ans_128_4), 128)
        question = Question(128, typeQuestionNoticeBoard, true, getString(R.string.ques_128), R.drawable.img_128, getString(R.string.explain_128))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau129
        ans1 = Answer(513, getString(R.string.ans_129_1), 129, true)
        ans2 = Answer(514, getString(R.string.ans_129_2), 129)
        ans3 = Answer(515, getString(R.string.ans_129_3), 129)
        ans4 = Answer(516, getString(R.string.ans_129_4), 129)
        question = Question(129, typeQuestionNoticeBoard, true, getString(R.string.ques_129), R.drawable.img_129, getString(R.string.explain_129))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau130
        ans1 = Answer(517, getString(R.string.ans_130_1), 130, true)
        ans2 = Answer(518, getString(R.string.ans_130_2), 130)
        ans3 = Answer(519, getString(R.string.ans_130_3), 130)
        question = Question(130, typeQuestionNoticeBoard, true, getString(R.string.ques_130), R.drawable.img_130, getString(R.string.explain_130))
        saveDb(ans1, ans2, ans3, null, question)
        //cau131
        ans1 = Answer(521, getString(R.string.ans_131_1), 131)
        ans2 = Answer(522, getString(R.string.ans_131_2), 131, true)
        ans3 = Answer(523, getString(R.string.ans_131_3), 131)
        question = Question(131, typeQuestionNoticeBoard, true, getString(R.string.ques_131), R.drawable.img_131, getString(R.string.explain_131))
        saveDb(ans1, ans2, ans3, null, question)
        //cau132
        ans1 = Answer(525, getString(R.string.ans_132_1), 132)
        ans2 = Answer(526, getString(R.string.ans_132_2), 132)
        ans3 = Answer(527, getString(R.string.ans_132_3), 132, true)
        question = Question(132, typeQuestionNoticeBoard, true, getString(R.string.ques_132), R.drawable.img_132, getString(R.string.explain_132))
        saveDb(ans1, ans2, ans3, null, question)
        //cau133
        ans1 = Answer(529, getString(R.string.ans_133_1), 133)
        ans2 = Answer(530, getString(R.string.ans_133_2), 133, true)
        ans3 = Answer(531, getString(R.string.ans_133_3), 133)
        question = Question(133, typeQuestionNoticeBoard, true, getString(R.string.ques_133), R.drawable.img_133, getString(R.string.explain_133))
        saveDb(ans1, ans2, ans3, null, question)
        //cau134
        ans1 = Answer(533, getString(R.string.ans_134_1), 134, true)
        ans2 = Answer(534, getString(R.string.ans_134_2), 134)
        ans3 = Answer(535, getString(R.string.ans_134_3), 134)
        question = Question(134, typeQuestionNoticeBoard, true, getString(R.string.ques_134), R.drawable.img_134, getString(R.string.explain_134))
        saveDb(ans1, ans2, ans3, null, question)
        //cau135
        ans1 = Answer(537, getString(R.string.ans_135_1), 135)
        ans2 = Answer(538, getString(R.string.ans_135_2), 135)
        ans3 = Answer(539, getString(R.string.ans_135_3), 135)
        ans4 = Answer(540, getString(R.string.ans_135_4), 135, true)
        question = Question(135, typeQuestionNoticeBoard, true, getString(R.string.ques_135), R.drawable.img_135, getString(R.string.explain_135))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau136
        ans1 = Answer(541, getString(R.string.ans_136_1), 136)
        ans2 = Answer(542, getString(R.string.ans_136_2), 136)
        ans3 = Answer(543, getString(R.string.ans_136_3), 136, true)
        question = Question(136, typeQuestionNoticeBoard, true, getString(R.string.ques_136), R.drawable.img_136, getString(R.string.explain_136))
        saveDb(ans1, ans2, ans3, null, question)
        //cau137
        ans1 = Answer(545, getString(R.string.ans_137_1), 137, true)
        ans2 = Answer(546, getString(R.string.ans_137_2), 137)
        question = Question(137, typeQuestionNoticeBoard, true, getString(R.string.ques_137), R.drawable.img_137, getString(R.string.explain_137))
        saveDb(ans1, ans2, null, null, question)
        //cau138
        ans1 = Answer(549, getString(R.string.ans_138_1), 138)
        ans2 = Answer(550, getString(R.string.ans_138_2), 138, true)
        question = Question(138, typeQuestionNoticeBoard, true, getString(R.string.ques_138), R.drawable.img_138, getString(R.string.explain_138))
        saveDb(ans1, ans2, null, null, question)
        //cau139
        ans1 = Answer(553, getString(R.string.ans_139_1), 139)
        ans2 = Answer(554, getString(R.string.ans_139_2), 139, true)
        ans3 = Answer(555, getString(R.string.ans_139_3), 139)
        question = Question(139, typeQuestionNoticeBoard, true, getString(R.string.ques_139), R.drawable.img_139, getString(R.string.explain_139))
        saveDb(ans1, ans2, ans3, null, question)
        //cau140
        ans1 = Answer(557, getString(R.string.ans_140_1), 140)
        ans2 = Answer(558, getString(R.string.ans_140_2), 140, true)
        ans3 = Answer(559, getString(R.string.ans_140_3), 140)
        question = Question(140, typeQuestionNoticeBoard, true, getString(R.string.ques_140), R.drawable.img_140, getString(R.string.explain_140))
        saveDb(ans1, ans2, ans3, null, question)
        //cau141
        ans1 = Answer(561, getString(R.string.ans_141_1), 141, true)
        ans2 = Answer(562, getString(R.string.ans_141_2), 141)
        ans3 = Answer(563, getString(R.string.ans_141_3), 141)
        ans4 = Answer(564, getString(R.string.ans_141_4), 141)
        question = Question(141, typeQuestionNoticeBoard, true, getString(R.string.ques_141), R.drawable.img_141, getString(R.string.explain_141))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau142
        ans1 = Answer(565, getString(R.string.ans_142_1), 142)
        ans2 = Answer(566, getString(R.string.ans_142_2), 142)
        ans3 = Answer(567, getString(R.string.ans_142_3), 142, true)
        ans4 = Answer(568, getString(R.string.ans_142_4), 142)
        question = Question(142, typeQuestionNoticeBoard, true, getString(R.string.ques_142), R.drawable.img_142, getString(R.string.explain_142))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau143
        ans1 = Answer(569, getString(R.string.ans_143_1), 143, true)
        ans2 = Answer(570, getString(R.string.ans_143_2), 143)
        ans3 = Answer(571, getString(R.string.ans_143_3), 143)
        question = Question(143, typeQuestionNoticeBoard, true, getString(R.string.ques_143), R.drawable.img_143, getString(R.string.explain_143))
        saveDb(ans1, ans2, ans3, null, question)
        //cau144
        ans1 = Answer(573, getString(R.string.ans_144_1), 144)
        ans2 = Answer(574, getString(R.string.ans_144_2), 144)
        ans3 = Answer(575, getString(R.string.ans_144_3), 144, true)
        question = Question(144, typeQuestionNoticeBoard, true, getString(R.string.ques_144), R.drawable.img_144, getString(R.string.explain_144))
        saveDb(ans1, ans2, ans3, null, question)
        //cau145
        ans1 = Answer(577, getString(R.string.ans_145_1), 145, true)
        ans2 = Answer(578, getString(R.string.ans_145_2), 145)
        ans3 = Answer(579, getString(R.string.ans_145_3), 145)
        ans4 = Answer(580, getString(R.string.ans_145_4), 145)
        question = Question(145, typeQuestionNoticeBoard, true, getString(R.string.ques_145), R.drawable.img_145, getString(R.string.explain_145))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau146
        ans1 = Answer(581, getString(R.string.ans_146_1), 146)
        ans2 = Answer(582, getString(R.string.ans_146_2), 146)
        ans3 = Answer(583, getString(R.string.ans_146_3), 146)
        ans4 = Answer(584, getString(R.string.ans_146_4), 146, true)
        question = Question(146, typeQuestionNoticeBoard, true, getString(R.string.ques_146), R.drawable.img_146, getString(R.string.explain_146))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau147
        ans1 = Answer(585, getString(R.string.ans_147_1), 147)
        ans2 = Answer(586, getString(R.string.ans_147_2), 147)
        ans3 = Answer(587, getString(R.string.ans_147_3), 147, true)
        ans4 = Answer(588, getString(R.string.ans_147_4), 147)
        question = Question(147, typeQuestionNoticeBoard, true, getString(R.string.ques_147), R.drawable.img_147, getString(R.string.explain_147))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau148
        ans1 = Answer(589, getString(R.string.ans_148_1), 148)
        ans2 = Answer(590, getString(R.string.ans_148_2), 148)
        ans3 = Answer(591, getString(R.string.ans_148_3), 148)
        ans4 = Answer(592, getString(R.string.ans_148_4), 148, true)
        question = Question(148, typeQuestionNoticeBoard, true, getString(R.string.ques_148), R.drawable.img_148, getString(R.string.explain_148))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau149
        ans1 = Answer(593, getString(R.string.ans_149_1), 149)
        ans2 = Answer(594, getString(R.string.ans_149_2), 149, true)
        ans3 = Answer(595, getString(R.string.ans_149_3), 149)
        ans4 = Answer(596, getString(R.string.ans_149_4), 149)
        question = Question(149, typeQuestionNoticeBoard, true, getString(R.string.ques_149), R.drawable.img_149, getString(R.string.explain_149))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau150
        ans1 = Answer(597, getString(R.string.ans_150_1), 150)
        ans2 = Answer(598, getString(R.string.ans_150_2), 150, true)
        ans3 = Answer(599, getString(R.string.ans_150_3), 150)
        question = Question(150, typeQuestionNoticeBoard, true, getString(R.string.ques_150), R.drawable.img_150, getString(R.string.explain_150))
        saveDb(ans1, ans2, ans3, null, question)
        //cau151
        ans1 = Answer(601, getString(R.string.ans_151_1), 151)
        ans2 = Answer(602, getString(R.string.ans_151_2), 151, true)
        ans3 = Answer(603, getString(R.string.ans_151_3), 151)
        question = Question(151, typeQuestionNoticeBoard, true, getString(R.string.ques_151), R.drawable.img_151, getString(R.string.explain_151))
        saveDb(ans1, ans2, ans3, null, question)
        //cau152
        ans1 = Answer(605, getString(R.string.ans_152_1), 152)
        ans2 = Answer(606, getString(R.string.ans_152_2), 152)
        ans3 = Answer(607, getString(R.string.ans_152_3), 152, true)
        question = Question(152, typeQuestionNoticeBoard, true, getString(R.string.ques_152), R.drawable.img_152, getString(R.string.explain_152))
        saveDb(ans1, ans2, ans3, null, question)
        //cau153
        ans1 = Answer(609, getString(R.string.ans_153_1), 153, true)
        ans2 = Answer(610, getString(R.string.ans_153_2), 153)
        question = Question(153, typeQuestionNoticeBoard, true, getString(R.string.ques_153), R.drawable.img_153, getString(R.string.explain_153))
        saveDb(ans1, ans2, null, null, question)
        //cau154
        ans1 = Answer(613, getString(R.string.ans_154_1), 154, true)
        ans2 = Answer(614, getString(R.string.ans_154_2), 154)
        question = Question(154, typeQuestionNoticeBoard, true, getString(R.string.ques_154), R.drawable.img_154, getString(R.string.explain_154))
        saveDb(ans1, ans2, null, null, question)
        //cau155
        ans1 = Answer(617, getString(R.string.ans_155_1), 155)
        ans2 = Answer(618, getString(R.string.ans_155_2), 155, true)
        ans3 = Answer(619, getString(R.string.ans_155_3), 155)
        question = Question(155, typeQuestionNoticeBoard, true, getString(R.string.ques_155), R.drawable.img_155, getString(R.string.explain_155))
        saveDb(ans1, ans2, ans3, null, question)
        //cau156
        ans1 = Answer(621, getString(R.string.ans_156_1), 156, true)
        ans2 = Answer(622, getString(R.string.ans_156_2), 156)
        ans3 = Answer(623, getString(R.string.ans_156_3), 156)
        ans4 = Answer(624, getString(R.string.ans_156_4), 156)
        question = Question(156, typeQuestionNoticeBoard, true, getString(R.string.ques_156), R.drawable.img_156, getString(R.string.explain_156))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau157
        ans1 = Answer(625, getString(R.string.ans_157_1), 157)
        ans2 = Answer(626, getString(R.string.ans_157_2), 157, true)
        ans3 = Answer(627, getString(R.string.ans_157_3), 157)
        ans4 = Answer(628, getString(R.string.ans_157_4), 157)
        question = Question(157, typeQuestionNoticeBoard, true, getString(R.string.ques_157), R.drawable.img_157, getString(R.string.explain_157))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau158
        ans1 = Answer(629, getString(R.string.ans_158_1), 158)
        ans2 = Answer(630, getString(R.string.ans_158_2), 158)
        ans3 = Answer(631, getString(R.string.ans_158_3), 158, true)
        question = Question(158, typeQuestionNoticeBoard, true, getString(R.string.ques_158), R.drawable.img_158, getString(R.string.explain_158))
        saveDb(ans1, ans2, ans3, null, question)
        //cau159
        ans1 = Answer(633, getString(R.string.ans_159_1), 159)
        ans2 = Answer(634, getString(R.string.ans_159_2), 159)
        ans3 = Answer(635, getString(R.string.ans_159_3), 159, true)
        question = Question(159, typeQuestionNoticeBoard, true, getString(R.string.ques_159), R.drawable.img_159, getString(R.string.explain_159))
        saveDb(ans1, ans2, ans3, null, question)
        //cau160
        ans1 = Answer(637, getString(R.string.ans_160_1), 160)
        ans2 = Answer(638, getString(R.string.ans_160_2), 160)
        ans3 = Answer(639, getString(R.string.ans_160_3), 160)
        ans4 = Answer(640, getString(R.string.ans_160_4), 160, true)
        question = Question(160, typeQuestionNoticeBoard, true, getString(R.string.ques_160), R.drawable.img_160, getString(R.string.explain_160))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau161
        ans1 = Answer(641, getString(R.string.ans_161_1), 161)
        ans2 = Answer(642, getString(R.string.ans_161_2), 161, true)
        ans3 = Answer(643, getString(R.string.ans_161_3), 161)
        ans4 = Answer(644, getString(R.string.ans_161_4), 161)
        question = Question(161, typeQuestionNoticeBoard, true, getString(R.string.ques_161), R.drawable.img_161, getString(R.string.explain_161))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau162
        ans1 = Answer(645, getString(R.string.ans_162_1), 162)
        ans2 = Answer(646, getString(R.string.ans_162_2), 162)
        ans3 = Answer(647, getString(R.string.ans_162_3), 162)
        ans4 = Answer(648, getString(R.string.ans_162_4), 162, true)
        question = Question(162, typeQuestionNoticeBoard, true, getString(R.string.ques_162), R.drawable.img_162, getString(R.string.explain_162))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau163
        ans1 = Answer(649, getString(R.string.ans_163_1), 163, true)
        ans2 = Answer(650, getString(R.string.ans_163_2), 163)
        question = Question(163, typeQuestionNoticeBoard, true, getString(R.string.ques_163), R.drawable.img_163, getString(R.string.explain_163))
        saveDb(ans1, ans2, null, null, question)
        //cau164
        ans1 = Answer(653, getString(R.string.ans_164_1), 164)
        ans2 = Answer(654, getString(R.string.ans_164_2), 164)
        ans3 = Answer(655, getString(R.string.ans_164_3), 164)
        ans4 = Answer(656, getString(R.string.ans_164_4), 164, true)
        question = Question(164, typeQuestionNoticeBoard, true, getString(R.string.ques_164), R.drawable.img_164, getString(R.string.explain_164))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau165
        ans1 = Answer(657, getString(R.string.ans_165_1), 165, true)
        ans2 = Answer(658, getString(R.string.ans_165_2), 165)
        ans3 = Answer(659, getString(R.string.ans_165_3), 165)
        question = Question(165, typeQuestionNoticeBoard, true, getString(R.string.ques_165), R.drawable.img_165, getString(R.string.explain_165))
        saveDb(ans1, ans2, ans3, null, question)
        //cau166
        ans1 = Answer(661, getString(R.string.ans_166_1), 166)
        ans2 = Answer(662, getString(R.string.ans_166_2), 166)
        ans3 = Answer(663, getString(R.string.ans_166_3), 166, true)
        question = Question(166, typeQuestionSituations, true, getString(R.string.ques_166), R.drawable.img_166, getString(R.string.explain_166))
        saveDb(ans1, ans2, ans3, null, question)
        //cau167
        ans1 = Answer(665, getString(R.string.ans_167_1), 167)
        ans2 = Answer(666, getString(R.string.ans_167_2), 167)
        ans3 = Answer(667, getString(R.string.ans_167_3), 167, true)
        ans4 = Answer(668, getString(R.string.ans_167_4), 167)
        question = Question(167, typeQuestionSituations, true, getString(R.string.ques_167), R.drawable.img_167, getString(R.string.explain_167))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau168
        ans1 = Answer(669, getString(R.string.ans_168_1), 168)
        ans2 = Answer(670, getString(R.string.ans_168_2), 168, true)
        ans3 = Answer(671, getString(R.string.ans_168_3), 168)
        question = Question(168, typeQuestionSituations, true, getString(R.string.ques_168), R.drawable.img_168, getString(R.string.explain_168))
        saveDb(ans1, ans2, ans3, null, question)
        //cau169
        ans1 = Answer(673, getString(R.string.ans_169_1), 169)
        ans2 = Answer(674, getString(R.string.ans_169_2), 169, true)
        question = Question(169, typeQuestionSituations, true, getString(R.string.ques_169), R.drawable.img_169, getString(R.string.explain_169))
        saveDb(ans1, ans2, null, null, question)
        //cau170
        ans1 = Answer(677, getString(R.string.ans_170_1), 170, true)
        ans2 = Answer(678, getString(R.string.ans_170_2), 170)
        question = Question(170, typeQuestionSituations, true, getString(R.string.ques_170), R.drawable.img_170, getString(R.string.explain_170))
        saveDb(ans1, ans2, null, null, question)
        //cau171
        ans1 = Answer(681, getString(R.string.ans_171_1), 171)
        ans2 = Answer(682, getString(R.string.ans_171_2), 171, true)
        question = Question(171, typeQuestionSituations, true, getString(R.string.ques_171), R.drawable.img_171, getString(R.string.explain_171))
        saveDb(ans1, ans2, null, null, question)
        //cau172
        ans1 = Answer(685, getString(R.string.ans_172_1), 172)
        ans2 = Answer(686, getString(R.string.ans_172_2), 172, true)
        ans3 = Answer(687, getString(R.string.ans_172_3), 172)
        ans4 = Answer(688, getString(R.string.ans_172_4), 172)
        question = Question(172, typeQuestionSituations, true, getString(R.string.ques_172), R.drawable.img_172, getString(R.string.explain_172))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau173
        ans1 = Answer(689, getString(R.string.ans_173_1), 173)
        ans2 = Answer(690, getString(R.string.ans_173_2), 173, true)
        ans3 = Answer(691, getString(R.string.ans_173_3), 173)
        question = Question(173, typeQuestionSituations, true, getString(R.string.ques_173), R.drawable.img_173, getString(R.string.explain_173))
        saveDb(ans1, ans2, ans3, null, question)
        //cau174
        ans1 = Answer(693, getString(R.string.ans_174_1), 174, true)
        ans2 = Answer(694, getString(R.string.ans_174_2), 174)
        ans3 = Answer(695, getString(R.string.ans_174_3), 174)
        question = Question(174, typeQuestionSituations, true, getString(R.string.ques_174), R.drawable.img_174, getString(R.string.explain_174))
        saveDb(ans1, ans2, ans3, null, question)
        //cau175
        ans1 = Answer(697, getString(R.string.ans_175_1), 175)
        ans2 = Answer(698, getString(R.string.ans_175_2), 175, true)
        ans3 = Answer(699, getString(R.string.ans_175_3), 175)
        question = Question(175, typeQuestionSituations, true, getString(R.string.ques_175), R.drawable.img_175, getString(R.string.explain_175))
        saveDb(ans1, ans2, ans3, null, question)
        //cau176
        ans1 = Answer(701, getString(R.string.ans_176_1), 176)
        ans2 = Answer(702, getString(R.string.ans_176_2), 176, true)
        ans3 = Answer(703, getString(R.string.ans_176_3), 176)
        ans4 = Answer(704, getString(R.string.ans_176_4), 176)
        question = Question(176, typeQuestionSituations, true, getString(R.string.ques_176), R.drawable.img_176, getString(R.string.explain_176))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau177
        ans1 = Answer(705, getString(R.string.ans_177_1), 177)
        ans2 = Answer(706, getString(R.string.ans_177_2), 177)
        ans3 = Answer(707, getString(R.string.ans_177_3), 177, true)
        question = Question(177, typeQuestionSituations, true, getString(R.string.ques_177), R.drawable.img_177, getString(R.string.explain_177))
        saveDb(ans1, ans2, ans3, null, question)
        //cau178
        ans1 = Answer(709, getString(R.string.ans_178_1), 178)
        ans2 = Answer(710, getString(R.string.ans_178_2), 178, true)
        ans3 = Answer(711, getString(R.string.ans_178_3), 178)
        ans4 = Answer(712, getString(R.string.ans_178_4), 178)
        question = Question(178, typeQuestionSituations, true, getString(R.string.ques_178), R.drawable.img_178, getString(R.string.explain_178))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau179
        ans1 = Answer(713, getString(R.string.ans_179_1), 179, true)
        ans2 = Answer(714, getString(R.string.ans_179_2), 179)
        question = Question(179, typeQuestionSituations, true, getString(R.string.ques_179), R.drawable.img_179, getString(R.string.explain_179))
        saveDb(ans1, ans2, null, null, question)
        //cau180
        ans1 = Answer(717, getString(R.string.ans_180_1), 180)
        ans2 = Answer(718, getString(R.string.ans_180_2), 180)
        ans3 = Answer(719, getString(R.string.ans_180_3), 180, true)
        question = Question(180, typeQuestionSituations, true, getString(R.string.ques_180), R.drawable.img_180, getString(R.string.explain_180))
        saveDb(ans1, ans2, ans3, null, question)
        //cau181
        ans1 = Answer(721, getString(R.string.ans_181_1), 181, true)
        ans2 = Answer(722, getString(R.string.ans_181_2), 181)
        ans3 = Answer(723, getString(R.string.ans_181_3), 181)
        ans4 = Answer(724, getString(R.string.ans_181_4), 181)
        question = Question(181, typeQuestionSituations, true, getString(R.string.ques_181), R.drawable.img_181, getString(R.string.explain_181))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau182
        ans1 = Answer(725, getString(R.string.ans_182_1), 182, true)
        ans2 = Answer(726, getString(R.string.ans_182_2), 182)
        ans3 = Answer(727, getString(R.string.ans_182_3), 182)
        ans4 = Answer(728, getString(R.string.ans_182_4), 182)
        question = Question(182, typeQuestionSituations, true, getString(R.string.ques_182), R.drawable.img_182, getString(R.string.explain_182))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau183
        ans1 = Answer(729, getString(R.string.ans_183_1), 183)
        ans2 = Answer(730, getString(R.string.ans_183_2), 183)
        ans3 = Answer(731, getString(R.string.ans_183_3), 183, true)
        ans4 = Answer(732, getString(R.string.ans_183_4), 183)
        question = Question(183, typeQuestionSituations, true, getString(R.string.ques_183), R.drawable.img_183, getString(R.string.explain_183))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau184
        ans1 = Answer(733, getString(R.string.ans_184_1), 184)
        ans2 = Answer(734, getString(R.string.ans_184_2), 184)
        ans3 = Answer(735, getString(R.string.ans_184_3), 184, true)
        ans4 = Answer(736, getString(R.string.ans_184_4), 184)
        question = Question(184, typeQuestionSituations, true, getString(R.string.ques_184), R.drawable.img_184, getString(R.string.explain_184))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau185
        ans1 = Answer(737, getString(R.string.ans_185_1), 185)
        ans2 = Answer(738, getString(R.string.ans_185_2), 185)
        ans3 = Answer(739, getString(R.string.ans_185_3), 185)
        ans4 = Answer(740, getString(R.string.ans_185_4), 185, true)
        question = Question(185, typeQuestionSituations, true, getString(R.string.ques_185), R.drawable.img_185, getString(R.string.explain_185))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau186
        ans1 = Answer(741, getString(R.string.ans_186_1), 186)
        ans2 = Answer(742, getString(R.string.ans_186_2), 186)
        ans3 = Answer(743, getString(R.string.ans_186_3), 186, true)
        ans4 = Answer(744, getString(R.string.ans_186_4), 186)
        question = Question(186, typeQuestionSituations, true, getString(R.string.ques_186), R.drawable.img_186, getString(R.string.explain_186))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau187
        ans1 = Answer(745, getString(R.string.ans_187_1), 187, true)
        ans2 = Answer(746, getString(R.string.ans_187_2), 187)
        ans3 = Answer(747, getString(R.string.ans_187_3), 187)
        question = Question(187, typeQuestionSituations, true, getString(R.string.ques_187), R.drawable.img_187, getString(R.string.explain_187))
        saveDb(ans1, ans2, ans3, null, question)
        //cau188
        ans1 = Answer(749, getString(R.string.ans_188_1), 188)
        ans2 = Answer(750, getString(R.string.ans_188_2), 188)
        ans3 = Answer(751, getString(R.string.ans_188_3), 188, true)
        ans4 = Answer(752, getString(R.string.ans_188_4), 188)
        question = Question(188, typeQuestionSituations, true, getString(R.string.ques_188), R.drawable.img_188, getString(R.string.explain_188))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau189
        ans1 = Answer(753, getString(R.string.ans_189_1), 189)
        ans2 = Answer(754, getString(R.string.ans_189_2), 189, true)
        question = Question(189, typeQuestionSituations, true, getString(R.string.ques_189), R.drawable.img_189, getString(R.string.explain_189))
        saveDb(ans1, ans2, null, null, question)
        //cau190
        ans1 = Answer(757, getString(R.string.ans_190_1), 190)
        ans2 = Answer(758, getString(R.string.ans_190_2), 190, true)
        question = Question(190, typeQuestionSituations, true, getString(R.string.ques_190), R.drawable.img_190, getString(R.string.explain_190))
        saveDb(ans1, ans2, null, null, question)
        //cau191
        ans1 = Answer(761, getString(R.string.ans_191_1), 191)
        ans2 = Answer(762, getString(R.string.ans_191_2), 191)
        ans3 = Answer(763, getString(R.string.ans_191_3), 191)
        ans4 = Answer(764, getString(R.string.ans_191_4), 191, true)
        question = Question(191, typeQuestionSituations, true, getString(R.string.ques_191), R.drawable.img_191, getString(R.string.explain_191))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau192
        ans1 = Answer(765, getString(R.string.ans_192_1), 192)
        ans2 = Answer(766, getString(R.string.ans_192_2), 192, true)
        question = Question(192, typeQuestionSituations, true, getString(R.string.ques_192), R.drawable.img_192, getString(R.string.explain_192))
        saveDb(ans1, ans2, null, null, question)
        //cau193
        ans1 = Answer(769, getString(R.string.ans_193_1), 193, true)
        ans2 = Answer(770, getString(R.string.ans_193_2), 193)
        ans3 = Answer(771, getString(R.string.ans_193_3), 193)
        ans4 = Answer(772, getString(R.string.ans_193_4), 193)
        question = Question(193, typeQuestionSituations, true, getString(R.string.ques_193), R.drawable.img_193, getString(R.string.explain_193))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau194
        ans1 = Answer(773, getString(R.string.ans_194_1), 194)
        ans2 = Answer(774, getString(R.string.ans_194_2), 194)
        ans3 = Answer(775, getString(R.string.ans_194_3), 194, true)
        ans4 = Answer(776, getString(R.string.ans_194_4), 194)
        question = Question(194, typeQuestionSituations, true, getString(R.string.ques_194), R.drawable.img_194, getString(R.string.explain_194))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau195
        ans1 = Answer(777, getString(R.string.ans_195_1), 195)
        ans2 = Answer(778, getString(R.string.ans_195_2), 195)
        ans3 = Answer(779, getString(R.string.ans_195_3), 195, true)
        ans4 = Answer(780, getString(R.string.ans_195_4), 195)
        question = Question(195, typeQuestionSituations, true, getString(R.string.ques_195), R.drawable.img_195, getString(R.string.explain_195))
        saveDb(ans1, ans2, ans3, ans4, question)
        //cau196
        ans1 = Answer(781, getString(R.string.ans_196_1), 196)
        ans2 = Answer(782, getString(R.string.ans_196_2), 196, true)
        ans3 = Answer(783, getString(R.string.ans_196_3), 196)
        question = Question(196, typeQuestionSituations, true, getString(R.string.ques_196), R.drawable.img_196, getString(R.string.explain_196))
        saveDb(ans1, ans2, ans3, null, question)
        //cau197
        ans1 = Answer(785, getString(R.string.ans_197_1), 197)
        ans2 = Answer(786, getString(R.string.ans_197_2), 197, true)
        ans3 = Answer(787, getString(R.string.ans_197_3), 197)
        question = Question(197, typeQuestionSituations, true, getString(R.string.ques_197), R.drawable.img_197, getString(R.string.explain_197))
        saveDb(ans1, ans2, ans3, null, question)
        //cau198
        ans1 = Answer(789, getString(R.string.ans_198_1), 198, true)
        ans2 = Answer(790, getString(R.string.ans_198_2), 198)
        ans3 = Answer(791, getString(R.string.ans_198_3), 198)
        question = Question(198, typeQuestionSituations, true, getString(R.string.ques_198), R.drawable.img_198, getString(R.string.explain_198))
        saveDb(ans1, ans2, ans3, null, question)
        //cau199
        ans1 = Answer(793, getString(R.string.ans_199_1), 199)
        ans2 = Answer(794, getString(R.string.ans_199_2), 199, true)
        ans3 = Answer(795, getString(R.string.ans_199_3), 199)
        question = Question(199, typeQuestionSituations, true, getString(R.string.ques_199), R.drawable.img_199, getString(R.string.explain_199))
        saveDb(ans1, ans2, ans3, null, question)
        //cau200
        ans1 = Answer(797, getString(R.string.ans_200_1), 200)
        ans2 = Answer(798, getString(R.string.ans_200_2), 200, true)
        ans3 = Answer(799, getString(R.string.ans_200_3), 200)
        question = Question(200, typeQuestionSituations, true, getString(R.string.ques_200), R.drawable.img_200, getString(R.string.explain_200))
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
            editor?.apply()
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
            if (!it.mIsNotParalysisPoint) {
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
                this.finish()
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
            while (arrLaw.contains(ques) || arrParalysisPoint.contains(ques) || !ques.mIsNotParalysisPoint) {
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
            sharedPreferences = getSharedPreferences("countExam", MODE_PRIVATE)
            val editor = sharedPreferences?.edit()
            val countExam = sharedPreferences?.getInt(COUNT_EXAM, 1)
            var exam = Exam(countExam!!, false)
            appDatabase?.appDao()?.saveDataExam(exam)
            editor?.putInt(COUNT_EXAM, countExam + 1)
            editor?.apply()
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
        var title = "Bạn có chắc chắn muốn vào thi ?"
        if (mItem.mIsFinished) {
            title = "Bạn có chắc chắn muốn vào xem ?"
        }
        mDialog.tv_TitleOfCustomDialogConfirm.text = title
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
            bundle.putSerializable("EXAM", mItem)
            intent.putExtras(bundle)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            this.finish()
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