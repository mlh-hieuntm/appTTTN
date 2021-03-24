package com.example.nthigplxa1.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Answer {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Ans_Id")
    var mID: Int = 0

    @ColumnInfo(name = "Ans_Content")
    var mContent: String = ""

    @ColumnInfo(name = "Ans_QuesID")
    var mQuestionID: Int = -1
    constructor()
    constructor(ansId: Int, content: String, quesId: Int) {
        this.mID = ansId
        this.mContent = content
        this.mQuestionID = quesId
    }

}