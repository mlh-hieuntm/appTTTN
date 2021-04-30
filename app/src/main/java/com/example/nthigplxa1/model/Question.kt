package com.example.nthigplxa1.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Question (
//câu hỏi: mã câu hỏi, mã đề, loại câu hỏi, liệt hay không, đáp án đã chọn, mã câu trả lời đúng, nội dung câu hỏi

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Ques_Id")
    var mID: Int = 0,

    @ColumnInfo(name = "Ques_Type")
    var mTypeQuestion: String = "",

    @ColumnInfo(name = "Ques_IsNotParalysisPoint")
    var mIsNotParalysisPoint: Boolean = true,

    @ColumnInfo(name = "Ques_Content")
    var mContent: String = "",

    @ColumnInfo(name = "Ques_Content_Img")
    var mContentImg: Int = -1,

    @ColumnInfo(name = "Ques_Explain")
    var mExplain: String = ""

)