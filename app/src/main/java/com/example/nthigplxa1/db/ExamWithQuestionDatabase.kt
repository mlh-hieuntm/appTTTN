package com.example.nthigplxa1.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nthigplxa1.db.DAO.ExamWithQuestionDAO
import com.example.nthigplxa1.model.ExamWithQuestion

@Database(entities = [(ExamWithQuestion::class)], version = 4)
abstract class ExamWithQuestionDatabase : RoomDatabase() {
    abstract fun examWithQuestionDao(): ExamWithQuestionDAO
}