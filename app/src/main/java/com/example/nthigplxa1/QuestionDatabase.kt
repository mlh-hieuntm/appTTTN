package com.example.nthigplxa1

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(Question::class)], version = 1)
abstract class QuestionDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDAO
}