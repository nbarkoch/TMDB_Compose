package com.example.thenewmoviedbcompose.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.thenewmoviedbcompose.model.Movie

@Database(
    entities = [Movie::class],
    version = 1
)
@TypeConverters(IntListConverter::class)
abstract class FavoriteMovieDatabase: RoomDatabase() {

    abstract val dao: FavoriteMovieDao

}