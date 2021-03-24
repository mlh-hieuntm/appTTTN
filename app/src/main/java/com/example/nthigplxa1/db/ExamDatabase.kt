package com.example.nthigplxa1.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nthigplxa1.model.Exam
import com.example.nthigplxa1.db.DAO.ExamDAO

@Database(entities = [(Exam::class)], version = 3)
abstract class ExamDatabase : RoomDatabase() {
    abstract fun examDao(): ExamDAO
}