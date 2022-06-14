package ba.etf.rma22.projekat.data

import androidx.room.TypeConverter
import ba.etf.rma22.projekat.data.models.AnketaiGrupe2

object ConverterOpcije {
    @TypeConverter
    fun zaSpasitUBazuOpcije(opcije: List<String>): String {
        var recenica = ""
        opcije.forEach { recenica.plus(it).plus(",") }
        return recenica
    }

    @TypeConverter
    fun izBazeDobavitOpcije(recenica: String): List<String> {
        var lista = recenica.split(",")
        return lista
    }
}