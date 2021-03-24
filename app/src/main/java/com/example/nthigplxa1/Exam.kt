package com.example.nthigplxa1

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
}