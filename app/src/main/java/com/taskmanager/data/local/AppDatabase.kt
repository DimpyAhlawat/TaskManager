package com.taskmanager.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.taskmanager.domain.model.Priority

@Database(entities = [TaskEntity::class], version = 1, exportSchema = true)
@TypeConverters(PriorityConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}

class PriorityConverter {
    @TypeConverter fun fromPriority(priority: Priority): String = priority.name
    @TypeConverter fun toPriority(value: String): Priority = Priority.valueOf(value)
}
