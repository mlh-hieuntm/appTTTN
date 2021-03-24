package com.example.nthigplxa1.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ExamWithQuestion {
    @PrimaryKey(autoGenerate = true)
    var mID: Int = 0

    @ColumnInfo(name = "ExamWithQuestion_idAnsSelected")
    var mIdAnsSelected: Int = -1

    @ColumnInfo(name = "ExamWithQuestion_QuesId")
    var mQuesId: Int = -1

    @ColumnInfo(name = "ExamWithQuestion_ExamId")
    var mExamId: Int = -1

    constructor()
    constructor(id: Int, idAnsSelected: Int, quesId: Int, examId: Int) {
        this.mID = id
        this.mIdAnsSelected = idAnsSelected
        this.mQuesId = quesId
        this.mExamId = examId
    }
}