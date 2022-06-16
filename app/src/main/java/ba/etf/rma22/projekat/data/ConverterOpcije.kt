package ba.etf.rma22.projekat.data

import androidx.room.TypeConverter

object ConverterOpcije {
    @TypeConverter
    fun zaSpasitUBazuOpcije(opcije: List<String>): String {
        var recenica = ""
        for (s in opcije) {
            recenica += s
            recenica += ","
        }
        recenica = recenica.dropLast(1)
        return recenica
    }

    @TypeConverter
    fun izBazeDobavitOpcije(recenica: String): List<String> {
        return recenica.split(",")
    }
}