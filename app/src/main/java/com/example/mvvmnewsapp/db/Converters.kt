package com.example.mvvmnewsapp.ui.db

import androidx.room.TypeConverter
import com.example.mvvmnewsapp.ui.model.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}