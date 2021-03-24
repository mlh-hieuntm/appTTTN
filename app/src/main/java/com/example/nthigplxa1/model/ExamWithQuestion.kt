package com.example.nthigplxa1.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class ExamWithQuestion (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "EwQ_Id")
    var mID: Int = 0,

    @ColumnInfo(name = "ExamWithQuestion_idAnsSelected")
    var mIdAnsSelected: Int = -1,

    @ColumnInfo(name = "ExamWithQuestion_QuesId")
    var mQuesId: Int = -1,

    @ColumnInfo(name = "ExamWithQuestion_ExamId")
    var mExamId: Int = -1

) : Serializable