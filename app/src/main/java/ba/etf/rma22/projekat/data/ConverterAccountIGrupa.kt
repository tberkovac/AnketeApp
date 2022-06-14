package ba.etf.rma22.projekat.data

import androidx.room.TypeConverter
import ba.etf.rma22.projekat.data.models.AccountIGrupa
import ba.etf.rma22.projekat.data.models.AnketaiGrupe2

object ConverterAccountIGrupa {
    @TypeConverter
    fun zaSpasitUBazuAccountIGrupa(accountIGrupa: AccountIGrupa?): String? {
        return if(accountIGrupa == null) null
        else
            accountIGrupa.AccountId.toString() + "," + accountIGrupa.GrupaId.toString()
    }

    @TypeConverter
    fun izBazeDobavitAccountIGrupa(recenica: String?): AccountIGrupa? {
        if(recenica == null) return null
        else {
            var lista = recenica.split(",")
            var x = lista[0].toInt()
            var y = lista[1].toInt()
            return AccountIGrupa(x, y)
        }
    }
}