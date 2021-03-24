package com.example.nthigplxa1

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(Answer::class)], version = 1)
abstract class AnswerDatabase : RoomDatabase() {
    abstract fun answerDao(): AnswerDAO
}