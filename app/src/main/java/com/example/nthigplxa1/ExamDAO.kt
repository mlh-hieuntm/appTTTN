package com.example.nthigplxa1

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExamDAO {
    @Insert()
    fun saveData(data: Exam)

    @Query("select * from Exam")
    fun readAllData(): List<Exam>

    @Query("select * from Exam where Exam_Id = :id ")
    fun readDataWithID(id: Int): Exam
}