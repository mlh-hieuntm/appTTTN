package com.example.nthigplxa1.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nthigplxa1.model.Question
import com.example.nthigplxa1.db.DAO.QuestionDAO

@Database(entities = [(Question::class)], version = 2)
abstract class QuestionDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDAO
}