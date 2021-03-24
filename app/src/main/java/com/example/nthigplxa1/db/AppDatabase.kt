package com.example.nthigplxa1.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.nthigplxa1.db.DAO.AppDAO
import com.example.nthigplxa1.model.Answer
import com.example.nthigplxa1.model.Exam
import com.example.nthigplxa1.model.ExamWithQuestion
import com.example.nthigplxa1.model.Question

@Database(
    entities = [ExamWithQuestion::class, Answer::class, Question::class, Exam::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDAO
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}