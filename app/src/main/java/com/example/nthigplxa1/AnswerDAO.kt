package com.example.nthigplxa1

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AnswerDAO {
    @Insert()
    fun saveData(data: Answer)

    @Query("select * from Answer")
    fun readAllData(): List<Answer>

    @Query("select * from Answer where Ans_Id = :id ")
    fun readDataWithID(id: Int): Answer
}