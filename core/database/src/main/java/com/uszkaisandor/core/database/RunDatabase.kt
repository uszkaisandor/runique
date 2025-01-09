package com.uszkaisandor.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.uszkaisandor.core.database.dao.RunDao
import com.uszkaisandor.core.database.dao.RunPendingSyncDao
import com.uszkaisandor.core.database.entity.RunEntity
import com.uszkaisandor.core.database.entity.RunPendingSyncEntity

@Database(
    entities = [RunEntity::class, RunPendingSyncEntity::class],
    version = 1
)
abstract class RunDatabase : RoomDatabase() {

    abstract val runDao: RunDao
    abstract val runPendingSyncDao: RunPendingSyncDao

}