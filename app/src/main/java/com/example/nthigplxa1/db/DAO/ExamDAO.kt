package com.example.nthigplxa1.db.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.nthigplxa1.model.Exam

@Dao
interface ExamDAO {
    @Insert()
    fun saveData(data: Exam)

    @Query("select * from Exam")
    fun readAllData(): List<Exam>

    @Query("select * from Exam where Exam_Id = :id ")
    fun readDataWithID(id: Int): Exam
}