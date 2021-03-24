package com.example.nthigplxa1.db.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.nthigplxa1.model.Question

@Dao
interface QuestionDAO {
    @Insert()
    fun saveData(data: Question)

    @Query("select * from Question")
    fun readAllData(): List<Question>

    @Query("select * from Question where Ques_Id = :id ")
    fun readDataWithID(id: Int): Question
}