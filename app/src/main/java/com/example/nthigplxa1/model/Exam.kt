package com.example.nthigplxa1.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity
data class Exam (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Exam_Id")
    var mID: Int = 0,

    @ColumnInfo(name = "Exam_Status")
    var mIsFinished: Boolean = false

) : Serializable