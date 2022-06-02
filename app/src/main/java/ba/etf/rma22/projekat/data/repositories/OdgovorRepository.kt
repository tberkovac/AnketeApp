package ba.etf.rma22.projekat.data.repositories

import android.util.Log
import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.data.models.Odgovor
import ba.etf.rma22.projekat.data.models.OdgovorResponse
import ba.etf.rma22.projekat.data.models.SendOdgovor
import java.lang.Exception

object OdgovorRepository {

    suspend fun getOdgovoriAnketa(idAnkete:Int):List<OdgovorResponse> {
        val pokusajRjesavanja = TakeAnketaRepository.getPoceteAnkete().find { it.anketumId == idAnkete }
        val listaUnesenihOdgovora = ApiConfig.retrofit.getOdgovoriAnketa(AccountRepository.getHash(), pokusajRjesavanja!!.id)
        Log.v("Odgovori su", listaUnesenihOdgovora.toString())
        return listaUnesenihOdgovora
    }

    suspend fun postaviOdgovorAnketa(pokusajRjesavanja:AnketaTaken,idPitanje:Int,indexOdgovora:Int) : Int {
        try{
            val prethodniProgres = pokusajRjesavanja.progres
            val buduciProgres =  obracunajBuduciProgresZaAnketu(pokusajRjesavanja.anketumId)
            val response =  ApiConfig.retrofit.postaviOdgovorAnketa(AccountRepository.getHash(),pokusajRjesavanja.id,
                SendOdgovor(indexOdgovora,idPitanje,buduciProgres))
            if(response.code() == 200)
                return buduciProgres
            return prethodniProgres
        }catch (exception : Exception){
            return -1
        }
    }

     suspend fun obracunajProgresZaAnketu(idAnekete: Int) : Int{
        val odgovoriNaAnketiOdgovoreni = getOdgovoriAnketa(idAnekete).size
        val brojPitanjaAnkete = PitanjeAnketaRepository.getPitanja(idAnekete).size
        return ((odgovoriNaAnketiOdgovoreni.toDouble()/brojPitanjaAnkete)*100).toInt()
     }



    suspend fun obracunajBuduciProgresZaAnketu(idAnekete: Int) : Int{
        var brojOdgovorenihPitanja = getOdgovoriAnketa(idAnekete).size
        val brojPitanjaAnkete = PitanjeAnketaRepository.getPitanja(idAnekete).size

        val jeLiSveOdgovoreno = brojOdgovorenihPitanja == brojPitanjaAnkete

        if(!jeLiSveOdgovoreno)
            brojOdgovorenihPitanja += 1
        return ((brojOdgovorenihPitanja.toDouble()/brojPitanjaAnkete)*100).toInt()
    }
}