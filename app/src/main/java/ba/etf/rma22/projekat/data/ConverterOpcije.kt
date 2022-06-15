package ba.etf.rma22.projekat.data

import android.util.Log
import androidx.room.TypeConverter
import ba.etf.rma22.projekat.data.models.AnketaiGrupe2

object ConverterOpcije {
    @TypeConverter
    fun zaSpasitUBazuOpcije(opcije: List<String>): String {
        Log.v("OPCIJESU", opcije.toString())
        var recenica = ""
      //  opcije.forEach { recenica = recenica.plus(it).plus(",") }
        for (s in opcije) {
            recenica = recenica + s
            recenica += ","
        }

        recenica = recenica.dropLast(1)

        return recenica
    }

    @TypeConverter
    fun izBazeDobavitOpcije(recenica: String): List<String> {
        var lista = recenica.split(",")
        return lista
    }
}