package com.example.nthigplxa1.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nthigplxa1.model.Answer
import com.example.nthigplxa1.db.DAO.AnswerDAO

@Database(entities = [(Answer::class)], version = 1)
abstract class AnswerDatabase : RoomDatabase() {
    abstract fun answerDao(): AnswerDAO
}