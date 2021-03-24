package com.example.nthigplxa1.db.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.nthigplxa1.model.ExamWithQuestion

@Dao
interface ExamWithQuestionDAO {
    @Insert()
    fun saveData(data: ExamWithQuestion)

//    @Query("select * from ExamWithQuestion")
//    fun readAllData(): ArrayList<ExamWithQuestion>
//
//    @Query("select * from ExamWithQuestion where ExamWithQuestion_QuesId = :qid and ExamWithQuestion_ExamId = :examId")
//    fun readDataWithID(qid: Int, examId: Int): ArrayList<ExamWithQuestion>
}