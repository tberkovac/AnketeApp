package ba.etf.rma22.projekat.data

import androidx.room.TypeConverter
import ba.etf.rma22.projekat.data.models.AnketaiGrupe2
import ba.etf.rma22.projekat.data.models.PitanjeAnketa

object ConvertPitanjeAnketa {
    @TypeConverter
    fun zaSpasitUBazuPitanjeAnketa(pitanjeAnketa: PitanjeAnketa): String {
        return pitanjeAnketa.AnketumId.toString() + "," + pitanjeAnketa.PitanjeId.toString()
    }

    @TypeConverter
    fun izBazeDobavitPitanjeAnketa(recenica: String): PitanjeAnketa {
        var lista = recenica.split(",")
        var x = lista[0].toInt()
        var y = lista[1].toInt()
        return PitanjeAnketa(x,y)
    }
}