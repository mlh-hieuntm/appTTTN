package com.example.nthigplxa1

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(Exam::class)], version = 1)
abstract class ExamDatabase : RoomDatabase() {
    abstract fun examDao(): ExamDAO
}