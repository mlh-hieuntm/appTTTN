package com.example.nthigplxa1.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class Exam {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Exam_Id")
    var mID: Int = 0

    @ColumnInfo(name = "Exam_Status")
    var mIsFinished: Boolean = false

    @ColumnInfo(name = "Exam_Time_Remaining")
    var mTimeRemaining: Int = 1140

    @ColumnInfo(name = "Exam_Name")
    var mName: String = ""
    constructor()
    constructor(id: Int, isFinish: Boolean, timeRemaining: Int, name: String) {
        this.mID = id
        this.mName = name
        this.mIsFinished = isFinish
        this.mTimeRemaining = timeRemaining
    }
}