package ba.etf.rma22.projekat.data

import androidx.room.TypeConverter
import ba.etf.rma22.projekat.data.models.AnketaiGrupe2

object ConverterAnketaIGrupe {
      @TypeConverter
      fun zaSpasitUBazu(anketaIGrupe: AnketaiGrupe2?): String? {
          if(anketaIGrupe == null) return null
          return anketaIGrupe.AnketumId.toString() + "," + anketaIGrupe.GrupaId.toString()
      }

      @TypeConverter
      fun izBazeDobavit(recenica: String?): AnketaiGrupe2? {
          if(recenica == null) return null
          var lista = recenica.split(",")
          var x = lista[0].toInt()
          var y = lista[1].toInt()
          return AnketaiGrupe2(x,y)
      }
}