package com.example.thenewmoviedbcompose.storage

import androidx.room.TypeConverter


class IntListConverter {
    @TypeConverter
    fun fromIntList(intList: List<Int>): String {
        return intList.joinToString(",")
    }

    @TypeConverter
    fun toIntList(intListString: String): List<Int> {
        return intListString.split(",").map { it.toInt() }
    }
}