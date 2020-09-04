package com.messiasjunior.codewarsv2.util.typeconverter

import androidx.room.TypeConverter

class ListStringTypeConverter {
    @TypeConverter
    fun listStringToString(list: List<String>): String {
        return list.joinToString(separator = SEPARATOR)
    }

    @TypeConverter
    fun stringToListString(str: String): List<String> {
        return str.split(SEPARATOR)
    }

    companion object {
        private const val SEPARATOR = "|"
    }
}
