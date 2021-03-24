package com.example.nthigplxa1.db.DAO

import androidx.room.*
import com.example.nthigplxa1.model.Answer
import com.example.nthigplxa1.model.Exam
import com.example.nthigplxa1.model.ExamWithQuestion
import com.example.nthigplxa1.model.Question

@Dao
interface AppDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveDataEwQ(data: ExamWithQuestion)

    @Query("select * from ExamWithQuestion")
    fun readAllData(): List<ExamWithQuestion>

    @Update
    fun updateEwQ(data: ExamWithQuestion)

    @Query("select * from ExamWithQuestion where ExamWithQuestion_QuesId = :qid and ExamWithQuestion_ExamId = :examId")
    fun readDataWithID(qid: Int, examId: Int): List<ExamWithQuestion>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveDataAnswer(data: Answer)

    @Query("select * from Answer")
    fun readAllDataAnswer(): List<Answer>

    @Query("select * from Answer where Ans_Id = :id  LIMIT 1")
    fun readDataWithIDAnswer(id: Int): Answer

    @Query("select * from Answer where Ans_QuesID = :id")
    fun readDataWithIDQues(id: Int): List<Answer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveDataExam(data: Exam)

    @Query("select * from Exam")
    fun readAllDataExam(): List<Exam>

    @Delete
    fun deleteExam(exam: Exam)

    @Query("select * from Exam where Exam_Id = :id  LIMIT 1")
    fun readDataWithIDExam(id: Int): Exam

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveDataQuestion(data: Question)

    @Query("select * from Question")
    fun readAllDataQuestion(): List<Question>

    @Query("select * from Question where Ques_Id = :id LIMIT 1 ")
    fun readDataWithIDQuestion(id: Int): Question
}